CREATE TABLE publishers (
  publisher_id INT AUTO_INCREMENT PRIMARY KEY,
  publisher_name VARCHAR(30) NOT NULL 
);

INSERT INTO publishers VALUES (NULL, 'Все книги');
INSERT INTO publishers VALUES (NULL, 'Прокачай мозги');
INSERT INTO publishers VALUES (NULL, 'Мир фантазий');
----------------------------------------------------
CREATE TABLE books (
  book_id INT AUTO_INCREMENT PRIMARY KEY,
  book_name VARCHAR(30) NOT NULL,
  publisher_id INT,
  FOREIGN KEY (publisher_id) REFERENCES publishers(publisher_id)
);
----------------------------------------------------
INSERT INTO books VALUES (NULL, 'Магия и кровь', 3);
INSERT INTO books VALUES (NULL, 'Уникальная вселенная', NULL );
INSERT INTO books VALUES (NULL, 'Физика просто', 2);
INSERT INTO books VALUES (NULL, 'Расказ о цветах', 1);

select * from publishers;
select * from books;

SELECT   b.book_name, p.publisher_name FROM   books b CROSS JOIN publishers p;

SELECT   b.book_name, p.publisher_name FROM  books b NATURAL JOIN publishers p;

SELECT * FROM  books b NATURAL JOIN publishers p;

SELECT b.book_name, p.publisher_name FROM books b INNER JOIN publishers p 
ON p.publisher_id <> b.publisher_id;

SELECT books.book_name, publishers.publisher_name FROM books LEFT JOIN publishers
ON books.publisher_id = publishers.publisher_id;

SELECT books.book_name, publishers.publisher_name FROM books right JOIN publishers
ON books.publisher_id = publishers.publisher_id;

SELECT books.book_name, publishers.publisher_name FROM books LEFT JOIN publishers
ON books.publisher_id = publishers.publisher_id WHERE books.publisher_id IS NULL ;

CREATE TABLE reviews (
  review_id INT PRIMARY KEY,
  review_rank INT,
  review_comment VARCHAR(50) NOT NULL DEFAULT 'Нет коментария',
  FOREIGN KEY (review_id) REFERENCES books(book_id)
);

INSERT INTO reviews VALUES ((SELECT book_id FROM books WHERE book_name = 'Магия и кровь'),
  8, 'Красочние битви и хороший сюжет ');
  
  INSERT INTO reviews VALUES ((SELECT book_id FROM books WHERE book_name = 'Уникальная вселенная'),
  9, 'Невероятная книга. Просто фантастика!');
  
  INSERT INTO reviews VALUES ((SELECT book_id FROM books WHERE book_name = 'Физика просто'),
  3, 'Скукотища');
  
  INSERT INTO reviews VALUES ((SELECT book_id FROM books WHERE book_name = 'Расказ о цветах'),
  5, 'Ну средненько так');
  
  
select * from reviews;
select * from books;
  
  ALTER TABLE books ADD COLUMN price INT AFTER book_name;
  
UPDATE books SET price = 20 WHERE book_name = 'Магия и кровь';
UPDATE books SET price = 100 WHERE book_name = 'Уникальная вселенная';
UPDATE books SET price = 70 WHERE book_name = 'Физика просто';
UPDATE books SET price = 200 WHERE book_name = 'Расказ о цветах';

SELECT
  b.book_name      AS Книга,
  r.review_rank    AS Оценка,
  r.review_comment AS Комментарий,
  b.price          AS Цена,
  p.publisher_name AS Изатель
FROM books b NATURAL JOIN publishers p INNER JOIN reviews r
    ON b.book_id = r.review_id;

SELECT
  b.book_name      AS Книга,
  r.review_rank    AS Оценка,
  r.review_comment AS Комментарий,
  b.price          AS Цена,
  p.publisher_name AS Изатель
FROM books b NATURAL JOIN publishers p INNER JOIN reviews r
    ON b.book_id = r.review_id
WHERE r.review_rank IN (SELECT r.review_rank
                        FROM reviews);
                        
select * from students;