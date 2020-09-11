package me.wilkins.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

public class MainSettings extends Menu {

	private static final int CommandSettingsSlot = 18 + 3;
	private static final ItemStack CommandSettingsItem = ItemCreator.of(
			CompMaterial.COMMAND_BLOCK, "&6&lCommand &eSettings",
			"&eClick &7to view all command settings!")
			.build().make();
	private static final int FreeCamSettingsSlot = 18 + 5;
	private static final ItemStack FreeCamSettingsItem = ItemCreator.of(
			CompMaterial.ELYTRA, "&6&lFreeCam &eSettings",
			"&eClick &7to view all free cam settings!")
			.build().make();
	private final Button COMMAND_SETTINGS;
	private final Button FREE_CAM_SETTINGS;

	private final CloseButton CLOSE_BUTTON = new CloseButton();

	public MainSettings(){
		setTitle("Main Settings");
		setSize(9 * 5);

		COMMAND_SETTINGS = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				new CommandSettings().displayTo(player);
			}

			@Override
			public ItemStack getItem() {
				return CommandSettingsItem;
			}
		};

		FREE_CAM_SETTINGS = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				new FreeCamSettings().displayTo(player);
			}

			@Override
			public ItemStack getItem() {
				return FreeCamSettingsItem;
			}
		};
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if(slot == CommandSettingsSlot)
			return COMMAND_SETTINGS.getItem();
		if(slot == FreeCamSettingsSlot)
			return FREE_CAM_SETTINGS.getItem();
		if(slot == getSize() - 5)
			return CLOSE_BUTTON.getItem();

		return null;
	}
}
