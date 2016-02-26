CREATE SEQUENCE my_seq_gen START 1;

DROP TABLE company_owners;
DROP TABLE companies;

CREATE TABLE companies (
    id         integer NOT NULL CONSTRAINT pk PRIMARY KEY,
    name          varchar(200),
    address       varchar(200),
    city          varchar(200),
    country       varchar(200),
    email         varchar(200),
    phone         varchar(200)
);

CREATE TABLE company_owners (
  company_id    integer NOT NULL REFERENCES companies (id) ON DELETE CASCADE ,
  owner         VARCHAR (200)
);

DROP INDEX owners_ref_idx
CREATE INDEX owners_ref_idx ON company_owners (company_id);