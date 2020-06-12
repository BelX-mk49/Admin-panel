create table usr
(
    user_id  int(8)          not null,
    username varchar(255) not null,
    password varchar(255) not null,
    active   boolean,
    email    varchar(255) not null,
    primary key (user_id)
);
insert into usr(user_id, username, password, active, email)
values (1, 'admin', '$2y$08$3F4NZRyuhlwH3uQ8GLecp.e0zhdzpe//PjbGdQCOzxPJnFyMSTwlO', true, 'example@mail.ru');
