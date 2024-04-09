package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain;

public enum InsuranceCategory {
    VIDA,
    AUTO,
    VIAGEM,
    RESIDENCIAL,
    PATRIMONIAL;

    public static boolean isValidCategory(String category) {
        boolean isValidCategory = Boolean.FALSE;
        for(InsuranceCategory item : InsuranceCategory.values()) {
            if(item.name().equalsIgnoreCase(category))
                isValidCategory = Boolean.TRUE;
        }
        return isValidCategory;
    }
}
