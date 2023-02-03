CREATE TABLE projeto
(
  id integer NOT NULL DEFAULT nextval('timetracker_sequence'::regclass),
  ativa boolean,
  descricaocurta character varying(100),
  numero character varying(15),
  statusatual character varying(20),
  cliente character varying(100),
  CONSTRAINT projeto_pkey PRIMARY KEY (id)
  
)
WITH (
  OIDS=FALSE
);
ALTER TABLE projeto
  OWNER TO saibr;
