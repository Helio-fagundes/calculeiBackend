package application.calculei.domain.enums.index_enum;

public enum InterestCorrection {
    SELIC("SELIC", "*Taxa SELIC acumulada no período, conforme Banco Central do Brasil."),
    CODIGO_CIVIL("CÓDIGO CIVIL", "*6% ao ano ou 0,5% ao mês até 10/01/2003; 12% ao ano ou 1% ao mês a partir de 11/01/2003."),
    JUROS_SIMPLES_6("JUROS SIMPLES DE 6%", "*Juros simples de 6% ao ano (0,5% ao mês)."),
    JUROS_SIMPLES_12("JUROS SIMPLES DE 12%", "*Juros simples de 12% ao ano (1% ao mês)."),
    CDI("CDI", "*Taxa CDI acumulada no período, conforme Banco Central do Brasil."),
    POUPANCA("POUPANÇA", "*Poupança (Antiga + Nova): 0,5% a.m. até 03/05/2012; Taxa Selic quando abaixo de 8,5% a.a., ou 0,5% a.m. + TR a partir de 04/05/2012."),
    TAXA_LEGAL("TAXA LEGAL", "*Taxa Legal (Res. CMN nº 5.171/2024): diferença entre a Selic mensal e a variação do IPCA-15 do mês anterior. Aplica juros simples com fração pro rata. Valores negativos são definidos como zero."),
    ESPECIFICAR_TAXA("ESPECIFICAR TAXA", "*Taxa a ser especificada pelo usuário.");

    private final String description;
    private final String label;

    InterestCorrection(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getLabel() {
        return label;
    }
}
