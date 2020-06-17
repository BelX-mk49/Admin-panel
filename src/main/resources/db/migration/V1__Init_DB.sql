create table usr
(
    user_id  serial        not null,
    username varchar(255) not null,
    password varchar(255) not null,
    active   boolean,
    email    varchar(255) not null,
    registration_date    varchar(255) not null,
    last_login_date    varchar(255) not null,
    primary key (user_id)
);
