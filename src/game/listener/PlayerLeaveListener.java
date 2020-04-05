package game.listener;

import game.Game;
import game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener{

	private Game game;
	public PlayerLeaveListener(Game game) {
		this.game = game;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (game.getGameState() == GameState.UNSTARTET) return;
		
		event.setQuitMessage("");
		
		Player p = event.getPlayer();
		
		game.getPlayerHandler().removePlayer(p);
	}	
}