create table if not exists currency
(
    name   varchar primary key unique,
    crypto boolean not null
);

insert into currency values ('BTC', true);
insert into currency values ('USDT', true);
insert into currency values ('USD', false);
insert into currency values ('EUR', false);
insert into currency values ('ETH', true);


