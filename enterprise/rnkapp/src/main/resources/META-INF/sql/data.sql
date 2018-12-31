--
-- PostgreSQL database dump
--

-- Dumped from database version 10.6 (Ubuntu 10.6-1.pgdg18.04+1)
-- Dumped by pg_dump version 11.1 (Ubuntu 11.1-1.pgdg18.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.authorities (id, authority) VALUES (1, 'can_get_general_information');
INSERT INTO public.authorities (id, authority) VALUES (2, 'can_get_salary');
INSERT INTO public.authorities (id, authority) VALUES (3, 'can_modify_salary');
INSERT INTO public.authorities (id, authority) VALUES (4, 'can_modify_general_information');
INSERT INTO public.authorities (id, authority) VALUES (5, 'can_delete_staff');
INSERT INTO public.authorities (id, authority) VALUES (6, 'can_add_staff');


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.roles (id, role) VALUES (1, 'Common');
INSERT INTO public.roles (id, role) VALUES (2, 'Accountant');
INSERT INTO public.roles (id, role) VALUES (3, 'HR');
INSERT INTO public.roles (id, role) VALUES (4, 'Boss');
INSERT INTO public.roles (id, role) VALUES (-1, 'admin');
INSERT INTO public.roles (id, role) VALUES (-2, 'admin-gui');
INSERT INTO public.roles (id, role) VALUES (-3, 'manager');
INSERT INTO public.roles (id, role) VALUES (-4, 'manager-gui');
INSERT INTO public.roles (id, role) VALUES (-5, 'manager-script');


--
-- Data for Name: departaments; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.departaments (id, departament, head_dept_id, head_of_dept_id, town, default_salary, head_dept_id0, head_of_dept_id0) VALUES (1, 'R&D', NULL, NULL, 'Kazan', NULL, NULL, NULL);
INSERT INTO public.departaments (id, departament, head_dept_id, head_of_dept_id, town, default_salary, head_dept_id0, head_of_dept_id0) VALUES (2, 'HR', NULL, NULL, 'Moscow', NULL, NULL, NULL);
INSERT INTO public.departaments (id, departament, head_dept_id, head_of_dept_id, town, default_salary, head_dept_id0, head_of_dept_id0) VALUES (3, 'Accounting office', NULL, NULL, 'Moscow', NULL, NULL, NULL);
INSERT INTO public.departaments (id, departament, head_dept_id, head_of_dept_id, town, default_salary, head_dept_id0, head_of_dept_id0) VALUES (4, 'CEO Office', NULL, NULL, 'Moscow', NULL, NULL, NULL);
INSERT INTO public.departaments (id, departament, head_dept_id, head_of_dept_id, town, default_salary, head_dept_id0, head_of_dept_id0) VALUES (5, 'Security office', NULL, NULL, 'Moscow', NULL, NULL, NULL);

--
-- Data for Name: positions; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.positions (id, "position", default_dept_id, head_id, default_role_id, default_salary, default_dept_id0, default_role_id0, head_id0) VALUES (1, 'Developer', 1, NULL, 1, 100000, NULL, NULL, NULL);
INSERT INTO public.positions (id, "position", default_dept_id, head_id, default_role_id, default_salary, default_dept_id0, default_role_id0, head_id0) VALUES (2, 'HR', 2, NULL, 3, 40000, NULL, NULL, NULL);
INSERT INTO public.positions (id, "position", default_dept_id, head_id, default_role_id, default_salary, default_dept_id0, default_role_id0, head_id0) VALUES (3, 'Accountant', 3, NULL, 2, 50000, NULL, NULL, NULL);
INSERT INTO public.positions (id, "position", default_dept_id, head_id, default_role_id, default_salary, default_dept_id0, default_role_id0, head_id0) VALUES (4, 'CEO', 4, NULL, 4, 200000, NULL, NULL, NULL);
INSERT INTO public.positions (id, "position", default_dept_id, head_id, default_role_id, default_salary, default_dept_id0, default_role_id0, head_id0) VALUES (5, 'Security officer', 5, NULL, 1, 25000, NULL, NULL, NULL);




--
-- Data for Name: role_auth; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.role_auth (role_id, auth_id) VALUES (4, 4);
INSERT INTO public.role_auth (role_id, auth_id) VALUES (4, 3);
INSERT INTO public.role_auth (role_id, auth_id) VALUES (4, 2);
INSERT INTO public.role_auth (role_id, auth_id) VALUES (4, 5);
INSERT INTO public.role_auth (role_id, auth_id) VALUES (4, 1);
INSERT INTO public.role_auth (role_id, auth_id) VALUES (1, 1);
INSERT INTO public.role_auth (role_id, auth_id) VALUES (3, 4);
INSERT INTO public.role_auth (role_id, auth_id) VALUES (3, 1);
INSERT INTO public.role_auth (role_id, auth_id) VALUES (3, 5);
INSERT INTO public.role_auth (role_id, auth_id) VALUES (2, 3);
INSERT INTO public.role_auth (role_id, auth_id) VALUES (2, 1);
INSERT INTO public.role_auth (role_id, auth_id) VALUES (2, 2);


--
-- Data for Name: staff; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.staff (id, fio, position_id, departament_id, salary, role_id, login, passwd_hash, passwd_salt, departament_id0, position_id0, role_id0, birth_date, gender, email) VALUES (228842, 'Иванов Иван Иванович', 1, 1, 111, 1, 'ivanov', '$2a$06$v5v0NcbDR/DjTM94PQWEtuiwR0BSA4zfiddR/j4o18YKloo7TSxRG', '$2a$06$v5v0NcbDR/DjTM94PQWEtu', NULL, NULL, NULL, '1964-12-20 00:00:00', 'М', 'a@b.ru');
INSERT INTO public.staff (id, fio, position_id, departament_id, salary, role_id, login, passwd_hash, passwd_salt, departament_id0, position_id0, role_id0, birth_date, gender, email) VALUES (228820, 'Бывал Балбес Трусович', 3, 3, 555, 2, 'oldman', '$2a$06$v5v0NcbDR/DjTM94PQWEtuiwR0BSA4zfiddR/j4o18YKloo7TSxRG', '$2a$06$v5v0NcbDR/DjTM94PQWEtu', 3, 3, 2, '1954-08-20 00:00:00', 'М', 'a@b.ru');
INSERT INTO public.staff (id, fio, position_id, departament_id, salary, role_id, login, passwd_hash, passwd_salt, departament_id0, position_id0, role_id0, birth_date, gender, email) VALUES (-1, 'Админ', 3, 3, 555, -1, 'admin', 'Oqn1tSY2tFD4eV+QB3HCYzfrSJgaNfYxm32v/L14mmc0iKpZLppAJcbiWPiJDTShc3RZuuLWDaEuBtZwWErmWQ==', 'UlNkPWKgiojGuu0nvxUam4Gh+ukuKbSk', NULL, NULL, NULL, '1954-08-20 00:00:00', 'М', 'a@b.ru');
INSERT INTO public.staff (id, fio, position_id, departament_id, salary, role_id, login, passwd_hash, passwd_salt, departament_id0, position_id0, role_id0, birth_date, gender, email) VALUES (228822, 'Сергеев Петр Мартынович', 1, 1, 222, 1, 'sergeev', '$2a$06$v5v0NcbDR/DjTM94PQWEtuiwR0BSA4zfiddR/j4o18YKloo7TSxRG', '$2a$06$v5v0NcbDR/DjTM94PQWEtu', NULL, NULL, NULL, '1974-11-20 00:00:00', 'М', 'a@b.ru');
INSERT INTO public.staff (id, fio, position_id, departament_id, salary, role_id, login, passwd_hash, passwd_salt, departament_id0, position_id0, role_id0, birth_date, gender, email) VALUES (228826, 'ddd', 1, 1, 444, 1, 'fff', '5bf1fd927dfb8679496a2e6cf00cbe50c1c87145', '-', 1, 1, 1, '2011-12-12 00:00:00', 'М', 'eee');
INSERT INTO public.staff (id, fio, position_id, departament_id, salary, role_id, login, passwd_hash, passwd_salt, departament_id0, position_id0, role_id0, birth_date, gender, email) VALUES (228827, 'Иванов Иван Иванович', 1, 1, 111, 1, 'ivanov', '$2a$06$v5v0NcbDR/DjTM94PQWEtuiwR0BSA4zfiddR/j4o18YKloo7TSxRG', '$2a$06$v5v0NcbDR/DjTM94PQWEtu', NULL, NULL, NULL, '1964-12-20 00:00:00', 'М', 'a@b.ru');
INSERT INTO public.staff (id, fio, position_id, departament_id, salary, role_id, login, passwd_hash, passwd_salt, departament_id0, position_id0, role_id0, birth_date, gender, email) VALUES (228828, 'Сергеев Петр Мартынович', 1, 1, 222, 1, 'sergeev', '$2a$06$v5v0NcbDR/DjTM94PQWEtuiwR0BSA4zfiddR/j4o18YKloo7TSxRG', '$2a$06$v5v0NcbDR/DjTM94PQWEtu', NULL, NULL, NULL, '1974-11-20 00:00:00', 'М', 'a@b.ru');
INSERT INTO public.staff (id, fio, position_id, departament_id, salary, role_id, login, passwd_hash, passwd_salt, departament_id0, position_id0, role_id0, birth_date, gender, email) VALUES (228832, 'Иванов Иван Иванович', 1, 1, 111, 1, 'ivanov', '$2a$06$v5v0NcbDR/DjTM94PQWEtuiwR0BSA4zfiddR/j4o18YKloo7TSxRG', '$2a$06$v5v0NcbDR/DjTM94PQWEtu', NULL, NULL, NULL, '1964-12-20 00:00:00', 'М', 'a@b.ru');
INSERT INTO public.staff (id, fio, position_id, departament_id, salary, role_id, login, passwd_hash, passwd_salt, departament_id0, position_id0, role_id0, birth_date, gender, email) VALUES (228833, 'Сергеев Петр Мартынович', 1, 1, 222, 1, 'sergeev', '$2a$06$v5v0NcbDR/DjTM94PQWEtuiwR0BSA4zfiddR/j4o18YKloo7TSxRG', '$2a$06$v5v0NcbDR/DjTM94PQWEtu', NULL, NULL, NULL, '1974-11-20 00:00:00', 'М', 'a@b.ru');


--
-- Data for Name: session; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: stats; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.stats (id, token, prev_id, "values") VALUES (1913, 'rnk.sharaban.17d6f53c215d491babefc51afe085a4a', NULL, '{"urn": "/admin", "client_ip": "127.0.1.1", "user_name": "admin", "client_time": "2018-12-03T18:58:35+03:00", "server_time": "2018-12-03T18:58:35.047899+03:00", "user_country": "RU", "jsp_page_name": "main", "browser_version": "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36", "last_tracker_id": "1912"}');


--
-- Data for Name: stats_switches; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.stats_switches (id, token, switch) VALUES (3, 'qqqwwwzzz', 'on');


--
-- Name: departaments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.departaments_id_seq', 10, true);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 5, true);


--
-- Name: positions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.positions_id_seq', 12, true);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 8, true);


--
-- Name: staff_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.staff_id_seq', 228876, true);


--
-- Name: stats_details_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stats_details_id_seq', 1, false);


--
-- Name: stats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stats_id_seq', 3185, true);


--
-- Name: stats_switch_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stats_switch_id_seq', 3, true);


--
-- PostgreSQL database dump complete
--

