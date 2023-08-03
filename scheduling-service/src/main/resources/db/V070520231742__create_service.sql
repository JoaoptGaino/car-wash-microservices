CREATE TABLE service(
    id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);