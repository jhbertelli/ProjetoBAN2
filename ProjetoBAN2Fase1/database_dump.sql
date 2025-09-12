--
-- PostgreSQL database dump
--

-- Dumped from database version 14.18 (Ubuntu 14.18-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.18 (Ubuntu 14.18-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: categorias; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categorias (
    id_categoria integer NOT NULL,
    nome character varying(30)
);


ALTER TABLE public.categorias OWNER TO postgres;

--
-- Name: categorias_id_categoria_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.categorias ALTER COLUMN id_categoria ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.categorias_id_categoria_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: fornecedores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fornecedores (
    id_fornecedor integer NOT NULL,
    endereco character varying(255),
    telefone character varying(20),
    nome character varying(255),
    nome_fantasia character varying(255),
    documento character varying(20) NOT NULL,
    email_contato character varying(100)
);


ALTER TABLE public.fornecedores OWNER TO postgres;

--
-- Name: fornecedores_id_fornecedor_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.fornecedores ALTER COLUMN id_fornecedor ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.fornecedores_id_fornecedor_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: produtos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produtos (
    id_produto integer NOT NULL,
    id_categoria integer NOT NULL,
    id_fornecedor integer NOT NULL,
    nome character varying(255),
    preco numeric,
    tempo_garantia integer,
    data_recebimento date,
    quantidade integer
);


ALTER TABLE public.produtos OWNER TO postgres;

--
-- Name: produtos_id_produto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.produtos ALTER COLUMN id_produto ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.produtos_id_produto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: venda_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.venda_produto (
    id_produto integer NOT NULL,
    id_venda integer NOT NULL,
    quantidade_vendida integer NOT NULL
);


ALTER TABLE public.venda_produto OWNER TO postgres;

--
-- Name: vendas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vendas (
    id_venda integer NOT NULL,
    id_vendedor integer
);


ALTER TABLE public.vendas OWNER TO postgres;

--
-- Name: vendas_id_venda_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.vendas ALTER COLUMN id_venda ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.vendas_id_venda_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: vendedores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vendedores (
    id_vendedor integer NOT NULL,
    nome character varying(100) NOT NULL,
    endereco character varying(255),
    telefone character varying(20),
    email character varying(100)
);


ALTER TABLE public.vendedores OWNER TO postgres;

--
-- Name: vendedores_id_vendedor_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.vendedores ALTER COLUMN id_vendedor ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.vendedores_id_vendedor_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

--
-- Name: categorias_id_categoria_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categorias_id_categoria_seq', 1, false);


--
-- Name: fornecedores_id_fornecedor_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fornecedores_id_fornecedor_seq', 1, false);


--
-- Name: produtos_id_produto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produtos_id_produto_seq', 1, false);


--
-- Name: vendas_id_venda_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vendas_id_venda_seq', 1, false);


--
-- Name: vendedores_id_vendedor_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vendedores_id_vendedor_seq', 1, false);


--
-- Name: vendedores email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendedores
    ADD CONSTRAINT email UNIQUE (email);


--
-- Name: categorias id_categoria; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categorias
    ADD CONSTRAINT id_categoria PRIMARY KEY (id_categoria);


--
-- Name: fornecedores id_fornecedor; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fornecedores
    ADD CONSTRAINT id_fornecedor PRIMARY KEY (id_fornecedor);


--
-- Name: produtos id_produto; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT id_produto PRIMARY KEY (id_produto);


--
-- Name: vendas id_venda; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT id_venda PRIMARY KEY (id_venda);


--
-- Name: vendedores id_vendedor; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendedores
    ADD CONSTRAINT id_vendedor PRIMARY KEY (id_vendedor);


--
-- Name: produtos id_categoria; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT id_categoria FOREIGN KEY (id_categoria) REFERENCES public.categorias(id_categoria);


--
-- Name: produtos id_fornecedor; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT id_fornecedor FOREIGN KEY (id_fornecedor) REFERENCES public.fornecedores(id_fornecedor);


--
-- Name: venda_produto id_produto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_produto
    ADD CONSTRAINT id_produto FOREIGN KEY (id_produto) REFERENCES public.produtos(id_produto);


--
-- Name: venda_produto id_venda; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_produto
    ADD CONSTRAINT id_venda FOREIGN KEY (id_venda) REFERENCES public.vendas(id_venda);


--
-- Name: vendas id_vendedor; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT id_vendedor FOREIGN KEY (id_vendedor) REFERENCES public.vendedores(id_vendedor);


--
-- PostgreSQL database dump complete
--
