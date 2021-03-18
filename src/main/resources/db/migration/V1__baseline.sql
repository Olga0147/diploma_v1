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
(1,'TestMain','Class_1','Тестовая страница'),
(2,'TestForRelations','Class_2','Обычная');

INSERT INTO Attribute (ID,NAME,OwnerClassId,AttributeType,AttributeSize, CanBeNull)VALUES
(1,'FloatField',1,1,0,false),
(2,'IntegerField',1,2,0,false),
(3,'SmallIntField',1,3,0,false),
--(4,'MMEDIAField',1,4,0,false),--
(5,'StringField',1,6,64,false),
(6,'TextField',1,5,0,false),
(7,'XMEMOField',1,7,0,false),
--(8,'TIMEField',1,8,0,false)--

(9,'TITLE',2,6,64,false),
(11,'AUTHOR',2,6,64,false)
;

INSERT INTO Object (ID,OwnerClassId) VALUES
(1,1),
(2,1),
(3,2),
(4,2);

CREATE TABLE Class_1 (
ID             INTEGER NOT NULL PRIMARY KEY,
FloatField     FLOAT NOT NULL,
IntegerField   INTEGER NOT NULL,
SmallIntField  SMALLINT NOT NULL,
StringField    VARCHAR(64) NOT NULL,
TextField      TEXT NOT NULL,
XMEMOField     TEXT NOT NULL
--TIMEField      DATE NOT NULL--
);

INSERT INTO Class_1 (ID,FloatField,IntegerField,SmallIntField,StringField,TextField,XMEMOField) VALUES
(1, 1.2, 123, 12,'Sara','She is my friend',
        '<div style="border:1px solid black">Left Right</div>
        <div>She reads: <aggregation aggregationId="1" templateId="2" objectId="3" type="hyperlink">Book</aggregation></div>
        <div><aggregation aggregationId="1" templateId="2" objectId="3" type="object"></aggregation></div>
        <div><aggregation aggregationId="1" templateId="2" type="objects"></aggregation></div>'),
(2, 1.23, 555555555, 66,'Tom','He is not my friend',
        '<div style="border:1px solid black">Round Square</div>
        <div>He reads: <aggregation aggregationId="1" templateId="2" objectId="4" type="hyperlink">Bad Book</aggregation></div>
        <div><aggregation aggregationId="1" templateId="2" objectId="4" type="object"></aggregation></div>
        <div><aggregation aggregationId="1" templateId="2" type="objects"></aggregation></div>');

CREATE TABLE Class_2 (
 ID             INTEGER NOT NULL PRIMARY KEY,
 TITLE           VARCHAR(64) NOT NULL,
 AUTHOR         VARCHAR(64) NOT NULL
);

INSERT INTO Class_2 (ID,TITLE,AUTHOR) VALUES
(3,'Русский язык для самых маленьких','Твой кот'),
(4,'Как 5G вышки портят экологию','Неизвестный дурачок');

INSERT INTO Aggregation (ID,NAME,FromClassId,ToClassId) VALUES
(1,'читает',1,2);

INSERT INTO Association (ID,NAME,FromClassId,ToClassId) VALUES
(1,'зачем я существую?',1,2);

INSERT INTO AssociationImplementation (ID,AssociationId,FromObjectId,ToObjectId) VALUES
(1,1,1,2);

INSERT INTO Template (ID,NAME,BODY,OwnerClassId,DESCRIPTION) VALUES
(1,'v1',
        '<div style="color:red"><field>FloatField</field></div>
        <div style="color:orange"><field>IntegerField</field></div>
        <div style="color:yellow"><field>SmallIntField</field></div>
        <div style="color:green"><field>StringField</field></div>
        <div style="color:#42aaff"><field>TextField</field></div>
        <div style="color:blue"><field>XMEMOField</field></div>

        <div><association associationId="2" templateId="5">Link</association></div>'
,1,'all var template'),
(2,'v1','<h2>Книга</h2>
        <div style="color:red">Название: <field>TITLE</field></div>
        <div style="color:orange">Автор : <field>AUTHOR</field></div>'
,2,'all var template');