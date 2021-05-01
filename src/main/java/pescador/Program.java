package pescador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

public class Program {
	public static void main(String[] args) {

		Locale local = new Locale("pt", "BR");
		LocalDate dataInitial = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

		List<Lojista> lojistasList = Stream.of(Lojista.values()).collect(Collectors.toList());
		List<Parceiro> parceirosList = Stream.of(Parceiro.values()).collect(Collectors.toList());

		parceirosList.stream().forEach(p -> {

			System.out.println(p.getDescricao());

			lojistasList.stream().forEach(l -> {

				for (int i = -5; i < 5; i++) {
					LocalDate dataUsada = dataInitial;
					dataUsada = dataUsada.minusDays(i);

					String mes = dataUsada.getMonth().getDisplayName(TextStyle.FULL, local);

					for (int x = p.getMinPts(); x <= p.getMaxPts(); x++) {
						String builderUrl = l.getDominio() + dataUsada.getYear() + "/B2B/" + p.getSigla() + "/"
								+ String.format("%02d", dataUsada.getMonthValue()) + "-" + firstLetterToUpperCase(mes)
								+ "/" + dataUsada.getYear() + String.format("%02d", dataUsada.getMonthValue())
								+ String.format("%02d", dataUsada.getDayOfMonth()) + "-" + p.getDescricao() + "-" + x
								+ "x1-1300x400.jpg";

						try {
							Thread.sleep(2002L);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						getBanners(builderUrl);

					}
				}

			});

		});
		
		System.out.println("Execução terminada [" + LocalDateTime.now() + "]");
	}

	public static void getBanners(String url) {
		Connection.Response response;
		try {
			response = Jsoup.connect(url).ignoreContentType(true)
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36 OPR/75.0.3969.243")
					.referrer("http://www.google.com.br") 
					.execute();

			if (response.statusCode() == 200) {
				System.out.println("ENCONTREI!");
				TelegramNotifier.sendNotification(url);
			}else {
				System.out.println("Não encontrei banner :(");
			}
		} catch (HttpStatusException e) {
			System.out.println(e.getMessage() + " [HttpStatusCode: " + e.getStatusCode() + "]");
			System.out.println(e.getUrl());
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	}

	public static String firstLetterToUpperCase(String letter) {
		// second substring contains remaining letters
		String firstLetter = letter.substring(0, 1);
		String remainingLetters = letter.substring(1, letter.length());

		// change the first letter to uppercase
		firstLetter = firstLetter.toUpperCase();

		// join the two substrings
		return firstLetter + remainingLetters;
	}

}
