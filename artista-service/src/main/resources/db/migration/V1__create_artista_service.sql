CREATE TABLE artista (
    id_artista BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_artista VARCHAR(50) NOT NULL,
    genero_artista VARCHAR(50) NOT NULL,
    redes_sociales VARCHAR(25) NOT NULL
);

CREATE TABLE artista_evento (
    artista_id_artista BIGINT NOT NULL,
    evento_id_evento BIGINT NOT NULL,
    PRIMARY KEY (artista_id_artista, evento_id_evento),
    FOREIGN KEY (artista_id_artista) REFERENCES artista(id_artista)
);
