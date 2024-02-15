DROP TABLE public.message IF EXISTS;

CREATE TABLE public.message (
    id bigint NOT NULL,
    text character varying(200) NOT NULL,
    author character varying(50) NOT NULL,
    created_timestamp timestamp with time zone DEFAULT now()
);


INSERT INTO public.message (id, text, author, created_timestamp)
VALUES (
  '1',
  'text message',
  'message author',
  '2024-02-06T18:37:04.099+00:00');
INSERT INTO public.message (id, text, author, created_timestamp)
VALUES (
  '2',
  'Lorem ipsum dolor sit amet consectetur adipiscing elit. Maecenas laoreet mauris et tincidunt tempus turpis metus accumsan leo id elementum massa sapien nec urna.',
  'Cicero',
  '0001-01-01T00:00:00.099+00:00');
