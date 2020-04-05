package game.weapon.primaryweapons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import game.weapon.Weapon;
import game.util.ItemBuilder;
import game.util.ProjectileUtil;

public class Shooter extends Weapon {
	
	public Shooter() {
		super(4, 3, "Shooter");
	}

	@Override
	public void action(Player p, Action a) {
		for (int i = 0; i < 3; i++)	ProjectileUtil.fireProjectile(p, 6, 2, EntityType.SNOWBALL, 1.5, 0.15);
	}

	@Override
	public ItemStack getItem() {
		return new ItemBuilder(Material.IRON_HOE).name("Kleckser").lore(ChatColor.GRAY + "A fast shooting Weapon").build();
	}
}
