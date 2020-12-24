create table therapies (
   id_therapy INT not null auto_increment,
   description varchar(45) not null,
   start_date DATE not null,
   end_date DATE not null,
   id_patient INT not null,
   primary key(id_therapy),
   constraint idpatient_thr
        foreign key (id_patient)
        references patients (id_patient)
) engine=InnoDB


