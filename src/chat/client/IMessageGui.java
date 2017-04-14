package chat.client;

public interface IMessageGui {
	public void showNewMessage(String fromUser, String message);
	public void showAdminMessage(String message);
}
