create table stock_price (
	id INT AUTO_INCREMENT PRIMARY KEY,
    stock_code VARCHAR(20) NOT NULL,
	txn_date DATE NOT NULL,
    open_price DECIMAL(19,6) NOT NULL,
    high_price DECIMAL(19,6) NOT NULL,
    low_price DECIMAL(19,6) NOT NULL,
    close_price DECIMAL(19,6) NOT NULL,
    adj_close_price DECIMAL(19,6) NOT NULL,
    volume DECIMAL(19,6) NOT NULL,
    created_date timestamp DEFAULT current_timestamp
);
