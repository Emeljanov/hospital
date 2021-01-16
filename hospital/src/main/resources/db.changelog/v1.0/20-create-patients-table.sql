create table patient (
    patient_id INT not null,
    doctor_id INT not null,
    primary key (patient_id),
    constraint idpatient
--        foreign key(id_patient)
        foreign key(patient_id)
        references user(id),
    constraint iddoctortopat
--        foreign key(id_doctor)
          foreign key(doctor_id)
        references doctor(doctor_id)

) engine=InnoDB

NEXT_SCRIPT

