package fu.se629.jobbe_chat;

public class Friend {
	private String friendName;
	private String lastMessenge;
	private String timeLastMessenge;
	private String urlAvata;

	public Friend(String friendName, String lastMessenge,
			String timeLastMessenge, String urlAvata) {
		super();
		this.friendName = friendName;
		this.lastMessenge = lastMessenge;
		this.timeLastMessenge = timeLastMessenge;
		this.urlAvata = urlAvata;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getLastMessenge() {
		return lastMessenge;
	}

	public void setLastMessenge(String lastMessenge) {
		this.lastMessenge = lastMessenge;
	}

	public String getTimeLastMessenge() {
		return timeLastMessenge;
	}

	public void setTimeLastMessenge(String timeLastMessenge) {
		this.timeLastMessenge = timeLastMessenge;
	}

	public String getUrlAvata() {
		return urlAvata;
	}

	public void setUrlAvata(String urlAvata) {
		this.urlAvata = urlAvata;
	}
	
}
