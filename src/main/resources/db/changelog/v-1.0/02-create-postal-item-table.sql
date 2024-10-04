--liquibase formatted sql

--changeset aleksandr_lukashevich:2_create_postal_item_table
CREATE TYPE postal_item_status AS ENUM ('ACCEPTED', 'IN_TRANSIT', 'ARRIVED', 'RECEIVED');
CREATE TYPE postal_item_type AS ENUM ('LETTER', 'PARCEL', 'PACKAGE', 'POSTCARD');

CREATE TABLE postal_item
(
    id                       UUID PRIMARY KEY,
    type                     postal_item_type   NOT NULL,
    recipient_name           TEXT               NOT NULL,
    recipient_post_office_id UUID               NOT NULL REFERENCES post_office (id),
    recipient_address        TEXT               NOT NULL,
    status                   postal_item_status NOT NULL
);

COMMENT ON TABLE postal_item IS 'table with postal items';
COMMENT ON COLUMN postal_item.id IS 'id of the postal item';
COMMENT ON COLUMN postal_item.type IS 'type of the postal item';
COMMENT ON COLUMN postal_item.recipient_name IS 'recipient''s name of the postal item';
COMMENT ON COLUMN postal_item.recipient_post_office_id IS 'recipient''s post office id of the postal item';
COMMENT ON COLUMN postal_item.recipient_address IS 'recipient''s address of the postal item';
COMMENT ON COLUMN postal_item.status IS 'status of the postal item';

CREATE CAST (VARCHAR AS postal_item_status) WITH INOUT AS IMPLICIT;
CREATE CAST (VARCHAR AS postal_item_type) WITH INOUT AS IMPLICIT;

--rollback DROP CAST (VARCHAR AS postal_item_type);
--rollback DROP CAST (VARCHAR AS postal_item_status);
--rollback DROP TABLE postal_item;
--rollback DROP TYPE postal_item_type;
--rollback DROP TYPE postal_item_status;
