delete from therapy;
delete from patient;
delete from doctor;
delete from user;

insert into user (id,login,password,name,birthday,user_type) values
('1','defaultDoctorLogin','$2y$12$wfxeN1UsTpRtnPpRxHUZme.8.kRfmfyzsUrZtfLXtlk7VmigcYMEG','defaultDoctorName','1901-01-01','DOCTOR'),
('2','defaultPatientLogin','defaultpatientPass','defaultPatientName','1990-01-01','PATIENT');

insert into doctor (id) values ('1');
insert into patient (id,doctor_id) values ('2','1');


insert into user(id,login,password,name,birthday,user_type)
values('3','SetPatientLogin','SetPatientPass','SetPatientName','2000-01-01','PATIENT');

insert into patient (id,doctor_id) values ('3',null);