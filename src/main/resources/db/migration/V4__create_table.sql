create table if not exists account.asset_history
(
    id        bigint auto_increment primary key,
    amount    int default 0 not null,
    timestamp timestamp     not null
)
