--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.0
-- Dumped by pg_dump version 9.4.0
-- Started on 2016-04-14 08:22:54

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 7 (class 2615 OID 267712)
-- Name: econtactschema; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA econtactschema;


ALTER SCHEMA econtactschema OWNER TO postgres;

SET search_path = econtactschema, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 173 (class 1259 OID 267713)
-- Name: access_church; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE access_church (
    id numeric(38,0) NOT NULL,
    version bigint,
    add_contact_permit boolean NOT NULL,
    confirm boolean NOT NULL,
    adit_access_permit boolean NOT NULL,
    edit_contact_permit boolean NOT NULL,
    edit_group_permit boolean NOT NULL,
    edit_permit boolean NOT NULL,
    edit_user_permit boolean NOT NULL,
    sign numeric(38,0) NOT NULL,
    upd_author character varying(200) NOT NULL,
    upd_date timestamp without time zone NOT NULL,
    view_permit boolean NOT NULL,
    id_church_fk numeric(38,0) NOT NULL,
    id_user_fk numeric(38,0) NOT NULL
);


ALTER TABLE access_church OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 267718)
-- Name: access_group; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE access_group (
    id numeric(38,0) NOT NULL,
    version bigint,
    confirm boolean NOT NULL,
    edit_permit boolean NOT NULL,
    register_permit boolean NOT NULL,
    sign numeric(38,0) NOT NULL,
    upd_author character varying(200) NOT NULL,
    upd_date timestamp without time zone NOT NULL,
    view_permit boolean NOT NULL,
    id_group_fk numeric(38,0) NOT NULL,
    id_user_fk numeric(38,0) NOT NULL
);


ALTER TABLE access_group OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 267723)
-- Name: address; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE address (
    id numeric(38,0) NOT NULL,
    version bigint,
    city character varying(200),
    country character varying(200),
    flat character varying(50),
    number character varying(50),
    region character varying(200),
    sign numeric(38,0) NOT NULL,
    state character varying(200),
    street character varying(200),
    upd_author character varying(200) NOT NULL,
    upd_date timestamp without time zone NOT NULL,
    zip character varying(50)
);


ALTER TABLE address OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 267731)
-- Name: audit_rev; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE audit_rev (
    id bigint NOT NULL,
    date_ev timestamp without time zone NOT NULL,
    name_ev character varying(300),
    note character varying(1000),
    time_stamp bigint,
    id_user_fk numeric(38,0)
);


ALTER TABLE audit_rev OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 267739)
-- Name: audit_rev_changed; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE audit_rev_changed (
    id numeric(38,0) NOT NULL,
    entity_id character varying(100),
    entity_name character varying(300) NOT NULL,
    id_audit_rev_fk bigint NOT NULL
);


ALTER TABLE audit_rev_changed OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 267744)
-- Name: church; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE church (
    id numeric(38,0) NOT NULL,
    version bigint,
    create_date timestamp without time zone NOT NULL,
    description_church character varying(1000),
    name_church character varying(255) NOT NULL,
    sign numeric(38,0) NOT NULL,
    upd_author character varying(200) NOT NULL,
    upd_date timestamp without time zone NOT NULL,
    id_owner_fk numeric(38,0)
);


ALTER TABLE church OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 267752)
-- Name: church_aud; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE church_aud (
    id numeric(38,0) NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
    create_date timestamp without time zone,
    description_church character varying(1000),
    name_church character varying(255),
    sign numeric(38,0),
    upd_author character varying(200),
    upd_date timestamp without time zone
);


ALTER TABLE church_aud OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 267760)
-- Name: connect_audit; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE connect_audit (
    id numeric(38,0) NOT NULL,
    devicename character varying(500) NOT NULL,
    endvisit timestamp without time zone,
    ipaddress character varying(100) NOT NULL,
    sessionid character varying(100) NOT NULL,
    startvisit timestamp without time zone NOT NULL,
    id_user_fk numeric(38,0) NOT NULL
);


ALTER TABLE connect_audit OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 267765)
-- Name: contact; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE contact (
    id numeric(38,0) NOT NULL,
    version bigint,
    sign numeric(38,0) NOT NULL,
    upd_author character varying(200) NOT NULL,
    upd_date timestamp without time zone NOT NULL,
    value character varying(255),
    id_person_fk numeric(38,0) NOT NULL,
    id_type_ud_fk numeric(38,0) NOT NULL
);


ALTER TABLE contact OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 267770)
-- Name: group; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE "group" (
    id numeric(38,0) NOT NULL,
    version bigint,
    description character varying(2000),
    name character varying(200) NOT NULL,
    sign numeric(38,0) NOT NULL,
    upd_author character varying(200) NOT NULL,
    upd_date timestamp without time zone NOT NULL,
    id_church_fk numeric(38,0) NOT NULL
);


ALTER TABLE "group" OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 267778)
-- Name: group_aud; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE group_aud (
    id numeric(38,0) NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
    description character varying(2000),
    name character varying(200),
    sign numeric(38,0),
    upd_author character varying(200),
    upd_date timestamp without time zone
);


ALTER TABLE group_aud OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 267786)
-- Name: person; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE person (
    id numeric(38,0) NOT NULL,
    version bigint,
    birthday timestamp without time zone,
    first_name character varying(100),
    last_name character varying(100),
    middle_name character varying(100),
    sign numeric(38,0) NOT NULL,
    upd_author character varying(200) NOT NULL,
    upd_date timestamp without time zone NOT NULL,
    id_address_fk numeric(38,0),
    id_age_range_ud_fk numeric(38,0),
    id_status_ud_fk numeric(38,0)
);


ALTER TABLE person OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 267930)
-- Name: seq_access_church_id; Type: SEQUENCE; Schema: econtactschema; Owner: postgres
--

CREATE SEQUENCE seq_access_church_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_access_church_id OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 267932)
-- Name: seq_access_group_id; Type: SEQUENCE; Schema: econtactschema; Owner: postgres
--

CREATE SEQUENCE seq_access_group_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_access_group_id OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 267934)
-- Name: seq_address_id; Type: SEQUENCE; Schema: econtactschema; Owner: postgres
--

CREATE SEQUENCE seq_address_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_address_id OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 267936)
-- Name: seq_audit_rev; Type: SEQUENCE; Schema: econtactschema; Owner: postgres
--

CREATE SEQUENCE seq_audit_rev
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_audit_rev OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 267938)
-- Name: seq_audit_rev_chenged; Type: SEQUENCE; Schema: econtactschema; Owner: postgres
--

CREATE SEQUENCE seq_audit_rev_chenged
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_audit_rev_chenged OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 267940)
-- Name: seq_church_id; Type: SEQUENCE; Schema: econtactschema; Owner: postgres
--

CREATE SEQUENCE seq_church_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_church_id OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 267942)
-- Name: seq_connect_audit_id; Type: SEQUENCE; Schema: econtactschema; Owner: postgres
--

CREATE SEQUENCE seq_connect_audit_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_connect_audit_id OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 267944)
-- Name: seq_contact_id; Type: SEQUENCE; Schema: econtactschema; Owner: postgres
--

CREATE SEQUENCE seq_contact_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_contact_id OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 267946)
-- Name: seq_group_id; Type: SEQUENCE; Schema: econtactschema; Owner: postgres
--

CREATE SEQUENCE seq_group_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_group_id OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 267948)
-- Name: seq_person_id; Type: SEQUENCE; Schema: econtactschema; Owner: postgres
--

CREATE SEQUENCE seq_person_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_person_id OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 267950)
-- Name: seq_univer_dict_id; Type: SEQUENCE; Schema: econtactschema; Owner: postgres
--

CREATE SEQUENCE seq_univer_dict_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_univer_dict_id OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 267952)
-- Name: seq_user_id; Type: SEQUENCE; Schema: econtactschema; Owner: postgres
--

CREATE SEQUENCE seq_user_id
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_user_id OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 267794)
-- Name: univer_dict; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE univer_dict (
    id numeric(38,0) NOT NULL,
    version bigint,
    abr_rec_dict character varying(40),
    id_rec_dict integer NOT NULL,
    name_rec_dict character varying(200) NOT NULL,
    param_dict character varying(40) NOT NULL,
    sign numeric(38,0) NOT NULL,
    upd_author character varying(200) NOT NULL,
    upd_date timestamp without time zone NOT NULL
);


ALTER TABLE univer_dict OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 267802)
-- Name: univer_dict_aud; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE univer_dict_aud (
    id numeric(38,0) NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
    abr_rec_dict character varying(40),
    id_rec_dict integer,
    name_rec_dict character varying(200),
    param_dict character varying(40),
    sign numeric(38,0),
    upd_author character varying(200),
    upd_date timestamp without time zone
);


ALTER TABLE univer_dict_aud OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 267810)
-- Name: user_account; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE user_account (
    id numeric(38,0) NOT NULL,
    version bigint,
    login character varying(100) NOT NULL,
    role character varying(255) NOT NULL,
    sign numeric(38,0) NOT NULL,
    allow_create_register boolean NOT NULL,
    enableduser integer NOT NULL,
    first_name character varying(100) NOT NULL,
    last_name character varying(100) NOT NULL,
    password character varying(100) NOT NULL,
    role_confirm integer NOT NULL,
    salt character varying(40) NOT NULL,
    upd_author character varying(200) NOT NULL,
    upd_date timestamp without time zone NOT NULL,
    id_parent_user_fk numeric(38,0)
);


ALTER TABLE user_account OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 267818)
-- Name: user_account_aud; Type: TABLE; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE TABLE user_account_aud (
    id numeric(38,0) NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
    login character varying(100),
    role character varying(255),
    sign numeric(38,0),
    allow_create_register boolean,
    enableduser integer,
    first_name character varying(100),
    last_name character varying(100),
    password character varying(100),
    role_confirm integer,
    salt character varying(40),
    upd_author character varying(200),
    upd_date timestamp without time zone
);


ALTER TABLE user_account_aud OWNER TO postgres;

--
-- TOC entry 1977 (class 2606 OID 267827)
-- Name: access_church_1; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY access_church
    ADD CONSTRAINT access_church_1 UNIQUE (id_user_fk, id_church_fk, sign);


--
-- TOC entry 1979 (class 2606 OID 267717)
-- Name: access_church_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY access_church
    ADD CONSTRAINT access_church_pkey PRIMARY KEY (id);


--
-- TOC entry 1981 (class 2606 OID 267722)
-- Name: access_group_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY access_group
    ADD CONSTRAINT access_group_pkey PRIMARY KEY (id);


--
-- TOC entry 2011 (class 2606 OID 267833)
-- Name: account_user_1; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_account
    ADD CONSTRAINT account_user_1 UNIQUE (login, sign);


--
-- TOC entry 1983 (class 2606 OID 267730)
-- Name: address_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);


--
-- TOC entry 1987 (class 2606 OID 267743)
-- Name: audit_rev_changed_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY audit_rev_changed
    ADD CONSTRAINT audit_rev_changed_pkey PRIMARY KEY (id);


--
-- TOC entry 1985 (class 2606 OID 267738)
-- Name: audit_rev_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY audit_rev
    ADD CONSTRAINT audit_rev_pkey PRIMARY KEY (id);


--
-- TOC entry 1989 (class 2606 OID 267829)
-- Name: church_1; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY church
    ADD CONSTRAINT church_1 UNIQUE (name_church, sign);


--
-- TOC entry 1993 (class 2606 OID 267759)
-- Name: church_aud_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY church_aud
    ADD CONSTRAINT church_aud_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 1991 (class 2606 OID 267751)
-- Name: church_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY church
    ADD CONSTRAINT church_pkey PRIMARY KEY (id);


--
-- TOC entry 1995 (class 2606 OID 267764)
-- Name: connect_audit_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY connect_audit
    ADD CONSTRAINT connect_audit_pkey PRIMARY KEY (id);


--
-- TOC entry 1997 (class 2606 OID 267769)
-- Name: contact_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT contact_pkey PRIMARY KEY (id);


--
-- TOC entry 2001 (class 2606 OID 267785)
-- Name: group_aud_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY group_aud
    ADD CONSTRAINT group_aud_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 1999 (class 2606 OID 267777)
-- Name: group_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "group"
    ADD CONSTRAINT group_pkey PRIMARY KEY (id);


--
-- TOC entry 2003 (class 2606 OID 267793)
-- Name: person_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- TOC entry 2005 (class 2606 OID 267831)
-- Name: univer_dict_1; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY univer_dict
    ADD CONSTRAINT univer_dict_1 UNIQUE (sign, param_dict, id_rec_dict);


--
-- TOC entry 2009 (class 2606 OID 267809)
-- Name: univer_dict_aud_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY univer_dict_aud
    ADD CONSTRAINT univer_dict_aud_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2007 (class 2606 OID 267801)
-- Name: univer_dict_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY univer_dict
    ADD CONSTRAINT univer_dict_pkey PRIMARY KEY (id);


--
-- TOC entry 2016 (class 2606 OID 267825)
-- Name: user_account_aud_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_account_aud
    ADD CONSTRAINT user_account_aud_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2014 (class 2606 OID 267817)
-- Name: user_account_pkey; Type: CONSTRAINT; Schema: econtactschema; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_account
    ADD CONSTRAINT user_account_pkey PRIMARY KEY (id);


--
-- TOC entry 2012 (class 1259 OID 267834)
-- Name: account_user_i_1; Type: INDEX; Schema: econtactschema; Owner: postgres; Tablespace: 
--

CREATE INDEX account_user_i_1 ON user_account USING btree (login);


--
-- TOC entry 2017 (class 2606 OID 267835)
-- Name: fk_15b16jv8ws9t1dil7iytawcrg; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY access_church
    ADD CONSTRAINT fk_15b16jv8ws9t1dil7iytawcrg FOREIGN KEY (id_church_fk) REFERENCES church(id);


--
-- TOC entry 2034 (class 2606 OID 267920)
-- Name: fk_2fhx84ba6pnkawbhhvnncx7s7; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY user_account
    ADD CONSTRAINT fk_2fhx84ba6pnkawbhhvnncx7s7 FOREIGN KEY (id_parent_user_fk) REFERENCES user_account(id);


--
-- TOC entry 2029 (class 2606 OID 267895)
-- Name: fk_4vqqivgxd5xi0g15xa4g9k2ju; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY group_aud
    ADD CONSTRAINT fk_4vqqivgxd5xi0g15xa4g9k2ju FOREIGN KEY (rev) REFERENCES audit_rev(id);


--
-- TOC entry 2030 (class 2606 OID 267900)
-- Name: fk_59levpt67niocwqvj2of51847; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY person
    ADD CONSTRAINT fk_59levpt67niocwqvj2of51847 FOREIGN KEY (id_address_fk) REFERENCES address(id);


--
-- TOC entry 2026 (class 2606 OID 267880)
-- Name: fk_5d5gjxwxj6ihea73fa4mr8w9c; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT fk_5d5gjxwxj6ihea73fa4mr8w9c FOREIGN KEY (id_person_fk) REFERENCES person(id);


--
-- TOC entry 2023 (class 2606 OID 267865)
-- Name: fk_757ki5d7l1bl5o0ssax9k6724; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY church
    ADD CONSTRAINT fk_757ki5d7l1bl5o0ssax9k6724 FOREIGN KEY (id_owner_fk) REFERENCES user_account(id);


--
-- TOC entry 2020 (class 2606 OID 267850)
-- Name: fk_7yy9i9u1uv4h4srg7rjkks2vt; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY access_group
    ADD CONSTRAINT fk_7yy9i9u1uv4h4srg7rjkks2vt FOREIGN KEY (id_user_fk) REFERENCES user_account(id);


--
-- TOC entry 2035 (class 2606 OID 267925)
-- Name: fk_8lr7bh3w6j3hgvum7422b017b; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY user_account_aud
    ADD CONSTRAINT fk_8lr7bh3w6j3hgvum7422b017b FOREIGN KEY (rev) REFERENCES audit_rev(id);


--
-- TOC entry 2027 (class 2606 OID 267885)
-- Name: fk_ajoxtadq2ll1hqo4frwjx5hs7; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT fk_ajoxtadq2ll1hqo4frwjx5hs7 FOREIGN KEY (id_type_ud_fk) REFERENCES univer_dict(id);


--
-- TOC entry 2033 (class 2606 OID 267915)
-- Name: fk_ap2bts0p38i7l35u0famtm1ow; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY univer_dict_aud
    ADD CONSTRAINT fk_ap2bts0p38i7l35u0famtm1ow FOREIGN KEY (rev) REFERENCES audit_rev(id);


--
-- TOC entry 2021 (class 2606 OID 267855)
-- Name: fk_bouqlj9xxla5b8dhf30kesmtc; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY audit_rev
    ADD CONSTRAINT fk_bouqlj9xxla5b8dhf30kesmtc FOREIGN KEY (id_user_fk) REFERENCES user_account(id);


--
-- TOC entry 2018 (class 2606 OID 267840)
-- Name: fk_gvi6e3brbth44gm4rwv2yyyw0; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY access_church
    ADD CONSTRAINT fk_gvi6e3brbth44gm4rwv2yyyw0 FOREIGN KEY (id_user_fk) REFERENCES user_account(id);


--
-- TOC entry 2025 (class 2606 OID 267875)
-- Name: fk_knnlaqag3xeuff2ju9lmjn8ba; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY connect_audit
    ADD CONSTRAINT fk_knnlaqag3xeuff2ju9lmjn8ba FOREIGN KEY (id_user_fk) REFERENCES user_account(id);


--
-- TOC entry 2022 (class 2606 OID 267860)
-- Name: fk_lagvr27ig7s9s096yi6x1pjyp; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY audit_rev_changed
    ADD CONSTRAINT fk_lagvr27ig7s9s096yi6x1pjyp FOREIGN KEY (id_audit_rev_fk) REFERENCES audit_rev(id);


--
-- TOC entry 2032 (class 2606 OID 267910)
-- Name: fk_lcb1g44r8nw8cny2vcyg4wnw7; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY person
    ADD CONSTRAINT fk_lcb1g44r8nw8cny2vcyg4wnw7 FOREIGN KEY (id_status_ud_fk) REFERENCES univer_dict(id);


--
-- TOC entry 2019 (class 2606 OID 267845)
-- Name: fk_npusux8quqjjasyood59mo5aa; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY access_group
    ADD CONSTRAINT fk_npusux8quqjjasyood59mo5aa FOREIGN KEY (id_group_fk) REFERENCES "group"(id);


--
-- TOC entry 2028 (class 2606 OID 267890)
-- Name: fk_op0m1m6oc6492l7x48ftu2hci; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY "group"
    ADD CONSTRAINT fk_op0m1m6oc6492l7x48ftu2hci FOREIGN KEY (id_church_fk) REFERENCES church(id);


--
-- TOC entry 2024 (class 2606 OID 267870)
-- Name: fk_otcg2gekq9hkarjddxb39hl49; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY church_aud
    ADD CONSTRAINT fk_otcg2gekq9hkarjddxb39hl49 FOREIGN KEY (rev) REFERENCES audit_rev(id);


--
-- TOC entry 2031 (class 2606 OID 267905)
-- Name: fk_pcw98fhds53bh92y0m4cj8lk; Type: FK CONSTRAINT; Schema: econtactschema; Owner: postgres
--

ALTER TABLE ONLY person
    ADD CONSTRAINT fk_pcw98fhds53bh92y0m4cj8lk FOREIGN KEY (id_age_range_ud_fk) REFERENCES univer_dict(id);


-- Completed on 2016-04-14 08:22:55

--
-- PostgreSQL database dump complete
--

