-- ALTER TABLE products ALTER COLUMN description varchar(1024)
-- show columns from products
-- truncate table products;


CREATE TABLE category
(
    id            int          NOT NULL AUTO_INCREMENT,
    category_name varchar(255) NOT NULL,
    description   varchar(255),
    image_url     varchar(255),
    PRIMARY KEY (id)
);
INSERT INTO category (category_name, description, image_url)
VALUES ('fruits', 'a category for fruits',
        'https://unsplash.com/photos/a-building-with-a-window-and-a-door-rjslAIgMomM');
INSERT INTO category (category_name, description, image_url)
VALUES ('meat', 'a category for meat',
        'https://unsplash.com/photos/a-building-with-a-window-and-a-door-rjslAIgMomM');


CREATE TABLE products
(
    id          int            NOT NULL AUTO_INCREMENT,
    name        varchar(255)   NOT NULL,
    price       decimal(10, 2) NOT NULL,
    description varchar(1024),
    image_url   varchar(255),
    category_id int            NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE users
(
    id         int          NOT NULL AUTO_INCREMENT,
    first_name varchar(255) NOT NULL,
    last_name  varchar(255) NOT NULL,
    email      varchar(255) NOT NULL,
    password   varchar(255) NOT NULL,
    PRIMARY KEY (id)
);



CREATE TABLE wishlist
(
    id           int NOT NULL AUTO_INCREMENT,
    user_id      int NOT NULL,
    product_id   int NOT NULL,
    created_date datetime(6)     NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cartitems
(
    id           int NOT NULL AUTO_INCREMENT,
    user_id      int NOT NULL,
    product_id   int NOT NULL,
    quantity     int NOT NULL,
    created_date datetime(6)     NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id   int          NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);
insert into roles (name)
values ('USER');
insert into roles (name)
values ('ADMIN');


CREATE TABLE user_role
(
    id      int NOT NULL AUTO_INCREMENT,
    user_id int NOT NULL,
    role_id int NOT NULL,
    PRIMARY KEY (id)
);
