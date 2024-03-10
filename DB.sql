CREATE DATABASE TruyenFBT;
GO
USE TruyenFBT;
GO

CREATE TABLE Account
(
    id          INT PRIMARY KEY IDENTITY(1,1),
    username    NVARCHAR(255),
    password    NVARCHAR( max),
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
    [view]   INT DEFAULT 0,
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

INSERT INTO Account  (username, password, role, createdDate, updatedDate)
VALUES
    ('admin', '1000:7293673fd3907186c0a0abaeaf9cd9f2:5b0fd9423ad1c93edb4ab3cc28980fc6b6f8adf2038d629a5e36896cd6a990f74e10209d8d5b646e21101cd69bcbe8716c7226592f13e54b6de1a35b1d94665b', 'admin', GETDATE(), GETDATE()),
    ('user', '1000:a7649f7e3dcd948a6c2e4404fb39bbed:932b9128f6deceb5148cf5a536e9d553741f35926cfd531088b15e151266bce89a9afc271c909045c8133a1839a29b0f15ada5e16d2ae347156ff787a45197cd', 'user', GETDATE(), GETDATE());


INSERT INTO Comic (title, description, img, createdDate, updatedDate)
VALUES ('One Piece', 'One Piece is a Japanese manga series written and illustrated by Eiichiro Oda. The series tells about the journey of Monkey D. Luffy and his friends to find the world''s greatest treasure, One Piece, to become the Pirate King.', 'img/onepiece.png', GETDATE(), GETDATE()),
       ('Lookism', 'Lookism is a South Korean webtoon written and illustrated by Park Tae-joon. It tells the story of a high school student who can switch between two bodies—one fat and ugly, and the other athletic and handsome.', 'img/lookism.png', GETDATE(), GETDATE()),
       ('Attack on Titan', 'Attack on Titan is a Japanese manga series written and illustrated by Hajime Isayama. The series follows Eren Yeager as he vows to retake the world after a Titan brings about the destruction of his hometown and the death of his mother.', 'img/attack-on-titan.png', GETDATE(), GETDATE());

INSERT INTO Genre (name)
VALUES ('Action'), ('Adventure'), ('Fantasy'), ('Shounen');

INSERT INTO ComicGenre (genreId, comicId)
VALUES
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 1),
    (1, 2),
    (3, 2),
    (4, 2),
    (1, 3),
    (2, 3);


INSERT INTO Chapter (comicId, number, createdDate, updatedDate)
VALUES
    (1, 1, GETDATE(), GETDATE()),
    (1, 2, GETDATE(), GETDATE()),
    (1, 3, GETDATE(), GETDATE()),
    (2, 1, GETDATE(), GETDATE()),
    (2, 2, GETDATE(), GETDATE()),
    (3, 1, GETDATE(), GETDATE());


INSERT INTO Image (url, chapterId, number, createdDate, updatedDate)
VALUES
    ('img/img.png', 1, 1, GETDATE(), GETDATE()),
    ('img/img_1.png', 1, 2, GETDATE(), GETDATE()),
    ('img/img_2.png', 1, 3, GETDATE(), GETDATE()),
    ('img/img_3.png', 1, 4, GETDATE(), GETDATE());

INSERT INTO Comment (accountId, chapterId, content, createdDate, updatedDate)
VALUES  (1, 1, 'I love this chapter', GETDATE(), GETDATE());

INSERT INTO Comment (accountId, chapterId, content, parentId ,createdDate, updatedDate)
VALUES  (2, 1, 'I love this chapter too', 1, GETDATE(), GETDATE());




