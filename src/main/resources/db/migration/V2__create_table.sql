create table if not exists account.asset
(
    id          bigint auto_increment primary key,
    name        varchar(30) not null,
    type        int         not null,
    amount      int                  default 0 not null,
    description text        null,
    created_at  timestamp   not null default current_timestamp,
    updated_at  timestamp   not null default current_timestamp on update current_timestamp
);


create table if not exists account.loan
(
    id             bigint auto_increment primary key,
    name           varchar(30) not null,
    principal      int         not null,
    amount         int                  default 0 not null,
    interest_rate  double      not null,
    total_period   int         not null,
    current_period int                  default 0 not null,
    description    text        null,
    created_at     timestamp   not null default current_timestamp,
    updated_at     timestamp   not null default current_timestamp on update current_timestamp
);

create index idx_asset_updated_at on account.asset (updated_at);
create index idx_asset_type on account.asset (type);
create index idx_loan_updated_at on account.loan (updated_at);

