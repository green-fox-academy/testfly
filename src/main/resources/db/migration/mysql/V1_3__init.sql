CREATE TABLE `troop`
(
  `id`          bigint(20) AUTO_INCREMENT,
  `troop_type`  varchar(255)        NOT NULL,
  `level`       bigint(20) UNSIGNED NOT NULL,
  `HP`          float(20)  DEFAULT NULL,
  `attack`      bigint(20) DEFAULT NULL,
  `defense`     bigint(20) DEFAULT NULL,
  `started_at`  TIMESTAMP(6),
  `finished_at` TIMESTAMP(6),
  `kingdom_id`  bigint(20),
  PRIMARY KEY (`id`),
  CONSTRAINT troop_kingdom_id FOREIGN KEY (`kingdom_id`) REFERENCES `kingdom` (`id`)
);
