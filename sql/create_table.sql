-- auto-generated definition
create table user
(
    id           bigint auto_increment comment '用户ID 主键',
    userName     varchar(256)                       null comment '昵称',
    userAccount  varchar(256)                       null comment '登录账号',
    avatarUrl    varchar(256)                       null comment '头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(256)                       not null comment '密码',
    phone        varchar(64)                        null comment '电话',
    email        varchar(256)                       null comment '邮箱',
    status       int      default 0                 not null comment '用户状态 0-正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      tinyint  default 1                 not null comment '0代表删除 1代表正常',
    role         tinyint  default 0                 not null comment '用户权限 0-默认权限 1-管理员权限',
    constraint user_pk
        unique (id)
)
    comment '用户信息表';

