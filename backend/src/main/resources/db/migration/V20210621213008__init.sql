CREATE TABLE shedlock
(
    name       VARCHAR(64),
    lock_until TIMESTAMP(3) NULL,
    locked_at  TIMESTAMP(3) NULL,
    locked_by  VARCHAR(255),
    PRIMARY KEY (name)
);

CREATE TABLE alerts
(
    id bigint not null auto_increment
        primary key,
    created_at datetime(6) null,
    name varchar(255) null,
    status varchar(255) null,
    updated_at datetime(6) null,
    url varchar(255) null
);
