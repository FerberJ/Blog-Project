-- Entry Table
CREATE TABLE Blog (
  likedByMe BIT(1) NOT NULL,
  id BIGINT NOT NULL PRIMARY KEY,
  author VARCHAR(255),
  content VARCHAR(255),
  title VARCHAR(255)
);

CREATE TABLE Blog_SEQ (
  next_val BIGINT,
  PRIMARY KEY (next_val)
);

INSERT INTO Blog_SEQ (next_val) VALUES (1);

CREATE TABLE Comment (
  id BIGINT NOT NULL PRIMARY KEY,
  comment VARCHAR(255)
);

CREATE TABLE Comment_SEQ (
  next_val BIGINT,
  PRIMARY KEY (next_val)
);

CREATE TABLE Blog_Comment (
  Blog_id BIGINT NOT NULL,
  comments_id BIGINT NOT NULL PRIMARY KEY,
  FOREIGN KEY (Blog_id) REFERENCES Blog(id)
);

INSERT INTO Comment_SEQ (next_val) VALUES (1);
