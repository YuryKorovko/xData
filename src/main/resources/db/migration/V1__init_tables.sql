create table if not exists currency
(
    name   varchar primary key unique,
    crypto boolean not null
);

create table if not exists wallets
(
    id         bigserial primary key not null,
    currency   varchar references currency (name),
    balance    decimal(10, 6),
    network_id varchar unique        not null
);

create table if not exists users
(
    id       bigserial primary key not null,
    email    varchar unique        not null,
    password varchar               not null
);

alter table wallets
    add column user_id bigint references users (id);

create table if not exists transactions
(
    id             bigserial primary key              not null,
    to_wallet_id   bigint references wallets (id)     not null,
    from_wallet_id bigint references wallets (id)     not null,
    created_at     timestamp default now()            not null,
    amount         decimal(10, 6)                     not null,
    status         varchar                            not null,
    operation      varchar                            not null,
    currency       varchar references currency (name) not null
);


