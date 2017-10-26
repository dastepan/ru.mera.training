CREATE TABLE public.category (
  category_id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('category_category_id_seq'::regclass),
  category_desc CHARACTER VARYING(255),
  category_title CHARACTER VARYING(255)
);

CREATE TABLE public.note (
  note_id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('note_id_note_id_seq'::regclass),
  note_title CHARACTER VARYING(120),
  note_body TEXT,
  category_id BIGINT NOT NULL
);
CREATE UNIQUE INDEX unique_note_id ON note USING BTREE (note_id);