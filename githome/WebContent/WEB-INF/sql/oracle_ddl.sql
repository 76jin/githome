create sequence message_id_seq increment by 1 start with 1;

create table guestbook_message (
    message_id int not null primary key,
    guest_name varchar(50) not null,
    password varchar(10) not null,
    message long varchar not null
)