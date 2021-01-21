create table patient (
    id INT not null,
    doctor_id INT,
    primary key (id),
    constraint idpatient
        foreign key(id)
        references user(id),
    constraint iddoctortopat
          foreign key(doctor_id)
        references doctor(id)

) engine=InnoDB;


