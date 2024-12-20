ALTER TABLE images
ADD COLUMN target_id UUID NOT NULL;

ALTER TABLE images
DROP CONSTRAINT fk_event_image,
DROP COLUMN event_id;