package me.wilkins.command;

import me.wilkins.FreeCamMode;
import me.wilkins.menu.FreeCamSettings;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.remain.CompSound;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FreeCamTimer {

	private static final Map<Player, FreeCamTimer> playerTimesMap = new HashMap<>();

	private long seconds;
	private BukkitTask runningTask;

	private final Player player;

	public FreeCamTimer(Player player){
		this.player = player;
		seconds = TimeUnit.SECONDS.convert(FreeCamSettings.getTimeLimitValue(), FreeCamSettings.getTimeLimitUnit());
	}

	public void startTimer(){
		playerTimesMap.put(player, this);

		Runnable task = () -> {
			if(getSeconds() > 0) {
				if(seconds > 60 && seconds % 60 == 0)
					Common.tell(player, "&8[&c&l!&8]&c You have " + seconds / 60 + " minutes remaining!");
				if(seconds == 60)
					Common.tell(player, "&8[&c&l!&8]&c You have " + seconds + " seconds remaining!");
				if(seconds == 30)
					Common.tell(player, "&8[&c&l!&8]&c You have " + seconds + " seconds remaining!");
				if(seconds == 10)
					Common.tell(player, "&8[&c&l!&8]&c You have " + seconds + " seconds remaining!");
				if (seconds <= 5)
					Common.tell(player, "&8[&c&l!&8]&c You have " + seconds + " seconds remaining!");

				setSeconds(getSeconds() - 1);
			}else{
				FreeCamMode.getFreeCamMode(player).exitFreeCam();
				Common.tell(player, "&8[&d&l!&8] &dYou are no longer in FreeCam!");
				CompSound.ENDERMAN_TELEPORT.play(player);

				FreeCamTimer.findTimer(player).stopTimer();
			}
		};

		if(seconds > 0)
			runningTask = Common.runTimer(20, task);
	}

	public static FreeCamTimer findTimer(Player player){
		return playerTimesMap.get(player);
	}

	private void setSeconds(long seconds){
		this.seconds = seconds;
	}

	public long getSeconds() {
		return seconds;
	}

	public void stopTimer(){
		if(runningTask != null)
			runningTask.cancel();
		playerTimesMap.remove(player);
	}
}
