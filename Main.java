public class Main {

    public static void main(String[] args) {

        // Mensagem inicial
        System.out.println("Bot rodando...");

        // Loop infinito
        while (true) {

            // Lê mensagem do Telegram
            String msg = TelegramBot.lerMensagem();

            // Se chegou mensagem
            if (msg != null) {

                System.out.println("Recebi: " + msg);

                System.out.println(
                        "Chat ID: " + TelegramBot.chatId
                );

                // Valida CPF
                boolean valido = ValidadorCPF.validar(msg);

                // Responde usuário
                if (valido) {

                    System.out.println(
                            "Enviando: CPF válido"
                    );

                    TelegramBot.enviarMensagem(
                            TelegramBot.chatId,
                            "CPF válido"
                    );

                } else {

                    System.out.println(
                            "Enviando: CPF inválido"
                    );

                    TelegramBot.enviarMensagem(
                            TelegramBot.chatId,
                            "CPF inválido"
                    );
                }
            }

            // Pausa para não sobrecarregar API
            try {

                Thread.sleep(1000);

            } catch (Exception e) {

                System.out.println(
                        "Erro no sleep"
                );

                e.printStackTrace();
            }
        }
    }
}