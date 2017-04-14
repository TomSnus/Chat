package chat.server;

public interface IChatListener {
	public void onMessage(String fromUser, String message, boolean isAdmin);
	public String getUsername();
}
