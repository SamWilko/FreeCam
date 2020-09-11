package me.wilkins;

import me.wilkins.command.FreeCamCommand;
import me.wilkins.command.FreeCamSettingsCommand;
import me.wilkins.event.PlayerListener;
import me.wilkins.settings.MessageSettings;
import me.wilkins.settings.SettingsLoad;
import net.citizensnpcs.api.CitizensAPI;
import org.mineacademy.fo.plugin.SimplePlugin;

public class FreeCamPlugin extends SimplePlugin {

	@Override
	protected void onPluginStart() {

		new SettingsLoad();
		new MessageSettings().load();

		registerCommand(new FreeCamCommand());
		registerCommand(new FreeCamSettingsCommand());

		registerEvents(new PlayerListener());

		FreeCamMode.setRegistry(CitizensAPI.getNPCRegistry());
	}

	@Override
	protected void onPluginStop() {
		FreeCamMode.exitAllFreecams();
	}
}
