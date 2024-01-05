----------------------
-- Créer les tables
----------------------

CREATE TABLE USERS (
	id	    INT			      NOT NULL,
	name	VARCHAR(30)       NOT NULL,
	PRIMARY KEY (id)
	);

CREATE TABLE SEQUENCES (
    id      VARCHAR(50)       NOT NULL,
    sValue  numeric(10)       NOT NULL,
    constraint IDSEQUENCE primary key (id)
    );
        
CREATE TABLE GAMES (
    id      INT             NOT NULL,
    userId  INT             NOT NULL,
    score   INT             NOT NULL,
    state   VARCHAR(50)     NOT NULL,
    word    VARCHAR(50)     NOT NULL,
    hardmode BOOLEAN DEFAULT false NOT NULL,	 
    PRIMARY KEY (id),
    FOREIGN KEY(userId) REFERENCES USERS(id)
    );
