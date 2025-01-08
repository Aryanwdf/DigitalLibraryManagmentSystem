DROP TABLE if exists Admin;
DROP TABLE if exists Books;
DROP TABLE if exists Student;
DROP TABLE if exists Faculty;
DROp TABLE if exists BookIssue;

CREATE TABLE
  if not exists Admin (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
  );

INSERT INTO
  Admin (username, password)
VALUES
  ('admin', 'admin');

CREATE TABLE
  if not exists Books (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    publisher VARCHAR(100) NOT NULL,
    isbn VARCHAR(100) NOT NULL,
    remark VARCHAR(100) NOT NULL,
    entryDate DATE NOT NULL,
    author VARCHAR(100) NOT NULL,
    price DOUBLE (10, 2) NOT NULL,
    pages INT NOT NULL,
    year INT NOT NULL,
    status VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
  );

insert into Books(isbn,name,author,price,pages,publisher,year,status,remark,entryDate) values('9780134190563','The Go Programming Language','Alan A. A. Donovan and Brian W. Kernighan',400,80,'Jaico Publishing House',2000,'Available','','2024-06-06');
insert into Books(isbn,name,author,price,pages,publisher,year,status,remark,entryDate) values('9780133053036','C++ Primer','Stanley Lippman and Josée Lajoie and Barbara Moo',976,113,'Westland Publications',2025,'Available','','2024-08-06');
insert into Books(isbn,name,author,price,pages,publisher,year,status,remark,entryDate) values('9781718500457','The Rust Programming Language','Steve Klabnik and Carol Nichols',560,112,'Penguin Random House India',2015,'Available','','2024-01-06');
insert into Books(isbn,name,author,price,pages,publisher,year,status,remark,entryDate) values('9781491910740','Head First Java','Kathy Sierra and Bert Bates and Trisha Gee',754,213,'Roli Books',2023,'Available','','2024-02-06');
insert into Books(isbn,name,author,price,pages,publisher,year,status,remark,entryDate) values('9781492056300','Fluent Python','Luciano Ramalho',1014,50,'Rupa Publications',2007,'Available','','2024-09-05');
insert into Books(isbn,name,author,price,pages,publisher,year,status,remark,entryDate) values('9781720043997','The Road to Learn React','Robin Wieruch',239,118,'Hachette India',2008,'Available','','2024-09-06');
insert into Books(isbn,name,author,price,pages,publisher,year,status,remark,entryDate) values('9780132350884','Clean Code: A Handbook of Agile Software Craftsmanship','Robert C Martin',288,37,'Aleph Book Company',2020,'Available','','2024-09-20');
insert into Books(isbn,name,author,price,pages,publisher,year,status,remark,entryDate) values('9780132181273','Domain-Driven Design','Eric Evans',560,218,'HarperCollins Publishers India',2018,'Available','','2024-09-30');
insert into Books(isbn,name,author,price,pages,publisher,year,status,remark,entryDate) values('9781951204006','A Programmers Guide to Computer Science','William Springer',188,49,'Pan Macmillan India',2014,'Available','','2024-09-25');
insert into Books(isbn,name,author,price,pages,publisher,year,status,remark,entryDate) values('9780316204552','The Soul of a New Machine','Tracy Kidder',293,310,'Scholastic India',2013,'Available','','2024-09-13');
insert into Books(isbn,name,author,price,pages,publisher,year,status,remark,entryDate) values('9780132778046','Effective Java','Joshua Bloch',368,221,'Westland Publications',2019,'Available','','2024-09-19');
insert into Books(isbn,name,author,price,pages,publisher,year,status,remark,entryDate) values('9781484255995','Practical Rust Projects','Shing Lyu',257,125,'Westland Publications',2017,'Available','','2024-09-16');

CREATE TABLE
  if not exists Student (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(10),
    year INT NOT NULL,
    PRIMARY KEY (id)
  );

insert into Student(name,contact,year) values('Anna Klein', '9742852354', 2020);
insert into Student(name,contact,year) values('Henry Weber', '8596425632', 2019);
insert into Student(name,contact,year) values('Elisabeth Fischer', '2354859632', 2021);
insert into Student(name,contact,year) values('Jacob Herrman', '4526529856', 2024);
insert into Student(name,contact,year) values('Luisa Muller', '7452365896', 2020);
insert into Student(name,contact,year) values('Emma Wagner', '4526579962', 2019);


CREATE TABLE
  if not exists Faculty (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(10),
    department VARCHAR(100),
    PRIMARY KEY (id)
  );

insert into Faculty(name,contact,department) values('Gisela Schulz', '7456325985', 'Computer Science');
insert into Faculty(name,contact,department) values('Jonas Wolf', '2652698563', 'Electrical Engineering');
insert into Faculty(name,contact,department) values('Matteo Koch', '7458965236', 'Civil Engineering');
insert into Faculty(name,contact,department) values('Adele Becker', '2478563598', 'Electronics Engineering');
insert into Faculty(name,contact,department) values('Felix Bauer', '4785635698', 'Administration');
insert into Faculty(name,contact,department) values('Elke Hoffmann', '4526589652', 'Payroll');

CREATE TABLE
  if not exists BookIssue (
    id INT NOT NULL AUTO_INCREMENT,
    studentID INT,
    employeeID INT,
    bookID INT,
    PRIMARY KEY (id)
  );

