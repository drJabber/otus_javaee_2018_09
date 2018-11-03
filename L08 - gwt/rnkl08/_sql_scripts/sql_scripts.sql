-- SEQUENCE: public.departaments_id_seq

CREATE SEQUENCE public.departaments_id_seq;


-- SEQUENCE: public.positions_id_seq

CREATE SEQUENCE public.positions_id_seq;


-- SEQUENCE: public.roles_id_seq

CREATE SEQUENCE public.roles_id_seq;

-- SEQUENCE: public.staff_id_seq

CREATE SEQUENCE public.staff_id_seq;


-- Table: public.authorities

CREATE TABLE public.authorities
(
    id integer NOT NULL,
    authority character varying(200) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT authorities_pkey PRIMARY KEY (id)
);

-- Table: public.roles

CREATE TABLE public.roles
(
    id integer NOT NULL DEFAULT nextval('roles_id_seq'::regclass),
    role character varying(200) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);

-- Table: public.role_auth

CREATE TABLE public.role_auth
(
    role_id integer NOT NULL,
    auth_id integer NOT NULL,
    CONSTRAINT role_auth_pkey PRIMARY KEY (role_id, auth_id),
    CONSTRAINT fk_role_auth_auth FOREIGN KEY (auth_id)
        REFERENCES public.authorities (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT fk_role_auth_role FOREIGN KEY (role_id)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

-- Table: public.positions

CREATE TABLE public.positions
(
    id integer NOT NULL DEFAULT nextval('positions_id_seq'::regclass),
    "position" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    default_dept_id integer,
    head_id integer,
    default_role_id integer,
    default_salary integer NOT NULL,
    CONSTRAINT positions_pkey PRIMARY KEY (id),
    CONSTRAINT fk_position_dept FOREIGN KEY (default_dept_id)
        REFERENCES public.departaments (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL,
    CONSTRAINT fk_position_head FOREIGN KEY (head_id)
        REFERENCES public.positions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL,
    CONSTRAINT fk_position_role FOREIGN KEY (default_role_id)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
);


-- Table: public.departaments

CREATE TABLE public.departaments
(
    id integer NOT NULL DEFAULT nextval('departaments_id_seq'::regclass),
    departament character varying(500) COLLATE pg_catalog."default" NOT NULL,
    head_dept_id integer,
    head_of_dept_id integer,
    town character varying(200) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT departaments_pkey PRIMARY KEY (id),
    CONSTRAINT fk_dept_head_dept FOREIGN KEY (head_dept_id)
        REFERENCES public.departaments (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL,
    CONSTRAINT fk_dept_head_od_dept FOREIGN KEY (head_of_dept_id)
        REFERENCES public.positions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
);


-- Table: public.staff

CREATE TABLE public.staff
(
    id integer NOT NULL DEFAULT nextval('staff_id_seq'::regclass),
    fio character varying(500) COLLATE pg_catalog."default" NOT NULL,
    position_id integer NOT NULL,
    departament_id integer NOT NULL,
    salary integer NOT NULL DEFAULT 0,
    role_id integer NOT NULL,
    login character varying(200) COLLATE pg_catalog."default" NOT NULL,
    passwd_hash character varying(500) COLLATE pg_catalog."default" NOT NULL,
    passwd_salt character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT staff_pkey PRIMARY KEY (id),
    CONSTRAINT fk_staff_dept FOREIGN KEY (departament_id)
        REFERENCES public.departaments (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT fk_staff_position FOREIGN KEY (position_id)
        REFERENCES public.positions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT fk_staff_role FOREIGN KEY (role_id)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);


-- FUNCTION: public.get_max_salary_fio()

CREATE OR REPLACE FUNCTION public.get_max_salary_fio(
    )
    RETURNS character varying
    LANGUAGE 'plpgsql'
AS $BODY$

begin														   
return(select fio 
          from staff 
	  order by salary desc
	  fetch first row only)
;
end														   

$BODY$;

-- FUNCTION: public.authorize(character varying, character varying)

-- DROP FUNCTION public.authorize(character varying, character varying);

CREATE OR REPLACE FUNCTION public.authorize(
	p_login character varying DEFAULT ''::character varying,
	p_password character varying DEFAULT ''::character varying)
    RETURNS TABLE(o_session_id character varying, o_expire timestamp without time zone) 
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$

declare
   v_staff_id int;
   v_session_id character varying(60) default null;
   v_password  character varying(60);
   v_expire Timestamp default null;
begin
  v_staff_id:=(select id 
			  from staff s 
			  where s.login=p_login and s.passwd_hash=crypt(p_password,s.passwd_salt)
			  fetch first 1 row only);

  if (v_staff_id is not null) then
  	  v_session_id:=encode(digest(p_login||p_password||gen_salt('bf'),'sha1'),'hex');
	  v_expire:=CURRENT_TIMESTAMP+ interval '30 minutes' ;
	  insert into session(id, staff_id,started, expire) values (v_session_id,v_staff_id,CURRENT_TIMESTAMP,v_expire);
  else
  	  v_session_id:='';
  end if;
			  
  o_session_id=v_session_id;
  o_expire=v_expire;
  
  return query values(o_session_id,o_expire);
end

$BODY$;

-- FUNCTION: public.logout(character varying, character varying)

-- DROP FUNCTION public.logout(character varying, character varying);

CREATE OR REPLACE FUNCTION public.logout(
	p_login character varying DEFAULT ''::character varying,
	p_session_id character varying DEFAULT ''::character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
AS $BODY$

begin
  delete from session x where x.id in (select s.id from session s
		 join staff u on u.id=s.staff_id
		 where (s.id=p_session_id) and (u.login=p_login));
  return 1;										
end

$BODY$;

-- FUNCTION: public.hash_password(character varying)

-- DROP FUNCTION public.hash_password(character varying);

CREATE OR REPLACE FUNCTION public.hash_password(
	p_password character varying DEFAULT ''::character varying)
    RETURNS TABLE(o_passwd_hash character varying, o_passwd_salt character varying) 
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$

declare
	v_passwd_salt  character varying(60);

begin
	v_passwd_salt=gen_salt('bf',6);
	return query values (cast(crypt(p_password, v_passwd_salt) as character varying), v_passwd_salt);
end

$BODY$;


CREATE OR REPLACE FUNCTION public.session_is_valid(
	p_session_id character varying DEFAULT ''::character varying)
    RETURNS TABLE(o_valid integer, o_expire timestamp without time zone) 
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$

begin
  
  return query select 1, expire from session s where (s.id=p_session_id) and (s.expire>CURRENT_TIMESTAMP); 
end

$BODY$;