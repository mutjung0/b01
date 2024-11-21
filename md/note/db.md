Hibernate:

    create table board (
       bno bigint not null auto_increment,
        moddate datetime(6),
        regdate datetime(6),
        content varchar(2000) not null,
        title varchar(500) not null,
        writer varchar(50) not null,
        primary key (bno)
    ) engine=InnoDB
Hibernate:

    create table board_image (
       uuid varchar(255) not null,
        file_name varchar(255),
        ord integer not null,
        board_bno bigint,
        primary key (uuid)
    ) engine=InnoDB
Hibernate:

    create table member (
       mid varchar(255) not null,
        moddate datetime(6),
        regdate datetime(6),
        del bit not null,
        email varchar(255),
        mpw varchar(255),
        social bit not null,
        primary key (mid)
    ) engine=InnoDB
Hibernate:

    create table member_role_set (
       member_mid varchar(255) not null,
        role_set integer
    ) engine=InnoDB
Hibernate:

    create table reply (
       rno bigint not null auto_increment,
        moddate datetime(6),
        regdate datetime(6),
        reply_text varchar(255),
        replyer varchar(255),
        board_bno bigint,
        primary key (rno)
    ) engine=InnoDB
Hibernate: create index idx_reply_board_bno on reply (board_bno)
Hibernate:

    alter table board_image 
       add constraint FKo4dbcmbib7vwlk8eplv2cwbe2 
       foreign key (board_bno) 
       references board (bno)
Hibernate:

    alter table member_role_set 
       add constraint FKfjsrs6u4t2kvu8l1brmn8r00a 
       foreign key (member_mid) 
       references member (mid)
Hibernate:

    alter table reply 
       add constraint FKr1bmblqir7dalmh47ngwo7mcs 
       foreign key (board_bno) 


## view
```shell
Hibernate: 
    select
        board0_.bno as bno1_0_0_,
        imageset1_.uuid as uuid1_1_1_,
        board0_.moddate as moddate2_0_0_,
        board0_.regdate as regdate3_0_0_,
        board0_.content as content4_0_0_,
        board0_.title as title5_0_0_,
        board0_.writer as writer6_0_0_,
        imageset1_.board_bno as board_bn4_1_1_,
        imageset1_.file_name as file_nam2_1_1_,
        imageset1_.ord as ord3_1_1_,
        imageset1_.board_bno as board_bn4_1_0__,
        imageset1_.uuid as uuid1_1_0__ 
    from
        board board0_ 
    left outer join
        board_image imageset1_ 
            on board0_.bno=imageset1_.board_bno 
    where
        board0_.bno=?
        
Hibernate: 
    select
        reply0_.rno as rno1_2_,
        reply0_.moddate as moddate2_2_,
        reply0_.regdate as regdate3_2_,
        reply0_.board_bno as board_bn6_2_,
        reply0_.reply_text as reply_te4_2_,
        reply0_.replyer as replyer5_2_ 
    from
        reply reply0_ 
    where
        reply0_.board_bno=? 
    order by
        reply0_.rno asc limit ?
```

## list
```shell
Hibernate: 
    select
        board0_.bno as col_0_0_,
        board0_.title as col_1_0_,
        board0_.writer as col_2_0_,
        board0_.regdate as col_3_0_,
        count(reply1_.rno) as col_4_0_ 
    from
        board board0_ 
    left outer join
        reply reply1_ 
            on (
                reply1_.board_bno=board0_.bno
            ) 
    where
        board0_.bno>? 
    group by
        board0_.bno 
    order by
        board0_.bno desc limit ?
Hibernate: 
    select
        count(distinct board0_.bno) as col_0_0_ 
    from
        board board0_ 
    left outer join
        reply reply1_ 
            on (
                reply1_.board_bno=board0_.bno
            ) 
    where
        board0_.bno>?
```