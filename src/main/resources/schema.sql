-- Create the files table
CREATE TABLE IF NOT EXISTS files (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  content_type VARCHAR(255) NOT NULL,
  data BLOB NOT NULL
);

-- Create the posts table
CREATE TABLE IF NOT EXISTS posts (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  file_id BIGINT NOT NULL,
  title VARCHAR(255),
  body VARCHAR(5000),
  FOREIGN KEY (file_id) REFERENCES files(id)
);

-- Create the comments table
CREATE TABLE IF NOT EXISTS comments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  file_id BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  body VARCHAR(5000) NOT NULL,
  FOREIGN KEY (file_id) REFERENCES files(id)
);