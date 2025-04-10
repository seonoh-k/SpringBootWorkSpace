`security`DROP TABLE tbl_memo;
DELETE from tbl_memo;

UPDATE user SET ROLE = 'ROLE_MANAGER' WHERE id = 4;
UPDATE user SET ROLE = 'ROLE_ADMIN' WHERE id = 5;

COMMIT;