DROP TABLE IF EXISTS certificate_tag;
DROP TABLE IF EXISTS gift_certificate;
DROP TABLE IF EXISTS tag;

CREATE TABLE gift_certificate
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    name             varchar(45)  DEFAULT NULL,
    description      varchar(100) DEFAULT NULL,
    price            double       DEFAULT NULL,
    duration         int          DEFAULT NULL,
    create_date      varchar(100) DEFAULT NULL,
    last_update_date varchar(100) DEFAULT NULL
);


CREATE TABLE tag
(
    id   int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(45) DEFAULT NULL

);


CREATE TABLE certificate_tag
(
    tag_id         int NOT NULL,
    certificate_id int NOT NULL,
    PRIMARY KEY (tag_id, certificate_id),
    CONSTRAINT FK_gift_serticiates FOREIGN KEY (certificate_id) REFERENCES gift_certificate (id),
    CONSTRAINT FK_tags FOREIGN KEY (tag_id) REFERENCES tag (id)
);
