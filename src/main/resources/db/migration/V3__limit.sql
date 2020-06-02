alter table core.account ADD available_limit numeric(19,2);

update core.account set available_limit = 5000;
