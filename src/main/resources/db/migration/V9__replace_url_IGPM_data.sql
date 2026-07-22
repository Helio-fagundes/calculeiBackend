UPDATE tbl_indices_bc
SET url_bc = 'https://api.bcb.gov.br/dados/serie/bcdata.sgs.28655/dados?formato=json'
WHERE serie IN ('IGPM');