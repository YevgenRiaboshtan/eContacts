drop schema econtactschema cascade;
create schema econtactschema;

-- Table: econtactschema.user_account
-- DROP TABLE econtactschema.user_account;
CREATE TABLE econtactschema.user_account
(
  id numeric(38,0) NOT NULL,
  version bigint,
  first_name character varying(100) NOT NULL,
  last_name character varying(100) NOT NULL,
  login character varying(100) NOT NULL,
  role character varying(255) NOT NULL,
  sign numeric(38,0) NOT NULL,
  upd_author character varying(200) NOT NULL,
  upd_date timestamp without time zone NOT NULL,
  allow_create_register boolean NOT NULL,
  enableduser integer NOT NULL,
  password character varying(100) NOT NULL,
  role_confirm integer NOT NULL,
  salt character varying(40) NOT NULL,
  id_parent_user_fk numeric(38,0),
  CONSTRAINT user_account_pkey PRIMARY KEY (id),
  CONSTRAINT fk_2fhx84ba6pnkawbhhvnncx7s7 FOREIGN KEY (id_parent_user_fk)
      REFERENCES econtactschema.user_account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_login_sign_unique_constraint UNIQUE (login, sign)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE econtactschema.user_account
  OWNER TO postgres;

-- Index: econtactschema.user_login_index
-- DROP INDEX econtactschema.user_login_index;
CREATE INDEX user_login_index
  ON econtactschema.user_account
  USING btree
  (login COLLATE pg_catalog."default");

-- Sequence: econtactschema.seq_user_id
-- DROP SEQUENCE econtactschema.seq_user_id;
CREATE SEQUENCE econtactschema.seq_user_id
  INCREMENT 50
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 51
  CACHE 1;
ALTER TABLE econtactschema.seq_user_id
  OWNER TO postgres;

  
-- Table: econtactschema.univer_dict
-- DROP TABLE econtactschema.univer_dict;
CREATE TABLE econtactschema.univer_dict
(
  id numeric(38,0) NOT NULL,
  version bigint,
  abr_rec_dict character varying(40),
  id_rec_dict integer NOT NULL,
  name_rec_dict character varying(200) NOT NULL,
  param_dict character varying(40) NOT NULL,
  sign numeric(38,0) NOT NULL,
  upd_author character varying(200) NOT NULL,
  upd_date timestamp without time zone NOT NULL,
  CONSTRAINT univer_dict_pkey PRIMARY KEY (id),
  CONSTRAINT param_dict_id_rec_dict_sign_unique_constraint UNIQUE (sign, param_dict, id_rec_dict)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE econtactschema.univer_dict
  OWNER TO postgres;

-- Sequence: econtactschema.seq_univer_dict_id
-- DROP SEQUENCE econtactschema.seq_univer_dict_id;
CREATE SEQUENCE econtactschema.seq_univer_dict_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 5
  CACHE 1;
ALTER TABLE econtactschema.seq_univer_dict_id
  OWNER TO postgres;

  
-- Table: econtactschema.church
-- DROP TABLE econtactschema.church;
CREATE TABLE econtactschema.church
(
  id numeric(38,0) NOT NULL,
  version bigint,
  create_date timestamp without time zone NOT NULL,
  description_church character varying(1000),
  name_church character varying(255) NOT NULL,
  sign numeric(38,0) NOT NULL,
  upd_author character varying(200) NOT NULL,
  upd_date timestamp without time zone NOT NULL,
  id_owner_fk numeric(38,0),
  CONSTRAINT church_pkey PRIMARY KEY (id),
  CONSTRAINT fk_757ki5d7l1bl5o0ssax9k6724 FOREIGN KEY (id_owner_fk)
      REFERENCES econtactschema.user_account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT church_name_sign_unique_constraint UNIQUE (name_church, sign)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE econtactschema.church
  OWNER TO postgres;
  
-- Sequence: econtactschema.seq_church_id
-- DROP SEQUENCE econtactschema.seq_church_id;
CREATE SEQUENCE econtactschema.seq_church_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE econtactschema.seq_church_id
  OWNER TO postgres;
  

-- Table: econtactschema.connect_audit
-- DROP TABLE econtactschema.connect_audit;

CREATE TABLE econtactschema.connect_audit
(
  id numeric(38,0) NOT NULL,
  date_con timestamp without time zone NOT NULL,
  ip_addr character varying(41) NOT NULL,
  name_comp character varying(100) NOT NULL,
  name_os character varying(100),
  prog character varying(500),
  id_action_ud numeric(38,0) NOT NULL,
  id_user_fk numeric(38,0) NOT NULL,
  CONSTRAINT connect_audit_pkey PRIMARY KEY (id),
  CONSTRAINT fk_knnlaqag3xeuff2ju9lmjn8ba FOREIGN KEY (id_user_fk)
      REFERENCES econtactschema.user_account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_kr9w82v6rg8uwishevswjd1ep FOREIGN KEY (id_action_ud)
      REFERENCES econtactschema.univer_dict (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE econtactschema.connect_audit
  OWNER TO postgres;

-- Index: econtactschema.id_index_pk
-- DROP INDEX econtactschema.id_index_pk;

CREATE INDEX id_index_pk
  ON econtactschema.connect_audit
  USING btree
  (id);

-- Sequence: econtactschema.seq_connect_audit_id
-- DROP SEQUENCE econtactschema.seq_connect_audit_id;
CREATE SEQUENCE econtactschema.seq_connect_audit_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE econtactschema.seq_connect_audit_id
  OWNER TO postgres;
  
-- Table: econtactschema.audit_rev
-- DROP TABLE econtactschema.audit_rev;

CREATE TABLE econtactschema.audit_rev
(
  id bigint NOT NULL,
  date_ev timestamp without time zone NOT NULL,
  name_ev character varying(300),
  note character varying(1000),
  time_stamp bigint,
  id_event_ud numeric(38,0) NOT NULL,
  id_user_fk numeric(38,0),
  CONSTRAINT audit_rev_pkey PRIMARY KEY (id),
  CONSTRAINT fk_bouqlj9xxla5b8dhf30kesmtc FOREIGN KEY (id_user_fk)
      REFERENCES econtactschema.user_account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_srjxyw3bja8qkpv5rk0djhv24 FOREIGN KEY (id_event_ud)
      REFERENCES econtactschema.univer_dict (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE econtactschema.audit_rev
  OWNER TO postgres;

-- Sequence: econtactschema."s$audit_rev"
-- DROP SEQUENCE econtactschema."s$audit_rev";
CREATE SEQUENCE econtactschema."s$audit_rev"
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 3
  CACHE 1;
ALTER TABLE econtactschema."s$audit_rev"
  OWNER TO postgres;

  
-- Table: econtactschema.audit_rev_changed
-- DROP TABLE econtactschema.audit_rev_changed;

CREATE TABLE econtactschema.audit_rev_changed
(
  id numeric(38,0) NOT NULL,
  entity_id character varying(100),
  entity_name character varying(300) NOT NULL,
  id_audit_rev_fk bigint NOT NULL,
  CONSTRAINT audit_rev_changed_pkey PRIMARY KEY (id),
  CONSTRAINT fk_lagvr27ig7s9s096yi6x1pjyp FOREIGN KEY (id_audit_rev_fk)
      REFERENCES econtactschema.audit_rev (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE econtactschema.audit_rev_changed
  OWNER TO postgres;

-- Sequence: econtactschema."s$audit_rev_chenged"
-- DROP SEQUENCE econtactschema."s$audit_rev_chenged";
CREATE SEQUENCE econtactschema."s$audit_rev_chenged"
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 3
  CACHE 1;
ALTER TABLE econtactschema."s$audit_rev_chenged"
  OWNER TO postgres;

  
-- Table: econtactschema.user_account_aud
-- DROP TABLE econtactschema.user_account_aud;

CREATE TABLE econtactschema.user_account_aud
(
  id numeric(38,0) NOT NULL,
  allow_create_register boolean NOT NULL,
  enableduser integer NOT NULL,
  first_name character varying(100) NOT NULL,
  last_name character varying(100) NOT NULL,
  login character varying(100) NOT NULL,
  password character varying(100) NOT NULL,
  role character varying(255) NOT NULL,
  role_confirm integer NOT NULL,
  salt character varying(40) NOT NULL,
  rev bigint NOT NULL,
  revtype smallint,
  sign numeric(38,0),
  upd_author character varying(200),
  upd_date timestamp without time zone,
  CONSTRAINT user_account_aud_pkey PRIMARY KEY (id, rev),
  CONSTRAINT fk_ph4786kygb9gg5uatj4wctm7q FOREIGN KEY (rev)
      REFERENCES econtactschema.audit_rev (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE econtactschema.user_account_aud
  OWNER TO postgres;

-- Table: econtactschema.univer_dict_aud
-- DROP TABLE econtactschema.univer_dict_aud;

CREATE TABLE econtactschema.univer_dict_aud
(
  id numeric(38,0) NOT NULL,
  rev bigint NOT NULL,
  revtype smallint,
  abr_rec_dict character varying(40),
  id_rec_dict integer,
  name_rec_dict character varying(200),
  param_dict character varying(40),
  sign numeric(38,0),
  upd_author character varying(200),
  upd_date timestamp without time zone,
  CONSTRAINT univer_dict_aud_pkey PRIMARY KEY (id, rev),
  CONSTRAINT fk_ap2bts0p38i7l35u0famtm1ow FOREIGN KEY (rev)
      REFERENCES econtactschema.audit_rev (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE econtactschema.univer_dict_aud
  OWNER TO postgres;

-- Table: econtactschema.church_aud
-- DROP TABLE econtactschema.church_aud;

CREATE TABLE econtactschema.church_aud
(
  id numeric(38,0) NOT NULL,
  rev bigint NOT NULL,
  revtype smallint,
  create_date timestamp without time zone,
  description_church character varying(1000),
  name_church character varying(255),
  sign numeric(38,0),
  upd_author character varying(200),
  upd_date timestamp without time zone,
  CONSTRAINT church_aud_pkey PRIMARY KEY (id, rev),
  CONSTRAINT fk_otcg2gekq9hkarjddxb39hl49 FOREIGN KEY (rev)
      REFERENCES econtactschema.audit_rev (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE econtactschema.church_aud
  OWNER TO postgres;
