create table users (
   id int not null auto_increment,
   login varchar(45) not null,
   password varchar(45) not null,
   name varchar(45) not null,
   birthday DATE not null,
   user_type ENUM('doctor','patient') not null,
   primary key(id)

) engine=InnoDB

NEXT_SCRIPT
