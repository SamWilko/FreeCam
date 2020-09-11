package me.wilkins.settings;

import org.mineacademy.fo.settings.YamlConfig;

import java.util.concurrent.TimeUnit;

public class SettingsSave extends YamlConfig {

	public SettingsSave(){
		loadConfiguration(null, "data.db");
	}

	public void setAliases(String aliases){
		save("Settings.Command.Aliases", aliases);
	}

	public void setCooldownValue(int value){
		save("Settings.Command.Cooldown_Value", value);
	}

	public void setCooldownTimeUnit(TimeUnit unit){
		save("Settings.Command.Cooldown_Time_Unit", unit.toString());
	}

	public void setTimeLimitValue(int value){
		save("Settings.FreeCam.Time_Limit_Value", value);
	}

	public void setTimeLimitUnit(TimeUnit unit){
		save("Settings.FreeCam.Time_Limit_Unit", unit.toString());
	}

	public void setMaxDistance(int maxDistance){
		if(maxDistance <= 0)
			save("Settings.FreeCam.Max_Distance", "unlimited");
		else
			save("Settings.FreeCam.Max_Distance", maxDistance);
	}

	public void setGravity(boolean value){
		save("Settings.FreeCam.NPC.Gravity", value);
	}

	public void setInvincible(boolean value){
		save("Settings.FreeCam.NPC.Invincible", value);
	}
}
