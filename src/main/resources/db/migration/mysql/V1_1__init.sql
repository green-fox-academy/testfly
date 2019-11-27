CREATE TABLE `application_user`
(
  `id`         bigint(20) AUTO_INCREMENT,
  `password`   varchar(255),
  `user_email` varchar(255) DEFAULT NULL,
  `username`   varchar(255) DEFAULT NULL,
  `kingdom_id` bigint(20)   DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `kingdom`
(
  `id`   bigint(20) AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);