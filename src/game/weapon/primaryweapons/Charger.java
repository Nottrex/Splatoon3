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

public class Charger extends Weapon {

	public Charger() {
		super(20, 40, "Charger");
	}

	@Override
	public void action(Player p, Action a) {
		for (int i = 0; i < 10; i++) {
			ProjectileUtil.fireProjectile(p, 12, 3, EntityType.SNOWBALL, i * 0.2, 0.05/(i+1));
		}
	}

	@Override
	public ItemStack getItem() {
		return new ItemBuilder(Material.BOW).name("Charger").lore(ChatColor.GRAY + "A slow powerfull Weapon").build();
	}

}
