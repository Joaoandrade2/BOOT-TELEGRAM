import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class TelegramBot {

    // Token do bot fornecido pelo BotFather (autenticação na API do Telegram)
    private static final String TOKEN = "8898468305:AAFHvfw8g0dO_7tUmfDCLtvk1i_EaJhmYoc";

    // URL base da API do Telegram já com o token embutido
    private static final String BASE_URL = "https://api.telegram.org/bot" + TOKEN;

     // Offset controla quais mensagens já foram processadas (evita repetição)
    private static int offset = 0;

    // Método responsável por ler novas mensagens do Telegram
    public static String lerMensagem() {
    try {

        // Faz requisição para pegar atualizações do bot
        URL url = new URL(BASE_URL + "/getUpdates?offset=" + offset + "&limit=100");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

         // Lê toda resposta JSON retornada pela API
        Scanner sc = new Scanner(conn.getInputStream());
        String json = sc.useDelimiter("\\A").next();
        sc.close();

        // Se não houver mensagens, retorna null
        if (!json.contains("\"result\":[")) return null;

        // Percorre o JSON procurando updates
        int searchPos = 0;

        while (true) {

            // Localiza próximo update_id no JSON
            int updatePos = json.indexOf("\"update_id\":", searchPos);
            if (updatePos == -1) break;

             // Extrai o ID do update
            int idStart = json.indexOf(":", updatePos) + 1;
            int idEnd = json.indexOf(",", idStart);

            int updateId = Integer.parseInt(json.substring(idStart, idEnd).trim());

            // Só processa se for novo (evita repetição)
            if (updateId > offset) {

                offset = updateId;

                // Encontra o texto da mensagem dentro do mesmo update
                int messagePos = json.indexOf("\"message\":", updatePos);
                int textPos = json.indexOf("\"text\":\"", messagePos);

                // Se existir texto, extrai ele
                if (textPos != -1) {
                    int start = textPos + 8;
                    int end = json.indexOf("\"", start);

                    return json.substring(start, end);
                }
            }
            // Move para continuar procurando outros updates
            searchPos = updatePos + 1;
        }

        return null;

     // Em caso de erro na requisição ou parsing, ignora e não quebra o bot
    } catch (Exception e) {
        return null;
    }
}
     // Método responsável por enviar mensagens ao Telegram
    public static void enviarMensagem(String chatId, String texto) {
        try {
            // Monta URL da API para enviar mensagem
            String urlStr = BASE_URL + "/sendMessage?chat_id=" + chatId +
                    "&text=" + URLEncoder.encode(texto, "UTF-8");
                    
            // Faz requisição HTTP GET para enviar a mensagem
            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.getInputStream().close();

        // Caso falhe o envio, imprime erro no console
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}