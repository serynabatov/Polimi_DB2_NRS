use user_responce;
show tables;
show triggers;
CREATE TABLE `user` (
	`user_id` int NOT NULL auto_increment,
    `username` varchar(50) NOT NULL,
    `password` varchar(50) NOT NULL,
    `mail` varchar(50) NOT NULL,
	`points` int default 0 NOT NULL, 
    `blocked` boolean default 0 NOT NULL,
    primary key(`user_id`)
);

CREATE TABLE `time_logged_in` (
	`id` int NOT NULL auto_increment,
	`user_id` int NOT NULL,
    `logged_in` datetime default current_timestamp NOT NULL,
    primary key(`id`),
	constraint `time_log` foreign key (`user_id`) references `user` (`user_id`) on delete cascade
);

CREATE TABLE `questions` (
	`question_id` int NOT NULL auto_increment,
	`text` varchar(250) NOT NULL,
    primary key(`question_id`)
);

CREATE TABLE `responses` (
	`response_id` int NOT NULL auto_increment,
    `text` varchar(250) NOT NULL,
    `question_id` int NOT NULL,
    `user_id` int NOT NULL,
	`response_datetime` datetime default current_timestamp NOT NULL,
    primary key(`response_id`),
    constraint `response_to_user` foreign key (`user_id`) references `user` (`user_id`) on delete cascade,
    constraint `response_to_question` foreign key (`user_id`) references `questions` (`question_id`)
);

CREATE TABLE `statistical_responses` (
	`stat_id` int NOT NULL auto_increment,
	`user_id` int NOT NULL,
    `age` int NOT NULL,
    `sex` boolean NOT NULL,
    `exp_level` int NOT NULL,
	`response_date` datetime default current_timestamp NOT NULL,
    primary key(`stat_id`),
    constraint `stat_response_to_user` foreign key (`user_id`) references `user` (`user_id`) on delete cascade
);

CREATE TABLE `product` (
	`product_id` int NOT NULL auto_increment,
	`linkimage` varchar(100) NOT NULL,
    `product_name` varchar(50) NOT NULL,
    primary key(`product_id`)
);

CREATE TABLE `productOTD` (
	`productOTD_id` int NOT NULL auto_increment,
	`product_id` int NOT NULL,
    `productOTD_date` date,
    primary key(`productOTD_id`),
    constraint `productOTD_to_product` foreign key (`product_id`) references `product` (`product_id`) on delete cascade
);

create trigger `setting_date`
after insert on `productOTD` 
for each row 
update `productOTD` set `productOTD_date`=current_date()
where  new.productOTD_id = `productOTD_id`;


create table `offensive_words`(
	word varchar(50) not null,
    primary key(word)
);

create trigger `adding_mandatory_points`
after insert on `responses` 
for each row 
update `user` set `points`=`points`+ 1
where new.user_id = `user_id`;

create trigger `adding_optional_points`
after insert on `statistical_responses` 
for each row 
update `user` set `points`=`points`+ 2
where new.user_id = `user_id`;

#insert `user` (`username`,`password`,`mail`) 
#values ('nik','nik','nik');

#insert `user` (`username`,`password`,`mail`,`points`) 
#values ('nik3','nik3','nik3', 1);

#insert `response` (`text`,`question_id`,`user_id`) 
#values ('blablabla','1','1');

#insert `response` (`text`,`question_id`,`user_id`) 
#values ('blablabla','3','3');


#select * from `product`;

