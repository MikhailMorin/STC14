create table course
(
  person_id  integer not null
    constraint course_person_person_id_fk
      references person
      on update restrict on delete restrict,
  subject_id integer not null
    constraint course_subject_subject_id_fk
      references subject
      on update restrict on delete restrict,
  start_date timestamp,
  constraint course_pk
    primary key (person_id, subject_id)
);

alter table course
  owner to postgres;

INSERT INTO public.course (person_id, subject_id, start_date) VALUES (1, 2, '2019-01-31 00:00:00.000000');
INSERT INTO public.course (person_id, subject_id, start_date) VALUES (9, 2, '2019-01-31 00:00:00.000000');
INSERT INTO public.course (person_id, subject_id, start_date) VALUES (1, 3, '2019-01-31 00:00:00.000000');