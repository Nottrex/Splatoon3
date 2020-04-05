package game.weapon.secondaryweapons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import game.weapon.Weapon;
import game.util.ItemBuilder;
import game.util.ProjectileUtil;

public class BurstBomb extends Weapon {

	public BurstBomb() {
		super(70, 20, "BurstBomb");
	}

	@Override
	public void action(Player p, Action a) {
		ProjectileUtil.fireProjectile(p, 20, 4, EntityType.EGG, 2, 0);
	}

	@Override
	public ItemStack getItem() {
		return new ItemBuilder(Material.FIREWORK_CHARGE).name("BurstBomb").lore(ChatColor.GRAY + "").build();
	}
	
}
