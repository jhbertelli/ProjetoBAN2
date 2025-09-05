--
-- PostgreSQL database dump
--

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2025-09-05 11:08:56

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
-- TOC entry 219 (class 1259 OID 16406)
-- Name: Categorias; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Categorias" (
    "ID_Categoria" integer NOT NULL,
    "Nome" character varying(30)
);


ALTER TABLE public."Categorias" OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16411)
-- Name: Fornecedores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Fornecedores" (
    "ID_Fornecedor" integer NOT NULL,
    "Endereco" character varying(255),
    "Telefone" character varying(20),
    "Nome" character varying(255),
    "NomeFantasia" character varying(255),
    "Documento" character varying(20) NOT NULL,
    "EmailContato" character varying(100)
);


ALTER TABLE public."Fornecedores" OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16425)
-- Name: Produtos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Produtos" (
    "ID_Produto" integer NOT NULL,
    "ID_Categoria" integer NOT NULL,
    "ID_Fornecedor" integer NOT NULL,
    "Nome" character varying(255),
    "Preco" numeric,
    "TempoGarantia" integer,
    "DataRecebimento" date,
    "Quantidade" integer
);


ALTER TABLE public."Produtos" OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16442)
-- Name: VendaProduto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."VendaProduto" (
    "ID_Produto" integer NOT NULL,
    "ID_Venda" integer NOT NULL,
    "QtdVendida" integer NOT NULL
);


ALTER TABLE public."VendaProduto" OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16396)
-- Name: Vendas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Vendas" (
    "ID_Venda" integer NOT NULL,
    "ID_Vendedor" integer
);


ALTER TABLE public."Vendas" OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16389)
-- Name: Vendedores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Vendedores" (
    "ID_Vendedor" integer NOT NULL,
    "Nome" character varying(100) NOT NULL,
    "Endereco" character varying(255),
    "Telefone" character varying(20),
    "Email" character varying(100)
);


ALTER TABLE public."Vendedores" OWNER TO postgres;

--
-- TOC entry 4762 (class 2606 OID 16395)
-- Name: Vendedores Email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Vendedores"
    ADD CONSTRAINT "Email" UNIQUE ("Email");


--
-- TOC entry 4768 (class 2606 OID 16410)
-- Name: Categorias ID_Categoria; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Categorias"
    ADD CONSTRAINT "ID_Categoria" PRIMARY KEY ("ID_Categoria");


--
-- TOC entry 4770 (class 2606 OID 16417)
-- Name: Fornecedores ID_Fornecedor; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Fornecedores"
    ADD CONSTRAINT "ID_Fornecedor" PRIMARY KEY ("ID_Fornecedor");


--
-- TOC entry 4772 (class 2606 OID 16431)
-- Name: Produtos ID_Produto; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Produtos"
    ADD CONSTRAINT "ID_Produto" PRIMARY KEY ("ID_Produto");


--
-- TOC entry 4766 (class 2606 OID 16400)
-- Name: Vendas ID_Venda; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Vendas"
    ADD CONSTRAINT "ID_Venda" PRIMARY KEY ("ID_Venda");


--
-- TOC entry 4764 (class 2606 OID 16393)
-- Name: Vendedores ID_Vendedor; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Vendedores"
    ADD CONSTRAINT "ID_Vendedor" PRIMARY KEY ("ID_Vendedor");


--
-- TOC entry 4774 (class 2606 OID 16437)
-- Name: Produtos ID_Categoria; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Produtos"
    ADD CONSTRAINT "ID_Categoria" FOREIGN KEY ("ID_Categoria") REFERENCES public."Categorias"("ID_Categoria");


--
-- TOC entry 4775 (class 2606 OID 16432)
-- Name: Produtos ID_Fornecedor; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Produtos"
    ADD CONSTRAINT "ID_Fornecedor" FOREIGN KEY ("ID_Fornecedor") REFERENCES public."Fornecedores"("ID_Fornecedor");


--
-- TOC entry 4776 (class 2606 OID 16445)
-- Name: VendaProduto ID_Produto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."VendaProduto"
    ADD CONSTRAINT "ID_Produto" FOREIGN KEY ("ID_Produto") REFERENCES public."Produtos"("ID_Produto");


--
-- TOC entry 4777 (class 2606 OID 16450)
-- Name: VendaProduto ID_Venda; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."VendaProduto"
    ADD CONSTRAINT "ID_Venda" FOREIGN KEY ("ID_Venda") REFERENCES public."Vendas"("ID_Venda");


--
-- TOC entry 4773 (class 2606 OID 16401)
-- Name: Vendas ID_Vendedor; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Vendas"
    ADD CONSTRAINT "ID_Vendedor" FOREIGN KEY ("ID_Vendedor") REFERENCES public."Vendedores"("ID_Vendedor");


-- Completed on 2025-09-05 11:08:56

--
-- PostgreSQL database dump complete
--
