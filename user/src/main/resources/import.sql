insert into tb_roles (id, role_name) values (1, 'ROLE_USER');
insert into tb_roles (id, role_name) values (2, 'ROLE_ADMIN');

insert into tb_users (id, name, email, password) values (1, 'John Doe', 'john@gmail.com', '$2a$10$C6crhX.HW5jVm89nkVXQ0OHMPbDZDiQQS5167mTpOs1DupG4po5.K'); //123456
insert into tb_users (id, name, email, password) values (2, 'Jane Doe', 'jane@gmail.com', '$2a$10$SP7nC4fZNlwrrp52L3tRgOh7MmS6BnqudnBvxQR7mllvRjcv3fxQK'); //654321

insert into tb_users_roles (user_id, role_id) values (1, 2);
insert into tb_users_roles (user_id, role_id) values (2, 1);
insert into tb_users_roles (user_id, role_id) values (2, 2);