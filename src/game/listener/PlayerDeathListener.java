package game.listener;

import game.Game;
import game.GameState;
import game.util.ProjectileUtil;
import game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import game.util.ChatUtil;
import org.bukkit.util.Vector;

public class PlayerDeathListener implements Listener {
	
	private Game game;
	private Plugin plugin;
	public PlayerDeathListener(Game game, Plugin plugin) {
		this.game = game;
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (game.getGameState() == GameState.UNSTARTET) return;
		Player p = event.getEntity();
		
		if (game.getPlayerHandler().isSpectator(p)) return;
		
		event.setDeathMessage("");
		event.setDroppedExp(0);
		event.setKeepInventory(true);
		
		Player killer = p.getKiller();
		if (killer == null) {
			ChatUtil.sendMessage(p.getDisplayName() + ChatUtil.COMMAND_COLOR2 + " died");
		} else {
			ChatUtil.sendMessage(p.getDisplayName() + ChatUtil.COMMAND_COLOR2 + " was killed by " + killer.getDisplayName());
			game.getPlayerHandler().getGamePlayer(killer).addScore(200);

			for (int i = 0; i < 15; i++) {
				ProjectileUtil.fireProjectile(killer, 1, 1, EntityType.SNOWBALL, new Vector((Util.RANDOM.nextFloat()*2-1)/4, (Util.RANDOM.nextFloat()*2-0.5f)/3, (Util.RANDOM.nextFloat()*2-1)/4), p.getEyeLocation());
			}

			p.getWorld().playSound(p.getEyeLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
		}
		
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				p.spigot().respawn();
			}
		}, 5L);
	}
}
