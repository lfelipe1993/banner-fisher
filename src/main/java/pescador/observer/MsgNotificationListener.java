package pescador.observer;

import pescador.TelegramNotifier;

public class MsgNotificationListener implements EventListener {

    public MsgNotificationListener() {
    }

	@Override
	public void update(String url) {
		TelegramNotifier.sendNotification(url);
	}

}
