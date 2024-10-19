INSERT INTO ACCOUNTS (first_name, last_name, email, password) VALUES ('admin', 'adminowski', 'admin@gmail.com', 'admin123');
INSERT INTO CURRENCIES (currency_code, currency_value, account_id) VALUES ('PLN', '100.5000', 1);
INSERT INTO TRANSACTIONS (transaction_type, currency_value, currency_code, account_id, date) VALUES ('DEPOSIT', '100.5000', 'PLN', 1, '2024-07-23');