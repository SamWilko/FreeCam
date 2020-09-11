package me.wilkins.settings;

import org.mineacademy.fo.settings.YamlConfig;

public class MessageSettings extends YamlConfig {

	public MessageSettings(){
		loadConfiguration("messages.yml", "messages.yml");
	}

	public void load(){

		Message.COMMAND_NO_PERMISSION = new Message(Message.MessageType.CHAT, getOrSetDefault("Command_No_Permission.Value", "&8[&c&l!&8]&c You cannot use this command!"));
		Message.COMMAND_ON_COOLDOWN = new Message(Message.MessageType.valueOf(getOrSetDefault("Command_On_Cooldown.Type", "chat").toUpperCase()), getOrSetDefault("Command_On_Cooldown.Value", "&8[&c&l!&8]&c You must wait %cooldown% before using this command again!"));
		Message.FREECAM_START = new Message(Message.MessageType.valueOf(getOrSetDefault("FreeCam_Start.Type", "chat").toUpperCase()), getOrSetDefault("FreeCam_Start.Value", "&8[&d&l!&8]&d You are now in FreeCam!"));
		Message.FREECAM_STOP = new Message(Message.MessageType.valueOf(getOrSetDefault("FreeCam_Stop.Type", "chat").toUpperCase()), getOrSetDefault("FreeCam_Stop.Value", "&8[&d&l!&8]&d You are no longer in FreeCam!"));
		Message.FREECAM_FORCE_STOP = new Message(Message.MessageType.valueOf(getOrSetDefault("FreeCam_Force_Stop.Type", "chat").toUpperCase()), getOrSetDefault("FreeCam_Force_Stop.Value", "&8[&d&l!&8]&d You were forced out of FreeCam!"));
		Message.OUT_OF_BOUNDS = new Message(Message.MessageType.valueOf(getOrSetDefault("Out_Of_Bounds.Type", "title").toUpperCase()), getOrSetDefault("Out_Of_Bounds.Value", "&c&lOut of Bounds!|&4Go Back"));
	}
}
