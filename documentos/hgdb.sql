CREATE DATABASE hgdb;

CREATE TYPE public.cargo AS
ENUM ('TAE','PROFESSOR');
ALTER TYPE public.cargo OWNER TO postgres;

CREATE TABLE public.usuario (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	nome varchar(255) NOT NULL,
	cpf varchar(14) NOT NULL,
	foto varchar(255) NOT NULL,
	id_departamento integer NOT NULL,
	tipo_usuario public.cargo NOT NULL,
	CONSTRAINT usuario_pk PRIMARY KEY (id)
);

CREATE TABLE public.departamento (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	nome varchar(255) NOT NULL,
	campus varchar(255) NOT NULL,
	telefone varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	CONSTRAINT departamento_pk PRIMARY KEY (id)
);

CREATE TYPE public.estado AS
ENUM ('NOVO','CONSERVADO','VELHO','ESTRAGADO');
ALTER TYPE public.estado OWNER TO postgres;

CREATE TABLE public.recurso (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	estado public.estado NOT NULL,
	marca varchar(255) NOT NULL,
	descricao varchar(255) NOT NULL,
	id_departamento integer NOT NULL,
	CONSTRAINT recurso_pk PRIMARY KEY (id)
);

CREATE TYPE public.status AS
ENUM ('AGUARDANDO','APROVADA','RECUSADA');
ALTER TYPE public.status OWNER TO postgres;

CREATE TABLE public.reserva (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	dt_inicio date NOT NULL,
	dt_fim date NOT NULL,
    status public.status NOT NULL,
	id_usuario integer NOT NULL,
	id_recurso integer NOT NULL,
	CONSTRAINT reserva_pk PRIMARY KEY (id)
);

CREATE TABLE public.reclamacao (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	comentario varchar(255) NOT NULL,
	data date NOT NULL,
	id_reserva integer NOT NULL,
	CONSTRAINT reclamacao_pk PRIMARY KEY (id)
);

CREATE TABLE public.punicao (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	dt_inicio date NOT NULL,
	dt_fim date NOT NULL,
	id_usuario integer NOT NULL,
	CONSTRAINT punicao_pk PRIMARY KEY (id)
);

CREATE TABLE public.resposta (
	id_usuario integer NOT NULL,
	id_reclamacao integer NOT NULL,
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	comentario varchar(255),
    data date,
	CONSTRAINT resposta_pk PRIMARY KEY (id_usuario,id_reclamacao,id)
);

ALTER TABLE public.resposta ADD CONSTRAINT usuario_fk FOREIGN KEY (id_usuario)
	REFERENCES public.usuario (id) MATCH FULL
	ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE public.resposta ADD CONSTRAINT reclamacao_fk FOREIGN KEY (id_reclamacao)
	REFERENCES public.reclamacao (id) MATCH FULL
	ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE public.usuario ADD CONSTRAINT departamento_fk FOREIGN KEY (id_departamento)
	REFERENCES public.departamento (id) MATCH FULL
	ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE public.punicao ADD CONSTRAINT usuario_fk FOREIGN KEY (id_usuario)
	REFERENCES public.usuario (id) MATCH FULL
	ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE public.reclamacao ADD CONSTRAINT reserva_fk FOREIGN KEY (id_reserva)
	REFERENCES public.reserva (id) MATCH SIMPLE
	ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE public.reserva ADD CONSTRAINT usuario_fk FOREIGN KEY (id_usuario)
	REFERENCES public.usuario (id) MATCH SIMPLE
	ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE public.reserva ADD CONSTRAINT recurso_fk FOREIGN KEY (id_recurso)
	REFERENCES public.recurso (id) MATCH SIMPLE
	ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE public.recurso ADD CONSTRAINT departamento_fk FOREIGN KEY (id_departamento)
	REFERENCES public.departamento (id) MATCH FULL
	ON DELETE RESTRICT ON UPDATE CASCADE;