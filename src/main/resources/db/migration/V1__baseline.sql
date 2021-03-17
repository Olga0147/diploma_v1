CREATE TABLE Class (
    ID          SERIAL NOT NULL PRIMARY KEY,
    NAME        VARCHAR(128) NOT NULL,
    SystemName VARCHAR(128),
    DESCRIPTION TEXT
);

CREATE TABLE Object (
    ID             SERIAL NOT NULL PRIMARY KEY,
    OwnerClassId INTEGER NOT NULL REFERENCES Class(ID)
);

CREATE TABLE AttributeType (
   ID             SERIAL NOT NULL PRIMARY KEY,
   NAME           VARCHAR(128) NOT NULL,
   IMPLEMENTATION VARCHAR(64)
);

INSERT INTO AttributeType (ID,NAME,IMPLEMENTATION) VALUES
(1,'FLOAT','FLOAT'),
(2,'INTEGER','INTEGER'),
(3,'SMALLINT','SMALLINT'),
(4,'MMEDIA','TEXT'),
(5,'TEXT','TEXT'),
(6,'STRING','VARCHAR'),
(7,'XMEMO','TEXT'),
(8,'TIME','DATE');

CREATE TABLE Attribute (
   ID             SERIAL NOT NULL PRIMARY KEY,
   NAME           VARCHAR(128) NOT NULL,
   OwnerClassId INTEGER NOT NULL REFERENCES Class(ID),
   AttributeType INTEGER NOT NULL REFERENCES AttributeType(ID),
   AttributeSize INTEGER,
   CanBeNull        BOOLEAN NOT NULL
);

CREATE TABLE Template (
   ID           SERIAL NOT NULL PRIMARY KEY,
   NAME         VARCHAR(128) NOT NULL,
   BODY         TEXT NOT NULL,
   OwnerClassId INTEGER NOT NULL REFERENCES Class(ID),
   DESCRIPTION  TEXT
);

CREATE TABLE Association (
     ID            SERIAL NOT NULL PRIMARY KEY,
     NAME          VARCHAR(128) NOT NULL,
     FromClassId INTEGER NOT NULL REFERENCES Class(ID),
     ToClassId   INTEGER NOT NULL REFERENCES Class(ID)
);

CREATE TABLE AssociationImplementation (
    ID             SERIAL NOT NULL PRIMARY KEY,
    AssociationId INTEGER NOT NULL REFERENCES Association(ID),
    FromObjectId INTEGER NOT NULL REFERENCES Object(ID),
    ToObjectId   INTEGER NOT NULL REFERENCES Object(ID)
);

CREATE TABLE Aggregation (
    ID            SERIAL NOT NULL PRIMARY KEY,
    NAME          VARCHAR(128) NOT NULL,
    FromClassId INTEGER NOT NULL REFERENCES Class(ID),
    ToClassId   INTEGER NOT NULL REFERENCES Class(ID)
);

CREATE TABLE AggregationImplementation (
    ID             SERIAL NOT NULL PRIMARY KEY,
    AggregationId INTEGER NOT NULL REFERENCES Aggregation(ID),
    FromObjectId INTEGER NOT NULL REFERENCES Object(ID),
    ToObjectId   INTEGER NOT NULL REFERENCES Object(ID),
    ToTemplateId   INTEGER NOT NULL REFERENCES Template(ID),
    AttributeId  INTEGER NOT NULL REFERENCES Attribute(ID),
    Type         INTEGER NOT NULL
);

CREATE TABLE Resource (
    ID             SERIAL NOT NULL PRIMARY KEY,
    NAME           VARCHAR(128) NOT NULL,
    PATH           VARCHAR(128) NOT NULL,
    OwnerClassId INTEGER NOT NULL REFERENCES Class(ID)
);

INSERT INTO Class (ID,NAME,SystemName,DESCRIPTION) VALUES
(1,'Home','Class_1','Начальная страница'),
(2,'Лекция','Class_2','Обычная');

INSERT INTO Attribute (ID,NAME,OwnerClassId,AttributeType,AttributeSize, CanBeNull)VALUES
(1,'name',1,5,64,false),
(2,'date',1,7,0,true),
(3,'title',2,5,64,false),
(5,'author',2,5,64,false);

INSERT INTO Object (ID,OwnerClassId) VALUES
(1,1),
(2,1),
(3,2),
(4,2);

CREATE TABLE Class_1 (
ID             INTEGER NOT NULL PRIMARY KEY,
NAME           VARCHAR(64) NOT NULL,
date           DATE
);

INSERT INTO Class_1 (ID,NAME) VALUES
(1,'Sara'),
(2,'Томас');

CREATE TABLE Class_2 (
 ID             INTEGER NOT NULL PRIMARY KEY,
 TITLE           VARCHAR(64) NOT NULL,
 AUTHOR         VARCHAR(64) NOT NULL
);

INSERT INTO Class_2 (ID,TITLE,AUTHOR) VALUES
(3,'Math','Cat'),
(4,'Русский язык для самых маленьких','Пушкин');

INSERT INTO Aggregation (ID,NAME,FromClassId,ToClassId) VALUES
(1,'включает в себя',1,2),
(2,'включен в',2,1);

INSERT INTO Association (ID,NAME,FromClassId,ToClassId) VALUES
(1,'включает в себя',1,2),
(2,'включен в',2,1);

INSERT INTO AssociationImplementation (ID,AssociationId,FromObjectId,ToObjectId) VALUES
(1,1,1,2),
(2,2,2,1);

INSERT INTO Template (ID,NAME,BODY,OwnerClassId,DESCRIPTION) VALUES
(1,'v1','Hello world!',1,'non var template'),
(2,'v1','<h2>Some lecture</h2>',2,'non var template');