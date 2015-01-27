create database assingment character set utf8 collate utf8_polish_ci;

GRANT ALL PRIVILEGES ON assingment.* TO card@localhost IDENTIFIED BY 'card';
GRANT ALL PRIVILEGES ON assingment.* TO card@"%" IDENTIFIED BY 'card';

flush privileges;
