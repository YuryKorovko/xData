create table if not exists currency (
    `name` varchar primary key unique,
    crypto boolean not null
);

create table if not exists wallets(
    id bigserial primary key,
    currency varchar references currency(`name`),
    balance decimal(10, 6)
    );

create table if not exists users (
    id bigserial primary key not null,
    email varchar unique not null,
    password varchar not null,
    wallet_id bigserial references wallets(id)
);

alter table wallets add column user_id bigserial references users(id) not null;

create table if not exists transactions(
    id bigserial primary key,
    wallet_id bigserial references wallets(id),
    created_at timestamp default now() not null,
    amount decimal(10, 6)
);


