
INSERT INTO supplier (uuid, name) VALUES ('9a53db66-573a-462a-bb1e-a576d6b81549', 'Supplier One');
INSERT INTO supplier (uuid, name) VALUES ('aa13794c-d363-4aa6-8370-529af93ee1e5', 'Second Supp');
INSERT INTO supplier (uuid, name) VALUES ('24fb6602-9b59-474c-a46c-820a1b50adab', 'Third supplier s the charm');

INSERT INTO purchaser (uuid, address, name) VALUES ('eedd4dcb-86cc-44a0-a2db-03dd9b7049f2', 'Trammesantlei 122, Schoten, Belgium', 'Joske Vermeulen');
INSERT INTO purchaser (uuid, address, name) VALUES ('ddfd3fa6-07d1-4f0f-80f2-d080615c477a', 'Roosendaalsebaan 101, Kalmthout, Belgium', 'Hanz Debacker');

INSERT INTO material (id, name, storage_cost, selling_price) VALUES (1, 'Gips', 1, 13);
INSERT INTO material (id, name, storage_cost, selling_price) VALUES (2, 'Ijzererts', 5, 110);
INSERT INTO material (id, name, storage_cost, selling_price) VALUES (3, 'Cement', 3, 95);
INSERT INTO material (id, name, storage_cost, selling_price) VALUES (4, 'Petcoke', 10, 210);
INSERT INTO material (id, name, storage_cost, selling_price) VALUES (5, 'Slak', 7, 160);

INSERT INTO warehouse (storage, material_id, supplier_uuid) values (25, 1, '9a53db66-573a-462a-bb1e-a576d6b81549');
INSERT INTO warehouse (storage, material_id, supplier_uuid) values (20, 2, '9a53db66-573a-462a-bb1e-a576d6b81549');
INSERT INTO warehouse (storage, material_id, supplier_uuid) values (50, 5, '24fb6602-9b59-474c-a46c-820a1b50adab');
INSERT INTO warehouse (storage, material_id, supplier_uuid) values (45, 3, '24fb6602-9b59-474c-a46c-820a1b50adab');

INSERT INTO payload_delivery_ticket (amount, time_of_delivery, storage_cost, warehouse_id, material_id) VALUES (25, '2024-06-24 12:01:30', 1, 1, 1);
INSERT INTO payload_delivery_ticket (amount, time_of_delivery, storage_cost, warehouse_id, material_id) VALUES (20, '2024-10-11 16:30:30', 5, 2, 2);
INSERT INTO payload_delivery_ticket (amount, time_of_delivery, storage_cost, warehouse_id, material_id) VALUES (25, '2024-10-11 18:05:23', 7, 3, 5);
INSERT INTO payload_delivery_ticket (amount, time_of_delivery, storage_cost, warehouse_id, material_id) VALUES (25, '2024-10-10 18:05:23', 7, 3, 5);
INSERT INTO payload_delivery_ticket (amount, time_of_delivery, storage_cost, warehouse_id, material_id) VALUES (25, '2024-10-10 18:05:23', 3, 4, 3);

INSERT INTO purchase_order (po_number, reference_uuid, vessel_number, seller_party_uuid, costumer_party_uuid, order_fulfilled)
VALUES ('PO123456', '550e8400-e29b-41d4-a716-446655440000', 'VSL7891', '24fb6602-9b59-474c-a46c-820a1b50adab', 'eedd4dcb-86cc-44a0-a2db-03dd9b7049f2', false);
INSERT INTO purchase_order (po_number, reference_uuid, vessel_number, seller_party_uuid, costumer_party_uuid, order_fulfilled)
VALUES ('PO654321', 'a1204097-baa4-43e2-87d9-8d0125c7e791', 'VSL1010', '9a53db66-573a-462a-bb1e-a576d6b81549', 'eedd4dcb-86cc-44a0-a2db-03dd9b7049f2', false);

INSERT INTO order_line (material_id, quantity, uom, purchase_order_reference_uuid) VALUES (5, 30, 'kt', '550e8400-e29b-41d4-a716-446655440000');
INSERT INTO order_line (material_id, quantity, uom, purchase_order_reference_uuid) VALUES (3, 10, 'kt', '550e8400-e29b-41d4-a716-446655440000');

INSERT INTO invoice (price, date, supplier_uuid) VALUES (5555, '2024-06-24 09:00:00', 'aa13794c-d363-4aa6-8370-529af93ee1e5');
INSERT INTO invoice (price, date, supplier_uuid) VALUES (7777, '2024-06-25 09:00:00', 'aa13794c-d363-4aa6-8370-529af93ee1e5');
INSERT INTO invoice (price, date, supplier_uuid) VALUES (10000, '2024-11-03 09:00:00', '9a53db66-573a-462a-bb1e-a576d6b81549');