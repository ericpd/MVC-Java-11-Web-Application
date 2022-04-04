#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
-- Default Users
insert into public.dp_user(_id, type, create_date, last_modified_date, update_date, confirmed, confirmed_date, email, first_name, last_name, password, user_name, system_user) values('0000-0001', 'ADMIN', now(), now(), now(), true, now(), null, 'System', 'Admin', 'D39321F4A16D4FC8', 'su', true);
insert into public.dp_user(_id, type, create_date, last_modified_date, update_date, confirmed, confirmed_date, email, first_name, last_name, password, user_name, system_user) values('0000-0002', 'ROOT', now(), now(), now(), true, now(), null, 'System', 'Root', 'D39321F4A16D4FC8', 'root', false);
insert into public.dp_user(_id, type, create_date, last_modified_date, update_date, confirmed, confirmed_date, email, first_name, last_name, password, user_name, system_user, admin_id) values('0000-0003', 'MEMBER', now(), now(), now(), true, now(), null, 'System', 'Member', 'D39321F4A16D4FC8', 'member', false, '0000-0002');

-- System Settings
insert into public.dpx_system_setting(name, value, encrypted, create_date, last_modified_date, update_date) values('system.session.timeout', '2700', false, now(), now(), now());
insert into public.dpx_system_setting(name, value, encrypted, create_date, last_modified_date, update_date) values('system.user.enable', 'true', false, now(), now(), now());
insert into public.dpx_system_setting(name, value, encrypted, create_date, last_modified_date, update_date) values('system.user.password', 'D39321F4A16D4FC8', true, now(), now(), now());

