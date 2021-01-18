create table doctor (
    id INT not null,
    primary key (id),
    constraint iddoctor
      foreign key (id)
    references user (id)

) engine=InnoDB

NEXT_SCRIPT








