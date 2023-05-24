create table if not exists post_liked_users (
                                  post_id int8 not null,
                                  liked_users varchar(255)
);

create table if not exists tbl_comments (
                              id  bigserial not null,
                              created_date timestamp,
                              message text not null,
                              user_id int8 not null,
                              username varchar(255) not null,
                              post_id int8,
                              primary key (id)
);

create table if not exists tbl_images (
                            id  bigserial not null,
                            image_bytes bytea,
                            name varchar(255) not null,
                            post_id int8,
                            user_id int8,
                            primary key (id)
);

create table if not exists tbl_posts (
                           id  bigserial not null,
                           caption varchar(255),
                           created_date timestamp,
                           likes int4,
                           location varchar(255),
                           title varchar(255),
                           user_id int8,
                           primary key (id)
);

create table if not exists tbl_users (
                           id  bigserial not null,
                           bio text,
                           created_date timestamp,
                           email varchar(255),
                           firstname varchar(255) not null,
                           lastname varchar(255) not null,
                           password varchar(3000),
                           username varchar(255),
                           primary key (id)
);

create table if not exists user_role (
                           user_id int8 not null,
                           roles int4
);

alter table tbl_users
    add constraint email_uniq not exists unique (email);


alter table tbl_users
    add constraint username_uniq not exists unique (username);

alter table post_liked_users
    add constraint post_user not exists
        foreign key (post_id)
            references tbl_posts;

alter table tbl_comments
    add constraint post_comment not exists
        foreign key (post_id)
            references tbl_posts;

alter table tbl_posts
    add constraint post_user not exists
        foreign key (user_id)
            references tbl_users;

alter table user_role
    add constraint role_user not exists
        foreign key (user_id)
            references tbl_users;
