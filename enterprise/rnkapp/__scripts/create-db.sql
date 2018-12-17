--
-- Name: authorities; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.authorities (
    id integer NOT NULL,
    authority character varying(200) NOT NULL
);


--
-- Name: departaments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.departaments (
    id integer NOT NULL,
    departament character varying(500) NOT NULL,
    head_dept_id integer,
    head_of_dept_id integer,
    town character varying(200) NOT NULL,
    default_salary integer,
    head_dept_id0 integer,
    head_of_dept_id0 integer
);


--
-- Name: departaments_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.departaments_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: departaments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.departaments_id_seq OWNED BY public.departaments.id;


-- --
-- -- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: -
-- --

-- CREATE SEQUENCE public.hibernate_sequence
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;


-- --
-- -- Name: positions; Type: TABLE; Schema: public; Owner: -
-- --

-- CREATE TABLE public.positions (
--     id integer NOT NULL,
--     "position" character varying(500) NOT NULL,
--     default_dept_id integer,
--     head_id integer,
--     default_role_id integer,
--     default_salary integer NOT NULL,
--     default_dept_id0 integer,
--     default_role_id0 integer,
--     head_id0 integer
-- );


-- --
-- -- Name: positions_id_seq; Type: SEQUENCE; Schema: public; Owner: -
-- --

-- CREATE SEQUENCE public.positions_id_seq
--     AS integer
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;


-- --
-- -- Name: positions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
-- --

-- ALTER SEQUENCE public.positions_id_seq OWNED BY public.positions.id;


-- --
-- -- Name: role_auth; Type: TABLE; Schema: public; Owner: -
-- --

-- CREATE TABLE public.role_auth (
--     role_id integer NOT NULL,
--     auth_id integer NOT NULL
-- );


-- --
-- -- Name: roles; Type: TABLE; Schema: public; Owner: -
-- --

-- CREATE TABLE public.roles (
--     id integer NOT NULL,
--     role character varying(200) NOT NULL
-- );


-- --
-- -- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: -
-- --

-- CREATE SEQUENCE public.roles_id_seq
--     AS integer
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;


-- --
-- -- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
-- --

-- ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


-- --
-- -- Name: session; Type: TABLE; Schema: public; Owner: -
-- --

-- CREATE TABLE public.session (
--     id character varying(60) NOT NULL,
--     staff_id integer NOT NULL,
--     started timestamp without time zone NOT NULL,
--     expire timestamp without time zone NOT NULL
-- );


-- --
-- -- Name: staff; Type: TABLE; Schema: public; Owner: -
-- --

-- CREATE TABLE public.staff (
--     id integer NOT NULL,
--     fio character varying(500) NOT NULL,
--     position_id integer NOT NULL,
--     departament_id integer NOT NULL,
--     salary integer DEFAULT 0 NOT NULL,
--     role_id integer NOT NULL,
--     login character varying(200) NOT NULL,
--     passwd_hash character varying(500) NOT NULL,
--     passwd_salt character varying(100) NOT NULL,
--     departament_id0 integer,
--     position_id0 integer,
--     role_id0 integer,
--     birth_date timestamp without time zone DEFAULT '1900-01-01 00:00:00'::timestamp without time zone NOT NULL,
--     gender character varying(1) DEFAULT 'М'::character varying NOT NULL,
--     email character varying(200)
-- );


-- --
-- -- Name: staff_id_seq; Type: SEQUENCE; Schema: public; Owner: -
-- --

-- CREATE SEQUENCE public.staff_id_seq
--     AS integer
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;


-- --
-- -- Name: staff_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
-- --

-- ALTER SEQUENCE public.staff_id_seq OWNED BY public.staff.id;


-- --
-- -- Name: staff_login; Type: VIEW; Schema: public; Owner: -
-- --

-- CREATE VIEW public.staff_login AS
--  SELECT staff.login,
--     staff.passwd_hash
--    FROM public.staff;


-- --
-- -- Name: staff_roles; Type: VIEW; Schema: public; Owner: -
-- --

-- CREATE VIEW public.staff_roles AS
--  SELECT s.login,
--     r.role
--    FROM (public.staff s
--      JOIN public.roles r ON ((r.id = s.role_id)));


-- --
-- -- Name: stats_id_seq; Type: SEQUENCE; Schema: public; Owner: -
-- --

-- CREATE SEQUENCE public.stats_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;


-- --
-- -- Name: stats; Type: TABLE; Schema: public; Owner: -
-- --

-- CREATE TABLE public.stats (
--     id integer DEFAULT nextval('public.stats_id_seq'::regclass) NOT NULL,
--     token character varying(200) NOT NULL,
--     prev_id integer,
--     "values" jsonb NOT NULL
-- );


-- --
-- -- Name: stats_details_id_seq; Type: SEQUENCE; Schema: public; Owner: -
-- --

-- CREATE SEQUENCE public.stats_details_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;


-- --
-- -- Name: stats_records; Type: VIEW; Schema: public; Owner: -
-- --

-- CREATE VIEW public.stats_records AS
--  SELECT s.id,
--     s.token,
--     ((s."values" ->> 'server_time'::text))::timestamp with time zone AS date,
--     (s."values" ->> 'urn'::text) AS urn,
--     (s."values" ->> 'user_name'::text) AS "user",
--     (s."values" ->> 'client_ip'::text) AS ip,
--     (s."values" ->> 'user_country'::text) AS country,
--     (s."values" ->> 'user_searched_for'::text) AS searchedfor
--    FROM public.stats s;


-- --
-- -- Name: stats_switch_id_seq; Type: SEQUENCE; Schema: public; Owner: -
-- --

-- CREATE SEQUENCE public.stats_switch_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;


-- --
-- -- Name: stats_switches; Type: TABLE; Schema: public; Owner: -
-- --

-- CREATE TABLE public.stats_switches (
--     id integer DEFAULT nextval('public.stats_switch_id_seq'::regclass) NOT NULL,
--     token character varying(200) NOT NULL,
--     switch character varying(10)
-- );


-- --
-- -- Name: departaments id; Type: DEFAULT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.departaments ALTER COLUMN id SET DEFAULT nextval('public.departaments_id_seq'::regclass);


-- --
-- -- Name: positions id; Type: DEFAULT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.positions ALTER COLUMN id SET DEFAULT nextval('public.positions_id_seq'::regclass);


-- --
-- -- Name: roles id; Type: DEFAULT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


-- --
-- -- Name: staff id; Type: DEFAULT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.staff ALTER COLUMN id SET DEFAULT nextval('public.staff_id_seq'::regclass);


-- --
-- -- Name: authorities authorities_pkey; Type: CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.authorities
--     ADD CONSTRAINT authorities_pkey PRIMARY KEY (id);


-- --
-- -- Name: departaments departaments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.departaments
--     ADD CONSTRAINT departaments_pkey PRIMARY KEY (id);


-- --
-- -- Name: positions positions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.positions
--     ADD CONSTRAINT positions_pkey PRIMARY KEY (id);


-- --
-- -- Name: role_auth role_auth_pkey; Type: CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.role_auth
--     ADD CONSTRAINT role_auth_pkey PRIMARY KEY (role_id, auth_id);


-- --
-- -- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.roles
--     ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


-- --
-- -- Name: session session_pkey; Type: CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.session
--     ADD CONSTRAINT session_pkey PRIMARY KEY (id);


-- --
-- -- Name: staff staff_pkey; Type: CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.staff
--     ADD CONSTRAINT staff_pkey PRIMARY KEY (id);


-- --
-- -- Name: stats stats_pkey; Type: CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.stats
--     ADD CONSTRAINT stats_pkey PRIMARY KEY (id);


-- --
-- -- Name: stats_switches stats_switches_pkey; Type: CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.stats_switches
--     ADD CONSTRAINT stats_switches_pkey PRIMARY KEY (id);


-- --
-- -- Name: departaments fk_dept_head_dept; Type: FK CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.departaments
--     ADD CONSTRAINT fk_dept_head_dept FOREIGN KEY (head_dept_id) REFERENCES public.departaments(id) ON DELETE SET NULL;


-- --
-- -- Name: departaments fk_dept_head_od_dept; Type: FK CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.departaments
--     ADD CONSTRAINT fk_dept_head_od_dept FOREIGN KEY (head_of_dept_id) REFERENCES public.positions(id) ON DELETE SET NULL;


-- --
-- -- Name: positions fk_position_dept; Type: FK CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.positions
--     ADD CONSTRAINT fk_position_dept FOREIGN KEY (default_dept_id) REFERENCES public.departaments(id) ON DELETE SET NULL;


-- --
-- -- Name: positions fk_position_head; Type: FK CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.positions
--     ADD CONSTRAINT fk_position_head FOREIGN KEY (head_id) REFERENCES public.positions(id) ON DELETE SET NULL;


-- --
-- -- Name: positions fk_position_role; Type: FK CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.positions
--     ADD CONSTRAINT fk_position_role FOREIGN KEY (default_role_id) REFERENCES public.roles(id) ON DELETE SET NULL;


-- --
-- -- Name: role_auth fk_role_auth_auth; Type: FK CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.role_auth
--     ADD CONSTRAINT fk_role_auth_auth FOREIGN KEY (auth_id) REFERENCES public.authorities(id) ON DELETE CASCADE;


-- --
-- -- Name: role_auth fk_role_auth_role; Type: FK CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.role_auth
--     ADD CONSTRAINT fk_role_auth_role FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE;


-- --
-- -- Name: session fk_session_staff; Type: FK CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.session
--     ADD CONSTRAINT fk_session_staff FOREIGN KEY (staff_id) REFERENCES public.staff(id) ON DELETE CASCADE;


-- --
-- -- Name: staff fk_staff_dept; Type: FK CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.staff
--     ADD CONSTRAINT fk_staff_dept FOREIGN KEY (departament_id) REFERENCES public.departaments(id) ON DELETE CASCADE;


-- --
-- -- Name: staff fk_staff_position; Type: FK CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.staff
--     ADD CONSTRAINT fk_staff_position FOREIGN KEY (position_id) REFERENCES public.positions(id) ON DELETE CASCADE;


-- --
-- -- Name: staff fk_staff_role; Type: FK CONSTRAINT; Schema: public; Owner: -
-- --

-- ALTER TABLE ONLY public.staff
--     ADD CONSTRAINT fk_staff_role FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE;


-- --
-- -- PostgreSQL database dump complete
-- --

-- --
-- -- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
-- --

-- CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


-- --
-- -- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: -
-- --

-- COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


-- --
-- -- Name: add_stats(character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
-- --

-- CREATE FUNCTION public.add_stats(p_token character varying, p_value character varying) RETURNS TABLE(o_id integer)
--     LANGUAGE plpgsql
--     AS $$

--  declare
--    stats_switch character varying default null;
-- begin		
-- 	stats_switch=coalesce((select switch from stats_switches where token=p_token),'on');
-- 	if (stats_switch='on') then
-- 		return query insert into stats(token,values) values(p_token,cast(p_value as jsonb))
-- 				returning id;
-- 	else
-- 		return query select (-1);																		 
-- 	end if;
-- end														   

-- $$;


-- --
-- -- Name: authorize(character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
-- --

-- CREATE FUNCTION public.authorize(p_login character varying DEFAULT ''::character varying, p_password character varying DEFAULT ''::character varying) RETURNS TABLE(o_session_id character varying, o_expire timestamp without time zone)
--     LANGUAGE plpgsql
--     AS $$
-- declare
--    v_staff_id int;
--    v_session_id character varying(60) default null;
--    v_password  character varying(60);
--    v_expire Timestamp default null;
-- begin
--   v_staff_id:=(select id 
-- 			  from staff s 
-- 			  where s.login=p_login and s.passwd_hash=crypt(p_password,s.passwd_salt)
-- 			  fetch first 1 row only);

--   if (v_staff_id is not null) then
--   	  v_session_id:=encode(digest(p_login||p_password||gen_salt('bf'),'sha1'),'hex');
-- 	  v_expire:=CURRENT_TIMESTAMP+ interval '30 minutes' ;
-- 	  insert into session(id, staff_id,started, expire) values (v_session_id,v_staff_id,CURRENT_TIMESTAMP,v_expire);
--   else
--   	  v_session_id:='';
--   end if;
			  
--   o_session_id=v_session_id;
--   o_expire=v_expire;
  
--   return query values(o_session_id,o_expire);
-- end

-- $$;


-- --
-- -- Name: get_max_salary_fio(); Type: FUNCTION; Schema: public; Owner: -
-- --

-- CREATE FUNCTION public.get_max_salary_fio() RETURNS character varying
--     LANGUAGE plpgsql
--     AS $$
-- begin														   
-- return(select fio 
--           from staff 
-- 		  order by salary desc
-- 		  fetch first row only)
-- ;
-- end														   
-- $$;


-- --
-- -- Name: get_stats_switch(character varying); Type: FUNCTION; Schema: public; Owner: -
-- --

-- CREATE FUNCTION public.get_stats_switch(p_token character varying) RETURNS TABLE(o_switch character varying)
--     LANGUAGE plpgsql
--     AS $$

-- begin		
-- 	if exists (select 1 from stats_switches where token=p_token) then
-- 		return query select switch from stats_switches where token=p_token;
-- 	else
-- 		return query values("on");
-- 	end if;
-- end														   

-- $$;


-- --
-- -- Name: hash_password(character varying); Type: FUNCTION; Schema: public; Owner: -
-- --

-- CREATE FUNCTION public.hash_password(p_password character varying DEFAULT ''::character varying) RETURNS TABLE(o_passwd_hash character varying, o_passwd_salt character varying)
--     LANGUAGE plpgsql
--     AS $$

-- declare
-- 	v_passwd_salt  character varying(60);

-- begin
-- 	v_passwd_salt='-';
-- 	return query values (cast(encode(digest('blah','sha1'),'hex') as character varying), v_passwd_salt);
-- end

-- $$;


-- --
-- -- Name: logout(character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
-- --

-- CREATE FUNCTION public.logout(p_login character varying DEFAULT ''::character varying, p_session_id character varying DEFAULT ''::character varying) RETURNS integer
--     LANGUAGE plpgsql
--     AS $$
-- begin
--   delete from session x where x.id in (select s.id from session s
-- 		 join staff u on u.id=s.staff_id
-- 		 where (s.id=p_session_id) and (u.login=p_login));
--   return 1;										
-- end

-- $$;


-- --
-- -- Name: session_is_valid(character varying); Type: FUNCTION; Schema: public; Owner: -
-- --

-- CREATE FUNCTION public.session_is_valid(p_session_id character varying DEFAULT ''::character varying) RETURNS TABLE(o_valid integer, o_expire timestamp without time zone)
--     LANGUAGE plpgsql
--     AS $$
-- begin
  
--   return query select 1, expire from session s where (s.id=p_session_id) and (s.expire>CURRENT_TIMESTAMP); 
-- end

-- $$;


-- --
-- -- Name: switch_stats(character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
-- --

-- CREATE FUNCTION public.switch_stats(p_token character varying, p_switch character varying) RETURNS TABLE(o_result character varying)
--     LANGUAGE plpgsql
--     AS $$

-- begin		
-- 	if exists (select 1 from stats where token=p_token) then
-- 		if exists (select 1 from stats_switches where token=p_token) then
-- 			update stats_switches set switch=p_switch where token=p_token;
-- 		else
-- 			insert into stats_switches(token,switch) values(p_token,p_switch);
-- 		end if;
		
-- 		return query values(p_switch);
-- 	else
-- 		return query values("fail");
-- 	end if;
-- end														   

-- $$;


-- SET default_with_oids = false;

