start transaction;

DROP TABLE IF EXISTS L_USER;

create table L_USER(
    user_id bigint not null,
    first_name varchar(100) default '' not null,
    middle_name varchar(100) default '' not null,
    last_name varchar(100) default '' not null,
    email varchar(100) not null,
    "role" varchar(100) not null,
    active boolean default false not null,
    primary key (user_id)
);

end transaction;