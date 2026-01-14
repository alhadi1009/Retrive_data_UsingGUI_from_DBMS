create table table1(
id int,
name varchar2(10));

create table table2(
id int,
salary int);

select *from table1 right join table2 on table1.id=table2.id;

 select *from table2;
INSERT INTO table1 (id, name) VALUES (1, 'Ali');
INSERT INTO table1 (id, name) VALUES (2, 'Bob');
INSERT INTO table1 (id, name) VALUES (3, 'Chin');
INSERT INTO table1 (id, name) VALUES (4, 'Dana');
INSERT INTO table1 (id, name) VALUES (5, 'Eva');

INSERT INTO table2 (id, salary) VALUES (1, 5000);
INSERT INTO table2 (id, salary) VALUES (2, 7000);
INSERT INTO table2 (id, salary) VALUES (3, 6000);
INSERT INTO table2 (id, salary) VALUES (6, 8000);
INSERT INTO table2 (id, salary) VALUES (7, 4500);

