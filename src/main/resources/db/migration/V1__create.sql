CREATE SCHEMA IF NOT EXISTS core;

create table core.account(
    id                      bigserial           not null
        constraint account_pkey primary key,
    document_number         varchar(20)         not null,
    balance                 numeric(19,2)       not null,
    created_at              timestamp           not null
);

create table core.transaction(
    id                      bigserial           not null
        constraint transaction_pkey primary key,
    account_id              bigint              not null
        constraint fk_account_id references core.account,
    amount                  numeric(19,2)       not null,
    operation_type          varchar(50)         not null,
    created_at              timestamp           not null
);
