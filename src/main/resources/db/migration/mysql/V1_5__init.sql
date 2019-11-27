CREATE TABLE `progression`
(
  `id`          bigint(20) AUTO_INCREMENT,
  `model_id`    bigint(20),
  `level`       bigint(20),
  `type`        varchar(20),
  `finished` timestamp(6),
  `kingdom_id`    bigint(20),
  PRIMARY KEY (`id`),
  CONSTRAINT progression_kingdom_id FOREIGN KEY (`kingdom_id`) REFERENCES `kingdom` (`id`)
);
