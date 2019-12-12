INSERT INTO permissions (id, description, name, shortcut) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', 'update user', 'update_user', 'upu'); -- 1 => '020ad06c-7e35-4ae4-92a0-535b9773a7f3'
INSERT INTO permissions (id, description, name, shortcut) VALUES ('02da1ace-f7d2-4d42-a505-52cc07cd38ee', 'delete user', 'delete_user', 'dlu'); -- 2 => '02da1ace-f7d2-4d42-a505-52cc07cd38ee'
INSERT INTO permissions (id, description, name, shortcut) VALUES ('06a1dd25-6979-4108-be2e-3f872eb56c21', 'create user', 'create_user', 'cru'); -- 3 => '06a1dd25-6979-4108-be2e-3f872eb56c21'
INSERT INTO permissions (id, description, name, shortcut) VALUES ('06bc1bb3-8b4e-4b15-a325-8234b2a8e12c', 'view users', 'view_user', 'vwu'); -- 4 => '06bc1bb3-8b4e-4b15-a325-8234b2a8e12c'
INSERT INTO permissions (id, description, name, shortcut) VALUES ('08a33502-8dab-4ea8-83a5-913c0e362302', 'view files', 'view_files', 'vwf'); -- 5 => '08a33502-8dab-4ea8-83a5-913c0e362302'
INSERT INTO permissions (id, description, name, shortcut) VALUES ('08c0dd1a-e62a-4688-bf3e-5dcd15a12080', 'modify files', 'modify_files', 'mdf'); -- 6 => '08c0dd1a-e62a-4688-bf3e-5dcd15a12080'
INSERT INTO permissions (id, description, name, shortcut) VALUES ('0923793d-a5e7-4e50-9a22-ade4fe4169a9', 'delete files', 'delete_files', 'dlf'); -- 7 => '0923793d-a5e7-4e50-9a22-ade4fe4169a9'
INSERT INTO permissions (id, description, name, shortcut) VALUES ('0f178aab-52f9-4459-83a9-c3078ca9d4d9', 'create files', 'create_files', 'crf'); -- 8 => '0f178aab-52f9-4459-83a9-c3078ca9d4d9'

INSERT INTO roles (id, description, name) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', 'The role root for administrators', 'root'); -- 1 => '020ad06c-7e35-4ae4-92a0-535b9773a7f3'
INSERT INTO roles (id, description, name) VALUES ('02da1ace-f7d2-4d42-a505-52cc07cd38ee', 'The role member for members', 'member'); -- 2 => '02da1ace-f7d2-4d42-a505-52cc07cd38ee'
INSERT INTO roles (id, description, name) VALUES ('06a1dd25-6979-4108-be2e-3f872eb56c21', 'The role guest for guests', 'guest'); -- 3 => '06a1dd25-6979-4108-be2e-3f872eb56c21'
INSERT INTO roles (id, description, name) VALUES ('06bc1bb3-8b4e-4b15-a325-8234b2a8e12c', 'The role premium for premium members', 'premium'); -- 4 => '06bc1bb3-8b4e-4b15-a325-8234b2a8e12c'

INSERT INTO role_permissions (role_id, permission_id) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', '020ad06c-7e35-4ae4-92a0-535b9773a7f3');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', '02da1ace-f7d2-4d42-a505-52cc07cd38ee');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', '06a1dd25-6979-4108-be2e-3f872eb56c21');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', '06bc1bb3-8b4e-4b15-a325-8234b2a8e12c');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', '08a33502-8dab-4ea8-83a5-913c0e362302');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', '08c0dd1a-e62a-4688-bf3e-5dcd15a12080');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', '0923793d-a5e7-4e50-9a22-ade4fe4169a9');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', '0f178aab-52f9-4459-83a9-c3078ca9d4d9');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('02da1ace-f7d2-4d42-a505-52cc07cd38ee', '08a33502-8dab-4ea8-83a5-913c0e362302');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('02da1ace-f7d2-4d42-a505-52cc07cd38ee', '08c0dd1a-e62a-4688-bf3e-5dcd15a12080');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('02da1ace-f7d2-4d42-a505-52cc07cd38ee', '0923793d-a5e7-4e50-9a22-ade4fe4169a9');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('02da1ace-f7d2-4d42-a505-52cc07cd38ee', '0f178aab-52f9-4459-83a9-c3078ca9d4d9');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('06a1dd25-6979-4108-be2e-3f872eb56c21', '06bc1bb3-8b4e-4b15-a325-8234b2a8e12c');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('06a1dd25-6979-4108-be2e-3f872eb56c21', '08a33502-8dab-4ea8-83a5-913c0e362302');

INSERT INTO users (id, active, locked, pw, salt, username) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', true, false, '993143AACF93E4FFD6A8065B57D054C1B784AEE3646883B9C9A8D16FB07B365A12AF8C7227326B51163A9679155251C141CD442359F377443679AEC5E73639284D772D8DDD04234969C25F8A18A3377B92B60B2E278D9A805EFC6D4AE8A6B9B78959DE94447D4AA68A1B2011713B7FA65688D8AAD8321F9694135C01187EAA84', 'bINU6W59', 'foo'); -- 1 => '020ad06c-7e35-4ae4-92a0-535b9773a7f3'

INSERT INTO user_roles (user_id, role_id) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', '020ad06c-7e35-4ae4-92a0-535b9773a7f3');