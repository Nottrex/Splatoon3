package game;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import game.util.ChatUtil;
import game.util.ItemBuilder;
import net.md_5.bungee.api.ChatColor;

public class Item {

	public static HashMap<String, ItemStack> items = new HashMap<>();
	static{
		items.put("selector", getTeamSelectionItem());
	}
	
	public static ItemStack getWeaponSelectionItemPrimary() {
		return new ItemBuilder(Material.GUNPOWDER).name("Primary Weapon").build();
	}
	
	public static ItemStack getWeaponSelectionItemSecondary() {
		return new ItemBuilder(Material.REDSTONE).name("Secondary Weapon").build();
	}
	
	public static ItemStack getWeaponSelectionItemSpecial() {
		return new ItemBuilder(Material.GLOWSTONE_DUST).name("Special Weapon").build();
	}
	
	public static ItemStack getWeaponSelectionItem() {
		return new ItemBuilder(Material.NETHER_STAR).name("Weapon-Selector").lore(ChatColor.GRAY + "Select your Weapons").build();
	}
	
	public static ItemStack getTeamSelectionItem() {
		return new ItemBuilder(Material.RED_BED).name("Team-Selector").lore(ChatColor.GRAY + "Select your Team").build();
	}
	
	public static ItemStack getTeamItem(TeamColor team, List<Player> players, int max_players) {
		ItemBuilder ib = new ItemBuilder(new ItemStack(Material.WHITE_WOOL, 1, team.getDyeColor().getDyeData()));
		ib.name(team.getChatColor() + team.getName());
		
		ib.lore(ChatUtil.HIGHLIGHT_COLOR + "" + players.size() + " / " + max_players);
		
		for (Player p: players) {
			ib.lore(team.getChatColor() + p.getDisplayName());
		}
				
		return ib.build();
	}
}
