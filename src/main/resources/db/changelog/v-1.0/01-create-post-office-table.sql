--liquibase formatted sql

--changeset aleksandr_lukashevich:1_create_post_office_table
CREATE TABLE post_office
(
    id       UUID PRIMARY KEY,
    postcode VARCHAR(6) NOT NULL UNIQUE,
    name     TEXT       NOT NULL,
    address  TEXT       NOT NULL
);

COMMENT ON TABLE post_office IS 'table with post offices';
COMMENT ON COLUMN post_office.id IS 'id of the post office';
COMMENT ON COLUMN post_office.postcode IS 'postal code of the post office';
COMMENT ON COLUMN post_office.name IS 'name of the post office';
COMMENT ON COLUMN post_office.address IS 'address of the post office';

CREATE INDEX idx_post_office_postcode ON post_office USING hash (postcode);

--rollback DROP INDEX idx_post_office_postcode;
--rollback DROP TABLE post_office;
