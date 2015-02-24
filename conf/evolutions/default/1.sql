# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table affiliated (
  user_id                   bigint,
  event_id                  bigint,
  alarm_time                timestamp not null,
  status                    integer,
  constraint ck_affiliated_status check (status in (0,1,2,3)))
;

create table bruker (
  user_id                   bigint auto_increment not null,
  username                  varchar(255) not null,
  passord                   varchar(255) not null,
  fornavn                   varchar(255),
  etternavn                 varchar(255),
  constraint pk_bruker primary key (user_id))
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
  creator                   bigint,
  event_starts              timestamp not null,
  event_ends                timestamp not null,
  date_made                 timestamp not null,
  constraint pk_event primary key (event_id))
;

create table room (
  room_id                   bigint auto_increment not null,
  name                      varchar(255) not null,
  text                      varchar(255),
  room_size                 integer not null,
  constraint pk_room primary key (room_id))
;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists affiliated;

drop table if exists bruker;

drop table if exists dbtest;

drop table if exists event;

drop table if exists room;

SET REFERENTIAL_INTEGRITY TRUE;

