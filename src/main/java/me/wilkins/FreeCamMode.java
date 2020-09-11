package me.wilkins;

import me.wilkins.menu.NPCSettings;
import me.wilkins.settings.Message;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.remain.CompProperty;
import org.mineacademy.fo.remain.CompSound;
import org.mineacademy.fo.remain.Remain;

import java.util.HashMap;
import java.util.Map;

public class FreeCamMode {

	private static final Map<Player, FreeCamMode> PLAYER_FREE_CAM_MODE_MAP = new HashMap<>();
	private static NPCRegistry registry;

	private final Player player;
	private final ItemStack[] oldInventory;
	private final GameMode previousGameMode;
	NPC npc;

	public FreeCamMode(Player player){
		this.player = player;
		oldInventory = player.getInventory().getContents();
		previousGameMode = player.getGameMode();

		PLAYER_FREE_CAM_MODE_MAP.put(player, this);
	}

	public static FreeCamMode getFreeCamMode(Player player){
		return PLAYER_FREE_CAM_MODE_MAP.get(player);
	}

	public static boolean isInFreeCam(Player player){
		return PLAYER_FREE_CAM_MODE_MAP.containsKey(player);
	}

	public static void setRegistry(NPCRegistry registry) {
		FreeCamMode.registry = registry;
	}

	public static void exitAllFreecams(){
		for(Map.Entry<Player, FreeCamMode> entry : PLAYER_FREE_CAM_MODE_MAP.entrySet()){
			entry.getValue().exitFreeCam();
		}
	}

	public void startFreeCam(){
		player.getInventory().clear();
		player.setGameMode(GameMode.SPECTATOR);
		spawnNPC();
	}

	private void spawnNPC(){
		npc = registry.createNPC(EntityType.PLAYER, player.getName());

		if(!NPCSettings.isInvincible()) {
			npc.setProtected(false);
			npc.data().setPersistent(NPC.DEFAULT_PROTECTED_METADATA, false);
			npc.data().setPersistent(NPC.TARGETABLE_METADATA, true);
		}

		npc.spawn(player.getLocation());

		CompProperty.GRAVITY.apply(npc.getEntity(), NPCSettings.hasGravity());
	}

	private void removeNPC(){
		npc.despawn();
		registry.deregister(npc);
	}

	public void exitFreeCam() {
		player.getInventory().clear();
		player.getInventory().setContents(oldInventory);
		player.teleport(npc.getStoredLocation());
		player.setGameMode(previousGameMode);
		removeNPC();

		PLAYER_FREE_CAM_MODE_MAP.remove(player);
	}

	public NPC getNpc() {
		return npc;
	}

	public void startFreeCamMessage(){
		if(Message.FREECAM_START.getType() == Message.MessageType.CHAT)
			Common.tell(player, Message.FREECAM_START.getValue());
		else {
			String[] arr = Message.FREECAM_START.getValue().split("\\|");
			if(arr.length >= 2)
				Remain.sendTitle(player, arr[0], arr[1]);
			else
				Remain.sendTitle(player, arr[0], null);
		}
		CompSound.ENDERMAN_TELEPORT.play(player);
	}

	public void stopFreeCamMessage(){
		if(Message.FREECAM_STOP.getType() == Message.MessageType.CHAT)
			Common.tell(player, Message.FREECAM_STOP.getValue());
		else {
			String[] arr = Message.FREECAM_STOP.getValue().split("\\|");
			if(arr.length >= 2)
				Remain.sendTitle(player, arr[0], arr[1]);
			else
				Remain.sendTitle(player, arr[0], null);
		}
		CompSound.ENDERMAN_TELEPORT.play(player);
	}

	public void forceExitFreeCamMessage(){
		if(Message.FREECAM_FORCE_STOP.getType() == Message.MessageType.CHAT)
			Common.tell(player, Message.FREECAM_FORCE_STOP.getValue());
		else {
			String[] arr = Message.FREECAM_FORCE_STOP.getValue().split("\\|");
			if(arr.length >= 2)
				Remain.sendTitle(player, arr[0], arr[1]);
			else
				Remain.sendTitle(player, arr[0], null);
		}
		CompSound.ENDERMAN_TELEPORT.play(player);
	}
}
