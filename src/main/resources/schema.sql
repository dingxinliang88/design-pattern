CREATE TABLE IF NOT EXISTS product_item (
    id INT PRIMARY KEY, -- ID
    name VARCHAR(8) NOT NULL, -- 商品类目名称
    pid INT NOT NULL -- 父级类目SQL
);

CREATE TABLE IF NOT EXISTS business_launch (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    business_detail VARCHAR(32) NOT NULL,
    target_city VARCHAR(32),
    target_sex VARCHAR(8),
    target_product VARCHAR(32)
);