DELETE FROM tbl_poupanca_nova;

UPDATE tbl_indices_bc
SET codigo = '195', url_bc = 'https://api.bcb.gov.br/dados/serie/bcdata.sgs.195/dados?formato=json'
WHERE serie IN ('POUPNOVA');