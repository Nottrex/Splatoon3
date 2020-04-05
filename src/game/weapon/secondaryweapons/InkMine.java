package game.weapon.secondaryweapons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import game.weapon.Weapon;
import game.util.ItemBuilder;

public class InkMine extends Weapon {

	public InkMine() {
		super(50, 20, "InkMine");
	}

	@Override
	public void action(Player p, Action a) {
		
	}

	@Override
	public ItemStack getItem() {
		return new ItemBuilder(Material.WHITE_CARPET).name("InkMine").lore(ChatColor.GRAY + "").build();
	}

}
