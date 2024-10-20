CREATE TABLE ACCOUNTS (
    id bigint auto_increment primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null
);

CREATE TABLE CURRENCIES (
    id bigint auto_increment primary key,
    currency_code enum('PLN', 'USD', 'AUD', 'CAD', 'EUR', 'HUF', 'CHF', 'GBP', 'JPY', 'CZK', 'DKK', 'NOK', 'SEK', 'XDR') not null,
    currency_value numeric(10, 4) not null,
    account_id bigint not null,
    CONSTRAINT fk_currencies_account_id FOREIGN KEY(account_id) REFERENCES ACCOUNTS(id) ON DELETE CASCADE
);

CREATE TABLE TRANSACTIONS (
    id bigint auto_increment primary key,
    transaction_type enum('SALE', 'PURCHASE', 'DEPOSIT', 'WITHDRAWAL') not null,
    currency_value numeric(10, 2) not null,
    currency_code enum('PLN', 'USD', 'AUD', 'CAD', 'EUR', 'HUF', 'CHF', 'GBP', 'JPY', 'CZK', 'DKK', 'NOK', 'SEK', 'XDR') not null,
    date timestamp not null,
    account_id bigint not null,
    CONSTRAINT fk_transactions_account_id FOREIGN KEY(account_id) REFERENCES ACCOUNTS(id) ON DELETE CASCADE
);




