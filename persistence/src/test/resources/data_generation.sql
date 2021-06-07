INSERT INTO `role` (`name`)
VALUES ('ADMIN');

INSERT INTO `role` (`name`)
VALUES ('USER');

INSERT INTO `role` (`name`)
VALUES ('Test');

INSERT INTO `user` (`user_name`,
                    `password`,
                    `first_name`,
                    `last_name`,
                    `role`,
                    `is_active`,
                    `created_date`)
VALUES ('Kiryl', '123', 'Kiryl', 'Sadko', 1, 1, '2018-08-30 01:12:15');

INSERT INTO `user` (`user_name`,
                    `password`,
                    `first_name`,
                    `last_name`,
                    `role`,
                    `is_active`,
                    `created_date`)
VALUES ('Vova', '123', 'Vova', 'Vovkin', 2, 1, '2018-08-30 01:55:15');
