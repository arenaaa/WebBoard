create table users (
	seq int(10) not null autoincrement ,
	userid varchar(24) not null,
	email varchar(128) not null,
	password varchar(40) not null
)