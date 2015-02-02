# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table dbtest (
  id                        integer auto_increment not null,
  text                      varchar(255),
  constraint pk_dbtest primary key (id))
;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists dbtest;

SET REFERENTIAL_INTEGRITY TRUE;

