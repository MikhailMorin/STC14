create table person
(
  person_id  serial      not null
    constraint person_pk
      primary key,
  name       varchar(20) not null,
  birth_date timestamp   not null
);

alter table person
  owner to postgres;

create unique index person_person_id_uindex
  on person (person_id);

INSERT INTO public.person (person_id, name, birth_date) VALUES (9, 'John Conor', '2019-01-30 00:00:00.000000');
INSERT INTO public.person (person_id, name, birth_date) VALUES (15, 'John Conor I', '2019-01-30 00:00:00.000000');
INSERT INTO public.person (person_id, name, birth_date) VALUES (1, 'Conor McGregor', '2019-01-30 00:00:00.000000');
INSERT INTO public.person (person_id, name, birth_date) VALUES (11, 'Vasya', '2019-01-30 00:00:00.000000');
INSERT INTO public.person (person_id, name, birth_date) VALUES (23, 'Kolya', '2019-01-31 00:00:00.000000');
INSERT INTO public.person (person_id, name, birth_date) VALUES (25, 'Azgen', '2019-01-31 00:00:00.000000');