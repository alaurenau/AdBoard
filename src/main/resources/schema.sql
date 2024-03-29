CREATE TABLE AD
(
  AD_ID INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
  SUBJECT VARCHAR(200) NOT NULL,
  BODY CLOB NOT NULL,
  USER_ID INTEGER NOT NULL,
  LAST_MODIFIED DATETIME NOT NULL
);
CREATE TABLE USER
(
  USER_ID INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
  LOGIN VARCHAR(100) NOT NULL UNIQUE,
  NAME VARCHAR(100) NOT NULL,
  PASSWORD VARCHAR(200) NOT NULL,
  EMAIL VARCHAR(200) NOT NULL,
);
CREATE TABLE COMMENT
(
  COMMENT_ID INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
  USER_ID INTEGER NOT NULL,
  AD_ID INTEGER NOT NULL,
  TEXT CLOB NOT NULL,
  IS_NEW BOOLEAN NOT NULL,
  DATE DATETIME NOT NULL
);

CREATE TABLE GROUPS
(
  ID INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
  NAME VARCHAR(50) NOT NULL,
  AUTHORITY VARCHAR(50) NOT NULL,
);
CREATE TABLE GROUP_MEMBERS
(
  GROUP_ID INTEGER NOT NULL,
  LOGIN VARCHAR(100) NOT NULL,
);