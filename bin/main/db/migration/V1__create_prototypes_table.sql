CREATE TABLE prototypes (
    id SERIAL NOT NULL,
    title VARCHAR(100) NOT NULL,
    catch_copy TEXT NOT NULL,
    concept TEXT NOT NULL,
    PRIMARY KEY (id)
);