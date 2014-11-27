DROP DATABASE IF EXISTS VideoGameDB;
CREATE DATABASE VideoGameDB;
use VideoGameDB;





/*employees(userid, password) */
CREATE TABLE employees (
    employeeID      varchar(30) primary key,
    password        varchar(18)
);

/*games(*title*, release, cost, genre, *platform*, multiplayer, qty)*/
create table games (
    game_title      varchar(30),
    platform_abv    varchar(8),
    foreign key (platform_abv) references platforms(platform_abv),
    primary key(game_title, platform_abv),
    released        date not null,
    cost            decimal(4,2) not null,
    genre           varchar(18) not null,
    is_mult         char(1) not null,
    qty             integer not null,
);

/*platforms(*name abv*, system_name, released)*/
create table platforms (
    platform_abv    varchar(8) primary key,
    platform_name   varchar(25),
    released        date
);

/*upcoming(*title*, *platform*, cost (null), genre(null), multiplayer(null), release(null))*/
create table upcoming (
    game_title      varchar(30) primary key,
    platform        varchar(8),
    foreign key (platform_abv) references platforms(platform_abv),
    cost            DECIMAL(4,2),
    genre           varchar(18),
    is_mult         char(1),
    released        date
);

/*purchases(*unique auto #*, purchase_date)*/
create table purchases (
    purchase_id     integer AUTO_INCREMENT primary key,
    purchase_time   timestamp not null
);

/*purchase_items(*purchase #*, qty, *title*, *platform*)*/
create table purchase_items (
    purchase_itemid     integer AUTO_INCREMENT primary key,
    purchase_id         integer,
    foreign key(purhchase_id) references purchases(purchase_id)
    
);

/*dlc(*title*, *platform*, dlc_name, release, cost)*/
create table dlc (
    game_title      varchar(30),
    platform_abv    varchar(8),
    foreign key (platform_abv) references platforms(platform_abv),
    foreign key (game_title) references games(game_title),
    primary key(title, platform_abv),
    dlc_title       varchar(30),
    released        date,
    cost            decimal(4,2)
);