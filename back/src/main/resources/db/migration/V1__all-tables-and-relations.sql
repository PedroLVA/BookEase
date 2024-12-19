CREATE TABLE events (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    date TIMESTAMP NOT NULL,
    is_active BOOLEAN NOT NULL,
    publishing_date TIMESTAMP NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    home_number VARCHAR(50) NOT NULL
);


CREATE TABLE users (
    id UUID PRIMARY KEY,
    role VARCHAR(50) NOT NULL, -- Enum-like column for role
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    date_of_birth TIMESTAMP,
    refresh_token VARCHAR(255),
    refresh_token_expiry_time TIMESTAMP
);


CREATE TABLE tickets (
    id UUID PRIMARY KEY,
    booking_date TIMESTAMP NOT NULL,
    payment_status VARCHAR(50) NOT NULL, -- Enum-like column for payment status
    ticket_price DOUBLE PRECISION NOT NULL,
    seat_number VARCHAR(50), -- Optional
    ticket_type VARCHAR(50) NOT NULL,
    valid_until TIMESTAMP
);

CREATE TABLE images (
    id UUID PRIMARY KEY,
    image_data BYTEA NOT NULL,
    type VARCHAR(50) NOT NULL -- Enum-like column for image type
);

CREATE TABLE organizers (
    id UUID PRIMARY KEY REFERENCES users(id), -- Organizer shares the same ID as User
    image_id UUID UNIQUE, -- One-to-one relation with Image
    FOREIGN KEY (image_id) REFERENCES images(id)
);

CREATE TABLE categories (
    id UUID PRIMARY KEY,
    description TEXT NOT NULL,
    name VARCHAR(255) NOT NULL
);

--Relations

-- Many-to-Many Relationship: Event and User (Attendees)
CREATE TABLE event_user (
    event_id UUID NOT NULL,
    user_id UUID NOT NULL,
    PRIMARY KEY (event_id, user_id),
    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Many-to-Many Relationship: Event and Category
CREATE TABLE event_category (
    event_id UUID NOT NULL,
    category_id UUID NOT NULL,
    PRIMARY KEY (event_id, category_id),
    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- One-to-Many Relationship: Event and Ticket
ALTER TABLE tickets
ADD COLUMN event_id UUID,
ADD CONSTRAINT fk_event_ticket FOREIGN KEY (event_id) REFERENCES events(id);

ALTER TABLE tickets
ADD COLUMN user_id UUID NOT NULL,
ADD CONSTRAINT fk_ticket_user FOREIGN KEY (user_id) REFERENCES users(id);

-- One-to-Many Relationship: Event and Image
ALTER TABLE images
ADD COLUMN event_id UUID,
ADD CONSTRAINT fk_event_image FOREIGN KEY (event_id) REFERENCES events(id);

-- Many-to-One Relationship: Event and Organizer
ALTER TABLE events
ADD COLUMN organizer_id UUID NOT NULL,
ADD CONSTRAINT fk_event_organizer FOREIGN KEY (organizer_id) REFERENCES organizers(id);

--Ticket










