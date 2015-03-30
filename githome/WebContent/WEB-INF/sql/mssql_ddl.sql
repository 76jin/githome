create table guestbook_message (
    message_id int not null IDENTITY(1,1) primary key,
    guest_name varchar(50) not null,
    password varchar(10) not null,
    message text not null
)