-- View: public.staff_login

-- DROP VIEW public.staff_login;

CREATE OR REPLACE VIEW public.staff_login AS
 SELECT staff.login,
    staff.passwd_hash
   FROM staff;

ALTER TABLE public.staff_login
    OWNER TO postgres;


-- View: public.staff_roles

-- DROP VIEW public.staff_roles;

CREATE OR REPLACE VIEW public.staff_roles AS
 SELECT s.login,
    r.role
   FROM staff s
     JOIN roles r ON r.id = s.role_id;

ALTER TABLE public.staff_roles
    OWNER TO postgres;

