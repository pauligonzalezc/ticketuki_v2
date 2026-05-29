ALTER TABLE promocion MODIFY COLUMN restriccion VARCHAR(255) NOT NULL;
UPDATE promocion SET restriccion = 'Válido solo con tarjeta de crédito o débito Banco de Chile' WHERE id_promocion = 1;
