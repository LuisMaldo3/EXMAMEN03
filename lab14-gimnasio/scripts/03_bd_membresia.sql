-- ============================================================
--  Base de datos del microservicio membresia-service (puerto 8083)
--  id_socio e id_plan son referencias lógicas (sin FOREIGN KEY):
--  cada microservicio es dueño de su propia base de datos.
-- ============================================================
CREATE DATABASE IF NOT EXISTS bd_membresia CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_membresia;

CREATE TABLE IF NOT EXISTS membresias (
    id           BIGINT        NOT NULL AUTO_INCREMENT,
    id_socio     BIGINT        NOT NULL,
    id_plan      BIGINT        NOT NULL,
    fecha_inicio DATE          NOT NULL,
    fecha_fin    DATE          NOT NULL,
    precio_plan  DECIMAL(10,2) NOT NULL,
    estado       VARCHAR(20)   NOT NULL,
    PRIMARY KEY (id),
    KEY idx_membresias_socio (id_socio),
    CONSTRAINT ck_membresias_fechas CHECK (fecha_fin > fecha_inicio)
);

-- Datos de ejemplo (opcionales; coinciden con los ids de los otros scripts)
INSERT INTO membresias (id_socio, id_plan, fecha_inicio, fecha_fin, precio_plan, estado) VALUES
(1, 1, '2026-11-01', '2026-12-01', 90.00, 'ACTIVA'),
(2, 2, '2026-10-01', '2026-12-30', 240.00, 'ACTIVA');
