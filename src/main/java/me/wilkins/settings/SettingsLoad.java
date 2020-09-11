package me.wilkins.settings;

import me.wilkins.menu.CommandSettings;
import me.wilkins.menu.FreeCamSettings;
import me.wilkins.menu.NPCSettings;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.concurrent.TimeUnit;

public class SettingsLoad extends YamlConfig {

	public SettingsLoad(){
		loadConfiguration(null, "data.db");
	}

	@Override
	protected void onLoadFinish() {
		CommandSettings.setAliases(getOrSetDefault("Settings.Command.Aliases", "roam"));
		CommandSettings.setCooldownValue(getOrSetDefault("Settings.Command.Cooldown_Value", 30));
		CommandSettings.setTimeUnit(TimeUnit.valueOf(getOrSetDefault("Settings.Command.Cooldown_Time_Unit", TimeUnit.SECONDS.toString())));

		FreeCamSettings.setTimeLimitValue(getOrSetDefault("Settings.FreeCam.Time_Limit_Value", 1));
		FreeCamSettings.setTimeLimitUnit(TimeUnit.valueOf(getOrSetDefault("Settings.FreeCam.Time_Limit_Unit", TimeUnit.MINUTES.toString())));

		String maxDistance = null;
		int maxDistanceInt = 0;
		try {
			maxDistance = getString("Settings.FreeCam.Max_Distance");
		}catch(Exception ex){
			maxDistanceInt = getInteger("Settings.FreeCam.Max_Distance");
		}

		if(maxDistance != null && maxDistance.equals("unlimited"))
			FreeCamSettings.setMaxDistance(0);
		else
			FreeCamSettings.setMaxDistance(maxDistanceInt);

		NPCSettings.setGravity(getOrSetDefault("Settings.FreeCam.NPC.Gravity", false));
		NPCSettings.setInvincible(getOrSetDefault("Settings.FreeCam.NPC.Invincible", true));
	}
}
