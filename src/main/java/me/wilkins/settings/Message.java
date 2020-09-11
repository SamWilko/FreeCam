package me.wilkins.settings;

public class Message {

	public static Message COMMAND_NO_PERMISSION;
	public static Message COMMAND_ON_COOLDOWN;
	public static Message FREECAM_START;
	public static Message FREECAM_STOP;
	public static Message FREECAM_FORCE_STOP;
	public static Message OUT_OF_BOUNDS;

	private final MessageType type;
	private final String value;

	public Message(MessageType type, String value){
		this.type = type;
		this.value = value;
	}

	public MessageType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public enum MessageType{
		CHAT,
		TITLE
	}
}
