package game.util;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

public class ProjectileUtil {
	public static void fireProjectile(Player p, int damage, int area, EntityType type, Vector velocity) {
		if (type != EntityType.SNOWBALL && type != EntityType.EGG && type != EntityType.ENDER_PEARL && type != EntityType.ARROW) return;
		Projectile projectile = (Projectile) p.getWorld().spawnEntity(p.getEyeLocation(), type);
		
		projectile.setVelocity(velocity);
		projectile.setCustomNameVisible(false);
		projectile.setCustomName(p.getName() + " " + damage + " " + area);
		projectile.setShooter(p);
	}
	
	public static void fireProjectile(Player p, int damage, int area, EntityType type, double strenght, double miss) {
		fireProjectile(p, damage, area, type, p.getEyeLocation().getDirection().multiply(strenght).add(new Vector((Util.RANDOM.nextDouble()*2 -1)*miss, (Util.RANDOM.nextDouble()*2 -1)*miss, (Util.RANDOM.nextDouble()*2 -1)*miss)));
	}
}
