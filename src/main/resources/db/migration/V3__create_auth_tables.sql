CREATE TABLE sys_user (
      id INT NOT NULL AUTO_INCREMENT,
      username VARCHAR(50) NOT NULL,
      password VARCHAR(100) NOT NULL,
      enabled TINYINT(1) NOT NULL DEFAULT 1,
      create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
      update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
          ON UPDATE CURRENT_TIMESTAMP,

      PRIMARY KEY (id),
      UNIQUE KEY uk_sys_user_username (username)
);


CREATE TABLE sys_role (
      id INT NOT NULL AUTO_INCREMENT,
      code VARCHAR(50) NOT NULL,
      name VARCHAR(50) NOT NULL,

      PRIMARY KEY (id),
      UNIQUE KEY uk_sys_role_code (code)
);


CREATE TABLE sys_user_role (
       user_id INT NOT NULL,
       role_id INT NOT NULL,

       PRIMARY KEY (user_id, role_id),

       CONSTRAINT fk_user_role_user
           FOREIGN KEY (user_id)
               REFERENCES sys_user (id)
               ON DELETE CASCADE,

       CONSTRAINT fk_user_role_role
           FOREIGN KEY (role_id)
               REFERENCES sys_role (id)
               ON DELETE CASCADE
);


INSERT INTO sys_role (code, name)
VALUES
    ('ADMIN', '管理员'),
    ('CLERK', '店员');