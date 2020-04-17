CREATE TABLE PUBLISHER (
	PUBLISHER_ID INT NOT NULL AUTO_INCREMENT,
	NAME VARCHAR NOT NULL,
	KEY VARCHAR NOT NULL,
	PRIMARY KEY (PUBLISHER_ID)
);

CREATE SEQUENCE SEQ_PUBLISHER
MINVALUE 1
START WITH 1
INCREMENT BY 50
CACHE 10;

CREATE TABLE COMIC (
	COMIC_ID INT NOT NULL AUTO_INCREMENT,
	KEY VARCHAR NOT NULL,
	NAME VARCHAR NOT NULL,
	PUBLISHER_ID INT NOT NULL,
	STATUS VARCHAR NOT NULL,
	DATE_OF_RELEASE DATE NOT NULL,
	AUTHOR VARCHAR,
	DESCRIPTION TEXT,
	PRIMARY KEY (COMIC_ID),
	FOREIGN KEY (PUBLISHER_ID) REFERENCES PUBLISHER(PUBLISHER_ID)
);

CREATE SEQUENCE SEQ_COMIC
MINVALUE 1
START WITH 1
INCREMENT BY 50
CACHE 10;

CREATE TABLE ISSUE (
	ISSUE_ID INT NOT NULL AUTO_INCREMENT,
	COMIC_ID INT NOT NULL,
	ISSUE_NUMBER VARCHAR NOT NULL,
	DATE_OF_RELEASE DATE NOT NULL,
	URL VARCHAR,
	PRIMARY KEY (ISSUE_ID),
	FOREIGN KEY (COMIC_ID) REFERENCES COMIC(COMIC_ID)
);

CREATE SEQUENCE SEQ_ISSUE
MINVALUE 1
START WITH 1
INCREMENT BY 50
CACHE 10;