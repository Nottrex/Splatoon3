package game.listener;

import game.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import game.util.ChatUtil;


public class PlayerChatListener implements Listener {
	private Game game;
	
	public PlayerChatListener(Game game) {
		this.game = game;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (game.getPlayerHandler().isPlayer(event.getPlayer())) {
			event.setFormat(ChatUtil.formatChat(event.getPlayer(), event.getMessage()));		
		} else {
			event.setCancelled(true);
		}
	}
}
