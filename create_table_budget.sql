CREATE TABLE budget
(
  id integer NOT NULL DEFAULT nextval('timetracker_sequence'::regclass),
  descricaocurta character varying(100),
  tipohoras character varying(40),
  projeto_id integer,
  valorhora numeric(10,2),
  CONSTRAINT budget_pkey PRIMARY KEY (id)
  
)
WITH (
  OIDS=FALSE
);
ALTER TABLE budget
  OWNER TO saibr;
