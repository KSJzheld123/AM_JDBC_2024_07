CREATE DATABASE `AM_JDBC_2024_07`;
USE `AM_JDBC_2024_07`;

DROP TABLE `article`;

CREATE TABLE `article` (
`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
`regDate` DATETIME NOT NULL,
`updateDate` DATETIME NOT NULL,
`title` VARCHAR(100) NOT NULL,
`body` TEXT NOT NULL
);

SELECT * FROM `article`;

INSERT INTO `article`
SET `regDate` = NOW(),
`updateDate` = NOW(),
`title` = '제목1',
`body` = '내용1';

## 글자 이어주는 실행함수
SELECT CONCAT('제목','1');

## 실행할때 마다 다른결과를 얻을수 있는 실행랜덤함수(0~1);
SELECT RAND();

SELECT SUBSTRING(RAND() * 10 FROM 1 FOR 1);

INSERT INTO `article`
SET `regDate` = NOW(),
`updateDate` = NOW(),
`title` = CONCAT('제목', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
`body` = CONCAT('내용', SUBSTRING(RAND() * 1000 FROM 1 FOR 2));

DROP TABLE article;

SELECT * FROM `article`;
