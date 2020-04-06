package game.listener;

import game.Game;
import game.GameState;
import game.TeamColor;
import game.gamestate.GameStateIngame;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import game.util.Util;


public class ProjectileHitListener implements Listener {
	
	private Game game;

	public ProjectileHitListener(Game game) {
		this.game = game;
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if (game.getGameState() != GameState.INGAME) return;
		
		if (event.getEntity() instanceof Projectile) {
			Projectile projectile = (Projectile) event.getEntity();
			String name = projectile.getCustomName();

			((GameStateIngame) game.getState()).removeProjectile(projectile);
			
			if (projectile.getShooter() instanceof Player && name.split(" ").length == 3) {
				
				Location l = projectile.getLocation().clone();
				Player p = (Player) projectile.getShooter();
				TeamColor c = game.getPlayerHandler().getTeam(p);
				String[] parts = name.split(" ");

				int damage = Integer.valueOf(parts[1]);
				int area = Integer.valueOf(parts[2]);

				if(projectile instanceof Egg) {
					projectile.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, projectile.getLocation(), 1);
				}

				if (projectile instanceof Snowball || projectile instanceof Egg) {
					for (int x = l.getBlockX() - area; x <= l.getBlockX() + area; x++) {
						for (int y = l.getBlockY() - area; y <= l.getBlockY() + area; y++) {
							for (int z = l.getBlockZ() - area; z <= l.getBlockZ() + area; z++) {
								if ((Math.abs(l.getBlockX()-x) + Math.abs(l.getBlockY()-y) + Math.abs(l.getBlockZ()-z)) <= area) {
									if (Util.setBlockTeam(l.getWorld().getBlockAt(x, y, z), c)) {
										game.getPlayerHandler().getGamePlayer(p).addScore(1);
									}
								}
							}
						}
					}
				}
				
				for (Entity e: projectile.getNearbyEntities(area, area, area)) {
					if (e instanceof Player) {
						Player p2 = (Player) e;
						if (game.getPlayerHandler().isPlayer(p2) && game.getPlayerHandler().getTeam(p2) != game.getPlayerHandler().getTeam(p)) {
							p2.damage(damage * Math.max(0, area - e.getLocation().distance(p2.getLocation())) / area, p);
						}
					}
				}
			}			
		}
	}
}
