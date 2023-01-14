drop table if exists statement;
drop table if exists category;
drop table if exists main_category;
drop table if exists account_card;
drop table if exists user;

create table if not exists account.account_card
(
    id         bigint auto_increment
        primary key,
    amount     int default 0 not null,
    created_at datetime(6)   null,
    name       varchar(255)  null,
    type       int           not null,
    updated_at datetime(6)   null
);

create table if not exists account.main_category
(
    id           bigint auto_increment
        primary key,
    created_at   datetime(6)    null,
    name         varchar(255)   null,
    updated_at   datetime(6)    null,
    weekly_limit int default -1 not null,
    constraint UK_gscoqiursggkh41pca2ct489l
        unique (name)
);

create table if not exists account.category
(
    id               bigint auto_increment
        primary key,
    name             varchar(255) null,
    type             int          not null,
    main_category_id bigint       not null,
    constraint UKhphvyvxe5rg0120b0mn4rti17
        unique (name, main_category_id),
    constraint FK1o9opwme2q425qr8iwr3jqqml
        foreign key (main_category_id) references account.main_category (id)
);

create table if not exists account.statement
(
    id              bigint auto_increment
        primary key,
    amount          int           not null,
    created_at      datetime(6)   null,
    date            datetime(6)   null,
    discount        int default 0 not null,
    name            varchar(255)  null,
    updated_at      datetime(6)   null,
    account_card_id bigint        not null,
    category_id     bigint        not null,
    constraint FKb49ck9t9smm3byugc5nfcrwyj
        foreign key (category_id) references account.category (id),
    constraint FKq5ngb49sb940bb1bdqpor122w
        foreign key (account_card_id) references account.account_card (id)
);

create index idx_statement_account_card
    on account.statement (account_card_id);

create index idx_statement_date
    on account.statement (date);

create table if not exists account.user
(
    id            bigint auto_increment
        primary key,
    created_at    datetime(6)  null,
    last_login_at datetime(6)  null,
    name          varchar(30)  not null,
    password      varchar(255) not null,
    role          varchar(255) null,
    updated_at    datetime(6)  null,
    username      varchar(30)  null,
    constraint UK_sb8bbouer5wak8vyiiy4pf2bx
        unique (username)
);

