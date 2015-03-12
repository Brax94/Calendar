# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table affiliated (
  affiliated_id             bigint auto_increment not null,
  bruker_username           varchar(255),
  event_event_id            bigint,
  alarm_time                timestamp not null,
  status                    integer,
  constraint ck_affiliated_status check (status in (0,1,2,3)),
  constraint pk_affiliated primary key (affiliated_id))
;

create table bruker (
  username                  varchar(255) not null,
  password                  varchar(255) not null,
  email                     varchar(255) not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  constraint pk_bruker primary key (username))
;

create table dbtest (
  id                        integer auto_increment not null,
  text                      varchar(255),
  constraint pk_dbtest primary key (id))
;

create table event (
  event_id                  bigint auto_increment not null,
  title                     varchar(255),
  text                      varchar(255),
  place                     varchar(255),
  creator_username          varchar(255),
  room_room_id              bigint,
  event_starts              timestamp not null,
  event_ends                timestamp not null,
  date_made                 timestamp not null,
  constraint pk_event primary key (event_id))
;

create table gruppe (
  group_id                  bigint auto_increment not null,
  group_name                varchar(255) not null,
  creator_username          varchar(255),
  mother_gruppe_group_id    bigint,
  constraint pk_gruppe primary key (group_id))
;

create table notification (
  notif_id                  bigint auto_increment not null,
  bruker_username           varchar(255),
  notification              varchar(255),
  date_made                 timestamp not null,
  constraint pk_notification primary key (notif_id))
;

create table room (
  room_id                   bigint auto_increment not null,
  name                      varchar(255) not null,
  text                      varchar(255),
  room_size                 integer not null,
  constraint pk_room primary key (room_id))
;


create table bruker_gruppe (
  bruker_username                varchar(255) not null,
  gruppe_group_id                bigint not null,
  constraint pk_bruker_gruppe primary key (bruker_username, gruppe_group_id))
;
create sequence bruker_seq;

alter table affiliated add constraint fk_affiliated_bruker_1 foreign key (bruker_username) references bruker (username) on delete restrict on update restrict;
create index ix_affiliated_bruker_1 on affiliated (bruker_username);
alter table affiliated add constraint fk_affiliated_event_2 foreign key (event_event_id) references event (event_id) on delete restrict on update restrict;
create index ix_affiliated_event_2 on affiliated (event_event_id);
alter table event add constraint fk_event_creator_3 foreign key (creator_username) references bruker (username) on delete restrict on update restrict;
create index ix_event_creator_3 on event (creator_username);
alter table event add constraint fk_event_room_4 foreign key (room_room_id) references room (room_id) on delete restrict on update restrict;
create index ix_event_room_4 on event (room_room_id);
alter table gruppe add constraint fk_gruppe_creator_5 foreign key (creator_username) references bruker (username) on delete restrict on update restrict;
create index ix_gruppe_creator_5 on gruppe (creator_username);
alter table gruppe add constraint fk_gruppe_motherGruppe_6 foreign key (mother_gruppe_group_id) references gruppe (group_id) on delete restrict on update restrict;
create index ix_gruppe_motherGruppe_6 on gruppe (mother_gruppe_group_id);
alter table notification add constraint fk_notification_bruker_7 foreign key (bruker_username) references bruker (username) on delete restrict on update restrict;
create index ix_notification_bruker_7 on notification (bruker_username);



alter table bruker_gruppe add constraint fk_bruker_gruppe_bruker_01 foreign key (bruker_username) references bruker (username) on delete restrict on update restrict;

alter table bruker_gruppe add constraint fk_bruker_gruppe_gruppe_02 foreign key (gruppe_group_id) references gruppe (group_id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists affiliated;

drop table if exists bruker;

drop table if exists bruker_gruppe;

drop table if exists dbtest;

drop table if exists event;

drop table if exists gruppe;

drop table if exists notification;

drop table if exists room;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists bruker_seq;

