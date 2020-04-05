package game.gamestate;

import game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import game.Game;
import game.events.GameStateFinishEvent;

public abstract class GameStateBase {
	
	public Game game;
	public Plugin plugin;
	public GameState gamestate;
	
	public GameStateBase(Game game, Plugin plugin, GameState gamestate) {
		this.game = game;
		this.plugin = plugin;
		this.gamestate = gamestate;
	}
	
	public abstract void stop();
	
	/**
	 * This method is called when a player joins / dies 
	 * or when the game enters this gamestate
	 * @param p the Player that will be prepared
	 */
	public abstract void preparePlayer(Player p, PrepareType type);
	
	/**
	 * This method is called when a spectator joins / dies 
	 * or when the game enters this gamestate
	 * @param p the Spectator that will be prepared
	 */
	public abstract void prepareSpectator(Player p, PrepareType type);
	
	public void finish() {
		stop();
		Bukkit.getPluginManager().callEvent(new GameStateFinishEvent(gamestate));
	}
	
	public enum PrepareType {
		DEATH, JOIN, GAMESTATE_CHANGE
	}
}
