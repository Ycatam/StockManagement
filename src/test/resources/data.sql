delete from quote;
delete from stock;

insert into stock (id, stock_id) values ('321', 'petr4');
insert into stock (id, stock_id) values ('213', 'vale5');

insert into quote (date, price, stock_id) values ('2019-02-01', 10, '321');
insert into quote (date, price, stock_id) values ('2019-02-02', 15, '213');