-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS `gonature`;
-- Use the database
USE `gonature`;
-- Drop the tables if they exist
DROP TABLE IF EXISTS `files`;
DROP TABLE IF EXISTS `park_changes`;
DROP TABLE IF EXISTS `order`;
DROP TABLE IF EXISTS `account_extra_info_worker`;
DROP TABLE IF EXISTS `park`;
DROP TABLE IF EXISTS `account`;
-- Create the `files` table
CREATE TABLE `files` (
    `file_name` VARCHAR(255) NOT NULL,
    `file_data` LONGBLOB
);
-- Create the `park_changes` table
CREATE TABLE `park_changes` (
    `park_name` VARCHAR(45) NOT NULL,
    `new_capacity` INT NULL,
    `old_capacity` INT NULL,
    `new_average_visit_time` INT NULL,
    `old_average_visit_time` INT NULL,
    `new_capacity_offset` INT NULL,
    `old_capacity_offset` INT NULL,
    PRIMARY KEY (`park_name`)
);
-- Create the `account` table
CREATE TABLE `account` (
    `account_id_pk` INT NOT NULL,
    `account_type` VARCHAR(45) NULL,
    `username` VARCHAR(45) NULL,
    `password` VARCHAR(45) NULL,
    `email` VARCHAR(45) NULL,
    `phone` VARCHAR(45) NULL,
    PRIMARY KEY (`account_id_pk`)
);
-- Create the `park` table
CREATE TABLE `park` (
    `park_id_pk` INT NOT NULL,
    `park_name` VARCHAR(45) NULL,
    `capacity` INT NULL,
    `current_visitors` INT NULL,
    `average_visit_time` INT NULL,
    `capacity_offset` INT NULL,
    PRIMARY KEY (`park_id_pk`)
);
-- Create the `order` table
CREATE TABLE `order` (
    `order_id_pk` VARCHAR(65) NOT NULL,
    `account_id` INT NULL,
    `park_id_fk` INT NULL,
    `visit_date` DATE NULL,
    `visit_time` TIME NULL,
    `exit_time` TIME NULL,
    `number_of_visitors` INT NULL,
    `email` VARCHAR(45) NULL,
    `phone` VARCHAR(45) NULL,
    `guided_order` BOOLEAN NULL,
    `on_arrival_order` BOOLEAN NULL,
    `on_waiting_list` BOOLEAN NULL,
    `cancelled` BOOLEAN NULL,
    `paid` BOOLEAN NULL,
    PRIMARY KEY (`order_id_pk`),
    INDEX `park_id_fk_idx` (`park_id_fk` ASC)
);
-- Create the `account_extra_info_worker` table
CREATE TABLE `account_extra_info_worker` (
    `account_id` INT NOT NULL,
    `firstname` VARCHAR(45) NULL,
    `lastname` VARCHAR(45) NULL,
    `worker_id` INT NULL,
    `park_id_fk` INT NULL,
    `worker_role` VARCHAR(45) NULL,
    PRIMARY KEY (`account_id`),
    INDEX `park_id_fk_idx` (`park_id_fk` ASC),
    CONSTRAINT `worker_account_fk`
        FOREIGN KEY (`account_id`)
        REFERENCES `account` (`account_id_pk`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- Insert account info
INSERT INTO `account` (`account_id_pk`, `account_type`, `username`, `password`, `email`, `phone`)
VALUES
    -- account_id_pk,account_type,username,password,email,phone
    (111111111, 'Worker','OfficeMan', '123456', 'OffMan@bla.com', '1234567890'),
    (222222222, 'Worker', 'TelMan', '123456', 'example@example.com', '1234567891'),
    (333333333, 'Worker', 'KarMan', '123456', 'bla@example.com', '1234567892'),
    (444444444, 'Worker','haifaMan', '123456', 'ParkMan@bla.com', '1234567893'),
    (555555555, 'Worker', 'OffWork', '123456', 'OF@bla.com', '1234567894'),
    (666666666, 'Worker', 'ParkWork', '123456', 'yoni@example.com', '1234567895'),
    (101010101, 'TourGuide', 'nir', '123456', 'tour@guide.com', '1234567896'),
    (202020202, 'Client', 'liad', '123456', 'client@liad.com', '1234567897');



-- Insert account extra info
INSERT INTO `account_extra_info_worker` (`account_id`, `firstname`, `lastname`, `worker_id`, `park_id_fk`, `worker_role`)
VALUES
    -- account_id,firstname,lastname,worker_id,park_id_fk,worker_role
    (111111111, 'ceo', 'ceo', 1, 1, 'OfficeManager'),
    (222222222, 'haif', 'aYosef', 2, 1, 'ParkManager'),
    (333333333, 'Tel', 'Aviv', 3, 2, 'ParkManager'),
    (444444444, 'kar', 'miel', 4, 3, 'ParkManager'),
    (555555555, 'shlomi', 'cohen', 5, 1, 'OfficeWorker'),
    (666666666, 'Israel', 'Israeli', 2, 1, 'ParkWorker');

-- Insert park info
INSERT INTO `park` (`park_id_pk`, `park_name`, `capacity`, `current_visitors`, `average_visit_time`, `capacity_offset`)
VALUES
    (1, 'Haifa', 400, 0, 4, 10),
    (2, 'Tel Aviv', 700, 0, 4, 10),
    (3, 'Karmiel', 300, 0, 4, 10);