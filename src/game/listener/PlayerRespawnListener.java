package game.listener;

import game.Game;
import game.GameState;
import game.PlayerHandler;
import game.gamestate.GameStateBase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener{

	Game game;
	
	public PlayerRespawnListener(Game game){
		this.game = game;
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		if (game.getGameState() == GameState.UNSTARTET) return;

		Player p = event.getPlayer();
		if (game.getPlayerHandler().isPlayer(p)) {
			game.getState().preparePlayer(p, GameStateBase.PrepareType.DEATH);
		} else {
			game.getState().prepareSpectator(p, GameStateBase.PrepareType.DEATH);
		}
		PlayerHandler pl = game.getPlayerHandler();
		if(game.getGameState() != GameState.INGAME) event.setRespawnLocation(game.getLobby());
		else event.setRespawnLocation(pl.getTeamSpawn(pl.getTeam(p)));
	}
}
