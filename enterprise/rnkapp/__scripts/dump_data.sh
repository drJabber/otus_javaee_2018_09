#!/bin/sh
 /usr/lib/postgresql/11/bin/pg_dump -U postgres  --schema-only --column-inserts --no-owner --no-owner --no-privileges --clean --no-tablespaces --if-exists rnk_db >> ./create-db.sql
 /usr/lib/postgresql/11/bin/pg_dump -U postgres  --data-only --column-inserts rnk_db >> ./data.sql
