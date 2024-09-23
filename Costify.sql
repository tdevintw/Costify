create database "Costify"
    with owner postgres;

create type public.role as enum ('Admin', 'Client');

alter type public.role owner to postgres;

create type public.project_status as enum ('InProgress', 'Completed ', 'Canceled', 'Completed');

alter type public.project_status owner to postgres;

create table public.users
(
    id             serial
        primary key,
    name           varchar(255)               not null,
    password       varchar(255)               not null,
    address        varchar(255),
    phone          varchar(20),
    isprofessional boolean,
    role           role default 'Admin'::role not null
);

alter table public.users
    owner to postgres;

create table public.projects
(
    id            serial
        primary key,
    user_id       integer
        constraint fk_user_id
            references public.users
            on delete set null,
    name          varchar(255) not null,
    profit_margin double precision,
    cost_total    double precision,
    status        project_status default 'InProgress'::project_status
);

alter table public.projects
    owner to postgres;

create table public.estimates
(
    id            serial
        primary key,
    project_id    integer
                       references public.projects
                           on update set null on delete set null,
    cost_total    double precision,
    creation_date date not null,
    validated_at  date,
    is_accepted   boolean
);

alter table public.estimates
    owner to postgres;

create table public.labors
(
    id                  serial
        primary key,
    project_id          integer
                                         references public.projects
                                             on update set null on delete set null,
    name                varchar(255)     not null,
    component_type      varchar(255),
    tva                 double precision,
    quality_coefficient double precision,
    cost_per_hour       double precision not null,
    hours_of_work       double precision not null
);

alter table public.labors
    owner to postgres;

create table public.materials
(
    id                  serial
        primary key,
    name                varchar(255)     not null,
    component_type      varchar(255),
    tva                 double precision,
    quality_coefficient double precision,
    cost_per_unit       double precision not null,
    quantity            double precision not null,
    cost_of_transport   double precision not null,
    project_id          integer
        constraint materils_project_id_fkey
            references public.projects
            on update set null on delete set null
);

alter table public.materials
    owner to postgres;

