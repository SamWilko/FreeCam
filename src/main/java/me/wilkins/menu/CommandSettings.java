package me.wilkins.menu;

import me.wilkins.prompt.AliasPrompt;
import me.wilkins.settings.SettingsSave;
import me.wilkins.util.TimeUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandSettings extends Menu {

	public static boolean needsRestart = false;

	private static String aliases = "roam";
	private static int cooldownValue = 30;
	private static TimeUnit timeUnit = TimeUnit.SECONDS;

	private final Button COOLDOWN_BUTTON;
	private final Button ALIAS_BUTTON;
	private final Button CLOSE_BUTTON = new CloseButton();
	private final Button GO_BACK_BUTTON;

	private int mode = 0;

	public CommandSettings(){
		setTitle("Command Settings");
		setSize(9 * 5);

		ALIAS_BUTTON = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				new AliasPrompt().show(player);
			}

			@Override
			public ItemStack getItem() {
				List<String> lore = new ArrayList<>();

				lore.add("&eClick &7to edit the alias of the command");
				lore.add("");
				lore.add("&6&lCurrent aliases:");
				for(String s : aliases.split("/")){
					lore.add("&f/" + s);
				}
				if(needsRestart){
					lore.add("");
					lore.add("&cRequires server restart to update!");
				}

				return ItemCreator.of(CompMaterial.NAME_TAG, "&6&lAlias Settings").lores(lore).build().make();
			}
		};

		COOLDOWN_BUTTON = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				if(click == ClickType.LEFT){
					if(mode == 0)
						cooldownValue++;
					else
						timeUnit = TimeUtil.getNextTimeUnit(timeUnit);

				}else if(click == ClickType.RIGHT){
					if(mode == 0) {
						if(cooldownValue > 0)
							cooldownValue--;
					}else
						timeUnit = TimeUtil.getLastTimeUnit(timeUnit);

				}else if(click == ClickType.MIDDLE){

					if(mode == 0)
						mode = 1;
					else
						mode = 0;
				}

				new SettingsSave().setCooldownValue(cooldownValue);
				new SettingsSave().setCooldownTimeUnit(timeUnit);

				restartMenu();
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.CLOCK, "&6&lCooldown Settings",
						"&7Edit the command cooldown", "",
						"&6&lCurrent: " + (cooldownValue <= 0 ? "&f&lNone" : (mode == 0 ? "&f&l" + cooldownValue : "&f" + cooldownValue) + " "
								+ (mode == 1 ? "&f&l" + timeUnit.toString().toLowerCase() : "&f" + timeUnit.toString().toLowerCase())),
						"",
						"&8Left click to increase value",
						"&8Right click to decrease value",
						"&8Middle click to switch between cooldown time and time unit")
						.build().make();
			}
		};

		GO_BACK_BUTTON = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				new MainSettings().displayTo(player);
			}

			@Override
			public ItemStack getItem() {
				return BackButton.getItem();
			}
		};
	}

	public static String getAliases() {
		return aliases;
	}

	public static void setAliases(String aliases) {
		CommandSettings.aliases = aliases;
	}

	public static int getCooldownValue() {
		return cooldownValue;
	}

	public static void setCooldownValue(int value) {
		CommandSettings.cooldownValue = value;
	}

	public static TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public static void setTimeUnit(TimeUnit timeUnit) {
		CommandSettings.timeUnit = timeUnit;
	}

	@Override
	public ItemStack getItemAt(int slot) {
		final int aliasSlot = 18 + 3;
		final int cooldownSlot = 18 + 5;

		if(slot == aliasSlot)
			return ALIAS_BUTTON.getItem();
		if(slot == cooldownSlot)
			return COOLDOWN_BUTTON.getItem();
		if(slot == getSize() - 5)
			return CLOSE_BUTTON.getItem();
		if(slot == getSize() - 1)
			return GO_BACK_BUTTON.getItem();

		return null;
	}
}
