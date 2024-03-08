CREATE
    DATABASE TruyenFBT;
GO
USE TruyenFBT;
GO

CREATE TABLE Account
(
    id          INT PRIMARY KEY IDENTITY(1,1),
    username    NVARCHAR(255),
    password    NVARCHAR( max),
    email       NVARCHAR(255),
    role        NVARCHAR(10),
    img			NVARCHAR(255),
    createdDate DATETIME,
    updatedDate DATETIME,
    deleted     BIT DEFAULT 0
);

CREATE TABLE Comic
(
    id          INT PRIMARY KEY IDENTITY(1,1),
    title       NVARCHAR(255),
    description NVARCHAR( MAX),
    img			NVARCHAR(255),
    viewCount   INT DEFAULT 0,
    createdDate DATETIME,
    updatedDate DATETIME,
    deleted     BIT DEFAULT 0
);

CREATE TABLE Genre
(
    id      INT PRIMARY KEY IDENTITY(1,1),
    name    NVARCHAR(255),
    deleted BIT DEFAULT 0
);

CREATE TABLE ComicGenre
(
    genreId INT,
    comicId INT,
    deleted BIT DEFAULT 0,
    PRIMARY KEY (genreID, comicID),
    FOREIGN KEY (comicId) REFERENCES Comic (id),
    FOREIGN KEY (genreID) REFERENCES Genre (id)
);

CREATE TABLE Chapter
(
    id          INT PRIMARY KEY IDENTITY(1,1),
    comicId     int,
    number      int,
    createdDate DATETIME,
    updatedDate DATETIME,
    deleted     BIT DEFAULT 0,
    FOREIGN KEY (comicId) REFERENCES Comic (id)
);

CREATE TABLE Image
(
    id          INT PRIMARY KEY IDENTITY(1,1),
    url         NVARCHAR(255),
    chapterId   INT,
    number     INT,
    createdDate DATETIME,
    updatedDate DATETIME,
    deleted     BIT DEFAULT 0,
    FOREIGN KEY (chapterId) REFERENCES Chapter (id)
);

CREATE TABLE ReadingProgress
(
    id          INT PRIMARY KEY IDENTITY(1,1),
    accountId   INT,
    chapterId   INT,
    createdDate DATETIME,
    updatedDate DATETIME,
    updateCount INT DEFAULT 0,
    deleted     BIT DEFAULT 0,
    FOREIGN KEY (accountId) REFERENCES Account (id),
    FOREIGN KEY (chapterId) REFERENCES Chapter (id)
);


CREATE TABLE Comment
(
    id          INT IDENTITY(1,1) PRIMARY KEY,
    accountId   INT,
    chapterId   INT,
    content     NVARCHAR( MAX),
    parentId    INT,
    createdDate DATETIME,
    updatedDate DATETIME,
    deleted     BIT DEFAULT 0,
    FOREIGN KEY (chapterId) REFERENCES Chapter (id),
    FOREIGN KEY (accountId) REFERENCES Account (id),
    FOREIGN KEY (parentId) REFERENCES Comment (id)
);

