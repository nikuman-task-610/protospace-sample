ALTER TABLE prototypes 
ADD COLUMN image_name VARCHAR(255),
ADD COLUMN image_type VARCHAR(100),
ADD COLUMN image_data BYTEA;