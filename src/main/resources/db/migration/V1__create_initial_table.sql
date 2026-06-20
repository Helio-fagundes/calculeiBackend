CREATE TABLE tbl_indices_bc (
                                id_bc BIGSERIAL PRIMARY KEY,
                                serie VARCHAR(255),
                                codigo INTEGER NOT NULL,
                                descricao VARCHAR(255),
                                data_init DATE,
                                url_bc VARCHAR(255),
                                controller VARCHAR(255),
                                periodicidade VARCHAR(255),
                                last_update DATE
);

CREATE TABLE tbl_history_pdf_value (
                                       id BIGSERIAL PRIMARY KEY,
                                       date DATE,
                                       json_data TEXT,
                                       token VARCHAR(255)
);

CREATE TABLE tbl_ufir_rj (
                             id BIGSERIAL PRIMARY KEY,
                             fator NUMERIC(20, 10),
                             data_init DATE,
                             id_bc BIGINT NOT NULL,
                             CONSTRAINT fk_ufir_rj_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_cdi (
                         id BIGSERIAL PRIMARY KEY,
                         fator NUMERIC(20, 10),
                         data_init DATE,
                         id_bc BIGINT NOT NULL,
                         CONSTRAINT fk_cdi_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_igp_di (
                            id BIGSERIAL PRIMARY KEY,
                            fator NUMERIC(20, 10),
                            data_init DATE,
                            id_bc BIGINT NOT NULL,
                            CONSTRAINT fk_igp_di_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_igpm (
                          id BIGSERIAL PRIMARY KEY,
                          fator NUMERIC(20, 10),
                          data_init DATE,
                          id_bc BIGINT NOT NULL,
                          CONSTRAINT fk_igpm_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_tj_l6899 (
                              id BIGSERIAL PRIMARY KEY,
                              fator NUMERIC(20, 10),
                              data_init DATE,
                              id_bc BIGINT NOT NULL,
                              CONSTRAINT fk_tj_l6899_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_tj_l11960_selic (
                                     id BIGSERIAL PRIMARY KEY,
                                     fator NUMERIC(20, 10),
                                     data_init DATE,
                                     id_bc BIGINT NOT NULL,
                                     CONSTRAINT fk_tj_l11960_selic_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_ipca (
                          id BIGSERIAL PRIMARY KEY,
                          fator NUMERIC(20, 10),
                          data_init DATE,
                          id_bc BIGINT NOT NULL,
                          CONSTRAINT fk_ipca_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_ipcae (
                           id BIGSERIAL PRIMARY KEY,
                           fator NUMERIC(20, 10),
                           data_init DATE,
                           id_bc BIGINT NOT NULL,
                           CONSTRAINT fk_ipcae_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_poupanca_antiga (
                                     id BIGSERIAL PRIMARY KEY,
                                     fator NUMERIC(20, 10),
                                     data_init DATE,
                                     id_bc BIGINT NOT NULL,
                                     CONSTRAINT fk_poupanca_antiga_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_poupanca_nova (
                                   id BIGSERIAL PRIMARY KEY,
                                   fator NUMERIC(20, 10),
                                   data_init DATE,
                                   id_bc BIGINT NOT NULL,
                                   CONSTRAINT fk_poupanca_nova_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_salario (
                             id BIGSERIAL PRIMARY KEY,
                             fator NUMERIC(20, 10),
                             data_init DATE,
                             id_bc BIGINT NOT NULL,
                             CONSTRAINT fk_salario_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_selic_diario (
                                  id BIGSERIAL PRIMARY KEY,
                                  fator NUMERIC(20, 10),
                                  data_init DATE,
                                  id_bc BIGINT NOT NULL,
                                  CONSTRAINT fk_selic_diario_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_selic_mensal (
                                  id BIGSERIAL PRIMARY KEY,
                                  fator NUMERIC(20, 10),
                                  data_init DATE,
                                  id_bc BIGINT NOT NULL,
                                  CONSTRAINT fk_selic_mensal_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_taxa_legal (
                                id BIGSERIAL PRIMARY KEY,
                                fator NUMERIC(20, 10),
                                data_init DATE,
                                id_bc BIGINT NOT NULL,
                                CONSTRAINT fk_taxa_legal_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);

CREATE TABLE tbl_tr (
                        id BIGSERIAL PRIMARY KEY,
                        fator NUMERIC(20, 10),
                        data_init DATE,
                        id_bc BIGINT NOT NULL,
                        CONSTRAINT fk_tr_indice_bc FOREIGN KEY (id_bc) REFERENCES tbl_indices_bc(id_bc)
);