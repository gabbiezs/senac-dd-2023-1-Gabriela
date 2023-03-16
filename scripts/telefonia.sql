CREATE SCHEMA `exemplos` ;

CREATE TABLE `exemplos`.`endereco` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `RUA` VARCHAR(45) NOT NULL,
  `CEP` VARCHAR(8) NOT NULL,
  `BAIRRO` VARCHAR(255) NOT NULL,
  `CIDADE` VARCHAR(255) NOT NULL,
  `ESTADO` VARCHAR(2) NOT NULL,
  `NUMERO` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `idENDERECO_UNIQUE` (`ID` ASC) VISIBLE);
  
  CREATE TABLE `exemplos`.`telefone` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `DDD` VARCHAR(3) NOT NULL,
  `NUMERO` VARCHAR(9) NOT NULL,
  `ATIVO` TINYINT NOT NULL,
  `MOVEL` TINYINT NOT NULL,
  `ID_CLIENTE` INT NULL,
  PRIMARY KEY (`ID`));
  
  CREATE TABLE `exemplos`.`cliente` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NOME` VARCHAR(255) NULL,
  `CPF` VARCHAR(11) NOT NULL,
  `ATIVO` TINYINT NOT NULL,
  `ID_ENDERECO` INT NOT NULL,
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `CPF_UNIQUE` (`CPF` ASC) VISIBLE,
  CONSTRAINT `ID_ENDERECO`
    FOREIGN KEY (`ID`)
    REFERENCES `exemplos`.`endereco` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);