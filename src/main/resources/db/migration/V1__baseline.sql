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
(4,'MMEDIA','INTEGER REFERENCES Mmedia(ID)'),
(5,'TEXT','TEXT'),
(6,'STRING','VARCHAR'),
(7,'XMEMO','TEXT'),
(8,'TIME','DATE');

CREATE TABLE ResourceType (
   ID             SERIAL NOT NULL PRIMARY KEY,
   NAME           VARCHAR(128) NOT NULL
);

INSERT INTO ResourceType (ID,NAME) VALUES
(0,'IMAGE'),
(1,'VIDEO'),
(2,'AUDIO'),
(3,'TEXT_FILE'),
(4,'FILE');

CREATE TABLE Attribute (
   ID             SERIAL NOT NULL PRIMARY KEY,
   NAME           VARCHAR(128) NOT NULL,
   OwnerClassId INTEGER NOT NULL REFERENCES Class(ID),
   AttributeType INTEGER NOT NULL REFERENCES AttributeType(ID),
   AttributeSize INTEGER,
   ResourceType INTEGER REFERENCES ResourceType(ID),
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
    data              bytea NOT NULL,
    ResourceType INTEGER REFERENCES ResourceType(ID),
    OwnerClassId INTEGER NOT NULL REFERENCES Class(ID)
);

CREATE TABLE Mmedia (
      ID             SERIAL NOT NULL PRIMARY KEY,
      NAME           VARCHAR(128) NOT NULL,
      data              bytea NOT NULL,
      ResourceType INTEGER REFERENCES ResourceType(ID),
      OwnerObjectId INTEGER NOT NULL REFERENCES Object(ID)
);

INSERT INTO Class (ID,NAME,SystemName,DESCRIPTION) VALUES
(1,'TestMain','Class_1','Тестовая страница'),
(2,'TestForRelations','Class_2','Обычная'),
(3,'Оглавление','Class_3','Оглавление для Справочника Системы'),
(4,'Справка','Class_4','Справка для Справочника Системы'),
(5,'Буквенный Указатель','Class_5','Справка для Справочника Системы');

INSERT INTO Attribute (ID,NAME,OwnerClassId,AttributeType,AttributeSize, CanBeNull)VALUES
(1,'FloatField',1,1,0,false),
(2,'IntegerField',1,2,0,false),
(3,'SmallIntField',1,3,0,false),
(4,'MMEDIAField',1,4,0,true),
(5,'StringField',1,6,64,false),
(6,'TextField',1,5,0,false),
(7,'XMEMOField',1,7,0,false),
--(8,'TIMEField',1,8,0,false)--

(9,'TITLE',2,6,64,false),
(11,'AUTHOR',2,6,64,false),

(17,'Название',3,6,64,false),

(12,'Название',4,6,64,false),
(13,'Текст',4,7,0,true),
(14,'Номер',4,2,0,false),
(16,'Иллюстрация',4,4,0,true),

(18,'Название',5,6,64,false)
;

INSERT INTO Object (ID,OwnerClassId) VALUES
(1,1),
(2,1),
(3,2),
(4,2),

(5,3),

(6,4),
(7,4),

(8,5);

CREATE TABLE Class_1 (
ID             INTEGER NOT NULL PRIMARY KEY REFERENCES Object(ID),
FloatField     FLOAT NOT NULL,
IntegerField   INTEGER NOT NULL,
SmallIntField  SMALLINT NOT NULL,
MMEDIAField    INTEGER REFERENCES Mmedia(ID),
StringField    VARCHAR(64) NOT NULL,
TextField      TEXT NOT NULL,
XMEMOField     TEXT NOT NULL
--TIMEField      DATE NOT NULL--
);

INSERT INTO Class_1 (ID,FloatField,IntegerField,SmallIntField,StringField,TextField,XMEMOField) VALUES
(1, 1.2, 123, 12,'Sara','She is my friend',
        'XMEMO STARTS HERE:
        <div>Left Right</div>
        <div>Агрегация hyperlink = <aggregation aggregationId="1" templateId="2" objectId="3" type="hyperlink">Book</aggregation></div>
        <div>Агрегация object = <br/><div style="border:1px solid black"><aggregation aggregationId="1" templateId="2" objectId="3" type="object"></aggregation></div></div>
        :XMEMO ENDS HERE
        '),
(2, 1.23, 555555555, 66,'Tom','He is not my friend',
        'XMEMO STARTS HERE:
        <div>Агрегация hyperlink = <aggregation aggregationId="1" templateId="2" objectId="4" type="hyperlink">Bad Book</aggregation></div>
        <div>Агрегация object = <br/><div style="border:1px solid black"><aggregation aggregationId="1" templateId="2" objectId="4" type="object"></aggregation></div></div>
        :XMEMO ENDS HERE');

CREATE TABLE Class_2 (
 ID             INTEGER NOT NULL PRIMARY KEY REFERENCES Object(ID),
 TITLE           VARCHAR(64) NOT NULL,
 AUTHOR         VARCHAR(64) NOT NULL
);

INSERT INTO Class_2 (ID,TITLE,AUTHOR) VALUES
(3,'Русский язык для самых маленьких','Твой кот'),
(4,'Как 5G вышки портят экологию','Неизвестный дурачок');

CREATE TABLE Class_3 (
     ID          INTEGER NOT NULL PRIMARY KEY REFERENCES Object(ID),
     Название    VARCHAR(64) NOT NULL
);

INSERT INTO Class_3 (ID,Название) VALUES
(5, 'Оглавление Справочника');

CREATE TABLE Class_4 (
     ID                     INTEGER NOT NULL PRIMARY KEY REFERENCES Object(ID),
     Название               VARCHAR(64) NOT NULL,
     Текст                  TEXT,
     Номер           INTEGER NOT NULL,
     Иллюстрация    INTEGER REFERENCES Mmedia(ID)
);

INSERT INTO Class_4 (ID,Название,Текст,Номер) VALUES
(6, 'Класс',
 '<p>Класс задается:</p>
<ul>
<li>своим типом (идентификатором класса)</li>
<li>своими членами (Атрибутами, Ресурсами и Шаблонами)</li>
</ul>
<p>В классе существуют Атрибуты (нестатические поля Класса). В них могут хранится:</p>
<ul>
<li>простые величины (Атрибуты типа smallint, int, float, string, text, date)</li>
<li>файлы (Атрибуты типа mmedia)</li>
<li>форматированный текст с авторскими проводками (Атрибуты типа xmemo)</li>
</ul>
<p>В Классе также существуют статические поля:</p>
<ul>
<li>Ресурсы, в которых могут храниться файлы</li>
</ul>
<p>И нестатические методы:</p>
<ul>
<li>Шаблоны, которые формируют визуальное представление <aggregation aggregationId="2" templateId="4" objectId="7" type="hyperlink">Объектов</aggregation></li>
</ul>',
 1),
(7, 'Объект',
 '<ul>
<li>объект обладает состоянием, поведением и идентичностью</li>
</ul>
<ul>
<li style="list-style-type: none;">
<ul>
<li>Поведение - это его наблюдаемая и проверяемая извне деятельность, реализованное через Шаблоны.</li>
<li>Состояние объекта характеризуется перечнем всех Атрибутов данного объекта и текущими значениями каждого из этих Атрибутов.</li>
<li>Идентичность - это такое свойство объекта, которое отличает его от всех других объектов, реализуется идентификатором.</li>
</ul>
</li>
</ul>
<ul>
<li>структура и поведение схожих Объектов определяет общий для них <aggregation aggregationId="2" templateId="4" objectId="6" type="hyperlink">Класс</aggregation></li>
<li>термины "экземпляр Класса" и "Объект" взаимозаменяемы</li>
</ul>',
 2);

CREATE TABLE Class_5 (
     ID          INTEGER NOT NULL PRIMARY KEY REFERENCES Object(ID),
     Название    VARCHAR(64) NOT NULL
);

INSERT INTO Class_5 (ID,Название) VALUES
(8, 'Буквенный Указатель Справочника');

INSERT INTO Aggregation (ID,NAME,FromClassId,ToClassId) VALUES
(1,'читает',1,2),

(2,'между справками',4,4);

INSERT INTO Association (ID,NAME,FromClassId,ToClassId) VALUES
(1,'читают',1,2),

(2,'от оглавления к справкам',3,4),

(3,'от буквенного указателя к справкам',5,4),

(4,'от справки к оглавлению',4,3);

INSERT INTO AssociationImplementation (ID,AssociationId,FromObjectId,ToObjectId) VALUES
(1,1,1,3),
(2,1,1,4),
(3,1,2,3),
(4,1,2,4),

(5,2,5,6),
(6,2,5,7),

(7,3,8,6),
(8,3,8,7),

(9,4,6,5),
(10,4,7,5);

INSERT INTO Template (ID,NAME,BODY,OwnerClassId,DESCRIPTION) VALUES
(1,'v1',
        '<div><h2>Тест полей Объекта:</h2>
        <div >FloatField = <field>FloatField</field></div>
        <div >IntegerField = <field>IntegerField</field></div>
        <div >SmallIntField = <field>SmallIntField</field></div>
        <div >StringField = <field>StringField</field></div>
        <div >TextField = <field>TextField</field></div>
        <div >XMEMOField = <br/> <div style="border:1px solid red"><field>XMEMOField</field></div></div>
        <div >MMEDIAField = <br/><field>MMEDIAField</field></div>
        </div><br/><hr/>

        <div><h2>Тест Ассоциаций Объекта:</h2>
        <div style="border:1px solid red">Ассоциация object = <association associationId="1" templateId="2" delimiter="&lt;hr&gt;&lt;/hr&gt;" type="object"></association></div>
        <div>Ассоциация hyperlink = <br/><association associationId="1" templateId="2" delimiter="&lt;br&gt;&lt;/br&gt;" type="hyperlink">TITLE AUTHOR</association></div>
        </div><br/><hr/>

        <div><h2>Тест ресурсов Класса:</h2>
        <div style="color:black">Должны быть добавлены тестовые файлы: 1- IMAGE, 2- AUDIO, 3 - VIDEO, 4 - FILE,5 - TEXT_FILE!!!</div>
        <br/><h3>Вставить полностью:</h3>
        <div><resource resourceId="1" type="whole"></resource></div>
        <div><resource resourceId="2" type="whole"></resource></div>
        <div><resource resourceId="3" type="whole"></resource></div>
        <div><resource resourceId="4" type="whole"></resource></div>
        <div><resource resourceId="5" type="whole"></resource></div>
        <br/><h3>Получить ссылку и вставить в пользовательский тег:</h3>
        <a href="" style="color:red"> Скачать картинку-уточку<resource resourceId="1" type="hyperlink"></resource></a><br/>
        <a href="" style="color:orange"> Скачать аудио-пианино<resource resourceId="2" type="hyperlink"></resource></a><br/>
        <a href="" style="color:green"> Скачать видео-кота<resource resourceId="3" type="hyperlink"></resource></a><br/>
        <a href="" style="color:#42aaff"> Скачать файл-приложение<resource resourceId="4" type="hyperlink"></resource></a><br/>
        <a href="" style="color:blue"> Скачать файл-текст<resource resourceId="5" type="hyperlink"></resource></a><br/>
        <br/><h3>Вставить ссылку на скачивание</h3>
        <div>Картинка <resource resourceId="1" type="download"></resource></div>
        <div>Аудио <resource resourceId="2" type="download"></resource></div>
        <div>Видео <resource resourceId="3" type="download"></resource></div>
        <div>Файл <resource resourceId="4" type="download"></resource></div>
        <div>Текст <resource resourceId="5" type="download"></resource></div>
        </div><br/><hr/>'
,1,'all var template'),
(2,'v1','<h2>Книга</h2>
        <div style="color:red">Название: <field>TITLE</field></div>
        <div style="color:orange">Автор : <field>AUTHOR</field></div>'
,2,'all var template'),

(3,'ДляОглавления',
'<head>
    <style type="text/css">
    .menu{
        background-color: #dedede;
    }
    .menu *{
        margin: 0 20px 0 20px;
        display: inline-block;
    }
    .menu a{
        color: black;
        font-size: 20px;
    }
    .inner{
        text-align: left;
        margin:0 10% 0 10%;
        width:80%;
        padding: 10px;
        border-left: 2px solid #dedede;
        border-right: 2px solid #dedede;
        min-height: 100%;
    }
    .inner div *{
        margin:0 10% 0 10%;
    }
    html{
        position: relative;
        min-height: 100%;
    }

    body {
        background-color: #f8f8f8;
    }
    </style>
</head>
<body>
<div class="menu">
    <resource resourceId="2" type="whole"></resource>
</div>
<div class="inner">
    <h2><field>Название</field></h2>
    <div><association associationId="2" templateId="4" delimiter="&lt;br&gt;&lt;/br&gt;" type="hyperlink">Номер Название</association></div>
</div>
</body>
'
,3,'all var template'),

(4,'дляСправки',
 '<head>
    <style type="text/css">
    .menu{
        background-color: #dedede;
    }
    .menu *{
        margin: 0 20px 0 20px;
        display: inline-block;
    }
    .menu a{
        color: black;
        font-size: 20px;
    }
    .inner{
        text-align: left;
        margin:0 10% 0 10%;
        width:80%;
        padding: 10px;
        border-left: 2px solid #dedede;
        border-right: 2px solid #dedede;
        min-height: 100%;
    }
    .inner div img{
        display: block;
        margin: 0 auto;
    }
    html{
        position: relative;
        min-height: 100%;
    }

    body {
        background-color: #f8f8f8;
    }
    </style>
</head>
<body>
<div class="menu">
    <resource resourceId="1" type="whole"></resource>
    <association associationId="4" templateId="3" type="hyperlink">Название</association>
</div>
<div class="inner">
    <h2><field>Название</field></h2>
    <div><field>Текст</field></div>
    <div><field>Иллюстрация</field></div>
</div>
</body>
'
,4,'all var template'),

(5,'дляБуквенногоУказателя',
 '<h2><field>Название</field></h2>
<div><association associationId="3" templateId="4" delimiter="&lt;br&gt;&lt;/br&gt;" type="hyperlink">Название</association></div>'
,5,'all var template');

INSERT INTO AggregationImplementation(ID, AggregationId,FromObjectId,ToObjectId,ToTemplateId,AttributeId,Type) VALUES
(1,2,6,7,4,13,0),
(2,2,7,6,4,13,0);