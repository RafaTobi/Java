
INSERT INTO bunker_operation (time_scheduled, completed) VALUES ('2024-10-24 12:01:30', false);
INSERT INTO bunker_operation (time_scheduled, completed) VALUES ('2024-10-25 13:01:30', false);
INSERT INTO bunker_operation (time_scheduled, completed) VALUES ('2024-10-26 14:01:30', false);

INSERT INTO inspection_operation (time_scheduled, completed) VALUES ('2024-10-24 12:01:30', false);
INSERT INTO inspection_operation (time_scheduled, completed) VALUES ('2024-10-25 13:01:30', false);
INSERT INTO inspection_operation (time_scheduled, completed) VALUES ('2024-10-23 14:01:30', false);

INSERT INTO dock_operation (arrival, vessel_number, purchase_order_reference, bunker_operation_id, inspection_operation_id)
VALUES ('2024-10-24 12:01:30', 'VSL7890', '550e8400-e29b-41d4-a716-446655440000', 1, 1);
INSERT INTO dock_operation (arrival, vessel_number, purchase_order_reference, bunker_operation_id, inspection_operation_id)
VALUES ('2024-10-25 13:01:30', 'VSL1010', 'a1204097-baa4-43e2-87d9-8d0125c7e791', 2, 2);
INSERT INTO dock_operation (arrival, vessel_number, purchase_order_reference, bunker_operation_id, inspection_operation_id)
VALUES ('2024-10-26 14:01:30', 'VSL9999', '44b8833b-ee3b-4811-bc9c-cf698297cac3', 3, 3);