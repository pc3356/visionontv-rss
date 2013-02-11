CREATE DATABASE rss_aggregator;

-- Sequence: feed_sequence

-- DROP SEQUENCE feed_sequence;

CREATE SEQUENCE feed_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- Table: feed

-- DROP TABLE feed;

CREATE TABLE feed
(
  id bigint NOT NULL DEFAULT nextval('feed_sequence'),
  short_name character varying(255) NOT NULL,
  title character varying(255) NOT NULL,
  link character varying(255) NOT NULL,
  uri character varying(255) NOT NULL,
  feed_type character varying(255),
  encoding character varying(10),
  published_date date,
  active boolean NOT NULL DEFAULT true,
  added timestamp without time zone,
  CONSTRAINT feed_pkey PRIMARY KEY (id )
);

-- Sequence: feed_entry_sequence

-- DROP SEQUENCE feed_entry_sequence;

CREATE SEQUENCE feed_entry_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- Table: feed_entry

-- DROP TABLE feed_entry;

CREATE TABLE feed_entry
(
  id bigint NOT NULL DEFAULT nextval('feed_entry_sequence'),
  feed_id bigint NOT NULL,
  title character varying(255) NOT NULL,
  link character varying(255) NOT NULL,
  uri character varying(255) NOT NULL,
  published date,
  updated date,
  added timestamp without time zone,
  description text,
  CONSTRAINT feed_entry_pkey PRIMARY KEY (id )
);

-- Sequence: tag_sequence

-- DROP SEQUENCE tag_sequence;

CREATE SEQUENCE tag_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- Table: tag

-- DROP TABLE tag;

CREATE TABLE tag
(
  id bigint NOT NULL DEFAULT nextval('tag_sequence'),
  value character varying(255) NOT NULL,
  CONSTRAINT tag_pkey PRIMARY KEY (id )
);

-- Table: entry_tag

-- DROP TABLE entry_tag;

CREATE TABLE entry_tag
(
  entry_id bigint NOT NULL,
  tag_id bigint NOT NULL
);

-- Table: feed_tag

-- DROP TABLE feed_tag;

CREATE TABLE feed_tag
(
  feed_id bigint NOT NULL,
  tag_id bigint NOT NULL
);
