package game.weapon.specialweapons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import game.weapon.Weapon;
import game.util.ItemBuilder;

public class BombRush extends Weapon {

	public BombRush() {
		super(100, 100, "BombRush");
	}

	@Override
	public void action(Player p, Action a) {
		
	}

	@Override
	public ItemStack getItem() {
		return new ItemBuilder(Material.GHAST_TEAR).name("BombRush").lore(ChatColor.GRAY + "").build();
	}

}
