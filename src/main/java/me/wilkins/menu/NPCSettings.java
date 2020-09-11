package me.wilkins.menu;

import me.wilkins.settings.SettingsSave;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

public class NPCSettings extends Menu {

	private static boolean gravity = false;
	private static boolean Invincible = true;

	private final Button GRAVITY_BUTTON;
	private final Button INVINCIBLE_BUTTON;

	private final Button CLOSE_BUTTON = new CloseButton();
	private final Button BACK_BUTTON;

	public NPCSettings(){
		setTitle("NPC Settings");
		setSize(9*5);

		GRAVITY_BUTTON = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				gravity = !gravity;
				new SettingsSave().setGravity(gravity);
				restartMenu();
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.FEATHER, "&6&lGravity Settings","&7Should the NPC have gravity?","",
						"&6&lCurrent: " + (gravity ? "&a&lYes" : "&c&lNo") , "",
						"&8Click to edit value")
						.build().make();
			}
		};

		INVINCIBLE_BUTTON = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				Invincible = !Invincible;
				new SettingsSave().setInvincible(Invincible);
				restartMenu();
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.DIAMOND_CHESTPLATE, "&6&lDamage Settings","&7Should the NPC be invincible?","",
						"&9If no, the player will be forced out of freecam",
						"&9when the NPC takes damage.",
						"",
						"&6&lCurrent: " + (Invincible ? "&a&lYes" : "&c&lNo") , "",
						"&8Click to edit value")
						.build().make();
			}
		};

		BACK_BUTTON = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				new FreeCamSettings().displayTo(player);
			}

			@Override
			public ItemStack getItem() {
				return BackButton.getItem();
			}
		};
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if(slot == 18 + 3)
			return GRAVITY_BUTTON.getItem();
		if(slot == 18 + 5)
			return INVINCIBLE_BUTTON.getItem();
		if(slot == getSize() - 5)
			return CLOSE_BUTTON.getItem();
		if(slot == getSize() - 1)
			return BACK_BUTTON.getItem();

		return null;
	}

	public static boolean hasGravity() {
		return gravity;
	}

	public static void setGravity(boolean gravity) {
		NPCSettings.gravity = gravity;
	}

	public static boolean isInvincible() {
		return Invincible;
	}

	public static void setInvincible(boolean invincible) {
		Invincible = invincible;
	}
}
