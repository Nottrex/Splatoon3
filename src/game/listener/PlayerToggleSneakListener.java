package game.listener;

import game.GamePlayer;
import game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import game.Game;

public class PlayerToggleSneakListener implements Listener {
	
	private Game game;
	public PlayerToggleSneakListener(Game game) {
		this.game = game;
	}
	
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		if (game.getGameState() != GameState.INGAME) return;
		
		Player p = event.getPlayer();
		
		if (game.getPlayerHandler().isPlayer(p)) {
			GamePlayer gp = game.getPlayerHandler().getGamePlayer(p);
			
			gp.setSneaking(event.isSneaking());
		}
	}
}
