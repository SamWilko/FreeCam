package me.wilkins.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

public class CloseButton extends Button {

	@Override
	public void onClickedInMenu(Player player, Menu menu, ClickType click) {
		player.closeInventory();
	}

	@Override
	public ItemStack getItem() {
		return ItemCreator.of(CompMaterial.BARRIER, "&c&lExit Menu", "&eClick &7to close this menu")
				.build().make();
	}
}
