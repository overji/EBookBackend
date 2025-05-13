# 数据库设计

![img.png](img.png)

```mysql
create table books
(
    id          bigint auto_increment
        primary key,
    author      tinytext null,
    cover       tinytext null,
    description text     null,
    price       bigint   null,
    sales       bigint   null,
    title       tinytext null
);

create table book_tag
(
    id          bigint auto_increment
        primary key,
    book_tag_id bigint   not null,
    name        tinytext null,
    book_id     bigint   not null,
    constraint FKbacbwwmfdh1bwsdgs9pycw9rr
        foreign key (book_id) references books (id)
);

create table users
(
    id           bigint auto_increment
        primary key,
    avatar       tinytext     null,
    balance      bigint       not null,
    created_at   datetime(6)  not null,
    email        varchar(255) null,
    introduction tinytext     null,
    nickname     varchar(255) not null,
    username     varchar(255) not null,
    constraint UKr43af9ap4edm43mmtq01oddj6
        unique (username)
);

create table cart
(
    id           bigint auto_increment
        primary key,
    number       bigint not null,
    user_cart_id bigint not null,
    book_id      bigint not null,
    user_id      bigint not null,
    constraint FK1ykovbj90wkvqwa6m6463h21p
        foreign key (book_id) references books (id),
    constraint FKg5uhi8vpsuy0lgloxk2h4w5o6
        foreign key (user_id) references users (id)
);

create table comments
(
    id         bigint auto_increment
        primary key,
    content    text        null,
    created_at datetime(6) not null,
    reply      text        null,
    book_id    bigint      not null,
    user_id    bigint      not null,
    constraint FK1ey8gegnanvybix5a025vepf4
        foreign key (book_id) references books (id),
    constraint FK8omq0tc18jd43bu5tjh6jvraq
        foreign key (user_id) references users (id)
);

create table comment_likes
(
    comment_id bigint not null,
    user_id    bigint not null,
    constraint FK3wa5u7bs1p1o9hmavtgdgk1go
        foreign key (comment_id) references comments (id),
    constraint FK6h3lbneryl5pyb9ykaju7werx
        foreign key (user_id) references users (id)
);

create table orders
(
    id            bigint auto_increment
        primary key,
    address       text        null,
    created_at    datetime(6) not null,
    receiver      text        null,
    tel           text        null,
    user_order_id bigint      not null,
    user_id       bigint      not null,
    constraint FK32ql8ubntj5uh44ph9659tiih
        foreign key (user_id) references users (id)
);

create table order_item
(
    id          bigint auto_increment
        primary key,
    number      bigint not null,
    book_id     bigint not null,
    order_in_id bigint not null,
    order_id    bigint not null,
    constraint FKb6jjegw7xwlwsk2ti3sxqpgq9
        foreign key (order_in_id) references orders (id),
    constraint FKegdk0kfrrdr1248hq5ixgr5x6
        foreign key (book_id) references books (id),
    constraint FKt4dc2r9nbvbujrljv3e23iibt
        foreign key (order_id) references orders (id)
);

create table user_address
(
    id           bigint auto_increment
        primary key,
    address      tinytext not null,
    phone        tinytext not null,
    receiver     tinytext not null,
    user_addr_id bigint   not null,
    uid          bigint   not null,
    constraint FKcf73it2qh8miua9osj685t1vx
        foreign key (uid) references users (id)
);

create table user_auth
(
    id       bigint auto_increment
        primary key,
    password varchar(255) not null,
    user_id  bigint       not null,
    constraint UKnd1cbjapgbp1cohkrlhq2jky4
        unique (user_id),
    constraint FK9hog4tmdalcddidanohglx6gl
        foreign key (user_id) references users (id)
);


```