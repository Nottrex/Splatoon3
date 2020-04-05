package game.events;

import game.GameState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStateFinishEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();

	private GameState gamestate;
	public GameStateFinishEvent(GameState gamestate) {
		this.gamestate = gamestate;
	}
	
	public GameState getGameState() {
		return gamestate;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
