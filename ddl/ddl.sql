CREATE DATABASE "users";

CREATE SCHEMA security
    AUTHORIZATION postgres;

CREATE TABLE security.roles
(
    uuid uuid NOT NULL UNIQUE,
    role_name text NOT NULL UNIQUE
);

ALTER TABLE IF EXISTS security.roles
    OWNER to postgres;



CREATE TABLE security.users (
    uuid uuid NOT NULL UNIQUE,
    account_non_expired boolean NOT NULL,
    account_non_locked boolean NOT NULL,
    credentials_non_expired boolean NOT NULL,
    enabled boolean NOT NULL,
    dt_create timestamp(3) WITHOUT time zone NULL,
    dt_update timestamp(3) WITHOUT time zone NULL,
    mail text NOT NULL UNIQUE,
    status text NOT NULL,
    PASSWORD text NOT NULL,
    username text NOT NULL UNIQUE,
    CONSTRAINT pk_users PRIMARY KEY (uuid)
);


CREATE TABLE security.users_roles (
    role_uuid uuid NOT NULL,
    user_uuid uuid NOT NULL
);

ALTER TABLE IF EXISTS security.users_roles
    ADD CONSTRAINT fk_user_on_role FOREIGN KEY (role_uuid) REFERENCES security.roles (uuid);

ALTER TABLE IF EXISTS security.users_roles
    ADD CONSTRAINT fk_user_on_user FOREIGN KEY (user_uuid) REFERENCES security.users (uuid);

//
ALTER TABLE security.users
    ADD CONSTRAINT uc_users_mail UNIQUE (mail);

ALTER TABLE security.users_roles
    ADD CONSTRAINT uc_users_roles_user_uuid UNIQUE (user_uuid);

ALTER TABLE security.users
    ADD CONSTRAINT uc_users_uuid UNIQUE (uuid);

ALTER TABLE security.users_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_uuid) REFERENCES security.roles (uuid);

ALTER TABLE security.users_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_uuid) REFERENCES security.users (uuid);
