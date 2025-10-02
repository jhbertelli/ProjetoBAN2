--
-- PostgreSQL database dump
--

-- Dumped from database version 14.18 (Ubuntu 14.18-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.18 (Ubuntu 14.18-0ubuntu0.22.04.1)

-- Started on 2025-10-02 14:27:18 -03

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
-- TOC entry 209 (class 1259 OID 17073)
-- Name: categorias; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categorias (
    id_categoria integer NOT NULL,
    nome character varying(30)
);


ALTER TABLE public.categorias OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 17076)
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
-- TOC entry 211 (class 1259 OID 17077)
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
-- TOC entry 212 (class 1259 OID 17082)
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
-- TOC entry 213 (class 1259 OID 17083)
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
-- TOC entry 214 (class 1259 OID 17088)
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
-- TOC entry 215 (class 1259 OID 17089)
-- Name: venda_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.venda_produto (
    id_produto integer NOT NULL,
    id_venda integer NOT NULL,
    quantidade_vendida integer NOT NULL
);


ALTER TABLE public.venda_produto OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 17092)
-- Name: vendas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vendas (
    id_venda integer NOT NULL,
    id_vendedor integer
);


ALTER TABLE public.vendas OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 17095)
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
-- TOC entry 218 (class 1259 OID 17096)
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
-- TOC entry 219 (class 1259 OID 17099)
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
-- TOC entry 3387 (class 0 OID 17073)
-- Dependencies: 209
-- Data for Name: categorias; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categorias OVERRIDING SYSTEM VALUE VALUES (1, 'Guitarra');
INSERT INTO public.categorias OVERRIDING SYSTEM VALUE VALUES (2, 'Baixo');
INSERT INTO public.categorias OVERRIDING SYSTEM VALUE VALUES (3, 'Bateria');
INSERT INTO public.categorias OVERRIDING SYSTEM VALUE VALUES (4, 'Amplificador');


--
-- TOC entry 3389 (class 0 OID 17077)
-- Dependencies: 211
-- Data for Name: fornecedores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.fornecedores OVERRIDING SYSTEM VALUE VALUES (1, 'Rua Machado, 123', '123456', 'Fender Musical Instruments Corporation', 'Fender', '123456789', 'fender@fake.com');
INSERT INTO public.fornecedores OVERRIDING SYSTEM VALUE VALUES (2, 'Rua Brasil, 543', '98756', 'Marshall Amplification', 'Marshall', '985431', 'marshall@fake.com');


--
-- TOC entry 3391 (class 0 OID 17083)
-- Dependencies: 213
-- Data for Name: produtos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.produtos OVERRIDING SYSTEM VALUE VALUES (1, 1, 1, 'Fender Stratocaster', 1999.99, 36, '2020-10-25', 5);
INSERT INTO public.produtos OVERRIDING SYSTEM VALUE VALUES (2, 4, 2, 'Amplificador Marshall', 799.99, 24, '2019-01-15', 7);


--
-- TOC entry 3393 (class 0 OID 17089)
-- Dependencies: 215
-- Data for Name: venda_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.venda_produto VALUES (2, 1, 2);
INSERT INTO public.venda_produto VALUES (1, 1, 1);
INSERT INTO public.venda_produto VALUES (1, 2, 1);


--
-- TOC entry 3394 (class 0 OID 17092)
-- Dependencies: 216
-- Data for Name: vendas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.vendas OVERRIDING SYSTEM VALUE VALUES (1, NULL);
INSERT INTO public.vendas OVERRIDING SYSTEM VALUE VALUES (2, 1);


--
-- TOC entry 3396 (class 0 OID 17096)
-- Dependencies: 218
-- Data for Name: vendedores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.vendedores OVERRIDING SYSTEM VALUE VALUES (1, 'Lucas Costa', 'Rua América, 123', '123456', 'lucascosta@fake.com');


--
-- TOC entry 3403 (class 0 OID 0)
-- Dependencies: 210
-- Name: categorias_id_categoria_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categorias_id_categoria_seq', 4, true);


--
-- TOC entry 3404 (class 0 OID 0)
-- Dependencies: 212
-- Name: fornecedores_id_fornecedor_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fornecedores_id_fornecedor_seq', 2, true);


--
-- TOC entry 3405 (class 0 OID 0)
-- Dependencies: 214
-- Name: produtos_id_produto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produtos_id_produto_seq', 2, true);


--
-- TOC entry 3406 (class 0 OID 0)
-- Dependencies: 217
-- Name: vendas_id_venda_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vendas_id_venda_seq', 2, true);


--
-- TOC entry 3407 (class 0 OID 0)
-- Dependencies: 219
-- Name: vendedores_id_vendedor_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vendedores_id_vendedor_seq', 1, true);


--
-- TOC entry 3240 (class 2606 OID 17101)
-- Name: vendedores email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendedores
    ADD CONSTRAINT email UNIQUE (email);


--
-- TOC entry 3232 (class 2606 OID 17103)
-- Name: categorias id_categoria; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categorias
    ADD CONSTRAINT id_categoria PRIMARY KEY (id_categoria);


--
-- TOC entry 3234 (class 2606 OID 17105)
-- Name: fornecedores id_fornecedor; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fornecedores
    ADD CONSTRAINT id_fornecedor PRIMARY KEY (id_fornecedor);


--
-- TOC entry 3236 (class 2606 OID 17107)
-- Name: produtos id_produto; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT id_produto PRIMARY KEY (id_produto);


--
-- TOC entry 3238 (class 2606 OID 17109)
-- Name: vendas id_venda; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT id_venda PRIMARY KEY (id_venda);


--
-- TOC entry 3242 (class 2606 OID 17111)
-- Name: vendedores id_vendedor; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendedores
    ADD CONSTRAINT id_vendedor PRIMARY KEY (id_vendedor);


--
-- TOC entry 3243 (class 2606 OID 17112)
-- Name: produtos id_categoria; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT id_categoria FOREIGN KEY (id_categoria) REFERENCES public.categorias(id_categoria);


--
-- TOC entry 3244 (class 2606 OID 17117)
-- Name: produtos id_fornecedor; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT id_fornecedor FOREIGN KEY (id_fornecedor) REFERENCES public.fornecedores(id_fornecedor);


--
-- TOC entry 3245 (class 2606 OID 17122)
-- Name: venda_produto id_produto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_produto
    ADD CONSTRAINT id_produto FOREIGN KEY (id_produto) REFERENCES public.produtos(id_produto);


--
-- TOC entry 3246 (class 2606 OID 17127)
-- Name: venda_produto id_venda; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda_produto
    ADD CONSTRAINT id_venda FOREIGN KEY (id_venda) REFERENCES public.vendas(id_venda);


--
-- TOC entry 3247 (class 2606 OID 17132)
-- Name: vendas id_vendedor; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT id_vendedor FOREIGN KEY (id_vendedor) REFERENCES public.vendedores(id_vendedor);


-- Completed on 2025-10-02 14:27:19 -03

--
-- PostgreSQL database dump complete
--

