INSERT INTO tbl_indices_bc
(
    codigo,
    controller,
    data_init,
    descricao,
    periodicidade,
    serie,
    url_bc
)
VALUES
    (12, 'cdiController', '1986-01-01', 'CDI', 'D', 'CDI',
     'https://api.bcb.gov.br/dados/serie/bcdata.sgs.12/dados?formato=json'),

    (190, 'igpdiController', '1986-01-01', 'IGP-DI', 'M', 'IGPDI',
     'https://api.bcb.gov.br/dados/serie/bcdata.sgs.190/dados?formato=json'),

    (189, 'igpmController', '1986-01-01', 'IGP-M', 'M', 'IGPM',
     'https://api.bcb.gov.br/dados/serie/bcdata.sgs.189/dados?formato=json'),

    (433, 'ipcaController', '1986-01-01', 'IPCA', 'M', 'IPCA',
     'https://api.bcb.gov.br/dados/serie/bcdata.sgs.433/dados?formato=json'),

    (10764, 'ipcaeController', '1986-01-01', 'IPCA-E', 'M', 'IPCAE',
     'https://api.bcb.gov.br/dados/serie/bcdata.sgs.10764/dados?formato=json'),

    (196, 'poupNovaController', '2012-05-04', 'Poupança Nova', 'M', 'POUPNOVA',
     'https://api.bcb.gov.br/dados/serie/bcdata.sgs.196/dados?formato=json'),

    (226, 'trController', '1995-01-01', 'TR', 'D', 'TR',
     'https://api.bcb.gov.br/dados/serie/bcdata.sgs.226/dados?formato=json'),

    (1619, 'salarioController', '1986-01-01', 'Salário Mínimo', 'M', 'SALARIO',
     'https://api.bcb.gov.br/dados/serie/bcdata.sgs.1619/dados?formato=json'),

    (25, 'poupAntigaController', '1964-01-01', 'Poupança Antiga', 'D', 'POUPANTIGA',
     'https://api.bcb.gov.br/dados/serie/bcdata.sgs.25/dados?formato=json'),

    (29543, 'taxaLegalController', '2024-08-01', 'Taxa Legal', 'M', 'TAXA_LEGAL',
     'https://api.bcb.gov.br/dados/serie/bcdata.sgs.29543/dados?formato=json'),

    (0, 'ufirController', '1995-01-01', 'UFIR', 'A', 'UFIR',
     NULL),

    (0, 'tj6899Controller', '1964-07-01', 'TJ-RJ Lei 6.899/81', 'M', 'TJ6899',
     NULL),

    (0, 'tj11960Controller', '1964-10-01', 'TJ-RJ Lei 11.960', 'M', 'TJ11960',
     NULL),

    (11, 'selicDiarioController', '1986-01-01', 'SELIC DIARIO', 'D', 'SELIC_DIARIO',
     'https://api.bcb.gov.br/dados/serie/bcdata.sgs.11/dados?formato=json'),

    (4390, 'selicMensalController', '1986-01-01', 'SELIC MENSAL', 'M', 'SELIC_MENSAL',
     'https://api.bcb.gov.br/dados/serie/bcdata.sgs.4390/dados?formato=json');