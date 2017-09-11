-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`friends`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`friends` (
  `Friend1` VARCHAR(45) NOT NULL,
  `Friend2` VARCHAR(45) NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`table1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`table1` (
  `user` VARCHAR(50) CHARACTER SET 'utf8' NOT NULL,
  `pass` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `gender` VARCHAR(45) NULL DEFAULT NULL,
  `age` VARCHAR(45) NULL DEFAULT NULL,
  `country` VARCHAR(45) NULL DEFAULT NULL,
  `question` VARCHAR(45) NULL DEFAULT NULL,
  `answer` VARCHAR(45) NULL DEFAULT NULL,
  `mailvisible` VARCHAR(45) NULL DEFAULT NULL,
  `agevisible` VARCHAR(45) NULL DEFAULT NULL,
  `countryvisible` VARCHAR(45) NULL DEFAULT NULL,
  `scoretic` VARCHAR(45) NULL DEFAULT NULL,
  `scoreblack` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`user`, `email`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `user_UNIQUE` (`user` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
