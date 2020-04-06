package game.util;

import game.Game;
import game.GameState;
import game.gamestate.GameStateIngame;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

public class ProjectileUtil {
	public static void fireProjectile(Player p, int damage, int area, EntityType type, Vector velocity) {
		fireProjectile(p, damage, area, type, velocity, p.getEyeLocation());
	}

	public static void fireProjectile(Player p, int damage, int area, EntityType type, Vector velocity, Location location) {
		if (Util.GAME.getGameState() != GameState.INGAME || (type != EntityType.SNOWBALL && type != EntityType.EGG && type != EntityType.ENDER_PEARL && type != EntityType.ARROW)) return;
		Projectile projectile = (Projectile) p.getWorld().spawnEntity(location, type);

		projectile.setVelocity(velocity);
		projectile.setCustomNameVisible(false);
		projectile.setCustomName(p.getName() + " " + damage + " " + area);
		projectile.setShooter(p);

		((GameStateIngame) Util.GAME.getState()).addProjectile(projectile);
	}
	
	public static void fireProjectile(Player p, int damage, int area, EntityType type, double strenght, double miss) {
		fireProjectile(p, damage, area, type, p.getEyeLocation().getDirection().multiply(strenght).add(new Vector((Util.RANDOM.nextDouble()*2 -1)*miss, (Util.RANDOM.nextDouble()*2 -1)*miss, (Util.RANDOM.nextDouble()*2 -1)*miss)));
	}
}
