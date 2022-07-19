package pescador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.DAO.BannersDAO;
import model.DAO.DAOFactory;
import model.banners.Banners;
import pescador.observer.MsgNotificationListener;

public class Program {
	//Dias do IF padrão
	static Integer dAnterior = -1;
	static Integer dPosterior = 1;
	
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
		
		//carregar banners ja visualizados
		Set<Banners> bannersSearched = buscaBanners(dAnterior); 
		
		System.out.println("dAnterior: " + dAnterior + " | dPosterior: " + dPosterior);
		
		//List que sera populada com todos os banners com status http 200
		List<String> urlsOfBanners = new ArrayList();
		
		//Lista a ser populada com os banners que vao para o banco de dados
		List<Banners> bannersToAddInDb = new ArrayList<>();

		//Instanciando classe :)
		CheckMsg checkMsg = new CheckMsg();
		checkMsg.events.subscribe("sendmsg", new MsgNotificationListener());
		
		List<Lojista> lojistasList = Stream.of(Lojista.values()).collect(Collectors.toList());
		List<Parceiro> parceirosList = Stream.of(Parceiro.values()).collect(Collectors.toList());

		parceirosList.stream().forEach(p -> {

			System.out.println(p.getDescricao().toUpperCase());

			lojistasList.stream().forEach(l -> {

				for (int i = dAnterior; i < dPosterior; i++) {
					LocalDate dataUsada = dataInitial;
					dataUsada = dataUsada.plusDays(i);

					String mes = dataUsada.getMonth().getDisplayName(TextStyle.FULL, local);

					for (int x = p.getMinPts(); x <= p.getMaxPts(); x++) {
						String builderUrl = l.getDominio() + dataUsada.getYear() + "/B2B/" + p.getSigla() + "/"
								+ String.format("%02d", dataUsada.getMonthValue()) + "-" + Utils.firstLetterToUpperCase(mes)
								+ "/" + dataUsada.getYear() + String.format("%02d", dataUsada.getMonthValue())
								+ String.format("%02d", dataUsada.getDayOfMonth()) + "-" + p.getDescricao() + "-" + x
								+ "x1-1300x400.jpg";

						try {
							Random aleatorio = new Random();
							int sleep = aleatorio.nextInt((2101 - 1002) + 1) + 1002;
							Thread.sleep(sleep);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if(BannersSearch.getBanners(builderUrl) == 200) {
							//urlsOfBanners.add(builderUrl);
							System.out.println("VERIFICANDO! [VerifyMsg]");
							checkMsg.VerifyMsg(builderUrl, urlsOfBanners,bannersSearched,bannersToAddInDb);
							System.out.println("---------------------------------------------------------");
						}

					}
				}

			});

		});
		
		System.out.println("-----------------BANNERS ENCONTRADOS-----------------");
		urlsOfBanners.forEach(System.out::println);
		System.out.println("-----------------------------------------------------");
		
		/*Remover os banners da lista caso ja estejam salvos no banco!
		urlsOfBanners.removeAll(bannersSearched.stream().map(Banners::getUrl).collect(Collectors.toList()));
		
		List<Banners> bannersToAddInDb = new ArrayList<>();
		
		urlsOfBanners.forEach(x -> {
			bannersToAddInDb.add(new Banners(null,x,null));
		});*/
		
		//Salvar banners que nao foram salvos ainda no banco de acordo com a leitura anterior.
		saveBanners(bannersToAddInDb);
		
		System.out.println("--------------------BANNERS NOVOS--------------------");
		//Envia mensagem dos banners reconhecidos
		bannersToAddInDb.forEach(x -> {
			System.out.println(x.getUrl());	
		});
		System.out.println("-----------------------------------------------------");

		//Envia mensagem dos banners reconhecidos
		/*bannersToAddInDb.forEach(x -> {
			TelegramNotifier.sendNotification(x.getUrl());
		});*/
		
		System.out.println("Execução terminada [" + LocalDateTime.now() + "]");
	}

	public static Set<Banners> buscaBanners(Integer daysAgo) {
		
		//Em buscas para datas futuras da problema. Tem que ser no minimo 15 dias no passado.
		if(daysAgo >= -5) {
			daysAgo = -15;
		}
		
		BannersDAO factory = DAOFactory.CreateBannersDao();
		Set<Banners> list = factory.findBanners(daysAgo);
		return list;
	}
	
	public static void saveBanners(List<Banners> banners) {
		BannersDAO factory = DAOFactory.CreateBannersDao();
		
		banners.forEach(x -> factory.insert(x));
	}


}
