drop database expenseDB;
drop user ${JDBC_DATABASE_USERNAME};

CREATE user ${JDBC_DATABASE_USERNAME} WITH password ${JDBC_PASSWORD} ;

CREATE database expenseDB with template=template0 owner=${JDBC_DATABASE_USERNAME};

\connect expensedb;

alter default privileges grant all on tables to ${JDBC_DATABASE_USERNAME};
alter default privileges grant all on sequences to ${JDBC_DATABASE_USERNAME};

CREATE table et_users(
 user_id integer primary key not null,
 first_name varchar(20) not null,
 last_name varchar(20) not null,
 email varchar(30) not null,
 password varchar(255) not null
);


create table et_categories(
category_id integer primary key not null,
user_id integer not null,
title varchar (20) not null,
description varchar(50) not null
);
alter table et_categories add constraint cat_users_fk
foreign key (user_id) references et_users(user_id);

create table et_transactions(
  transaction_id integer primary key not null,
  category_id integer not null,
  user_id integer not null,
  amount numeric(10, 2) not null,
  note varchar(50) not null,
  transaction_date bigint not null
);
alter table et_transactions add constraint trans_cat_fk
foreign key (user_id) references et_users(user_id);


create sequence et_users_seq increment 1 start 1;
create sequence et_categories_seq increment 1 start 1;
create sequence et_transactions_seq increment 1 start 1000;
