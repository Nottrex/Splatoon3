package game.listener;

import java.util.logging.Level;

import game.Game;
import game.GameState;
import game.Inventories;
import game.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import game.weapon.Weapon;
import game.weapon.WeaponType;
import game.util.Util;

public class PlayerInteractListener implements Listener {
	
	private Game game;
	public PlayerInteractListener(Game game) {
		this.game = game;
	}
	
	@EventHandler(ignoreCancelled = false)
	public void onPlayerInteract(PlayerInteractEvent event) {
		event.setCancelled(true);
		
		Player p = event.getPlayer();
		ItemStack item = event.getItem();
		
		if ((game.getGameState() == GameState.LOBBY || game.getGameState() == GameState.COUNTDOWN) && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if (item != null) {
				if (item.equals(Item.getTeamSelectionItem())) {
					event.getPlayer().openInventory(Inventories.getTeamSelectionInventory(game.getPlayerHandler()));
					Inventories.getTeamSelectionInventory(game.getPlayerHandler());
				} else if (item.equals(Item.getWeaponSelectionItem())) {
					event.getPlayer().openInventory(Inventories.getWeaponSelectionInventory());
				}
			}
		}
		
		if (item != null && game.getGameState() == GameState.INGAME && game.getPlayerHandler().isPlayer(p)) {
			for (Weapon w: Weapon.getPrimaryWeapons()) {
				if (w.getItem().equals(item)) {
					game.getPlayerHandler().getGamePlayer(p).action(WeaponType.PRIMARY, event.getAction());
				}
			}
			
			for (Weapon w: Weapon.getSecondaryWeapons()) {
				if (w.getItem().equals(item)) {
					game.getPlayerHandler().getGamePlayer(p).action(WeaponType.SECONDARY, event.getAction());
				}
			}
			
			for (Weapon w: Weapon.getSpecialWeapons()) {
				if (w.getItem().equals(item)) {
					game.getPlayerHandler().getGamePlayer(p).action(WeaponType.SPECIAL, event.getAction());
				}
			}
		}
	}
}
