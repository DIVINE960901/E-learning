create table if not exists carousel
(
    carouse_id int auto_increment comment '轮播图id'
        primary key,
    nickname   varchar(255) charset latin1  not null comment '作者',
    title      varchar(30) collate utf8_bin not null comment '标题',
    cover_img  varchar(1024) charset latin1 not null,
    id         varchar(255) charset utf8    not null comment '视频id'
)
    comment '轮播图' collate = utf8mb4_bin;

create table if not exists log
(
    id          int unsigned auto_increment comment '日志ID'
        primary key,
    user_id     int unsigned not null comment '用户ID',
    exam_id     int unsigned not null comment '试卷ID',
    accuracy    float        not null comment '答题正确率',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null comment '修改时间'
)
    comment '日志表';

create table if not exists recommend
(
    id        int auto_increment comment '推荐id'
        primary key,
    title     varchar(30)   not null comment '标题',
    nickname  varchar(255)  not null comment '作者',
    video_id  varchar(255)  not null comment '视频id',
    cover_img varchar(1024) not null comment '封面'
)
    comment '首页推荐' charset = utf8;

create table if not exists user
(
    id          int unsigned auto_increment comment 'ID'
        primary key,
    username    varchar(20)                                                                                not null comment '用户名',
    password    varchar(32)                                                                                not null comment '密码',
    nickname    varchar(10)  default ''                                                                    null comment '昵称',
    user_pic    varchar(128) default 'http://localhost:8080/icon/b7f97a45-78fd-467d-bfd1-b804e927224f.jpg' null comment '头像',
    create_time datetime                                                                                   not null comment '创建时间',
    update_time datetime                                                                                   not null comment '修改时间',
    identity    varchar(10)  default 'student'                                                             not null comment '身份',
    constraint username
        unique (username)
)
    comment '用户表';

create table if not exists course
(
    id                    varchar(255)                   not null comment 'VideoID'
        primary key,
    title                 varchar(30)                    not null comment '课程标题',
    content_Url           varchar(1024) default ''       null comment '课程视频链接',
    cover_img             varchar(1024)                  null,
    state                 varchar(3)    default '待审核' null comment '课程状态: 只能是[已通过] 或者 [待审核]或者 [未通过]',
    create_user           int unsigned                   not null comment '创建人ID',
    create_time           datetime                       not null comment '创建时间',
    update_time           datetime                       not null comment '修改时间',
    Number_of_Collections int           default 0        not null comment '收藏人数',
    Number_of_viewers     int           default 0        not null comment '观看人数',
    constraint fk_article_user
        foreign key (create_user) references user (id)
            on delete cascade
)
    comment '课程表' charset = utf8;

create table if not exists comment
(
    id          int unsigned auto_increment comment 'ID'
        primary key,
    user_id     int unsigned  not null comment '评论用户ID',
    video_id    varchar(255)  not null comment '评论视频ID',
    content     varchar(1024) not null comment '评论内容',
    create_time datetime      not null comment '创建时间',
    constraint fk_comment_course
        foreign key (video_id) references course (id),
    constraint fk_comment_user
        foreign key (user_id) references user (id)
)
    comment '评论表' charset = utf8;

create table if not exists exam
(
    id            int unsigned auto_increment comment 'ID'
        primary key,
    title         varchar(30) charset latin1 not null comment '试卷标题',
    user_id       int unsigned               not null comment '试卷创建人ID',
    single_number int unsigned               not null comment '选择题个数',
    create_time   datetime                   not null comment '创建时间',
    update_time   datetime                   not null comment '修改时间',
    nickname      varchar(10)  default ''    null comment '昵称',
    `describe`    varchar(255) default ''    null comment '描述',
    constraint exam_user_id_fk
        foreign key (user_id) references user (id)
            on delete cascade
)
    comment '考试' charset = utf8;

create table if not exists favorite
(
    id          int unsigned auto_increment comment '订阅ID'
        primary key,
    user_id     int unsigned not null comment '订阅用户id',
    video_id    varchar(255) not null comment '订阅视频id',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null comment '修改时间',
    constraint favorite_course_id_fk
        foreign key (video_id) references course (id)
            on delete cascade,
    constraint fk_favorite_user
        foreign key (user_id) references user (id)
            on delete cascade
)
    comment '收藏表' charset = utf8;

create table if not exists topic
(
    id          int unsigned auto_increment comment '题目ID'
        primary key,
    topic_title varchar(255) not null comment '题目标题',
    option_A    varchar(255) not null comment '选项A',
    option_B    varchar(255) not null comment '选项B',
    option_C    varchar(255) not null comment '选项C',
    option_D    varchar(255) not null comment '选项D',
    answer      varchar(2)   not null comment '答案',
    exam_id     int unsigned not null comment '试卷ID',
    constraint fk_topic_exam
        foreign key (exam_id) references exam (id)
            on delete cascade
)
    comment '题目内容';

create table if not exists videorecord
(
    id          int unsigned auto_increment comment 'ID'
        primary key,
    user_id     int unsigned not null comment '用户ID',
    video_id    varchar(255) not null comment '视频ID',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null comment '修改时间',
    constraint fk_videoRecord_user
        foreign key (user_id) references user (id)
            on delete cascade,
    constraint videorecord_course_id_fk
        foreign key (video_id) references course (id)
            on delete cascade
)
    charset = utf8;