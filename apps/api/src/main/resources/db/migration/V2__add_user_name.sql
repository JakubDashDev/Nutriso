ALTER TABLE public.users
    ADD COLUMN name character varying(255) NOT NULL DEFAULT '';

ALTER TABLE public.users
    ALTER COLUMN name DROP DEFAULT;
