package me.wilkins.command;

import me.wilkins.FreeCamMode;
import me.wilkins.menu.CommandSettings;
import me.wilkins.settings.Message;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.remain.Remain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FreeCamCommand extends SimpleCommand {

	private static final Map<Player, Long> cooldowns = new HashMap<>();

	public FreeCamCommand(){
		super(CommandSettings.getAliases().replace('/', '|'));
		setPermission("freecam.command.use");

		setPermissionMessage(Message.COMMAND_NO_PERMISSION.getValue());
	}

	private static boolean isInCooldown(Player player) {

		if(!cooldowns.containsKey(player))
			return false;

		return System.currentTimeMillis() - cooldowns.get(player) < CommandSettings.getTimeUnit().toMillis(CommandSettings.getCooldownValue());
	}

	private static String getCooldownTime(Player player) {

		return CommandSettings.getCooldownValue() - CommandSettings.getTimeUnit()
				.convert(System.currentTimeMillis() - cooldowns.get(player), TimeUnit.MILLISECONDS)
				+ " " + CommandSettings.getTimeUnit().toString().toLowerCase();
	}

	@Override
	protected void onCommand() {
		checkConsole();

		Player player = getPlayer();

		if(FreeCamMode.isInFreeCam(player)){
			FreeCamMode freeCam = FreeCamMode.getFreeCamMode(player);
			freeCam.stopFreeCamMessage();
			freeCam.exitFreeCam();

			FreeCamTimer.findTimer(player).stopTimer();

		}else if(!(isInCooldown(player))){

			cooldowns.remove(player);
			cooldowns.put(player, System.currentTimeMillis());

			new FreeCamTimer(player).startTimer();

			FreeCamMode freeCamMode = new FreeCamMode(player);
			freeCamMode.startFreeCam();
			freeCamMode.startFreeCamMessage();

		}else{
			String messageValue = Message.COMMAND_ON_COOLDOWN.getValue().replace("%cooldown%", getCooldownTime(getPlayer()));
			if(Message.COMMAND_ON_COOLDOWN.getType() == Message.MessageType.CHAT)
				Common.tell(player, messageValue);
			else {
				String[] arr = messageValue.split("\\|");
				if(arr.length >= 2)
					Remain.sendTitle(player, arr[0], arr[1]);
				else
					Remain.sendTitle(player, arr[0], null);
			}
		}
	}
}
