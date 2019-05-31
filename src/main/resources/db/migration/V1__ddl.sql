create sequence hibernate_sequence;

create table permissions
(
    id          integer not null,
    description varchar(64),
    name        varchar(64),
    shortcut    varchar(10),
    constraint permissions_pkey
        primary key (id),
    constraint uk_pnvtwliis6p05pn6i3ndjrqt2
        unique (name),
    constraint uk_g2rmf7gqfiy4nnpgn5dvm93o2
        unique (shortcut)
);

create table roles
(
    id          integer not null,
    description varchar(64),
    name        varchar(64),
    constraint roles_pkey
        primary key (id),
    constraint uk_ofx66keruapi6vyqpv6f2or37
        unique (name)
);

create table role_permissions
(
    role_id       integer not null,
    permission_id integer not null,
    constraint role_permissions_pkey
        primary key (role_id, permission_id),
    constraint fkegdk29eiy7mdtefy5c7eirr6e
        foreign key (permission_id) references permissions,
    constraint fkn5fotdgk8d1xvo8nav9uv3muc
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
    constraint uk_fvl6k04x11pern525noiw5k6v
        unique (token),
    constraint uk_97k2k0b1p7rph9rbpr6um0ltw
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
    constraint uk_r43af9ap4edm43mmtq01oddj6
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
    constraint fkqd0xlwl87en0dd3og131b7mxg
        foreign key (permission_id) references permissions,
    constraint fknwsjvj80u1boqvqocf2f6m7v9
        foreign key (user_relation_permission_id) references relation_permissions
);

create table user_roles
(
    user_id bigint  not null,
    role_id integer not null,
    constraint user_roles_pkey
        primary key (user_id, role_id),
    constraint fkh8ciramu9cc9q3qcqiv4ue8a6
        foreign key (role_id) references roles,
    constraint fkhfh9dx7w3ubf1co1vdev94g3f
        foreign key (user_id) references users
);

