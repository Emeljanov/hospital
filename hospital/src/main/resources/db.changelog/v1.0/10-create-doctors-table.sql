create table doctors (
    id_doctor INT not null,
    primary key (id_doctor),
    constraint iddoctor
    foreign key (id_doctor)
    references users (id)

) engine=InnoDB

NEXT_SCRIPT








