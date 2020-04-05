package game.weapon.specialweapons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import game.weapon.Weapon;
import game.util.ItemBuilder;

public class Inkzooka extends Weapon {

	public Inkzooka() {
		super(100, 100, "Inkzooka");
	}

	@Override
	public void action(Player p, Action a) {
		
	}

	@Override
	public ItemStack getItem() {
		return new ItemBuilder(Material.BLAZE_ROD).name("Inkzooka").lore(ChatColor.GRAY + "").build();
	}

}
