package game.listener;

import game.Game;
import game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityDamageListener implements Listener {
	private Game game;
	
	public EntityDamageListener(Game game) {
		this.game = game;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (game.getGameState() != GameState.UNSTARTET && game.getGameState() != GameState.INGAME) {
			event.setCancelled(true);
		}
		
		if (game.getGameState() == GameState.INGAME && event.getCause() != DamageCause.ENTITY_ATTACK) {
			event.setCancelled(true);
		}
	}
}
