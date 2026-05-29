CREATE TABLE estado_evento (
    id_estado_evento BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado_evento VARCHAR(25)
);

CREATE TABLE estado_venta (
    id_estado_venta BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado_venta VARCHAR(25)
);

CREATE TABLE estado_ticket (
    id_estado_ticket BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado_ticket VARCHAR(25)
);