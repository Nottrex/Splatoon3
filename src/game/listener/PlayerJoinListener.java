package game.listener;

import game.Game;
import game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import game.util.ChatUtil;
import game.util.PlayerUtil;

public class PlayerJoinListener implements Listener{

	private Game game;
	public PlayerJoinListener(Game game) {
		this.game = game;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (game.getGameState() == GameState.UNSTARTET) return;
		
		event.setJoinMessage("");
		
		Player p = event.getPlayer();
		
		game.getPlayerHandler().addPlayer(p);
		PlayerUtil.setTabTitle(p, ChatUtil.PLUGIN_COLOR + "SPLATOON", ChatUtil.HIGHLIGHT_COLOR + "by PhoenixofForce and Nottrex");
	}	
}