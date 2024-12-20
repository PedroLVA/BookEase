-- Drop the existing targetId column if it exists
ALTER TABLE images
DROP COLUMN IF EXISTS targetId;

-- Add the organizer_id column referencing users
ALTER TABLE images
ADD COLUMN organizer_id UUID,
ADD CONSTRAINT fk_user_image FOREIGN KEY (organizer_id) REFERENCES users(id);
