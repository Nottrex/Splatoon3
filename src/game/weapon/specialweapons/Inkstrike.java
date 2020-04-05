package game.weapon.specialweapons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import game.weapon.Weapon;
import game.util.ItemBuilder;

public class Inkstrike extends Weapon {

	public Inkstrike() {
		super(100, 100, "Inkstrike");
	}

	@Override
	public void action(Player p, Action a) {
		
	}

	@Override
	public ItemStack getItem() {
		return new ItemBuilder(Material.SLIME_BALL).name("Inkstrike").lore(ChatColor.GRAY + "").build();
	}

}
