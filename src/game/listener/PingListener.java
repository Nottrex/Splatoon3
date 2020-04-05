package game.listener;

import game.Game;
import game.GameState;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener implements Listener{

	private Game game;
	
	public PingListener(Game game){
		this.game = game;
	}
	
	@EventHandler
	public void onPing(ServerListPingEvent event){
		if(game.getGameState() == GameState.LOBBY || game.getGameState() == GameState.COUNTDOWN){
			event.setMaxPlayers(game.getTeamSize() * game.getTeamCount());
			event.setMotd(ChatColor.GREEN + "LOBBY PHASE");
		}else if(game.getGameState() == GameState.INGAME){
			event.setMotd(ChatColor.GOLD + "INGAME");
		}else if(game.getGameState() == GameState.END){
			event.setMotd(ChatColor.RED + "END");
		}	
	}
}