drop table if exists report;
drop table if exists report_category;

create table report_category (
  report_category_id  identity,
  description         varchar(25)  not null,
  abbreviation        varchar(20)  not null,
  active              boolean      not null
);

create table report (
  report_id           integer      identity primary key,
  report_category_id  integer      not null,
  name                varchar(80)  not null,
  created_on          timestamp    not null,
  foreign key (report_category_id) references report_category(report_category_id)
);
