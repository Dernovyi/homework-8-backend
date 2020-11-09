DROP TABLE if exists notes;
CREATE TABLE `notes` (id BIGINT(20) primary key auto_increment, text VARCHAR (5000) not null , createdOn datetime,  updatedOn datetime);
