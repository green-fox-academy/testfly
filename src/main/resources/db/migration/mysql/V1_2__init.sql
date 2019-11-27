CREATE TABLE `building`
(
  `building_type` varchar(255) NOT NULL,
  `id`            bigint(20) AUTO_INCREMENT,
  `level`         bigint(20),
  `HP`            float(20),
  `started_at`    timestamp(6),
  `finished_at`   timestamp(6),
  `kingdom_id`    bigint(20),
  PRIMARY KEY (`id`),
  CONSTRAINT building_kingdom_id FOREIGN KEY (`kingdom_id`) REFERENCES `kingdom` (`id`)
);
