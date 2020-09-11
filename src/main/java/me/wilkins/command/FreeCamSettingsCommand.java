package me.wilkins.command;

import me.wilkins.menu.MainSettings;
import org.mineacademy.fo.command.SimpleCommand;

public class FreeCamSettingsCommand extends SimpleCommand {

	public FreeCamSettingsCommand(){
		super("fcs|freecamsettings|fcsettings");
		setPermission("freecam.command.settings");
		setPermissionMessage("&8[&c&l!&8] &cYou cannot use this command!");
	}

	@Override
	protected void onCommand() {
		checkConsole();

		new MainSettings().displayTo(getPlayer());
	}
}
