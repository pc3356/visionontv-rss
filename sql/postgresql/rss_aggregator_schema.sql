-- Table: feed

-- DROP TABLE feed;

CREATE TABLE feed
(
  id bigint NOT NULL,
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

-- Table: feed_entry

-- DROP TABLE feed_entry;

CREATE TABLE feed_entry
(
  id bigint NOT NULL,
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

-- Table: tag

-- DROP TABLE tag;

CREATE TABLE tag
(
  id bigint NOT NULL,
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
