-- lege data file geeft errors
INSERT INTO TRUCK (id, licenseplate, laadvermogen) VALUES (gen_random_uuid(), '1-ABC-234', 50);

SELECT * FROM TRUCK;

-- Inserting test data for appointments
INSERT INTO APPOINTMENT (appointment_id, supplier_id, arrival_window_venster_id, resource_grondstof_id, truck_id) VALUES (gen_random_uuid(), gen_random_uuid(), gen_random_uuid(), gen_random_uuid());
INSERT INTO APPOINTMENT (id, date, time, truck_id) VALUES (gen_random_uuid(), '2022-03-02', '11:00:00', (SELECT id FROM TRUCK WHERE licenseplate = '1-ABC-234'));
INSERT INTO APPOINTMENT (id, date, time, truck_id) VALUES (gen_random_uuid(), '2022-03-03', '12:00:00', (SELECT id FROM TRUCK WHERE licenseplate = '1-ABC-234'));

-- Select all from APPOINTMENT to verify the data
SELECT * FROM APPOINTMENT;