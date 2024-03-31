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
                        `approve` BOOLEAN NULL,
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
    (222222222, 'Tel', 'Aviv', 3, 2, 'ParkManager'),
    (333333333, 'kar', 'miel', 4, 3, 'ParkManager'),
    (444444444, 'haif', 'aYosef', 2, 1, 'ParkManager'),
    (555555555, 'shlomi', 'cohen', 5, 1, 'OfficeWorker'),
    (666666666, 'Israel', 'Israeli', 2, 1, 'ParkWorker');

-- Insert park info
INSERT INTO `park` (`park_id_pk`, `park_name`, `capacity`, `current_visitors`, `average_visit_time`, `capacity_offset`)
VALUES
    (1, 'Haifa', 400, 0, 4, 10),
    (2, 'Tel Aviv', 700, 0, 4, 10),
    (3, 'Karmiel', 300, 0, 4, 10);


INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('032f684d-0675-45bd-a452-b5d0defbf76e', 281459953, 1, '2024-03-28', '07:00:00', '15:00:00', 3, ' another@example.com', '9876543210', 0, 1, 0, 0, 1);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('032f684d-0675-45bd-a452-b5d0defbf86e', 281459953, 1, '2024-03-28', '07:00:00', '15:00:00', 3, ' another@example.com', ' 9876543210', 0, 1, 0, 0, 1);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('12aa88f4-e2bb-45ac-bf2f-3008095fe279', 123456789, 3, '2024-03-30', '07:30:00', '16:30:00', 1, '@', '3214569870', 0, 0, 0, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('27d58c57-20a1-4361-975f-82b31369ef48', 222222222, 1, '2024-04-04', '07:00:00', '13:00:00', 13, '@', '0501114444', 1, 0, 1, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('2b8f9e7f-4f2b-4f2b-8f9e-7f4f2b8f9e7f', 456789987, 1, '2024-03-28', '07:00:00', '15:00:00', 1, ' tenth@example.com', ' 9012345678', 0, 1, 1, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('2b8f9e7f-4f2b-4f2b-8f9e-7f4f2b8f9e9f', 654321987, 1, '2024-03-28', '07:00:00', '15:00:00', 2, ' seventh@example.com', '6789012345', 1, 0, 0, 1, 1);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('2b8f9e7f-4f2b-4f2b-8f9e-7f4f2b9f9e7f', 456789987, 1, '2024-03-28', '07:00:00', '15:00:00', 1, ' tenth@example.com', '9012345678', 0, 1, 1, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('3f561698-fcdd-4dc9-9245-5e50b7234ecc', 123654789, 3, '2024-03-30', '06:30:00', '15:30:00', 1, '@', '1234567890', 0, 0, 0, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('41e4c67e-8dd8-4ac6-ab47-d8726c7a4d47', 123654987, 1, '2024-04-02', '06:00:00', '12:00:00', 3, '@', '1234567890', 0, 0, 0, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('4d55f854-f0d5-4075-b3a8-ee5d739e280a', 222222222, 3, '2024-04-04', '07:00:00', '16:00:00', 9, '@', '0501114444', 1, 0, 1, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('4f2b8f9e-7f4f-4f2b-8f9e-7f4f2b8f9e4f', 456789123, 1, '2024-03-28', '07:00:00', '15:00:00', 4, ' fifth@example.com', '4567890123', 1, 0, 0, 0, 1);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('4f2b8f9e-7f4f-4f2b-8f9e-7f4f2b8f9e5f', 987123456, 1, '2024-03-28', '07:00:00', '15:00:00', 3, ' eighth@example.com', '7890123456', 0, 1, 1, 0, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('4f2b8f9e-7f4f-4f2b-8f9e-7f4f2b8f9e7f', 987123456, 1, '2024-03-28', '07:00:00', '15:00:00', 3, ' eighth@example.com', ' 7890123456', 0, 1, 1, 0, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('5250f130-7fb7-4d7d-9e26-14657a9103b6', 123654789, 3, '2024-04-02', '06:00:00', '15:00:00', 1, '@', '3214569870', 0, 0, 0, 0, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('70610790-a21a-4be7-98bf-8b1e304dddda', 123456789, 3, '2024-03-30', '06:30:00', '15:30:00', 2, '@', '3216549870', 0, 0, 0, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('7f4f2b8f-9e7f-4f2b-8f9e-7f4f2b8f9e1f', 987654321, 1, '2024-03-28', '07:00:00', '15:00:00', 1, ' fourth@example.com', '3456789012', 0, 1, 0, 1, 1);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('7f4f2b8f-9e7f-4f2b-8f9e-7f4f2b8f9e7f', 987654321, 1, '2024-03-28', '07:00:00', '15:00:00', 1, ' fourth@example.com', ' 3456789012', 0, 1, 0, 1, 1);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('810160d6-f02a-4429-ba26-a2230be213b4', 222222222, 1, '2024-04-16', '08:30:00', '14:30:00', 6, '@', '0501114444', 1, 0, 0, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('833f3039-bcb7-46cf-bb41-2cf5e1bb81e5', 222222222, 2, '2025-03-05', '07:00:00', '10:00:00', 11, '@', '0501114444', 1, 0, 1, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('8a1b6c9e-4e7f-4f2b-8f9e-7f4f2b1f9e7f', 123456789, 1, '2024-03-28', '07:00:00', '15:00:00', 2, ' third@example.com', '2345678901', 1, 0, 1, 0, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('8a1b6c9e-4e7f-4f2b-8f9e-7f4f2b8f9e7f', 123456789, 1, '2024-03-28', '07:00:00', '15:00:00', 2, ' third@example.com', ' 2345678901', 1, 0, 1, 0, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('8f9e7f4f-2b8f-4f2b-8f9e-7f3f2b8f9e7f', 789123456, 1, '2024-03-28', '07:00:00', '15:00:00', 6, ' sixth@example.com', '5678901234', 1, 1, 1, 0, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('8f9e7f4f-2b8f-4f2b-8f9e-7f4f2b6f9e7f', 123987456, 1, '2024-03-28', '07:00:00', '15:00:00', 5, ' ninth@example.com', '8901234567', 1, 0, 0, 0, 1);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('8f9e7f4f-2b8f-4f2b-8f9e-7f4f2b8f9e7f', 123987456, 1, '2024-03-28', '07:00:00', '15:00:00', 5, ' ninth@example.com', ' 8901234567', 1, 0, 0, 0, 1);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('97c8b496-e500-4113-afd4-773115310e2d', 999999999, 2, '2024-04-04', '06:30:00', '09:30:00', 11, '@', '3214569870', 1, 0, 0, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('99566db6-d163-4499-b122-94be37b05bb6', 551732262, 1, '2024-03-28', '07:00:00', '15:00:00', 5, ' user@example.com', ' 1234567890', 1, 0, 0, 0, 1);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('b12ecde0-5cbd-4205-b6e6-9f9359cad5df', 123456789, 3, '2024-03-30', '06:30:00', '15:30:00', 1, '@', '1234567890', 0, 0, 0, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('ba30fa86-78c5-4103-8716-918abf4b0a04', 321456987, 2, '2024-04-02', '06:30:00', '09:30:00', 1, '@', '1234569870', 0, 0, 0, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('c1c0200d-8785-4f11-8fe5-2d6923dc0f7c', 123456789, 1, '2024-03-31', '06:00:00', '12:00:00', 1, '@', '1111111111', 0, 0, 0, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('c2b7f1e0-6a4e-4d9e-9b7e-3e1b5e5a7a7a', 987456321, 2, '2024-03-15', '15:30:00', '19:30:00', 3, 'nir8@example.com', '1111111124', 1, 0, 0, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('c2b8f1e0-6a2e-4d9e-9b7e-3e1b5e5a0a7a', 123456777, 1, '2024-03-26', '07:30:00', '11:30:00', 10, 'nir2@example.com', '1111111114', 1, 0, 1, 0, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('c2b8f1e0-6a2e-4d9e-9b7e-3e1b5e5a6a7a', 987654321, 2, '2024-03-21', '07:00:00', '11:00:00', 14, 'nir1@example.com', '1111111112', 1, 0, 0, 0, 1);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('c2b8f1e0-6a2e-4d9e-9b7e-3e1b5e5a7a7a', 123456789, 1, '2024-03-21', '07:00:00', '11:00:00', 2, 'nir@example.com', '1111111111', 0, 0, 0, 0, 1);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('c2b8f1e0-6a2e-4d9e-9b7e-3e1b5e5a9a7a', 123456788, 3, '2024-03-21', '07:00:00', '11:00:00', 14, 'nir4@example.com', '1111111113', 1, 0, 0, 0, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('c2b8f1e0-6a4e-4d9e-9b7e-3e1b5e5a7a7a', 123456987, 2, '2024-03-26', '09:30:00', '11:30:00', 5, 'nir6@example.com', '1111111124', 0, 1, 0, 0, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('dc408096-785b-4cbe-9675-76260ce7607a', 222222222, 3, '2024-04-04', '07:00:00', '16:00:00', 13, '@', '0501114444', 1, 0, 1, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('e14f273f-14a9-4e2e-adcd-deac86396eec', 222222222, 2, '2024-04-04', '07:00:00', '10:00:00', 9, '@', '0501114444', 1, 0, 1, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('ebc10c57-4dbe-4f66-ad6c-2a6e60853484', 999999999, 3, '2024-04-04', '06:30:00', '15:30:00', 11, '@', '3214569870', 1, 0, 1, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('ee9e2cb5-1c65-453f-b97d-a79280421ca7', 123456788, 1, '2024-04-05', '08:30:00', '14:30:00', 5, '@', '3216549870', 0, 1, 0, 1, 0);
INSERT INTO gonature.`order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid) VALUES ('fcc28ee2-4c5f-45ad-a47c-4d6ca2a006a5', 333333333, 1, '2024-04-02', '05:00:00', '11:00:00', 1, '@', '0508138535', 0, 0, 0, 1, 0);