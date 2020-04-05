package game.gamestate;

import game.GameState;
import game.Item;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import game.Game;
import game.util.ChatUtil;
import game.util.PlayerUtil;

public class GameStateLobby extends GameStateBase {

	private int task_id;
	public GameStateLobby(Game game, Plugin plugin) {
		super(game, plugin, GameState.LOBBY);
		
		task_id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			private int i = 0;
			@Override
			public void run() {
				i++;
				if (game.getPlayerHandler().getPlayers().size() > game.getPlayerHandler().getTeamSize()) {
					finish();
				} else if ((i % 30) == 0) {
					ChatUtil.sendMessage("Waiting for more Players... " + ChatUtil.HIGHLIGHT_COLOR + game.getPlayerHandler().getPlayers().size() + " / " + game.getTeamCount()*game.getTeamSize());
				}
			}
		}, 20L, 20L);
	}

	@Override
	public void stop() {
		Bukkit.getScheduler().cancelTask(task_id);
	}

	
	//TODO: Bug: Player gets Teleported when stopping countdown!
	@Override
	public void preparePlayer(Player p, PrepareType type) {
		PlayerUtil.preparePlayer(p);
		p.teleport(game.getLobby());
		p.getInventory().addItem(Item.getTeamSelectionItem());
		p.getInventory().addItem(Item.getWeaponSelectionItem());
	}

	@Override
	public void prepareSpectator(Player p, PrepareType type) {
		PlayerUtil.prepareSpectator(p);
		p.teleport(game.getLobby());
	}
}
