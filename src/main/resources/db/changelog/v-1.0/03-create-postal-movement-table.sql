--liquibase formatted sql

--changeset aleksandr_lukashevich:3_create_postal_movement_table
CREATE TYPE postal_movement_type AS ENUM ('RECEIVED', 'ARRIVED', 'DEPARTED', 'ISSUED');

CREATE TABLE postal_movement
(
    id             UUID PRIMARY KEY,
    postal_item_id UUID                 NOT NULL REFERENCES postal_item (id),
    post_office_id UUID                 NOT NULL REFERENCES post_office (id),
    type           postal_movement_type NOT NULL,
    timestamp      TIMESTAMP            NOT NULL
);

COMMENT ON TABLE postal_movement IS 'table to track the movements of postal items between different post offices';
COMMENT ON COLUMN postal_movement.id IS 'unique identifier for each postal movement';
COMMENT ON COLUMN postal_movement.postal_item_id IS 'id of the postal item';
COMMENT ON COLUMN postal_movement.post_office_id IS 'id of the post office where the item arrives';
COMMENT ON COLUMN postal_movement.type IS 'type of the postal movement';
COMMENT ON COLUMN postal_movement.timestamp IS 'timestamp of the postal movement';

CREATE CAST (VARCHAR as postal_movement_type) WITH INOUT AS IMPLICIT;

CREATE INDEX idx_postal_movement_postal_item_id ON postal_movement USING hash (postal_item_id);

--rollback DROP INDEX idx_postal_movement_postal_item_id;
--rollback DROP CAST (VARCHAR as postal_movement_type);
--rollback DROP TYPE postal_movement_type;
--rollback DROP TABLE postal_movement;
