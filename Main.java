public class Main {

    public static void main(String[] args) {

        // Mensagem inicial para indicar que o bot foi iniciado
        System.out.println("Bot rodando...");

        // Chat ID fixo (destinatário das respostas no Telegram)
        String chatId = "8943958332";

        // Loop infinito para manter o bot sempre ativo
        while (true) {

            // Lê nova mensagem do Telegram (caso exista)
            String msg = TelegramBot.lerMensagem();

            // Se chegou alguma mensagem nova
            if (msg != null) {

                // Mostra no terminal o que foi recebido
                System.out.println("Recebi: " + msg);

                // Valida o CPF usando a classe ValidadorCPF
                boolean valido = ValidadorCPF.validar(msg);

                // Se CPF for válido, responde "CPF válido"
                if (valido) {
                    TelegramBot.enviarMensagem(chatId, "CPF válido");
                // Se não for válido, responde "CPF inválido"
                } else {
                    TelegramBot.enviarMensagem(chatId, "CPF inválido");
                }
            }
            // Pequena pausa para não sobrecarregar a API do Telegra
            try {
                Thread.sleep(1000);
            } catch (Exception e) {} // Ignora erros de interrupção do sleep
        }
    }
}