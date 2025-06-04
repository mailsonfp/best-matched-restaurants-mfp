create table restaurant (
    id int not null PRIMARY KEY auto_increment,
    name varchar,
    customer_rating int,
    distance int,
    price numeric(15,2),
    cuisine_id int);

create table cuisine(
    id int not null PRIMARY KEY auto_increment,
    name varchar
);

 ALTER TABLE restaurant ADD FOREIGN KEY (cuisine_id) REFERENCES cuisine(id);