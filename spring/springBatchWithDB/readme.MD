Generate Table Before start

Docker command for run mysql

docker run -it -v /data/:/var/lib/mysql --name mulitibank -p 3307:3306 -e MYSQL_ROOT_PASSWORD=multibank -e MYSQL_DATABASE=multibank centurylink/mysql


This example will read data, process and store in Database (Mysql)

https://spring.io/guides/gs/batch-processing/

modified version of above link
