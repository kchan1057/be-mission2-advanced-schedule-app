#LV 3~6
CREATE TABLE writer
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR (50) NOT NULL,
    email VARCHAR (100) NOT NULL,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL
);

CREATE TABLE schedule
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    todo VARCHAR(20) NOT NULL,
    writer_id BIGINT NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    FOREIGN KEY (writer_id) REFERENCES writer(id)
);