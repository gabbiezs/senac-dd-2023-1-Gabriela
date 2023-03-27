DROP DATABASE IF EXISTS DBVACINA; 
CREATE DATABASE DBVACINA; 

USE DBVACINA; 

CREATE TABLE ESTAGIOPESQUISA ( 
IDESTAGIOPESQUISA INT NOT NULL PRIMARY KEY AUTO_INCREMENT , 
DESCRICAO VARCHAR(255) 
); 

CREATE TABLE TIPOPESSOA ( 
IDTIPOPESSOA INT NOT NULL PRIMARY KEY AUTO_INCREMENT , 
DESCRICAO VARCHAR(255) 

); 

CREATE TABLE AVALIACAO ( 
IDAVALIACAO INT NOT NULL PRIMARY KEY AUTO_INCREMENT , 
DESCRICAO VARCHAR(255) 

); 

CREATE TABLE PESSOA ( 
	IDPESSOA INT NOT NULL AUTO_INCREMENT,
    IDTIPOPESSOA INT NOT NULL,
    NOME VARCHAR(255), 
    DATANASCIMENTO VARCHAR(45), 
    SEXO VARCHAR(45), 
	CPF VARCHAR (11) UNIQUE, 
    FOREIGN KEY (IDTIPOPESSOA) REFERENCES TIPOPESSOA(IDTIPOPESSOA), 

PRIMARY KEY(IDPESSOA) 

); 

CREATE TABLE VACINA ( 
	IDVACINA INT NOT NULL AUTO_INCREMENT,
    IDPESSOA INT NOT NULL,
    NOMEVACINA VARCHAR(45),
	PAISORIGEM VARCHAR(45), 
    ESTADOPESQUISA INT, 
    DATAINICIO DATE,
    FOREIGN KEY (IDPESSOA) REFERENCES PESSOA(IDPESSOA),
    PRIMARY KEY (IDVACINA) 
);

INSERT INTO ESTAGIOPESQUISA (DESCRICAO) VALUES ('INICIAL');
INSERT INTO ESTAGIOPESQUISA (DESCRICAO) VALUES ('TESTES');
INSERT INTO ESTAGIOPESQUISA (DESCRICAO) VALUES ('MASSA');

INSERT INTO TIPOPESSOA (DESCRICAO) VALUES ('PESQUISADOR');
INSERT INTO TIPOPESSOA (DESCRICAO) VALUES ('VOLUNTARIO');
INSERT INTO TIPOPESSOA (DESCRICAO) VALUES ('GERAL');

INSERT INTO AVALIACAO (DESCRICAO) VALUES ('PESSIMO');
INSERT INTO AVALIACAO (DESCRICAO) VALUES ('DESCONFORTAVEL');
INSERT INTO AVALIACAO (DESCRICAO) VALUES ('NORMAL');
INSERT INTO AVALIACAO (DESCRICAO) VALUES ('BOM');
INSERT INTO AVALIACAO (DESCRICAO) VALUES ('OTIMO');