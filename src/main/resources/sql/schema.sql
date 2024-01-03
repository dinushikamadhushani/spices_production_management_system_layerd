Drop database if exists spices_production_management_system;

create database if not exists spices_production_management_system;

use spices_production_management_system;


CREATE TABLE employee(
                         employee_id VARCHAR(20) PRIMARY KEY,
                         employee_name VARCHAR(25) NOT NULL,
                         email VARCHAR(30),
                         tel INT (20),
                         job_title VARCHAR(15) NOT NULL,
                         salary DOUBLE NOT NULL,
                         date DATE,
                         INDEX(email)



);

DESC employee;



CREATE TABLE user(
                     user_id VARCHAR(20) PRIMARY KEY,
                     user_name VARCHAR(25) NOT NULL,
                     email VARCHAR(30),
                     password VARCHAR(20) NOT NULL,
                     employee_id VARCHAR(20) NOT NULL,
                     FOREIGN KEY(employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE customer(
                         customer_id VARCHAR(20) PRIMARY KEY,
                         customer_name VARCHAR(25) NOT NULL,
                         address VARCHAR(20) NOT NULL,
                         tel INT(15)
);


DESC customer;


CREATE TABLE orders(
                       Order_id VARCHAR(20) PRIMARY KEY,
                       date date NOT NULL,
                       customer_id VARCHAR(20) NOT NULL,

                      FOREIGN KEY(customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE ON UPDATE CASCADE
);

DESC orders;

create table raw_material(
                             rawMaterial_id VARCHAR(20) PRIMARY KEY,
                             material_name VARCHAR(25) NOT NULL,
                             qty_on_stock DOUBLE NOT NULL,
                             unit_price DOUBLE NOT NULL
);

DESC raw_material;



CREATE TABLE item(
                     item_id VARCHAR(20) PRIMARY KEY,
                     item_name VARCHAR(25) NOT NULL,
                     unit_price DOUBLE NOT NULL,
                     qty_on_hand INT(30),
                     rawMaterial_id VARCHAR(20),
                     CONSTRAINT FOREIGN KEY(rawMaterial_id) REFERENCES raw_material(rawMaterial_id) ON DELETE CASCADE ON UPDATE CASCADE
);


DESC item;


CREATE TABLE Order_detail(
                             Order_id VARCHAR(20) NOT NULL,
                             item_id VARCHAR(20) NOT NULL,
                             qty INT(25),
                             unit_price DOUBLE NOT NULL,
                             CONSTRAINT FOREIGN KEY(Order_id) REFERENCES orders(Order_id) ON DELETE CASCADE ON UPDATE CASCADE,
                             CONSTRAINT FOREIGN KEY(item_id) REFERENCES item(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);


DESC Order_detail;



CREATE TABLE delivery(
                         delivery_id VARCHAR(20) PRIMARY KEY,
                         Order_id VARCHAR(20)NOT NULL,
                         employee_id VARCHAR(20) NOT NULL,
                         location VARCHAR(25) ,
                         delivery_status VARCHAR(20),
                         email Varchar(35),


                         CONSTRAINT FOREIGN KEY(employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE ON UPDATE CASCADE,
                         CONSTRAINT FOREIGN KEY(Order_id) REFERENCES orders(Order_id) ON DELETE CASCADE ON UPDATE CASCADE,
                         CONSTRAINT FOREIGN KEY(email) REFERENCES employee(email) ON DELETE CASCADE ON UPDATE CASCADE
);


DESC delivery;


CREATE TABLE supplier(
                         supplier_id VARCHAR(20) PRIMARY KEY,
                         supplier_name VARCHAR(25)NOT NULL,

                         address VARCHAR(15) NOT NULL,
                         tel INT (20)
);


DESC supplier;


CREATE TABLE supplier_detail(
                                supplier_id VARCHAR(20) NOT NULL,
                                rawMaterial_id VARCHAR(20) NOT NULL,
                                date DATE,
                                unit_price DOUBLE NOT NULL,
                                qty_on_stock INT(25),
                                FOREIGN KEY(supplier_id) REFERENCES supplier(supplier_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                FOREIGN KEY(rawMaterial_id) REFERENCES raw_material (rawMaterial_id) ON DELETE CASCADE ON UPDATE CASCADE
);

DESC supplier_detail;

INSERT INTO employee VALUES('E001', 'Dinu Madhushani', 'madhushanidinushika1@gmail.com', 0713356788,'Manager',50000.00 ,'2023-10-5');
INSERT INTO employee VALUES('E002', 'Nadee Wikramage', 'nadeesamaraweera2000@gmail.com', 0712230556,'Worker',40000.00 ,'2023-10-5');
INSERT INTO employee VALUES('E003', 'Kamal weerasekara', 'kamal@gmail.com', 0713356733,'Worker',35000.00 ,'2023-10-5');
INSERT INTO employee VALUES('E004', 'Nimal', 'nimal@gmail.com', 0714456788,'Worker',35000.00 ,'2023-10-5');


INSERT INTO user VALUES ('U001','Meri','madhushanidinushika1@gmail.com','1111','E001');



