package game.gamestate;

import game.GameState;
import game.Item;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import game.Game;
import game.util.ChatUtil;
import game.util.PlayerUtil;

public class GameStateCountdown extends GameStateBase {

	private int task_id;
	public GameStateCountdown(Game game, Plugin plugin) {
		super(game, plugin, GameState.COUNTDOWN);
	
		task_id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			int i = 31;
			@Override
			public void run() {
				i--;
				for (Player p: game.getPlayerHandler().getPlayers()) {
					p.setLevel(i);
				}
				
				for (Player p: game.getPlayerHandler().getSpectators()) {
					p.setLevel(i);
				}
				
				if (game.getPlayerHandler().getPlayers().size() <= game.getTeamSize()) {
					stop();
					game.setGameState(GameState.LOBBY);
					return;
				} else if (i%10 == 0 || i < 6) {
					ChatUtil.sendMessage("Game starts in " + ChatUtil.HIGHLIGHT_COLOR + i + " seconds");
				}else if(i == 15) ChatUtil.sendMessage("Splatoon by: " +ChatUtil.HIGHLIGHT_COLOR + "PhoenixofForce and Nottrex");
				
				if (i == 0) {
					finish();
				}
			}
		}, 0L, 20L);
	}

	@Override
	public void stop() {
		Bukkit.getScheduler().cancelTask(task_id);
	}

	@Override
	public void preparePlayer(Player p, PrepareType type) {
		if (type != PrepareType.GAMESTATE_CHANGE) {
			PlayerUtil.preparePlayer(p);
			p.getInventory().addItem(Item.getTeamSelectionItem());
			p.getInventory().addItem(Item.getWeaponSelectionItem());
			p.teleport(game.getLobby());
		}
	}

	@Override
	public void prepareSpectator(Player p, PrepareType type) {
		if (type != PrepareType.GAMESTATE_CHANGE) {
			PlayerUtil.prepareSpectator(p);
			p.teleport(game.getLobby());
		}
	}
}
