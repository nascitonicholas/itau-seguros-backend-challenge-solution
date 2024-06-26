CREATE TABLE IF NOT EXISTS insurance_products (
  id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  name VARCHAR(255),
  category_name VARCHAR(255),
  base_price NUMERIC(17,2),
  tariffed_price NUMERIC(17,2),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP FOR EACH ROW ON UPDATE AS ROW CHANGE TIMESTAMP NOT NULL
);