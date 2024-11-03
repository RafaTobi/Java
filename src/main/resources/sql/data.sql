-- src/main/resources/sql/data.sql
-- Inserting test data for Supplier
INSERT INTO SUPPLIER (id, name)
VALUES (1, 'Supplier 1');
INSERT INTO SUPPLIER (id, name)
VALUES (2, 'Supplier 2');
INSERT INTO SUPPLIER (id, name)
VALUES (3, 'Supplier 3');
INSERT INTO SUPPLIER (id, name)
VALUES (4, 'Supplier 4');
INSERT INTO SUPPLIER (id, name)
VALUES (5, 'Supplier 5');
INSERT INTO SUPPLIER (id, name)
VALUES (6, 'Supplier 6');

-- Inserting test data for ArrivalWindow
INSERT INTO ARRIVAL_WINDOW (id, date, start_time, end_time)
VALUES ('8bb66f8f-7d3e-4283-8abd-ad5dae3fb90c', '2024-10-20', '08:00:00', '09:00:00');

INSERT INTO ARRIVAL_WINDOW (id, date, start_time, end_time)
VALUES ('94b66f8f-8a3e-1283-9aef-ed3dae3fb94f', '2024-10-21', '10:00:00', '11:00:00');

INSERT INTO ARRIVAL_WINDOW (id, date, start_time, end_time)
VALUES ('96a66f8e-6f3e-4ab3-8e8d-d5dae3fb76bc', '2024-10-22', '14:00:00', '15:00:00');
-- Inserting additional test data for ArrivalWindow
INSERT INTO ARRIVAL_WINDOW (id, date, start_time, end_time)
VALUES ('d4e66f8f-7e3e-4283-8bcd-ad5dae3fb91d', '2024-10-23', '16:00:00', '17:00:00');


-- Inserting test data for Resource
INSERT INTO RESOURCE (name, description, product_price, storage_price)
VALUES ('Gips', 'Gips is een zacht mineraal, vaak gebruikt in de bouw voor gipsplaten, cement en als bodemverbeteraar in de landbouw.', 13, 1);

INSERT INTO RESOURCE (name, description, product_price, storage_price)
VALUES ('IjzerErts', 'IJzererts is een mineraal dat wordt gebruikt voor de productie van staal, cruciaal in de bouw en industrie.', 110, 5);

INSERT INTO RESOURCE (name, description, product_price, storage_price)
VALUES ('Cement', 'Cement is een bindmiddel dat wordt gebruikt in beton en mortel, essentieel in de bouw voor structurele sterkte.', 95, 3);

INSERT INTO RESOURCE (name, description, product_price, storage_price)
VALUES ('Petcoke', 'Petcoke is een koolstofrijk bijproduct van olieraffinage, vaak gebruikt als brandstof in industriële processen.', 210, 10);

INSERT INTO RESOURCE (name, description, product_price, storage_price)
VALUES ('Slak', 'Slak is een bijproduct van smeltprocessen, gebruikt in beton en de wegenbouw om de duurzaamheid te verbeteren.', 160, 7);

-- Inserting test data for Truck
INSERT INTO TRUCK (id, licenseplate, laadvermogen, on_site)
VALUES ('81d36e8e-8d4c-478e-bf8f-12dce4fb34ac', '1-ABC-234', 50, false);

INSERT INTO TRUCK (id, licenseplate, laadvermogen, on_site)
VALUES ('92b46e8f-9a6b-489e-af9f-13bce4fb45bd', '2-DEF-567', 60, false);


INSERT INTO TRUCK (id, licenseplate, laadvermogen, on_site)
VALUES ('a1d36e8e-8d4c-478e-bf8f-12dce4fb34ad', '3-GHI-890', 70, true);
INSERT INTO TRUCK (id, licenseplate, laadvermogen, on_site)
VALUES ('b2b46e8f-9a6b-489e-af9f-13bce4fb45be', '4-JKL-123', 80, true);
INSERT INTO TRUCK (id, licenseplate, laadvermogen, on_site)
VALUES ('c3c56e8f-9b7c-489f-bf9f-14cde4fb56cf', '5-MNO-456', 90, false);


-- Inserting test data for Appointment
INSERT INTO APPOINTMENT (appointment_id, supplier_id, arrival_window_id,resource_name, truck_id)
VALUES (gen_random_uuid(), 1, '8bb66f8f-7d3e-4283-8abd-ad5dae3fb90c', 'Gips', '81d36e8e-8d4c-478e-bf8f-12dce4fb34ac');
-- Inserting additional test data for Appointment
INSERT INTO APPOINTMENT (appointment_id, supplier_id, arrival_window_id, resource_name, truck_id)
VALUES (gen_random_uuid(), 2, '94b66f8f-8a3e-1283-9aef-ed3dae3fb94f', 'IjzerErts', '92b46e8f-9a6b-489e-af9f-13bce4fb45bd');
INSERT INTO APPOINTMENT (appointment_id, supplier_id, arrival_window_id, resource_name, truck_id)
VALUES (gen_random_uuid(), 3, '96a66f8e-6f3e-4ab3-8e8d-d5dae3fb76bc', 'Cement', 'a1d36e8e-8d4c-478e-bf8f-12dce4fb34ad');
INSERT INTO APPOINTMENT (appointment_id, supplier_id, arrival_window_id, resource_name, truck_id)
VALUES (gen_random_uuid(), 4, 'd4e66f8f-7e3e-4283-8bcd-ad5dae3fb91d', 'Petcoke', 'c3c56e8f-9b7c-489f-bf9f-14cde4fb56cf');