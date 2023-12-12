drop sequence hibernate_sequence if exists;
drop table training_enrollment if exists;
drop table training if exists;
drop table participant if exists;
drop table course if exists;
drop table address if exists;

create table address (
	id integer identity primary key,
	street varchar(50) not null,
	number varchar(50) not null,
	country varchar(15) not null
);

create table course (
	id integer identity primary key,
	title varchar(50) not null,
	duration integer,
	level varchar(15)
);

create table participant (
	id integer identity primary key,
	first_name varchar(10) not null,
	last_name varchar(10) not null,
	address_id integer
);

create table training (
	id integer identity primary key,
	course_id integer not null,
	start_date date,
	end_date date
);

create table training_enrollment (
	training_id integer not null,
	trainee_id integer not null,
);

create sequence hibernate_sequence;