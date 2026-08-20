package application.calculei.domain.enums.index_enum;

public enum MonetaryCorrection {
    CDI("*Certificado de Depósito Interbancário, índice referencial baseado na taxa média diária de empréstimos entre bancos."),
    SELIC("*Taxa básica da economia brasileira apurada pelo Banco Central, utilizada como indexador referencial primário."),
    IPCA("*Índice Nacional de Preços ao Consumidor Amplo (IBGE), considerado o medidor oficial da inflação no país."),
    IPCA_E("*Índice Nacional de Preços ao Consumidor Amplo Especial, amplamente utilizado na atualização de débitos judiciais."),
    IGP_M("*Índice Geral de Preços - Mercado (FGV), índice padrão tradicional para reajustes de contratos e tarifas públicas."),
    TJ11960("*Correção aplicável a condenações contra a Fazenda Pública, seguindo a metodologia da Lei nº 11.960/2009."),
    TJ6899("*Atualização monetária padrão para débitos judiciais de natureza civil, fundamentada na Lei nº 6.899/1981.");

    private final String description;

    MonetaryCorrection(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}