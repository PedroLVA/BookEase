ALTER TABLE images
ADD COLUMN event_id UUID,
ADD CONSTRAINT fk_event_image FOREIGN KEY (event_id) REFERENCES events(id);