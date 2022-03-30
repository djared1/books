
-- Person Group Table and Partitions
CREATE TABLE IF NOT EXISTS books
(
    id                  BIGSERIAL,
    book_title          VARCHAR,
    book_category       VARCHAR,
    author_id           BIGINT,
    publish_date        TIMESTAMP WITHOUT TIME ZONE,
    created_timestamp   TIMESTAMP WITHOUT TIME ZONE,
    updated_timestamp   TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS authors
(
    id                      BIGSERIAL,
    author_first_name       VARCHAR NOT NULL,
    author_last_name        VARCHAR NOT NULL,
    created_timestamp       TIMESTAMP WITHOUT TIME ZONE,
    updated_timestamp       TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS book_authors
(
    book_id   BIGINT,
    author_id BIGINT
);

CREATE SEQUENCE SEQ_BOOKS_ID START 1 INCREMENT 1 CACHE 10;
CREATE SEQUENCE SEQ_AUTHORS_ID START 1 INCREMENT 1 CACHE 10;

CREATE INDEX IF NOT EXISTS idx_books_name on books (book_title);
CREATE INDEX IF NOT EXISTS idx_authors_first_name on authors (author_first_name);
CREATE INDEX IF NOT EXISTS idx_authors_last_name on authors (author_last_name);

-- Create some authors ... just to have something to use
INSERT INTO authors (author_first_name, author_last_name) values ('JK', 'Rowling');
INSERT INTO authors (author_first_name, author_last_name) values ('Nora', 'Roberts');
INSERT INTO authors (author_first_name, author_last_name) values ('James', 'Patterson');
INSERT INTO authors (author_first_name, author_last_name) values ('Dr.', 'Seuss');
INSERT INTO authors (author_first_name, author_last_name) values ('John', 'Grisham');
INSERT INTO authors (author_first_name, author_last_name) values ('Stephanie', 'Meyer');
INSERT INTO authors (author_first_name, author_last_name) values ('Dan', 'Brown');
INSERT INTO authors (author_first_name, author_last_name) values ('Nicholas', 'Sparks');
INSERT INTO authors (author_first_name, author_last_name) values ('Janet', 'Evanovich');
INSERT INTO authors (author_first_name, author_last_name) values ('Jeff', 'Kinney');