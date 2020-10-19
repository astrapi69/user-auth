create sequence hibernate_sequence;

create table applications
(
    id          uuid not null
        constraint applications_pkey
            primary key,
    name        varchar(255)
        constraint idx_applications_name
            unique,
    version     integer,
    domain_name varchar(1024),
    email       varchar(1024)
);

create table contactmethods
(
    id            uuid not null,
    contactmethod varchar(255),
    contactvalue  varchar(1024),
    constraint contactmethods_pkey
        primary key (id)
);

create table permissions
(
    id          uuid not null,
    description varchar(64),
    name        varchar(64),
    shortcut    varchar(10),
    constraint permissions_pkey
        primary key (id),
    constraint uk_permissions_name
        unique (name),
    constraint uk_permissions_shortcut
        unique (shortcut)
);

create table roles
(
    id          uuid not null,
    description varchar(64),
    name        varchar(64),
    constraint roles_pkey
        primary key (id),
    constraint uk_roles_name
        unique (name)
);

create table role_permissions
(
    role_id       uuid not null,
    permission_id uuid not null,
    constraint role_permissions_pkey
        primary key (role_id, permission_id),
    constraint fk_role_permissions_permission_id
        foreign key (permission_id) references permissions,
    constraint fk_role_permissions_role_id
        foreign key (role_id) references roles
);

create table users
(
    id       uuid not null,
    active   boolean,
    locked   boolean,
    salt     varchar(8),
    username varchar(256),
    email varchar(512),
    password       varchar(1024),
    application uuid,
    constraint users_pkey
        primary key (id),
    constraint uk_users_username
        unique (username),
    constraint uk_users_email
        unique (email),
    constraint fk_users_application_id
        foreign key (application) references applications
);

create table relation_permissions
(
    id            uuid not null,
    provider_id   uuid not null,
    subscriber_id uuid not null,
    constraint relation_permissions_pkey
        primary key (id),
    constraint fk_user_relation_permissions_provider_id
        foreign key (provider_id) references users,
    constraint fk_user_relation_permissions_subscriber_id
        foreign key (subscriber_id) references users
);

create table reset_passwords
(
    id                 uuid not null,
    expiry_date        timestamp,
    generated_password varchar(1024),
    starttime          timestamp,
    user_id            uuid,
    constraint reset_passwords_pkey
        primary key (id),
    constraint fk_reset_passwords_user_id
        foreign key (user_id) references users
);

create table user_relation_permissions
(
    user_relation_permission_id uuid  not null,
    permission_id               uuid not null,
    constraint user_relation_permissions_pkey
        primary key (user_relation_permission_id, permission_id),
    constraint fk_user_relation_permissions_permission_id
        foreign key (permission_id) references permissions,
    constraint fk_user_relation_permissions_user_relation_permission_id
        foreign key (user_relation_permission_id) references relation_permissions
);

create table user_roles
(
    user_id uuid  not null,
    role_id uuid not null,
    constraint user_roles_pkey
        primary key (user_id, role_id),
    constraint fk_user_roles_role_id
        foreign key (role_id) references roles,
    constraint fk_user_roles_user_id
        foreign key (user_id) references users
);

create table user_infos
(
    id          uuid not null,
    birthname   varchar(64),
    credits     double precision,
    dateofbirth timestamp,
    firstname   varchar(64),
    gender      varchar(255),
    ip_address  varchar(16),
    lastname    varchar(64),
    locale      varchar(12),
    stripe_customer_id varchar(64),
    owner       uuid,
    constraint user_infos_pkey
        primary key (id),
    constraint fk_user_infos_user_id
        foreign key (owner) references users
);

create table user_contactmethods
(
    user_infos_id     uuid not null,
    contactmethods_id uuid not null,
    constraint user_contactmethods_pkey
        primary key (user_infos_id, contactmethods_id),
    constraint fk_user_infos_contactmethods_id
        foreign key (contactmethods_id) references contactmethods,
    constraint fk_user_infos_user_infos_id
        foreign key (user_infos_id) references user_infos
);

