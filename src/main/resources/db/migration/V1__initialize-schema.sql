create sequence hibernate_sequence;

create table permissions
(
    id          integer not null,
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
    id          integer not null,
    description varchar(64),
    name        varchar(64),
    constraint roles_pkey
        primary key (id),
    constraint uk_roles_name
        unique (name)
);

create table role_permissions
(
    role_id       integer not null,
    permission_id integer not null,
    constraint role_permissions_pkey
        primary key (role_id, permission_id),
    constraint fk_role_permissions_permission_id
        foreign key (permission_id) references permissions,
    constraint fk_role_permissions_role_id
        foreign key (role_id) references roles
);

create table user_tokens
(
    id       bigint not null,
    expiry   timestamp,
    token    varchar(128),
    username varchar(256),
    constraint user_tokens_pkey
        primary key (id),
    constraint uk_user_tokens_token
        unique (token),
    constraint uk_user_tokens_username
        unique (username)
);

create table users
(
    id       bigint not null,
    active   boolean,
    locked   boolean,
    pw       varchar(1024),
    salt     varchar(8),
    username varchar(256),
    constraint users_pkey
        primary key (id),
    constraint uk_users_username
        unique (username)
);

create table relation_permissions
(
    id            bigint not null,
    provider_id   bigint,
    subscriber_id bigint,
    constraint relation_permissions_pkey
        primary key (id),
    constraint fk_user_relation_permissions_provider_id
        foreign key (provider_id) references users,
    constraint fk_user_relation_permissions_subscriber_id
        foreign key (subscriber_id) references users
);

create table reset_passwords
(
    id                 bigint not null,
    expiry_date        timestamp,
    generated_password varchar(1024),
    starttime          timestamp,
    user_id            bigint,
    constraint reset_passwords_pkey
        primary key (id),
    constraint fk_reset_passwords_user_id
        foreign key (user_id) references users
);

create table user_relation_permissions
(
    user_relation_permission_id bigint  not null,
    permission_id               integer not null,
    constraint user_relation_permissions_pkey
        primary key (user_relation_permission_id, permission_id),
    constraint fk_user_relation_permissions_permission_id
        foreign key (permission_id) references permissions,
    constraint fk_user_relation_permissions_user_relation_permission_id
        foreign key (user_relation_permission_id) references relation_permissions
);

create table user_roles
(
    user_id bigint  not null,
    role_id integer not null,
    constraint user_roles_pkey
        primary key (user_id, role_id),
    constraint fk_user_roles_role_id
        foreign key (role_id) references roles,
    constraint fk_user_roles_user_id
        foreign key (user_id) references users
);

