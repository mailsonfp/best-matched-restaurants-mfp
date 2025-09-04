create table restaurant (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar,
    customer_rating int,
    distance int,
    price numeric(15,2),
    cuisine_id int);

create table cuisine(
    id INT PRIMARY KEY,
    name varchar
);

 ALTER TABLE restaurant ADD FOREIGN KEY (cuisine_id) REFERENCES cuisine(id);