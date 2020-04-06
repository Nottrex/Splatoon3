package game.listener;

import game.Game;
import game.GameState;
import game.PlayerHandler;
import game.TeamColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.MaterialData;

import game.util.Util;

public class PlayerMoveListener implements Listener {
	private Game game;
	
	public PlayerMoveListener(Game game) {
		this.game = game;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
			
		PlayerHandler pl = game.getPlayerHandler();
		
		if (game.getGameState() != GameState.UNSTARTET && pl.isPlayer(p)) {
			if (event.getTo().getBlockY() < -20 && !event.getPlayer().isDead()) {
				p.setHealth(0);
			}
		}
		
		if(game.getGameState() == GameState.INGAME && pl.isPlayer(event.getPlayer())){
			TeamColor c = pl.getTeam(p);
			
			pl.getGamePlayer(p).setOnTeamColor(c.isBlockTeam(p.getLocation().subtract(0, 1, 0).getBlock()));
									
			if (pl.getGamePlayer(p).isSneaking() && pl.getGamePlayer(p).isOnTeamColor()) {
				p.getWorld().spawnParticle(Particle.BLOCK_CRACK, p.getLocation(), 1, p.getLocation().subtract(0, 1, 0).getBlock().getBlockData());
			}
			
			if(event.getPlayer().getLocation().getBlock().getType() == Material.WATER){
				event.getPlayer().setHealth(0);
			}
		}
	}
}
