package me.wilkins.menu;

import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

public class BackButton {

	public static ItemStack getItem(){
		return ItemCreator.of(CompMaterial.OAK_DOOR, "&c&lGo Back").build().make();
	}
}
