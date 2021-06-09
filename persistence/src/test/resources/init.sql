-- -----------------------------------------------------
-- Schema user_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `user_db`;
USE `user_db` ;

-- -----------------------------------------------------
-- Table `user_db`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_db`.`role`
(
    `id`   BIGINT(100) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table `user_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_db`.`user`
(
    `id`           BIGINT(100)  NOT NULL AUTO_INCREMENT,
    `user_name`    VARCHAR(30)  NOT NULL,
    `password`     VARCHAR(100) NOT NULL,
    `first_name`   VARCHAR(30)  NOT NULL,
    `last_name`    VARCHAR(30)  NOT NULL,
    `role_id`      VARCHAR(10)  NOT NULL,
    `is_active`    TINYINT      NOT NULL DEFAULT 1,
    `created_date` TIMESTAMP    NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_user_role`
        FOREIGN KEY (`id`)
            REFERENCES `user_db`.`role` (`id`)
);