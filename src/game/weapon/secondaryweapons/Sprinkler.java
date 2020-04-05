package game.weapon.secondaryweapons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import game.weapon.Weapon;
import game.util.ItemBuilder;

public class Sprinkler extends Weapon {

	public Sprinkler() {
		super(50, 20, "Sprinkler");
	}

	@Override
	public void action(Player p, Action a) {
		
	}

	@Override
	public ItemStack getItem() {
		return new ItemBuilder(Material.OAK_FENCE).name("Sprinkler").lore(ChatColor.GRAY + "").build();
	}

}
