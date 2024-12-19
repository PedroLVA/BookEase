ALTER TABLE events
DROP CONSTRAINT fk_event_organizer, -- Drop the old FK
ADD CONSTRAINT fk_event_user FOREIGN KEY (organizer_id) REFERENCES users(id);

DROP TABLE IF EXISTS organizers;

