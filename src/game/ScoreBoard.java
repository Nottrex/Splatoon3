package game;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import game.gamestate.GameStateIngame;
import game.util.ChatUtil;

public class ScoreBoard {
	private Map<Player, Scoreboard> scoreboards;
	private Game game;
	private PlayerHandler playerhandler;
	
	public ScoreBoard(Game game){
		this.game = game;
		this.playerhandler = game.getPlayerHandler();
		scoreboards = new HashMap<Player, Scoreboard>();
	}
	
	public void initPlayer(Player p) {
		Scoreboard sb = (Scoreboard) Bukkit.getScoreboardManager().getNewScoreboard();
		
		for (TeamColor c: playerhandler.getTeams()) {
			Team t = sb.registerNewTeam(c.getName());
			t.setCanSeeFriendlyInvisibles(true);
			t.setPrefix(c.getChatColor().toString());
			t.setAllowFriendlyFire(false);
		}
		
		for (Player p2: scoreboards.keySet()) {
			Team team = scoreboards.get(p2).getEntryTeam(p2.getName());
			if (team != null) sb.getTeam(team.getName()).addEntry(p2.getName());
		}
		
		Objective sidebar = sb.registerNewObjective("sidebar", "dummy");
		sidebar.setDisplayName(ChatUtil.PLUGIN_COLOR + "Splatoon");
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		scoreboards.put(p, sb);
		p.setScoreboard(sb);
		updatePlayer(p);
	}
	
	public void setTeam(Player p, TeamColor team) {
		for (Player p2: scoreboards.keySet()) {
			scoreboards.get(p2).getTeam(team.getName()).addEntry(p.getName());
		}
	}
	
	public void removePlayer(Player p) {
		for (Player p2: scoreboards.keySet()) {
			Scoreboard sb = scoreboards.get(p2);

			for (Team t: sb.getTeams()) {
				t.removeEntry(p.getName());
			}
		}
		scoreboards.remove(p);
	}
	
	public void updatePlayers() {
		for (Player p: playerhandler.getPlayers()) {
			updatePlayer(p);
		}
	}
	
	public void updatePlayer(Player p) {
		
		for (TeamColor c: playerhandler.getTeams()) {
			playerhandler.getTeamScore(c, false);
		}
		
		Scoreboard sb = scoreboards.get(p);
		Objective sidebar = sb.getObjective("sidebar");
		while (sidebar != null) {
			sidebar.unregister();
			sidebar = sb.getObjective("sidebar");
		}
		sidebar = sb.registerNewObjective("sidebar", "dummy");

		int i = 0;
		switch (game.getGameState()) {
		case LOBBY:
		case COUNTDOWN:
			TeamColor team = playerhandler.getTeam(p);
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------" + ChatColor.RESET + ChatColor.RESET + ChatColor.RESET).setScore(i++);
			sidebar.getScore(ChatUtil.SCOREBOARD_COLOR + "Team: " + (team == null ? ChatColor.WHITE + "None" : team.getChatColor() + team.getName())).setScore(i++);
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------" + ChatColor.RESET + ChatColor.RESET).setScore(i++);
			for (TeamColor c: playerhandler.getTeams()) {
				sidebar.getScore(c.getChatColor() + c.getName() + ": " + ChatUtil.HIGHLIGHT_COLOR + playerhandler.getPlayersInTeam(c).size() + " / " + playerhandler.getTeamSize()).setScore(i++);
			}
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------" + ChatColor.RESET).setScore(i++);
			sidebar.getScore(ChatUtil.SCOREBOARD_COLOR + "Players: " + ChatUtil.HIGHLIGHT_COLOR + playerhandler.getPlayers().size() + " / " + game.getTeamCount()*game.getTeamSize()).setScore(i++);
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------").setScore(i++);
			
			break;
		case INGAME:
			if (!(game.getState() instanceof GameStateIngame)) return;
			TeamColor team1 = playerhandler.getTeam(p);
			GameStateIngame gamestate = (GameStateIngame) game.getState();
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------" + ChatColor.RESET + ChatColor.RESET + ChatColor.RESET + ChatColor.RESET).setScore(i++);
			sidebar.getScore(ChatUtil.SCOREBOARD_COLOR + "Score: " + ChatUtil.HIGHLIGHT_COLOR + playerhandler.getGamePlayer(p).getScore()).setScore(i++);
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------" + ChatColor.RESET + ChatColor.RESET + ChatColor.RESET).setScore(i++);
			sidebar.getScore(ChatUtil.SCOREBOARD_COLOR + "Team: " + (team1 == null ? ChatColor.WHITE + "None" : team1.getChatColor() + team1.getName())).setScore(i++);
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------" + ChatColor.RESET + ChatColor.RESET).setScore(i++);
			for (TeamColor c: playerhandler.getTeams()) {
				sidebar.getScore(c.getChatColor() + c.getName() + ": " + ChatUtil.HIGHLIGHT_COLOR + playerhandler.getTeamScore(c, false)).setScore(i++);
			}
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------" + ChatColor.RESET).setScore(i++);
			sidebar.getScore(ChatUtil.SCOREBOARD_COLOR + "Players: " + ChatUtil.HIGHLIGHT_COLOR + playerhandler.getPlayers().size() + " / " + game.getTeamCount()*game.getTeamSize()).setScore(i++);
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------").setScore(i++);
			sidebar.getScore(ChatUtil.SCOREBOARD_COLOR + "Time: " + ChatUtil.HIGHLIGHT_COLOR + gamestate.getTime()).setScore(i++);
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------" + ChatColor.RESET + ChatColor.RESET + ChatColor.RESET + ChatColor.RESET).setScore(i++);
			break;
		case END:
			
			break;
		default:
			break;		
		}

		sidebar.setDisplayName(ChatUtil.PLUGIN_COLOR + "Splatoon");
		p.setScoreboard(sb);
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	public void updateSpectators() {
		for (Player p: playerhandler.getSpectators()) {
			updateSpectator(p);
		}
	}
	
	public void updateSpectator(Player p) {
		Scoreboard sb = scoreboards.get(p);
		Objective sidebar;
		while ((sidebar = sb.getObjective("sidebar")) != null) {
			sidebar.unregister();
		}
		sidebar = sb.registerNewObjective("sidebar", "dummy");
		sidebar.setDisplayName(ChatUtil.PLUGIN_COLOR + "Splatoon");
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

		int i = 0;
		switch (game.getGameState()) {
		case LOBBY:
		case COUNTDOWN:
			sidebar.getScore(ChatUtil.SCOREBOARD_COLOR + "---------------" + ChatColor.RESET + ChatColor.RESET).setScore(i++);
			for (TeamColor c: playerhandler.getTeams()) {
				sidebar.getScore(c.getChatColor() + c.getName() + ": " + ChatUtil.HIGHLIGHT_COLOR + playerhandler.getPlayersInTeam(c).size() + " / " + playerhandler.getTeamSize()).setScore(i++);
			}
			sidebar.getScore(ChatUtil.SCOREBOARD_COLOR + "---------------" + ChatColor.RESET).setScore(i++);
			sidebar.getScore(ChatUtil.SCOREBOARD_COLOR + "Players: " + ChatUtil.HIGHLIGHT_COLOR + playerhandler.getPlayers().size() + " / " + game.getTeamCount()*game.getTeamSize()).setScore(i++);
			sidebar.getScore(ChatUtil.SCOREBOARD_COLOR + "---------------").setScore(i++);
			
			break;
		case INGAME:
			if (!(game.getState() instanceof GameStateIngame)) return;
			GameStateIngame gamestate = (GameStateIngame) game.getState();
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------" + ChatColor.RESET).setScore(i++);
			sidebar.getScore(ChatUtil.SCOREBOARD_COLOR + "Players: " + ChatUtil.HIGHLIGHT_COLOR + playerhandler.getPlayers().size() + " / " + game.getTeamCount()*game.getTeamSize()).setScore(i++);
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------" + ChatColor.RESET + ChatColor.RESET).setScore(i++);
			for (TeamColor c: playerhandler.getTeams()) {
				sidebar.getScore(c.getChatColor() + c.getName() + ": " + ChatUtil.HIGHLIGHT_COLOR + playerhandler.getTeamScore(c, false)).setScore(i++);
			}
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------").setScore(i++);
			sidebar.getScore(ChatUtil.SCOREBOARD_COLOR + "Time: " + ChatUtil.HIGHLIGHT_COLOR + gamestate.getTime()).setScore(i++);
			sidebar.getScore(ChatUtil.SYMBOL_COLOR + "---------------" + ChatColor.RESET + ChatColor.RESET + ChatColor.RESET + ChatColor.RESET).setScore(i++);
			break;
		case END:
			
			break;
		default:
			break;		
		}
	}
	
	public void update() {
		updatePlayers();
		updateSpectators();
	}
	
	public void cleanUp() {
		for (Player p: scoreboards.keySet()) {
			p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		}
	}
}
