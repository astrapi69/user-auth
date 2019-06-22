INSERT INTO permissions (id, description, name, shortcut) VALUES (1, 'update user', 'update_user', 'upu');
INSERT INTO permissions (id, description, name, shortcut) VALUES (2, 'delete user', 'delete_user', 'dlu');
INSERT INTO permissions (id, description, name, shortcut) VALUES (3, 'create user', 'create_user', 'cru');
INSERT INTO permissions (id, description, name, shortcut) VALUES (4, 'view users', 'view_user', 'vwu');
INSERT INTO permissions (id, description, name, shortcut) VALUES (5, 'view files', 'view_files', 'vwf');
INSERT INTO permissions (id, description, name, shortcut) VALUES (6, 'modify files', 'modify_files', 'mdf');
INSERT INTO permissions (id, description, name, shortcut) VALUES (7, 'delete files', 'delete_files', 'dlf');
INSERT INTO permissions (id, description, name, shortcut) VALUES (8, 'create files', 'create_files', 'crf');

INSERT INTO roles (id, description, name) VALUES (1, 'The role root for administrators', 'root');
INSERT INTO roles (id, description, name) VALUES (2, 'The role member for members', 'member');
INSERT INTO roles (id, description, name) VALUES (3, 'The role guest for guests', 'guest');
INSERT INTO roles (id, description, name) VALUES (4, 'The role premium for premium members', 'premium');

INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 2);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 3);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 4);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 5);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 6);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 7);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 8);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 5);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 6);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 7);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 8);
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 4);
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 5);

INSERT INTO users (id, active, locked, pw, salt, username) VALUES (1, true, false, '993143AACF93E4FFD6A8065B57D054C1B784AEE3646883B9C9A8D16FB07B365A12AF8C7227326B51163A9679155251C141CD442359F377443679AEC5E73639284D772D8DDD04234969C25F8A18A3377B92B60B2E278D9A805EFC6D4AE8A6B9B78959DE94447D4AA68A1B2011713B7FA65688D8AAD8321F9694135C01187EAA84', 'bINU6W59', 'foo');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);