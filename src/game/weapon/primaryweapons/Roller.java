package game.weapon.primaryweapons;

import game.Game;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import game.weapon.Weapon;
import game.util.ItemBuilder;
import game.util.ProjectileUtil;
import game.util.Util;

public class Roller extends Weapon {

	public Roller() {
		super(30, 40, "Roller");
	}
	
	@Override
	public void action(Player p, Action a) {
		if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
			for (int i = 0; i < 10; i++)
				ProjectileUtil.fireProjectile(p, 8, 3, EntityType.SNOWBALL, 0.4, 0.1);
		} else {
			int size = 2;
			
			Game game = Util.GAME;
			Location l = p.getLocation();
			
			for (int x = l.getBlockX() - size; x <= l.getBlockX() + size; x++) {
				for (int z = l.getBlockZ() - size; z <= l.getBlockZ() + size; z++) {
					if (Util.setBlockTeam(l.getWorld().getBlockAt(x, l.getBlockY()-1, z), game.getPlayerHandler().getTeam(p))) {
						game.getPlayerHandler().getGamePlayer(p).addScore(1);
					}
				}
			}
		}		
	}

	@Override
	public ItemStack getItem() {
		return new ItemBuilder(Material.IRON_SHOVEL).name("Roller").lore(ChatColor.GRAY + "A slow powerfull Weapon").build();
	}

	
	@Override
	public int getInk(Action a) {
		if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) return 30;
		else return 10;
	}
	
	@Override
	public int getTime(Action a) {
		if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) return 40;
		else return 5; 
	}
}
