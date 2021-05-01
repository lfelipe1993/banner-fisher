package pescador;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import jakarta.ws.rs.core.UriBuilder;

public class TelegramNotifier {
	private static final String CHAT_ID = "-1001399432278";
    private static final String TOKEN = "1620921748:AAH3Xm-qANhi_ZQcF-VFIbYoUgKgRUq6dFk";

    public static void sendNotification(String message) {
    	

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        UriBuilder builder = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/{token}/sendMessage")
                .queryParam("chat_id", CHAT_ID)
                .queryParam("text", message)
                .queryParam("parse_mode", "html")
        		.queryParam("disable_web_page_preview", "false");

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + TOKEN))
                .timeout(Duration.ofSeconds(5))
                .build();
        
        HttpResponse<String> response = null;
        
		try {
			response = client
			  .send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {

			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(request.toString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
    
	
    
}
