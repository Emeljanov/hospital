create table therapy (
   id INT NOT NULL auto_increment,
   description VARCHAR(255) not null,
   start_date DATE NOT NULL,
   end_date DATE NOT NULL,
   patient_id INT NOT NULL,
   primary key(id),
   constraint idpatient_thr
          foreign key (patient_id)
        references patient (id)
) engine=InnoDB;


