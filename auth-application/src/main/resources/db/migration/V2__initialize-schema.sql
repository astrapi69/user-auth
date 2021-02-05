INSERT INTO applications (id, version, name, domain_name, email) VALUES ('0084d910-d153-4bd4-86bf-f5e5a8492c7e', 1, 'test-application','testapp.com', 'info@testapp.com');

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
INSERT INTO roles (id, description, name) VALUES ('646c10ee-50f9-475c-8f4a-400fe9375f39', 'The role testmember is for test member role issues', 'testmember');
INSERT INTO roles (id, description, name) VALUES ('ff8a4a73-f0b9-4abf-a9bd-39573dd1968e', 'The role testpremium is for test premium role issues', 'testpremium');

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
INSERT INTO role_permissions (role_id, permission_id) VALUES ('646c10ee-50f9-475c-8f4a-400fe9375f39', '08a33502-8dab-4ea8-83a5-913c0e362302');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('646c10ee-50f9-475c-8f4a-400fe9375f39', '08c0dd1a-e62a-4688-bf3e-5dcd15a12080');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('646c10ee-50f9-475c-8f4a-400fe9375f39', '0923793d-a5e7-4e50-9a22-ade4fe4169a9');
INSERT INTO role_permissions (role_id, permission_id) VALUES ('646c10ee-50f9-475c-8f4a-400fe9375f39', '0f178aab-52f9-4459-83a9-c3078ca9d4d9');

INSERT INTO users (id, active, locked, salt, username, email, password, application) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', true, false, 'bINU6W59', 'foo', 'f00B4r@gmol.org', '51E420FA55D5CDC7DFFE32A24D96CC36A83687D885193157A7C5763888AA3B04FAB27EBE448131B534344AD6263318EB40E0CC6EDBAE27781442E71A4F4C30A56EB3A04BED35B2B06A62CA2D800F81885E7288EFD8CCADDB1A16A3877BD00088D676A16063AF235F9A4B67561828860717AB9F8ECE1DB92E6BAB57974E2EF975', '0084d910-d153-4bd4-86bf-f5e5a8492c7e'); -- pw is => bar
INSERT INTO users (id, active, locked, salt, username, email, password, application) VALUES ('ae2aac3f-72de-4eb7-a4b3-4f09fd4eecfb', true, false, '4xi4cEEK', 'bla', 'bla@fasel.org', 'F9CF119A564B48F916E5869ED3B43D9F1C4C4AF20509862F7EAEAC7A4187C8BAD02A9FAE0003C4F9240810E44CCB6DE7852934030730E1142229F2D8648CE8A6772D89E4E5E939453365609D0E29A1EB654FFCCDF44A0C91C82343BBA0C31C69D2B846163AF316E02A380FD9895E73D4A69BBE7729A46D4EC7997C7636AF82EF', '0084d910-d153-4bd4-86bf-f5e5a8492c7e'); -- pw is => fasel
INSERT INTO users (id, active, locked, salt, username, email, password, application) VALUES ('c4fb6dd8-6bd8-4d45-afae-1eeafe66f13b', true, false, 'jT1QtTMH', 'anton', 'anton@fritz.org', 'A660427F58CE8177A9B117D992B46FAC01131533386C96BF83B9AD812AAEC4569614E683E9586ED87B26709183DD218D6CC23747977DD882F6557153C890C2545168B4B496A63D4A396B46EE289785EE9803A9A5EC3F7344A44B83CCBE44DA68F7F701E864F1747AD11DD5FFDBD7E4C8494ABE7B629CB0C8B547A62576AA4B6D', '0084d910-d153-4bd4-86bf-f5e5a8492c7e'); -- pw is => fritz
INSERT INTO users (id, active, locked, salt, username, email, password, application) VALUES ('b916a8ab-428f-46c8-95a3-1e09b6152a26', true, false, 'OOK7TdIL', 'meli', 'meli@honig.org', '8DC66AE2FFABFEFD8BDBFC16BB83DEA4E4510ADC1A67E4BF816A6B8945C0DC772CBFA058D4748109F50CE2D9AF1309B228CA5C5D1221316A146FC0FF539AE141541D0661E2C40E83A0D4D880137DDF8AA1776DEB19A7E5AE20FEFDDC6DE926EA1283F626FA94AC34EB6B49ED9D29702D', '0084d910-d153-4bd4-86bf-f5e5a8492c7e'); -- pw is => honig

INSERT INTO user_roles (user_id, role_id) VALUES ('020ad06c-7e35-4ae4-92a0-535b9773a7f3', '020ad06c-7e35-4ae4-92a0-535b9773a7f3');
INSERT INTO user_roles (user_id, role_id) VALUES ('ae2aac3f-72de-4eb7-a4b3-4f09fd4eecfb', '02da1ace-f7d2-4d42-a505-52cc07cd38ee');
INSERT INTO user_roles (user_id, role_id) VALUES ('c4fb6dd8-6bd8-4d45-afae-1eeafe66f13b', '06a1dd25-6979-4108-be2e-3f872eb56c21');
INSERT INTO user_roles (user_id, role_id) VALUES ('b916a8ab-428f-46c8-95a3-1e09b6152a26', '06bc1bb3-8b4e-4b15-a325-8234b2a8e12c');

INSERT INTO application_roles (application_id, role_id) VALUES ('0084d910-d153-4bd4-86bf-f5e5a8492c7e', '020ad06c-7e35-4ae4-92a0-535b9773a7f3');
INSERT INTO application_roles (application_id, role_id) VALUES ('0084d910-d153-4bd4-86bf-f5e5a8492c7e', '02da1ace-f7d2-4d42-a505-52cc07cd38ee');
INSERT INTO application_roles (application_id, role_id) VALUES ('0084d910-d153-4bd4-86bf-f5e5a8492c7e', '06a1dd25-6979-4108-be2e-3f872eb56c21');
INSERT INTO application_roles (application_id, role_id) VALUES ('0084d910-d153-4bd4-86bf-f5e5a8492c7e', '06bc1bb3-8b4e-4b15-a325-8234b2a8e12c');

INSERT INTO application_permissions (application_id, permission_id) VALUES ('0084d910-d153-4bd4-86bf-f5e5a8492c7e', '020ad06c-7e35-4ae4-92a0-535b9773a7f3');
INSERT INTO application_permissions (application_id, permission_id) VALUES ('0084d910-d153-4bd4-86bf-f5e5a8492c7e', '02da1ace-f7d2-4d42-a505-52cc07cd38ee');
INSERT INTO application_permissions (application_id, permission_id) VALUES ('0084d910-d153-4bd4-86bf-f5e5a8492c7e', '06a1dd25-6979-4108-be2e-3f872eb56c21');
INSERT INTO application_permissions (application_id, permission_id) VALUES ('0084d910-d153-4bd4-86bf-f5e5a8492c7e', '06bc1bb3-8b4e-4b15-a325-8234b2a8e12c');
INSERT INTO application_permissions (application_id, permission_id) VALUES ('0084d910-d153-4bd4-86bf-f5e5a8492c7e', '08a33502-8dab-4ea8-83a5-913c0e362302');
INSERT INTO application_permissions (application_id, permission_id) VALUES ('0084d910-d153-4bd4-86bf-f5e5a8492c7e', '08c0dd1a-e62a-4688-bf3e-5dcd15a12080');
INSERT INTO application_permissions (application_id, permission_id) VALUES ('0084d910-d153-4bd4-86bf-f5e5a8492c7e', '0923793d-a5e7-4e50-9a22-ade4fe4169a9');
INSERT INTO application_permissions (application_id, permission_id) VALUES ('0084d910-d153-4bd4-86bf-f5e5a8492c7e', '0f178aab-52f9-4459-83a9-c3078ca9d4d9');
