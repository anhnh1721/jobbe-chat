package fu.se629.jobbe_chat.entity;

public class Friend {
	public int id;
	public String friendName;
	public String avatar;
	public Message lastMessage;
	
	public Friend(int id, String friendName, String avatar, Message lastMessage) {
		super();
		this.id = id;
		this.friendName = friendName;
		this.avatar = avatar;
		this.lastMessage = lastMessage;
	}
}
