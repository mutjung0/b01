select * from board;

create table persistent_logins (
	username varchar(64) not null,
	series varchar(64) primary key,
	token varchar(64) not null,
	last_used timestamp not null
);

select * from persistent_logins;

select * from member;
select * from member_role_set;