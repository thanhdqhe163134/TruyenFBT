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
    nubmer      int,
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

GO

-- Insert into Account
INSERT INTO Account(username, password, email, role, img, createdDate, updatedDate)
VALUES ('username1', 'password1', 'email1@example.com', 'user', 'img1.jpg', GETDATE(), GETDATE());

-- Insert into Comic
INSERT INTO Comic(title, description, img, createdDate, updatedDate)
VALUES ('Comic1', 'Description1', 'comic1.jpg', GETDATE(), GETDATE());

-- Insert into Genre
INSERT INTO Genre(name)
VALUES ('Genre1');

-- Get the inserted ids
DECLARE @comicId INT, @genreId INT, @accountId INT;
SELECT @comicId = SCOPE_IDENTITY();
INSERT INTO Genre(name)
VALUES ('Genre2');
SELECT @genreId = SCOPE_IDENTITY();
SELECT @accountId = id FROM Account WHERE username = 'username1';

-- Insert into ComicGenre
INSERT INTO ComicGenre(genreId, comicId)
VALUES (@genreId, @comicId);

-- Insert into Chapter
INSERT INTO Chapter(comicId, nubmer, createdDate, updatedDate)
VALUES (@comicId, 1, GETDATE(), GETDATE());

-- Get the inserted chapter id
DECLARE @chapterId INT;
SELECT @chapterId = SCOPE_IDENTITY();

-- Insert into Image
INSERT INTO Image(url, chapterId, number, createdDate, updatedDate)
VALUES ('image1.jpg', @chapterId, 1, GETDATE(), GETDATE());

-- Insert into ReadingProgress
INSERT INTO ReadingProgress(accountId, chapterId, createdDate, updatedDate)
VALUES (@accountId, @chapterId, GETDATE(), GETDATE());

-- Insert into Comment
INSERT INTO Comment(accountId, chapterId, content, createdDate, updatedDate)
VALUES (@accountId, @chapterId, 'This is a comment', GETDATE(), GETDATE());
