create database rss_aggregator; /* UTF8 */

create table rss_aggregator.feed (
	id bigint auto_increment primary key,
	short_name varchar(255) not null,
  title varchar(255) not null,
	link varchar(255) not null,
	uri varchar(255) not null,
	feed_type varchar(255),
	encoding varchar(10),
	published_date date,
	active boolean not null default true,
	added timestamp
);

create table rss_aggregator.feed_entry (
	id bigint auto_increment primary key,
	feed_id bigint not null,
	title varchar(255) not null,
	link varchar(255) not null,
	uri varchar(255) not null,
	published datetime,
	updated datetime,
	added timestamp,
	description text
);

-- UNIQUE ON VALUE
create table rss_aggregator.tag (
	id bigint auto_increment primary key,
	value varchar(255) not null
);

-- COMPOSITE KEY, FK CONSTRAINTS
create table rss_aggregator.entry_tag (
	entry_id bigint not null,
	tag_id bigint not null
);

-- COMPOSITE KEY, FK CONSTRAINTS
create table rss_aggregator.feed_tag (
	feed_id bigint not null,
	tag_id bigint not null
);