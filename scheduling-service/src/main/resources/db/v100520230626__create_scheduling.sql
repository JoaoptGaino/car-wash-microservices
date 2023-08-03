CREATE TABLE scheduling (
    id UUID NOT NULL PRIMARY KEY,
    vehicle_id INT,
    date TIMESTAMP,
    status VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE scheduling_department (
    scheduling_id UUID NOT NULL,
    department_id INT NOT NULL,
    PRIMARY KEY (scheduling_id, department_id),
    FOREIGN KEY (scheduling_id) REFERENCES scheduling (id),
    FOREIGN KEY (department_id) REFERENCES department (id)
);