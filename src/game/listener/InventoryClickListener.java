package game.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import game.Game;
import game.GameState;
import game.Inventories;
import game.Item;
import game.TeamColor;
import game.weapon.Weapon;
import game.util.ChatUtil;
import game.util.ItemBuilder;

public class InventoryClickListener implements Listener {
	private Game game;
	
	public InventoryClickListener(Game game) {
		this.game = game;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (game.getGameState() != GameState.COUNTDOWN && game.getGameState() != GameState.LOBBY) return;
		
		if (!(event.getWhoClicked() instanceof Player)) return;
		
		ItemStack item = event.getCurrentItem();
		Inventory inv = event.getInventory();

		Player p = (Player) event.getWhoClicked();
		
		if (item != null && item.getItemMeta() != null) {
			
			if (event.getView().getTitle().equals("TeamSelection")) {
				for (TeamColor c: TeamColor.values()) {
					if (item.getType() == c.getWool()) {
						if (game.getPlayerHandler().setTeam(p, c)) {
							p.closeInventory();
							Inventories.getTeamSelectionInventory(game.getPlayerHandler());
						}
					}
				}
			} else if (event.getView().getTitle().equals("WeaponSelection")) {
				if (item.equals(Item.getWeaponSelectionItemPrimary())) {
					event.getWhoClicked().openInventory(Inventories.getWeaponSelectionInventoryPrimary(game.getPlayerHandler().getGamePlayer(p)));
				} else if (item.equals(Item.getWeaponSelectionItemSecondary())) {
					event.getWhoClicked().openInventory(Inventories.getWeaponSelectionInventorySecondary(game.getPlayerHandler().getGamePlayer(p)));
				} else if (item.equals(Item.getWeaponSelectionItemSpecial())) {
					event.getWhoClicked().openInventory(Inventories.getWeaponSelectionInventorySpecial(game.getPlayerHandler().getGamePlayer(p)));
				}
			} else if (event.getView().getTitle().equals("PrimaryWeaponSelection")) {
				for (Weapon w: Weapon.getPrimaryWeapons()) {
					if (w.getItem().equals(item)) {
						p.closeInventory();
						game.getPlayerHandler().getGamePlayer(p).setPrimaryWeapon(w);
						ChatUtil.sendMessage(p, "You equiped " + ChatUtil.HIGHLIGHT_COLOR + w.getName() + ChatUtil.COMMAND_COLOR + " as your primary weapon");
					}
				}
			} else if (event.getView().getTitle().equals("SecondaryWeaponSelection")) {
				for (Weapon w: Weapon.getSecondaryWeapons()) {
					if (w.getItem().equals(item)) {
						p.closeInventory();
						game.getPlayerHandler().getGamePlayer(p).setSecondaryWeapon(w);
						ChatUtil.sendMessage(p, "You equiped " + ChatUtil.HIGHLIGHT_COLOR + w.getName() + ChatUtil.COMMAND_COLOR + " as your secondary weapon");
					}
				}
			} else if (event.getView().getTitle().equals("SpecialWeaponSelection")) {
				for (Weapon w: Weapon.getSpecialWeapons()) {
					if (w.getItem().equals(item)) {
						p.closeInventory();
						game.getPlayerHandler().getGamePlayer(p).setSpecialWeapon(w);
						ChatUtil.sendMessage(p, "You equiped " + ChatUtil.HIGHLIGHT_COLOR + w.getName() + ChatUtil.COMMAND_COLOR + " as your special weapon");
					}
				}
			}
		}
		
		int a = 0;
		if (s.containsKey(p)) {
			a = s.get(p);
		}

		p.getInventory().setItem(17, null);
		p.getInventory().setItem(35, null);
		
		if (event.getRawSlot() == pos[a]) {			
			a++;
			if (a==pos.length) {
				ChatUtil.sendMessage(p.getDisplayName() + ChatUtil.HIGHLIGHT_COLOR + " found an amazing secret");
				a = 0;
			} else if (a == 8) {
				p.getInventory().setItem(17, B);
			} else if (a == 9) {
				p.getInventory().setItem(35, A);
			}
		} else {
			a = 0;
		}
		
		s.put(p, a);
		
	}
	
	private Map<Player, Integer> s = new HashMap<Player, Integer>();
	
	private static final int[] pos = new int[] {16, 16, 34, 34, 24, 26, 24, 26, 17, 35};
	
	private static ItemStack A = new ItemBuilder(Material.OAK_SIGN).name("A").build();
	private static ItemStack B = new ItemBuilder(Material.OAK_SIGN).name("B").build();
}
