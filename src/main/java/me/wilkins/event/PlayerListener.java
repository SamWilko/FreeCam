package me.wilkins.event;

import me.wilkins.FreeCamMode;
import me.wilkins.command.FreeCamTimer;
import me.wilkins.menu.FreeCamSettings;
import me.wilkins.menu.NPCSettings;
import me.wilkins.settings.Message;
import me.wilkins.util.MathUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.remain.Remain;

public class PlayerListener implements Listener {

	@EventHandler
	public static void onPlayerQuit(PlayerQuitEvent event){

		if(FreeCamMode.isInFreeCam(event.getPlayer())){

			FreeCamMode.getFreeCamMode(event.getPlayer()).exitFreeCam();
		}
	}

	@EventHandler
	public static void onPlayerMove(PlayerMoveEvent event){

		Player player = event.getPlayer();
		if(FreeCamMode.isInFreeCam(player) && FreeCamSettings.getMaxDistance() > 0){

			double distance = MathUtil.calculateDistanceFrom(FreeCamMode.getFreeCamMode(player).getNpc().getStoredLocation(),player.getLocation());
			if(distance >= FreeCamSettings.getMaxDistance()){
				Vector center = FreeCamMode.getFreeCamMode(player).getNpc().getStoredLocation().toVector();
				center.subtract(player.getLocation().toVector());
				player.setVelocity(center.normalize().multiply(0.5));

				if(Message.OUT_OF_BOUNDS.getType() == Message.MessageType.CHAT)
					Common.tell(player, Message.OUT_OF_BOUNDS.getValue());
				else {
					String[] arr = Message.OUT_OF_BOUNDS.getValue().split("\\|");
					if(arr.length >= 2)
						Remain.sendTitle(player, arr[0], arr[1]);
					else
						Remain.sendTitle(player, arr[0], null);
				}
			}
		}
	}

	@EventHandler
	public static void onNPCDamage(EntityDamageEvent event) {

		if (event.getEntity().hasMetadata("NPC")) {

			if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
				return;

			if(NPCSettings.isInvincible())
				return;

			Player playerInFreecam = PlayerUtil.getPlayerByNick(event.getEntity().getName(), true);

			FreeCamMode freeCam = FreeCamMode.getFreeCamMode(playerInFreecam);
			freeCam.forceExitFreeCamMessage();
			freeCam.exitFreeCam();
			FreeCamTimer.findTimer(playerInFreecam).stopTimer();
		}

	}

	@EventHandler
	public static void onPlayerHitNPC(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player && event.getEntity().hasMetadata("NPC")) {

			if(NPCSettings.isInvincible())
				return;

			Player damager = (Player) event.getDamager();
			Player playerInFreecam = PlayerUtil.getPlayerByNick(event.getEntity().getCustomName(), true);

			if (Common.stripColors(damager.getDisplayName()).equals(Common.stripColors(playerInFreecam.getDisplayName()))) {
				event.setCancelled(true);
			} else {
				FreeCamMode freeCam = FreeCamMode.getFreeCamMode(playerInFreecam);
				freeCam.forceExitFreeCamMessage();
				freeCam.exitFreeCam();
				FreeCamTimer.findTimer(playerInFreecam).stopTimer();
			}
		}
	}
}
