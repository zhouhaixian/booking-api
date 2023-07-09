create table if not exists `user` (
    `id` int(11) unsigned not null auto_increment,
    `name` varchar(16) not null,
    `password` varchar(64) not null,
    `phone` varchar(11) not null,
    `gender` enum('FEMALE', 'MALE') not null,
    `subject` varchar(32) not null,
    `role` enum('ADMIN', 'USER') not null default 'USER',
    `created_at` datetime not null default current_timestamp,
    `updated_at` datetime not null default current_timestamp on update current_timestamp,
    primary key (`id`),
    Constraint PHONE_UNIQUE unique key (`phone`)
) engine=InnoDB default charset=utf8;

create table if not exists `config` (
    `config_key` varchar(64) not null,
    `config_value` text not null,
    primary key (`config_key`)
) engine=InnoDB default charset=utf8;

create table if not exists `record` (
    `id` int(11) unsigned not null auto_increment,
    `name` varchar(16) not null,
    `phone` varchar(11) not null,
    `grade` varchar(16) not null,
    `class` int(11) unsigned not null,
    `subject` varchar(16) not null,
    `record_index` int(11) unsigned not null,
    `start_time` datetime not null,
    `end_time` datetime not null,
    `created_at` datetime not null default current_timestamp,
    primary key (`id`)
) engine=InnoDB default charset=utf8;