-- ============================================================
--  Base de datos del microservicio plan-service (puerto 8082)
-- ============================================================
CREATE DATABASE IF NOT EXISTS bd_plan CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_plan;

CREATE TABLE IF NOT EXISTS planes (
    id            BIGINT        NOT NULL AUTO_INCREMENT,
    nombre        VARCHAR(60)   NOT NULL,
    descripcion   VARCHAR(200)  NULL,
    precio        DECIMAL(10,2) NOT NULL,
    duracion_dias INT           NOT NULL,
    estado        VARCHAR(20)   NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_planes_nombre (nombre),
    CONSTRAINT ck_planes_precio CHECK (precio > 0),
    CONSTRAINT ck_planes_duracion CHECK (duracion_dias >= 1)
);

-- Datos de ejemplo (opcionales)
INSERT INTO planes (nombre, descripcion, precio, duracion_dias, estado) VALUES
('Mensual',   'Acceso libre a sala de máquinas por 30 días', 90.00, 30, 'ACTIVO'),
('Trimestral','Acceso libre por 90 días + evaluación física', 240.00, 90, 'ACTIVO'),
('Anual',     'Acceso libre por 365 días + clases grupales', 850.00, 365, 'ACTIVO');
