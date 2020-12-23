create table patients (
    id_patient INT not null,
    id_doctor INT not null,
    primary key (id_patient),
    constraint idpatient
        foreign key(id_patient)
        references users(id),
    constraint iddoctortopat
        foreign key(id_doctor)
        references doctors(id_doctor)

) engine=InnoDB

NEXT_SCRIPT

