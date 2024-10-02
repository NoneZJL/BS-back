create table if not exists `user` (
    id int not null auto_increment ,
    username varchar(25) unique not null ,
    password varchar(25) not null ,
    email varchar(64) unique not null ,
    primary key (id)
);