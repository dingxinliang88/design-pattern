INSERT INTO product_item(id, name, pid) VALUES
(1, '商城', 0),
(2, '电脑', 1),
(3, '书籍', 1),
(4, '台式电脑', 2),
(5, '笔记本电脑', 2),
(6, '游戏电脑', 4),
(7, '办公电脑', 4),
(8, '教育类', 3),
(9, '科普类', 3),
(10, '九年义务教育书籍', 8);

INSERT INTO business_launch(id, business_detail, target_city, target_sex, target_product) VALUES
(1, '苹果计算机投放业务', '','','Computer,Phone'),
(2, '某奢侈品投放业务', '', 'F', 'Female Bag'),
(3, '北方某店投放业务', 'bj,tj', '', ''),
(4, '平台优惠券', '', '', '');

INSERT INTO products (id, product_id, send_red_bag) VALUES
(1, '100', 0),
(2, '101', 1);