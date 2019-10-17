CREATE TABLE california
(
  id INT,
  folder_name STRING,
  file_name STRING,
  unixtime BIGINT,
  time TIMESTAMP,
   latitude DOUBLE,
   longitude DOUBLE,
   speed INT,
   the_geom STRING
 )
 row format delimited
 fields terminated by ',';
