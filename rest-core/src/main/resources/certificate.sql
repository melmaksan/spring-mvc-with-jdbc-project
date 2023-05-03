-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema certificate_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema certificate_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `certificate_db` DEFAULT CHARACTER SET utf8 ;
USE `certificate_db` ;

-- -----------------------------------------------------
-- Table `certificate_db`.`tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `certificate_db`.`tag` ;

CREATE TABLE IF NOT EXISTS `certificate_db`.`tag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `certificate_db`.`gift_certificate`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `certificate_db`.`gift_certificate` ;

CREATE TABLE IF NOT EXISTS `certificate_db`.`gift_certificate` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` MEDIUMTEXT NOT NULL,
  `price` INT NOT NULL,
  `duration` INT NULL,
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `certificate_db`.`gift_certificate_has_tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `certificate_db`.`gift_certificate_has_tag` ;

CREATE TABLE IF NOT EXISTS `certificate_db`.`gift_certificate_has_tag` (
  `gift_certificate_id` BIGINT NOT NULL,
  `tag_id` INT NOT NULL,
  PRIMARY KEY (`gift_certificate_id`, `tag_id`),
  INDEX `fk_gift_certificate_has_tag_tag1_idx` (`tag_id` ASC) VISIBLE,
  INDEX `fk_gift_certificate_has_tag_gift_certificate_idx` (`gift_certificate_id` ASC) VISIBLE,
  CONSTRAINT `fk_gift_certificate_has_tag_gift_certificate`
    FOREIGN KEY (`gift_certificate_id`)
    REFERENCES `certificate_db`.`gift_certificate` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_gift_certificate_has_tag_tag1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `certificate_db`.`tag` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


INSERT into gift_certificate (name, description, price, duration, create_date) VALUES ('Promo_1000', 'gift certificate gives 1000₴ discount for 30 days', 1000, 30, NOW());
INSERT into gift_certificate (name, description, price, duration, create_date) VALUES ('Promo_100', 'gift certificate gives 100₴ discount for 30 days', 100, 30, NOW());
INSERT into gift_certificate (name, description, price, duration, create_date) VALUES ('Promo_500', 'gift certificate gives 500₴ discount for 30 days', 500, 30, NOW());

INSERT into tag (name) VALUE ('discount');
INSERT into tag (name) VALUE ('gift');

INSERT into gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (1, 1);
INSERT into gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (1, 2);
INSERT into gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (2, 1);
INSERT into gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (2, 2);
INSERT into gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (3, 1);
INSERT into gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (3, 2);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
