CREATE TABLE `resource`
(
  `resource_type` varchar(255) NOT NULL,
  `id`            bigint(20) AUTO_INCREMENT,
  `amount`        bigint(20)  DEFAULT NULL,
  `updated_at`    datetime(6) DEFAULT NULL,
  `kingdom_id`    bigint(20),
  PRIMARY KEY (`id`),
  CONSTRAINT `kingdom_resource_kingdom_id` FOREIGN KEY (`kingdom_id`) REFERENCES `kingdom` (`id`)
);