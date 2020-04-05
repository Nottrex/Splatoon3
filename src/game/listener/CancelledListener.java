package game.listener;

import game.Game;
import game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class CancelledListener implements Listener{

	private Game game;
	public CancelledListener(Game game) {
		this.game = game;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (game.getGameState() != GameState.UNSTARTET) event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (game.getGameState() != GameState.UNSTARTET) event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		if (game.getGameState() != GameState.UNSTARTET) event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (game.getGameState() != GameState.UNSTARTET) event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerFoodLevelChange(FoodLevelChangeEvent event) {
		if (game.getGameState() != GameState.UNSTARTET) event.setCancelled(true);
	}
	
	@EventHandler
	public void onInventoryInteract(InventoryClickEvent event) {
		if (game.getGameState() != GameState.UNSTARTET) event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (game.getGameState() != GameState.UNSTARTET) event.setCancelled(true);
	}
	
	@EventHandler
	public void onItemSwitch(PlayerItemHeldEvent event){
		if(game.getGameState() == GameState.INGAME && game.getPlayerHandler().isPlayer(event.getPlayer())){
			if(game.getPlayerHandler().getGamePlayer(event.getPlayer()).isSneaking()) event.setCancelled(true);
		}
	}
}