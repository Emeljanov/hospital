create table doctor (
    doctor_id INT not null,
    primary key (doctor_id),
    constraint iddoctor
--    foreign key (id_doctor)
      foreign key (doctor_id)
    references user (id)

) engine=InnoDB

NEXT_SCRIPT








