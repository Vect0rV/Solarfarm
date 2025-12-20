drop database if exists solar_farm_test_dep;
create database solar_farm_test;
use solar_farm_test;

create table panel (
	panel_id int auto_increment primary key,
    section varchar(100) not null,
    `row` int not null,
    `column` int not null,
    material_type varchar(50) not null,
    installation_year int not null,
    isTracking boolean not null
);

delimiter // 
create procedure set_known_good_state()
begin 
	truncate table panel;
    
    insert into panel
		(panel_id, section, `row`, `column`, material_type, installation_year, isTracking)
	values 
		(1,'Trap',2,2,'CIGS',2021,0),
		(2,'Farm',1,1,'ASI',2022,1),
		(3,'Farm',1,3,'MONO_SI',2023,0);
        
end //
delimiter ; 

SHOW PROCEDURE STATUS WHERE Db = 'solar_farm_test';

    select * from panel;