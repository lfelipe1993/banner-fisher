package pescador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

public class Program {
	//Dias do IF padrão
	static Integer dAnterior = -5;
	static Integer dPosterior = 5;
	
	public static void main(String[] args) {

		Locale local = new Locale("pt", "BR");
		LocalDate dataInitial = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
		
		List<String> params = Arrays.asList(args);

		params.forEach(p -> {
			if (p != null) {
				String[] teste = p.split("=");
				

				if (teste[0].equalsIgnoreCase("dAnterior")) {
					dAnterior = Math.negateExact(Integer.parseInt(teste[1]));
				} else if (teste[0].equalsIgnoreCase("dPosterior")) {
					dPosterior = Integer.parseInt(teste[1]);
				}

			}
			// System.out.println("lojista: " + varejista.getDescricao());
		});
		
		System.out.println("dAnterior: " + dAnterior + " - dPosterior: " + dPosterior);
		

		List<Lojista> lojistasList = Stream.of(Lojista.values()).collect(Collectors.toList());
		List<Parceiro> parceirosList = Stream.of(Parceiro.values()).collect(Collectors.toList());

		parceirosList.stream().forEach(p -> {

			System.out.println(p.getDescricao().toUpperCase());

			lojistasList.stream().forEach(l -> {

				for (int i = dAnterior; i < dPosterior; i++) {
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
							Random aleatorio = new Random();
							int sleep = aleatorio.nextInt((2901 - 1102) + 1) + 1102;
							Thread.sleep(sleep);
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
