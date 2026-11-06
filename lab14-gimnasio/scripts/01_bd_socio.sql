-- ============================================================
--  Base de datos del microservicio socio-service (puerto 8081)
-- ============================================================
CREATE DATABASE IF NOT EXISTS bd_socio CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_socio;

CREATE TABLE IF NOT EXISTS socios (
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    dni       VARCHAR(8)   NOT NULL,
    nombres   VARCHAR(60)  NOT NULL,
    apellidos VARCHAR(60)  NOT NULL,
    correo    VARCHAR(100) NOT NULL,
    telefono  VARCHAR(9)   NULL,
    estado    VARCHAR(20)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_socios_dni (dni),
    UNIQUE KEY uk_socios_correo (correo)
);

-- Datos de ejemplo (opcionales)
INSERT INTO socios (dni, nombres, apellidos, correo, telefono, estado) VALUES
('70112233', 'Carlos', 'Ramos Pérez', 'carlos.ramos@mail.com', '987654321', 'ACTIVO'),
('70445566', 'Lucía', 'Torres Díaz', 'lucia.torres@mail.com', '912345678', 'ACTIVO'),
('70778899', 'Mario', 'Quispe León', 'mario.quispe@mail.com', NULL, 'INACTIVO');
