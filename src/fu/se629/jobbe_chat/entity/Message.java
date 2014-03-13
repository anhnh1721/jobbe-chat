package fu.se629.jobbe_chat.entity;

public class Message {
	public boolean friend;
	public String content;
	public String time;

	public Message(boolean friend, String content, String time) {
		super();
		this.friend = friend;
		this.content = content;
		this.time = time;
	}
}
