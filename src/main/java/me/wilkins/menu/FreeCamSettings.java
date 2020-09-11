package me.wilkins.menu;

import me.wilkins.settings.SettingsSave;
import me.wilkins.util.SkullUtil;
import me.wilkins.util.TimeUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.concurrent.TimeUnit;

public class FreeCamSettings extends Menu {

	private static int timeLimitValue = 1;
	private static TimeUnit timeLimitUnit = TimeUnit.MINUTES;

	private static int maxDistance = 50;

	private final Button TIME_LIMIT_BUTTON;
	private final Button MAX_DISTANCE_BUTTON;
	private final Button NPC_SETTINGS_BUTTON;
	private final Button CLOSE_BUTTON = new CloseButton();
	private final Button GO_BACK_BUTTON;

	private int mode = 0;

	public FreeCamSettings(){
		setTitle("FreeCam Settings");
		setSize(9 * 5);

		TIME_LIMIT_BUTTON = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				if(click == ClickType.LEFT){
					if(mode == 0)
						timeLimitValue++;
					else
						timeLimitUnit = TimeUtil.getNextTimeUnit(timeLimitUnit);

				}else if(click == ClickType.RIGHT){
					if(mode == 0) {
						if(timeLimitValue > 0)
							timeLimitValue--;
					}else
						timeLimitUnit = TimeUtil.getLastTimeUnit(timeLimitUnit);

				}else if(click == ClickType.MIDDLE){
					if(mode == 0)
						mode = 1;
					else if(mode == 1)
						mode = 0;
				}

				new SettingsSave().setTimeLimitValue(timeLimitValue);
				new SettingsSave().setTimeLimitUnit(timeLimitUnit);

				restartMenu();
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.CLOCK, "&6&lTime Limit Settings",
						"&7Set a freecam time limit",
						"",
						"&6&lCurrent: " + (timeLimitValue <= 0 ? "&f&lUnlimited" : (mode == 0 ? "&f&l" + timeLimitValue : "&f" + timeLimitValue) + " "
								+ (mode == 1 ? "&f&l" + timeLimitUnit.toString().toLowerCase() : "&f" + timeLimitUnit.toString().toLowerCase())),
						"",
						"&8Left click to increase value",
						"&8Right click to decrease value",
						"&8Middle click to switch between time value and time unit")
						.build().make();
			}
		};

		MAX_DISTANCE_BUTTON = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {

				if(click == ClickType.LEFT){
					maxDistance += 5;
				}else if(click == ClickType.RIGHT){
					if(maxDistance >= 10)
						maxDistance -= 5;
					else
						maxDistance = 0;
				}

				new SettingsSave().setMaxDistance(maxDistance);

				restartMenu();
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.FEATHER, "&6Max Distance Settings",
						"&7Set how far the player can freecam",
						"",
						"&6&lCurrent: &f" + (maxDistance == 0 ? "&f&lUnlimited" : maxDistance + " blocks"),
						"",
						"&8Left click to increase value",
						"&8Right click to decrease value")
						.build().make();
			}
		};

		NPC_SETTINGS_BUTTON = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				new NPCSettings().displayTo(player);
			}

			@Override
			public ItemStack getItem() {
				return SkullUtil.getPlayerHead(getViewer().getName(), "&6&lNPC Settings",
						"&eClick &7to view all NPC settings");
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

	@Override
	public ItemStack getItemAt(int slot) {
		if(slot == 9 + 3)
			return TIME_LIMIT_BUTTON.getItem();
		if(slot == 9 + 5)
			return MAX_DISTANCE_BUTTON.getItem();
		if(slot == 18 + 4)
			return NPC_SETTINGS_BUTTON.getItem();
		if(slot == getSize() - 5)
			return CLOSE_BUTTON.getItem();
		if(slot == getSize() - 1)
			return GO_BACK_BUTTON.getItem();

		return null;
	}

	public static int getTimeLimitValue() {
		return timeLimitValue;
	}

	public static TimeUnit getTimeLimitUnit() {
		return timeLimitUnit;
	}

	public static int getMaxDistance() {
		return maxDistance;
	}

	public static void setTimeLimitValue(int timeLimitValue) {
		FreeCamSettings.timeLimitValue = timeLimitValue;
	}

	public static void setTimeLimitUnit(TimeUnit timeLimitUnit) {
		FreeCamSettings.timeLimitUnit = timeLimitUnit;
	}

	public static void setMaxDistance(int maxDistance) {
		FreeCamSettings.maxDistance = maxDistance;
	}
}
