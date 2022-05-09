-- Simple Warehouse schema
create schema if not exists sw;

-- Datasource dimension
create table if not exists sw.datasource_dim
(
    id         bigint primary key auto_increment,
    datasource varchar(255) not null,
    index (datasource) using hash
);

-- Campaign dimension
create table if not exists sw.campaign_dim
(
    id       bigint primary key auto_increment,
    campaign varchar(255) not null,
    index (campaign) using hash
);

-- Date dimension
create table if not exists sw.date_dim
(
    id   bigint primary key auto_increment,
    date date not null,
    index (date) using btree
);

-- Metrics (facts)
create table if not exists sw.metrics
(
    id                bigint primary key auto_increment,
    clicks            bigint not null,
    impressions       bigint not null,
    datasource_dim_id bigint,
    campaign_dim_id   bigint,
    date_dim_id       bigint,
    foreign key (datasource_dim_id) references sw.datasource_dim (id),
    foreign key (campaign_dim_id) references sw.campaign_dim (id),
    foreign key (date_dim_id) references sw.date_dim (id)
);

create procedure sw.insert_metrics(
    in datasource varchar(255),
    in campaign varchar(255),
    in date varchar(255),
    in clicks bigint,
    in impressions bigint
)
begin
    declare exit handler for sqlexception
        begin
            show errors;
            rollback;
        end;

    start transaction;

    insert into sw.datasource_dim(datasource) value (datasource);
    set @datasource_dim_id = last_insert_id();

    insert into sw.campaign_dim(campaign) value (campaign);
    set @campaign_dim_id = last_insert_id();

    insert into sw.date_dim(date) value (str_to_date(date, '%m/%d/%Y'));
    set @date_dim_id = last_insert_id();

    insert into sw.metrics(clicks, impressions, datasource_dim_id, campaign_dim_id, date_dim_id)
        value (clicks, impressions, @datasource_dim_id, @campaign_dim_id, @date_dim_id);

    commit work;
end;
