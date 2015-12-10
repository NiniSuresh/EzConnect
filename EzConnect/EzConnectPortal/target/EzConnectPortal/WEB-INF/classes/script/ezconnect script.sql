START TRANSACTION

CREATE TABLE User_Master(id INT NOT NULL AUTO_INCREMENT, NAME VARCHAR(30), role VARCHAR(20),security_code VARCHAR(20),phone_number VARCHAR(11) NOT NULL,PRIMARY KEY(id));
CREATE TABLE Call_Request(id INT NOT NULL AUTO_INCREMENT, created_date DATETIME NOT NULL,ezConnect_id VARCHAR(100) NOT NULL,
callee_1 VARCHAR(15) NOT NULL, callee_2 VARCHAR(15) NOT NULL,user_id INT,
PRIMARY KEY(id),
FOREIGN KEY(user_id) REFERENCES user_master(id));
CREATE TABLE Call_State(id INT NOT NULL AUTO_INCREMENT,created_date DATETIME NOT NULL,ezConnect_id VARCHAR(100) NOT NULL,
STATUS VARCHAR(20) NOT NULL,PRIMARY KEY(id));
CREATE TABLE Call_Data(id INT NOT NULL AUTO_INCREMENT, created_date DATETIME NOT NULL,ezConnect_id VARCHAR(100) NOT NULL,
call_duration INT, recording LONGBLOB,PRIMARY KEY(id));
CREATE TABLE Active_Device(id INT NOT NULL AUTO_INCREMENT, IMEI VARCHAR(30), IP_address VARCHAR(20),created_date DATETIME NOT NULL,PRIMARY KEY(id));
INSERT INTO `ezconnect`.`user_master` 
	(
	`NAME`, 
	`role`, 
	`security_code`,
	`phone_number`
	)
	VALUES
	( 
	'Vikas', 
	'HR', 
	'abc123',
	'9899276356'
	);

ROLLBACK

/*
drop table Call_Request;
drop table User_Master;
drop table Call_State;
drop table User_Master;
drop table Call_Data;
*/