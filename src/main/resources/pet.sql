create table if not exists address
(
    id          bigint auto_increment comment '地址id'
        primary key,
    user_id     bigint                   not null comment '用户id',
    province    varchar(20)              null comment '省',
    city        varchar(20)              null comment '市',
    district    varchar(20)              null comment '区',
    detail      varchar(255)             null comment '详细地址',
    create_time datetime default (now()) not null comment '创建时间',
    update_time datetime                 null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table if not exists announcement
(
    id          bigint auto_increment comment '公告id'
        primary key,
    title       varchar(255)             not null comment '标题',
    content     text                     not null comment '内容',
    create_time datetime default (now()) not null comment '创建时间',
    update_time datetime                 null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table if not exists comment
(
    id          bigint auto_increment comment '评论id'
        primary key,
    user_id     bigint                   not null comment '用户id',
    post_id     bigint                   not null comment '帖子id',
    content     text                     not null comment '内容',
    create_time datetime default (now()) not null comment '创建时间',
    update_time datetime                 null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table if not exists favorites
(
    id          bigint auto_increment comment '收藏id'
        primary key,
    user_id     bigint                   not null comment '用户id',
    post_id     bigint                   not null comment '帖子id',
    create_time datetime default (now()) null comment '创建时间',
    update_time datetime                 null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table if not exists likes
(
    id          bigint auto_increment comment '点赞id'
        primary key,
    user_id     bigint                   not null comment '用户id',
    post_id     bigint                   not null comment '帖子id',
    create_time datetime default (now()) null comment '创建时间',
    update_time datetime                 null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table if not exists orders
(
    id           bigint auto_increment comment '订单id'
        primary key,
    order_name   varchar(30)              null comment '订单名称',
    seller_id    bigint                   not null comment '卖家id',
    buyer_id     bigint                   not null comment '买家id',
    amount       decimal(10, 2)           not null comment '金额',
    type         varchar(20)              not null comment '类型',
    start_time   datetime                 not null comment '开始时间',
    end_time     datetime                 not null comment '结束时间',
    address_id   bigint                   not null comment '地址表id',
    days         int                      not null comment '寄养天数',
    payment_time datetime                 null comment '支付时间',
    status       int      default 0       not null comment '订单状态',
    comment      text                     null comment '买家评价',
    create_time  datetime default (now()) not null comment '创建时间',
    update_time  datetime                 null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table if not exists post
(
    id          bigint auto_increment comment '帖子id'
        primary key,
    user_id     bigint                        not null comment '用户id',
    title       varchar(255)                  not null comment '标题',
    imgs        varchar(1024) default ''      not null comment '图片(第一个为封面图)',
    content     text                          not null comment '内容',
    pet_type    varchar(20)                   not null comment '宠物类别',
    address_id  bigint                        not null comment '地址id',
    create_time datetime      default (now()) not null comment '创建时间',
    update_time datetime                      null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table if not exists role
(
    id          bigint auto_increment comment '角色id'
        primary key,
    name        varchar(20)              not null comment '角色名',
    create_time datetime default (now()) not null comment '创建时间',
    update_time datetime                 null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table if not exists user
(
    id          bigint auto_increment comment '用户id'
        primary key,
    name        varchar(20)              not null comment '用户名',
    password    varchar(255)             not null comment '密码',
    email       varchar(255)             null comment '邮箱',
    age         int                      null comment '年龄',
    phone       varchar(20)              null comment '手机号',
    avatar      varchar(255)             null comment '头像',
    create_time datetime default (now()) not null comment '创建时间',
    update_time datetime                 null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table if not exists user_address
(
    id          bigint auto_increment comment '用户地址表主键'
        primary key,
    user_id     bigint   null comment '用户id',
    address_id  bigint   null comment '地址id',
    create_time datetime null comment '创建时间',
    update_time datetime null comment '更新时间'
);

create table if not exists user_role
(
    user_id bigint not null comment '用户id',
    role_id bigint not null comment '角色id',
    primary key (user_id, role_id)
);

