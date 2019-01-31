create table subject
(
  subject_id  serial not null
    constraint subject_pk
      primary key,
  description varchar(40)
);

alter table subject
  owner to postgres;

INSERT INTO public.subject (subject_id, description) VALUES (1, 'Programming');
INSERT INTO public.subject (subject_id, description) VALUES (2, 'Math');
INSERT INTO public.subject (subject_id, description) VALUES (3, 'Sport');