-- V2 里不需要重复写 V1 已经执行过的 SQL。 Flyway 会按版本依次执行，每个迁移文件只描述“这一次数据库发生了什么变化”。

CREATE INDEX idx_product_category_price
    on product (category_id, sale_price);