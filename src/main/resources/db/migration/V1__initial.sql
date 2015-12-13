create table if not exists customer (
    id varchar(6) not null,
    firstName varchar(200) not null,
    surname varchar(200) not null,
    balance decimal not null,

    primary key (id)
);

insert into customer (id, firstName, surname, balance) values
('000001','Michael', 'Ruocco', 10000),
('000002','Joe', 'Bloggs', 5),
('000003','Jim', 'Johnson', 0),
('000004','Michael', 'Ruocco', 180),
('000005','Joe', 'Bloggs', 432),
('000006','Jim', 'Johnson', 1),
('000007','Michael', 'Ruocco', 432000),
('000008','Joe', 'Bloggs', 43),
('000009','Jim', 'Johnson', 75),
('000010','Michael', 'Ruocco', 650),
('000011','Joe', 'Bloggs', 543),
('000012','Jim', 'Johnson', 423),
('000013','Michael', 'Ruocco', 10000),
('000014','Joe', 'Bloggs', 5),
('000015','Jim', 'Johnson', 0);