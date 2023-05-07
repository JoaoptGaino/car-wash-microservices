CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE vehicle (
  id UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
  make VARCHAR(255) NOT NULL,
  model VARCHAR(255) NOT NULL,
  vehicle_type VARCHAR(50) NOT NULL,
  plate VARCHAR(10) UNIQUE NOT NULL,
  color VARCHAR(50) NOT NULL,
  year INTEGER NOT NULL,
  user_id UUID NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);