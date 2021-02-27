# Ride Motors
  
  Projeto construido para o TCC, do curso de Análise e Desenvolvimento de Sistemas. 

# Sobre o projeto

Ride motors é um sistema de controle de estoque para oficina mecânica, contruido para
auxiliar o gestor da oficina nas suas atividades do dia a dia, como vendas e controle dos seus produtos, melhorando o gerenciamento e evitando peças perdidas ou 
em falta por exemplo.

# Principais Telas do Sistema
#### Tela de Cadastro de produtos
![Cadastro de proutos](https://github.com/Alanlima21/Ride_Motors/blob/master/Produtos.png)
#### Tela de vendas
![Tela de vendas](https://github.com/Alanlima21/Ride_Motors/blob/master/TelaVendas.png)

# Tecnologias utilizadas
## Back end
- Java
- JDBC
- Maven
## Front end
- JSF
- PrimeFaces
- Omnifaces
## Banco de dados
- MySql

# Como executar o projeto
  - Pré-requisitos: Java 11
  - Servidor Tomcat
  - MySql
  - xampp
```bash
# iniciar o MySql no xampp
# clonar repositório
git clone https://github.com/Alanlima21/Ride_Motors.git

# Executar o Script no mysql
-- MySQL Script generated by MySQL Workbench
-- Sat Feb 27 14:32:00 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema oficina
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema oficina
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `oficina` DEFAULT CHARACTER SET latin1 ;
USE `oficina` ;

-- -----------------------------------------------------
-- Table `oficina`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`cliente` (
  `id_clie` INT(11) NOT NULL AUTO_INCREMENT,
  `nome_clie` VARCHAR(30) NOT NULL,
  `cpf_clie` VARCHAR(15) NOT NULL,
  `email_clie` VARCHAR(40) NOT NULL,
  `data_clie` VARCHAR(15) NULL DEFAULT NULL,
  PRIMARY KEY (`id_clie`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `oficina`.`funcionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`funcionario` (
  `id_func` INT(11) NOT NULL AUTO_INCREMENT,
  `nome_func` VARCHAR(30) NOT NULL,
  `cpf_func` VARCHAR(15) NOT NULL,
  `funcao_func` VARCHAR(30) NOT NULL,
  `salario_func` DOUBLE NOT NULL,
  `telefone_func` VARCHAR(15) NULL DEFAULT NULL,
  PRIMARY KEY (`id_func`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `oficina`.`produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`produto` (
  `id_prod` INT(11) NOT NULL AUTO_INCREMENT,
  `nome_prod` VARCHAR(30) NOT NULL,
  `fornecedor_prod` VARCHAR(30) NOT NULL,
  `preco_prod` DOUBLE NOT NULL,
  `quantidade_prod` INT(11) NOT NULL,
  `telefone_prod` VARCHAR(15) NULL DEFAULT NULL,
  PRIMARY KEY (`id_prod`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `oficina`.`usuariocad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`usuariocad` (
  `id_usca` INT(11) NOT NULL AUTO_INCREMENT,
  `nome_usca` VARCHAR(15) NOT NULL,
  `sobrenome_usca` VARCHAR(15) NOT NULL,
  `senha_usca` VARCHAR(20) NOT NULL,
  `confSenha_usca` VARCHAR(20) NOT NULL,
  `cpf_usca` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id_usca`),
  UNIQUE INDEX `cpf_usca` (`cpf_usca` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `oficina`.`venda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oficina`.`venda` (
  `id_vend` INT(11) NOT NULL AUTO_INCREMENT,
  `quantidade_vend` INT(11) NULL DEFAULT NULL,
  `valor_vend` DOUBLE NULL DEFAULT NULL,
  `funcionario_vend` INT(11) NULL DEFAULT NULL,
  `cliente_vend` INT(11) NULL DEFAULT NULL,
  `data_vend` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id_vend`),
  INDEX `fk_funcionario` (`funcionario_vend` ASC) VISIBLE,
  INDEX `fk_cliente` (`cliente_vend` ASC) VISIBLE,
  CONSTRAINT `fk_cliente`
    FOREIGN KEY (`cliente_vend`)
    REFERENCES `oficina`.`cliente` (`id_clie`),
  CONSTRAINT `fk_funcionario`
    FOREIGN KEY (`funcionario_vend`)
    REFERENCES `oficina`.`funcionario` (`id_func`))
ENGINE = InnoDB
AUTO_INCREMENT = 34
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

# Fazer o import do projeto na sua IDE
File -> Import -> Existing Maven Projects

# executar o projeto
 src -> main -> webapp -> sistema -> index.html -> Run As -> Run on Server
```
# Autor

Alan Souza Lima
<br> </br>
email: alan.lima123@hotmail.com
