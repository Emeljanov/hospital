create table user (
   id int not null auto_increment,
   login varchar(100) not null,
   password varchar(2555) not null,
   name varchar(2555) not null,
   birthday DATE not null,
   user_type ENUM('DOCTOR','PATIENT') not null,
   primary key(id)

) engine=InnoDB ;

