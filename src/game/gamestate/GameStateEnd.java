package game.gamestate;

import java.util.HashMap;
import java.util.Map;

import game.GameState;
import game.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import game.Game;
import game.util.ChatUtil;
import game.util.PlayerUtil;

public class GameStateEnd extends GameStateBase {

	private int taskId;
	public GameStateEnd(Game game, Plugin plugin) {
		super(game, plugin, GameState.END);
		
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			private int i = 11;
			@Override
			public void run() {
				i--;
				
				if (i == 10) {
					Map<TeamColor, Integer> scores = new HashMap<TeamColor, Integer>();
					
					for (TeamColor team: game.getPlayerHandler().getTeams()) {
						scores.put(team, game.getPlayerHandler().getTeamScore(team, true));
					}
					
					int pl = 1;
					int pl_new = pl;
					
					int max_2 = Integer.MAX_VALUE;
					while (pl <= scores.keySet().size()) {
						int max = Integer.MIN_VALUE;
						
						for (TeamColor c: scores.keySet()) {
							if (scores.get(c) > max && scores.get(c) < max_2) {
								max = scores.get(c);
							}
						}
						
						max_2 = max;
						
						for (TeamColor c: scores.keySet()) {
							if (scores.get(c) == max) {
								ChatUtil.sendMessage(ChatUtil.HIGHLIGHT_COLOR + "" + pl + ". " + ChatUtil.COMMAND_COLOR2 + "Place: Team " + c.getChatColor() + c.getName());
								
								pl_new++;
							}
						}
						
						pl = pl_new;
					}
				}
				
				ChatUtil.sendMessage("Game restarts in " + ChatUtil.HIGHLIGHT_COLOR + i + ChatUtil.COMMAND_COLOR + " Seconds");
				
				if (i <= 0) {
					finish();
				}
			}
		}, 0L, 20L);
		
		game.getScoreboard().update();
	}

	@Override
	public void stop() {
		Bukkit.getScheduler().cancelTask(taskId);
	}

	@Override
	public void preparePlayer(Player p, PrepareType type) {
		PlayerUtil.preparePlayer(p);
		p.teleport(game.getLobby());
	}

	@Override
	public void prepareSpectator(Player p, PrepareType type) {
		PlayerUtil.prepareSpectator(p);
		p.teleport(game.getLobby());
	}
}
