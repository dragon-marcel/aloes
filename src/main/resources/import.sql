INSERT INTO Client (id,name,surname,number,email,create_date) VALUES ('1','Marcel','Dragon','796-105-227','dragon.marcel@o2.pl','2018-02-12');
INSERT INTO Client (id,name,surname,number,email,create_date) VALUES ('2','Madrcel','fsdf','796-105-227','drako@o2.pl','2018-02-12');
INSERT INTO Client (id,name,surname,number,email,create_date) VALUES ('3','Mardscel','Dragon','796-105-227','drako@o2.pl','2018-02-12');
INSERT INTO Client (id,name,surname,number,email,create_date) VALUES ('4','Marsdcel','sd','796-105-227','drako@o2.pl','2018-02-12');
INSERT INTO Client (id,name,surname,number,email,create_date) VALUES ('5','d','d','796-105-227','drako@o2.pl','2018-02-12');
INSERT INTO Client (id,name,surname,number,email,create_date) VALUES ('7','Marcel','Dragon','796-105-227','drako@o2.pl','2018-02-12');

/*MASSAGE */
INSERT INTO Massage (id,name,price) VALUES ('1','Massage Head','24');
INSERT INTO Massage (id,name,price) VALUES ('2','Massage Leg','44');
INSERT INTO Massage (id,name,price) VALUES ('3','Massage Neck','4.9');

/*VISIT */
INSERT INTO Visits (id,create_date,description,visit_date,send_email,visit_time,status,client_id)VALUES ('1','2019-12-12','Massage','2016-03-12',false ,'12:12:12',TRUE,'1');
INSERT  INTO Items_visit(id,quantity,massage_id,items_id) VALUES ('1','4','2','1');
INSERT  INTO Items_visit(id,quantity,massage_id,items_id) VALUES ('2','3','1','1');

INSERT INTO Visits (id,create_date,description,visit_date,send_email,visit_time,status,client_id)VALUES ('2','2013-11-14','Massage','2013-12-22',false ,'12:22:12',FALSE,'1');
INSERT  INTO Items_visit(id,quantity,massage_id,items_id) VALUES ('3','4','2','2');
INSERT  INTO Items_visit(id,quantity,massage_id,items_id) VALUES ('4','1','1','2');

INSERT INTO Visits (id,create_date,description,visit_date,send_email,visit_time,status,client_id)VALUES ('3','2015-12-22','Massage','2013-01-22',false ,'12:12:12',TRUE,'5');
INSERT  INTO Items_visit(id,quantity,massage_id,items_id) VALUES ('5','4','2','3');
INSERT  INTO Items_visit(id,quantity,massage_id,items_id) VALUES ('6','3','1','3');

INSERT INTO Visits (id,create_date,description,visit_date,send_email,visit_time,status,client_id)VALUES ('4','2017-12-12','Massage','2013-01-07',false ,'12:12:12',FALSE,'4');
INSERT  INTO Items_visit(id,quantity,massage_id,items_id) VALUES ('7','4','2','4');
INSERT  INTO Items_visit(id,quantity,massage_id,items_id) VALUES ('8','3','1','4');

INSERT INTO Visits (id,create_date,description,visit_date,send_email,visit_time,status,client_id)VALUES ('5','2019-11-12','Massage','2016-11-20',false ,'12:12:12',TRUE,'3');
INSERT  INTO Items_visit(id,quantity,massage_id,items_id) VALUES ('9','4','2','5');
INSERT  INTO Items_visit(id,quantity,massage_id,items_id) VALUES ('10','3','1','5');

/*USERS*/
INSERT INTO User (id,user_name,password,role,enabled) VALUES ('1','admin','$2a$10$2mBQiQ6VvWtQVprEt7LTRu8pToAc1YvhM/glDVBPxeEdPqJYA1t.i','ADMIN',TRUE );
INSERT INTO User (id,user_name,password,role,enabled) VALUES ('2','user','$2a$10$pGUnbw64o4s6u7JkAxsZDuqf81vBoOOD5uNYvc7kvhXHTxAiyb3YS','USER',TRUE );
