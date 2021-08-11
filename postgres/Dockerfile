FROM postgres:13 AS postgres_with_script
COPY ./10-create-user-and-db /docker-entrypoint-initdb.d/10-create-user-and-db.sql
