CREATE DATABASE IF NOT EXISTS zona_fit_db;

USE zona_fit_db;

CREATE TABLE if not exists cliente(
	id INT AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(50),
    apellido VARCHAR(50),
    membresia INT
);

INSERT INTO cliente (nombre, apellido, membresia) VALUES
('Julio', 'González', 100),
('Daniel', 'Barbosa', 101),
('Sebastián', 'Flores', 102),
('Mauricio', 'Rico', 103),
('Jaime', 'Cuellar', 104),
('Luis', 'Martinez', 105),
('Ana', 'Gomez', 106),
('Carlos', 'Lopez', 107),
('Sofia', 'Ramirez', 108),
('Diego', 'Hernandez', 109),
('Maria', 'Torres', 110),
('Jorge', 'Vargas', 111),
('Lucia', 'Castillo', 112),
('Pedro', 'Rios', 113),
('Elena', 'Santos', 114),
('Miguel', 'Mendoza', 115),
('Paula', 'Ortiz', 116),
('Fernando', 'Silva', 117),
('Valeria', 'Cruz', 118),
('Ricardo', 'Diaz', 119);

SELECT * FROM cliente;