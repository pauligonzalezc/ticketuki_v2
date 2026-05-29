CREATE TABLE historial (
    id_historial BIGINT AUTO_INCREMENT PRIMARY KEY,
    entidad VARCHAR(50) NOT NULL,
    id_entidad INTEGER NOT NULL,
    accion VARCHAR(50) NOT NULL,
    usuario_id INTEGER,
    timestamp DATETIME,
    cambios_anteriores TEXT,
    cambios_nuevos TEXT
);
