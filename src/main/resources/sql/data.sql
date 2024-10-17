-- Inserting test data for Supplier
INSERT INTO SUPPLIER (id, name) VALUES (1, 'Supplier 1');
INSERT INTO SUPPLIER (id, name) VALUES (2, 'Supplier 2');
INSERT INTO SUPPLIER (id, name) VALUES (3, 'Supplier 3');

-- Inserting test data for ArrivalWindow
INSERT INTO ARRIVAL_WINDOW (venster_id, arrival_time, leaving_time)
VALUES ('8bb66f8f-7d3e-4283-8abd-ad5dae3fb90c', '2022-03-01 08:00:00', '2022-03-01 09:00:00');

INSERT INTO ARRIVAL_WINDOW (venster_id, arrival_time, leaving_time)
VALUES ('94b66f8f-8a3e-1283-9aef-ed3dae3fb94f', '2022-03-02 10:00:00', '2022-03-02 12:00:00');

INSERT INTO ARRIVAL_WINDOW (venster_id, arrival_time, leaving_time)
VALUES ('96a66f8e-6f3e-4ab3-8e8d-d5dae3fb76bc', '2022-03-03 14:00:00', '2022-03-03 16:00:00');

-- Inserting test data for Resource
INSERT INTO RESOURCE (resource_id, name, product_price, storage_price, description)
VALUES ('61b86f1d-8d3a-428f-8c8f-a7dae9fb10ab', 'Resource 1', 10, 20, 'Resource 1 description');

INSERT INTO RESOURCE (resource_id, name, product_price, storage_price, description)
VALUES ('71a16c2d-5b3b-4aa7-9c9e-b9cde3fc22ac', 'Resource 2', 20, 30, 'Resource 2 description');

-- Inserting test data for Truck
INSERT INTO TRUCK (id, licenseplate, laadvermogen)
VALUES ('81d36e8e-8d4c-478e-bf8f-12dce4fb34ac', '1-ABC-234', 50);

INSERT INTO TRUCK (id, licenseplate, laadvermogen)
VALUES ('92b46e8f-9a6b-489e-af9f-13bce4fb45bd', '2-DEF-567', 60);

-- Now, when inserting data into Appointment, reference the correct UUIDs:
INSERT INTO APPOINTMENT (appointment_id, supplier_id, arrival_window_venster_id, resource_resource_id, truck_id)
VALUES (gen_random_uuid(), 1, '8bb66f8f-7d3e-4283-8abd-ad5dae3fb90c', '61b86f1d-8d3a-428f-8c8f-a7dae9fb10ab', '81d36e8e-8d4c-478e-bf8f-12dce4fb34ac');
