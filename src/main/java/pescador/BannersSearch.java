package pescador;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

public class BannersSearch {
	public static int getBanners(String url) {
		Connection.Response response;
		int StatusCodeFinal = 0;
		try {
			response = Jsoup.connect(url).ignoreContentType(true).userAgent(
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36 OPR/75.0.3969.243")
					.referrer("http://www.google.com.br").execute();

			if (response.statusCode() == 200) {
				System.out.println(url);
				System.out.println("ENCONTREI!");
				System.out.println("---------------------------------------------------------");
				//TelegramNotifier.sendNotification(url);
				StatusCodeFinal = response.statusCode();
			} else {
				System.out.println("Não encontrei banner :(");
			}
		} catch (HttpStatusException e) {
			System.out.println(e.getMessage() + " [HttpStatusCode: " + e.getStatusCode() + "]");
			System.out.println(e.getUrl());
			System.out.println("Não encontrei banner :(");
			System.out.println("---------------------------------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return StatusCodeFinal;

	}

}
