CREATE TABLE ACCOUNTS (
    id bigint auto_increment primary key,
    email varchar(255) not null,
    password varchar(255) not null
);

CREATE TABLE CURRENCIES (
    id bigint auto_increment primary key,
    currency_type enum('PLN', 'USD', 'EUR') not null,
    balance decimal(19, 4) not null,
    account_id bigint not null,
    constraint fk_product_id foreign key (account_id) references ACCOUNTS(id)
);