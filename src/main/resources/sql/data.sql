-- lege data file geeft errors
INSERT INTO TRUCK (id, licenseplate, laadvermogen) VALUES (gen_random_uuid(), '1-ABC-234', 50);

SELECT * FROM TRUCK;