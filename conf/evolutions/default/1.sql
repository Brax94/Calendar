# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

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
  event_starts              timestamp not null,
  event_ends                timestamp not null,
  date_made                 timestamp not null,
  constraint pk_event primary key (event_id))
;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists dbtest;

drop table if exists event;

SET REFERENTIAL_INTEGRITY TRUE;

