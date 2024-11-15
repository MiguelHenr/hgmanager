-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler version: 0.9.4
-- PostgreSQL version: 13.0
-- Project Site: pgmodeler.io
-- Model Author: ---

-- Database creation must be performed outside a multi lined SQL file. 
-- These commands were put in this file only as a convenience.
-- 
-- object: hgdb | type: DATABASE --
-- DROP DATABASE IF EXISTS hgdb;
CREATE DATABASE hgdb;
-- ddl-end --


-- object: public.usuario | type: TABLE --
-- DROP TABLE IF EXISTS public.usuario CASCADE;
CREATE TABLE public.usuario (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	cpf character varying(14) NOT NULL,
	id_departamento integer NOT NULL,
	CONSTRAINT usuario_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.usuario OWNER TO postgres;
-- ddl-end --

-- object: public.punicao | type: TABLE --
-- DROP TABLE IF EXISTS public.punicao CASCADE;
CREATE TABLE public.punicao (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	dt_inicio date NOT NULL,
	dt_fim date NOT NULL,
	id_usuario integer NOT NULL,
	CONSTRAINT punicao_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.punicao OWNER TO postgres;
-- ddl-end --

-- object: usuario_fk | type: CONSTRAINT --
-- ALTER TABLE public.punicao DROP CONSTRAINT IF EXISTS usuario_fk CASCADE;
ALTER TABLE public.punicao ADD CONSTRAINT usuario_fk FOREIGN KEY (id_usuario)
REFERENCES public.usuario (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: public.departamento | type: TABLE --
-- DROP TABLE IF EXISTS public.departamento CASCADE;
CREATE TABLE public.departamento (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	nome varchar(255) NOT NULL,
	campus varchar(255) NOT NULL,
	CONSTRAINT departamento_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.departamento OWNER TO postgres;
-- ddl-end --

-- object: departamento_fk | type: CONSTRAINT --
-- ALTER TABLE public.usuario DROP CONSTRAINT IF EXISTS departamento_fk CASCADE;
ALTER TABLE public.usuario ADD CONSTRAINT departamento_fk FOREIGN KEY (id_departamento)
REFERENCES public.departamento (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: public.estado | type: TYPE --
-- DROP TYPE IF EXISTS public.estado CASCADE;
CREATE TYPE public.estado AS
ENUM ('NOVO','CONSERVADO','VELHO','ESTRAGADO');
-- ddl-end --
ALTER TYPE public.estado OWNER TO postgres;
-- ddl-end --

-- object: public.recurso | type: TABLE --
-- DROP TABLE IF EXISTS public.recurso CASCADE;
CREATE TABLE public.recurso (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	estado public.estado,
	marca varchar(255) NOT NULL,
	descricao varchar(255) NOT NULL,
	id_departamento integer NOT NULL,
	CONSTRAINT recurso_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.recurso OWNER TO postgres;
-- ddl-end --

-- object: departamento_fk | type: CONSTRAINT --
-- ALTER TABLE public.recurso DROP CONSTRAINT IF EXISTS departamento_fk CASCADE;
ALTER TABLE public.recurso ADD CONSTRAINT departamento_fk FOREIGN KEY (id_departamento)
REFERENCES public.departamento (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: public.reserva | type: TABLE --
-- DROP TABLE IF EXISTS public.reserva CASCADE;
CREATE TABLE public.reserva (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	dt_inicio date NOT NULL,
	dt_fim date NOT NULL,
	id_usuario integer NOT NULL,
	id_recurso integer NOT NULL,
	CONSTRAINT reserva_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.reserva OWNER TO postgres;
-- ddl-end --

-- object: public.reclamacao | type: TABLE --
-- DROP TABLE IF EXISTS public.reclamacao CASCADE;
CREATE TABLE public.reclamacao (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	comentario varchar(255) NOT NULL,
	data date NOT NULL,
	id_reserva integer NOT NULL,
	CONSTRAINT reclamacao_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.reclamacao OWNER TO postgres;
-- ddl-end --

-- object: public.resposta | type: TABLE --
-- DROP TABLE IF EXISTS public.resposta CASCADE;
CREATE TABLE public.resposta (
	id_usuario integer NOT NULL,
	id_reclamacao integer NOT NULL,
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	comentario varchar(255),
	CONSTRAINT resposta_pk PRIMARY KEY (id_usuario,id_reclamacao,id)
);
-- ddl-end --

-- object: usuario_fk | type: CONSTRAINT --
-- ALTER TABLE public.resposta DROP CONSTRAINT IF EXISTS usuario_fk CASCADE;
ALTER TABLE public.resposta ADD CONSTRAINT usuario_fk FOREIGN KEY (id_usuario)
REFERENCES public.usuario (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: reclamacao_fk | type: CONSTRAINT --
-- ALTER TABLE public.resposta DROP CONSTRAINT IF EXISTS reclamacao_fk CASCADE;
ALTER TABLE public.resposta ADD CONSTRAINT reclamacao_fk FOREIGN KEY (id_reclamacao)
REFERENCES public.reclamacao (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: usuario_fk | type: CONSTRAINT --
-- ALTER TABLE public.reserva DROP CONSTRAINT IF EXISTS usuario_fk CASCADE;
ALTER TABLE public.reserva ADD CONSTRAINT usuario_fk FOREIGN KEY (id_usuario)
REFERENCES public.usuario (id) MATCH SIMPLE
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: recurso_fk | type: CONSTRAINT --
-- ALTER TABLE public.reserva DROP CONSTRAINT IF EXISTS recurso_fk CASCADE;
ALTER TABLE public.reserva ADD CONSTRAINT recurso_fk FOREIGN KEY (id_recurso)
REFERENCES public.recurso (id) MATCH SIMPLE
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: reserva_fk | type: CONSTRAINT --
-- ALTER TABLE public.reclamacao DROP CONSTRAINT IF EXISTS reserva_fk CASCADE;
ALTER TABLE public.reclamacao ADD CONSTRAINT reserva_fk FOREIGN KEY (id_reserva)
REFERENCES public.reserva (id) MATCH SIMPLE
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --


