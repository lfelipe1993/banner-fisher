package pescador;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import model.banners.Banners;
import pescador.observer.EventManager;

public class CheckMsg {
	public EventManager events;

	public CheckMsg() {
		this.events = new EventManager("sendmsg");
	}

	public void VerifyMsg(String url, List<String> urlsOfBanners, 
			Set<Banners> bannersSearchedInDb,List<Banners> bannersToAddInDb ) {
		
		urlsOfBanners.add(url);
		
		Optional<String> hasBanner = bannersSearchedInDb.stream().map(Banners::getUrl).filter(x -> x.equals(url)).findAny();
		
		if(!hasBanner.isPresent()) {
			System.out.println("BANNER DON'T EXISTS IN DB!");
			events.notify("sendmsg", url);
			bannersToAddInDb.add(new Banners(null,url,null));
		}else {
			System.out.println("BANNER ALREAD EXISTS IN DB!");
		}
		
	}
	
}
