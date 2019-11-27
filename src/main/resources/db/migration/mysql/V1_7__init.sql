CREATE TABLE `role`
(
  `id`   bigint(20) AUTO_INCREMENT,
  `role` varchar(255),
  PRIMARY KEY (`id`)
);

INSERT INTO role(id, role)
VALUES (1, 'ADMIN'),
       (2, 'PREMIUM_MEMBER'),
       (3, 'MEMBER');

CREATE TABLE application_users_roles
(
  application_user_id BIGINT NOT NULL,
  role_id             BIGINT NOT NULL,
  PRIMARY KEY (application_user_id, role_id),
  CONSTRAINT application_users_roles_application_user_id FOREIGN KEY (application_user_id) REFERENCES application_user (id),
  CONSTRAINT application_users_roles_role_id FOREIGN KEY (role_id) REFERENCES role (id)
);

INSERT INTO application_user(id, password, user_email, username, kingdom_id)
VALUES (1, '$2a$10$/6Ly28j.LINYYQaYP/oF/eET63q9OYXy8HMvaLwoYxoStOTFG3N4K', 'fkrisztian93@gmail.com', 'ChrisFrill',
        null);

INSERT application_users_roles(application_user_id, role_id)
VALUES (1, 1);