public class ValidadorCPF {

    /**
     * Valida um CPF usando o algoritmo oficial dos dígitos verificadores.
     * Remove formatação (pontos e traço) antes de validar.
     */
    public static boolean validar(String cpf) {
        // 1. Remove caracteres não numéricos (pontos, traços, espaços)
        cpf = cpf.replaceAll("[^0-9]", "");

        // 2. Verifica se tem exatamente 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // 3. Rejeita CPFs com todos os dígitos iguais (ex: 111.111.111-11)
        if (todosDigitosIguais(cpf)) {
            return false;
        }

        // 4. Valida o primeiro dígito verificador
        int primeiroDigito = calcularDigito(cpf, 9);
        if (primeiroDigito != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        // 5. Valida o segundo dígito verificador
        int segundoDigito = calcularDigito(cpf, 10);
        if (segundoDigito != Character.getNumericValue(cpf.charAt(10))) {
            return false;
        }

        return true;
    }

    /**
     * Calcula um dígito verificador do CPF.
     * @param cpf    String com 11 dígitos
     * @param tamanho quantos dígitos usar no cálculo (9 para o 1º, 10 para o 2º)
     */
    private static int calcularDigito(String cpf, int tamanho) {
        int soma = 0;
        int peso = tamanho + 1;

        for (int i = 0; i < tamanho; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * peso;
            peso--;
        }

        int resto = soma % 11;

        // Se o resto for menor que 2, o dígito é 0; caso contrário, é 11 - resto
        if (resto < 2) {
            return 0;
        } else {
            return 11 - resto;
        }
    }

    /**
     * Verifica se todos os dígitos do CPF são iguais.
     * CPFs como 000.000.000-00 e 111.111.111-11 são matematicamente válidos
     * pelo algoritmo, mas são considerados inválidos na prática.
     */
    private static boolean todosDigitosIguais(String cpf) {
        char primeiroDigito = cpf.charAt(0);
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != primeiroDigito) {
                return false;
            }
        }
        return true;
    }
}