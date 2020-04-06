package game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import game.gamestate.GameStateBase.PrepareType;
import game.util.ChatUtil;
import game.util.TimeUtil;
import game.util.Util;
import game.util.WorldUtil;

public class PlayerHandler {
	public static final long TEAM_SCORE_CHECK_TIME = 50L;
	
	private int team_count;
	private int team_size;
	
	private List<Player> players;
	private List<Player> spectators;

	private HashMap<Player, TeamColor> playerTeam;
	private HashMap<Player, GamePlayer> playerGame;
	private HashMap<TeamColor, Integer> teamScore;
	private HashMap<TeamColor, Long> teamScoreLast;
	
	private List<TeamColor> teams;
	
	private Game game;
	
	public PlayerHandler(Game game, int team_count, int team_size) {
		this.game = game;
		
		this.team_count = team_count;
		this.team_size = team_size;
		
		players = new ArrayList<Player>();
		spectators = new ArrayList<Player>();
		playerTeam = new HashMap<Player, TeamColor>();
		playerGame = new HashMap<Player, GamePlayer>();
		teamScore = new HashMap<TeamColor, Integer>();
		teamScoreLast = new HashMap<TeamColor, Long>();
		teams = new ArrayList<TeamColor>();
		
		switch (team_count) {
		case 2:
			teams.add(TeamColor.BLACK);
			teams.add(TeamColor.WHITE);
			break;
		case 5:
			teams.add(TeamColor.BLACK);
		case 4:
			teams.add(TeamColor.BLUE);
		case 3:
			teams.add(TeamColor.GREEN);
			teams.add(TeamColor.RED);
			teams.add(TeamColor.YELLOW);
			break;
		default:
			Util.LOGGER.log(Level.WARNING, "Unsupported team size: " + team_size);
		}
		
		for (TeamColor team: teams) {
			teamScore.put(team, 0);
			teamScoreLast.put(team, 0L);
		}
	}
	
	public boolean isSpectator(Player p) {
		return spectators.contains(p);
	}
	
	public boolean isPlayer(Player p) {
		return players.contains(p);
	}
	
	private void addSpectator(Player p) {
		spectators.add(p);			
		game.getState().prepareSpectator(p, PrepareType.JOIN);
		game.getScoreboard().initPlayer(p);
		game.getScoreboard().updateSpectator(p);
		
		for (Player p2: players) {
			p2.hidePlayer(p);
		}
		
		for (Player p2: spectators) {
			p.showPlayer(p2);
		}
	}
	
	private void addGamePlayer(Player p) {
		playerGame.put(p, new GamePlayer(p, game));
		players.add(p);
		game.getState().preparePlayer(p, PrepareType.JOIN);
		game.getScoreboard().initPlayer(p);
		game.getScoreboard().update();
		
		for (Player p2: players) {
			p2.showPlayer(p);
		}
		
		for (Player p2: spectators) {
			p.hidePlayer(p2);
		}
	}
	
	public void addPlayer(Player p) {
		if (game.getGameState() == GameState.LOBBY || game.getGameState() == GameState.COUNTDOWN) {
			if (players.size() < team_count*team_size) {
				addGamePlayer(p);
				
				ChatUtil.sendMessage(ChatUtil.PLAYER_COLOR + p.getDisplayName() + ChatUtil.COMMAND_COLOR2 + " joined the game " 
						+ ChatUtil.HIGHLIGHT_COLOR + players.size() + " / " + game.getTeamCount()*game.getTeamSize());
			} else {
				addSpectator(p);
				ChatUtil.sendMessage(p, "Sry, the game is full! You can wait and hope that a player leaves!");
			}
		} else {
			addSpectator(p);
			ChatUtil.sendMessage(p, "Sry, the game already startet!");
		}
	}
	
	public void removePlayer(Player p) {
		if (players.contains(p)) {
			players.remove(p);
			playerTeam.keySet().remove(p);
			playerGame.keySet().remove(p);
			game.getScoreboard().removePlayer(p);
			
			ChatUtil.sendMessage(ChatUtil.PLAYER_COLOR + p.getDisplayName() + ChatUtil.COMMAND_COLOR2 + " left the game " 
					+ ChatUtil.HIGHLIGHT_COLOR + players.size() + " / " + game.getTeamCount()*game.getTeamSize());
			
			if ((game.getGameState() == GameState.LOBBY || game.getGameState() == GameState.COUNTDOWN) && spectators.size() > 0) {
				Player player = spectators.get(Util.RANDOM.nextInt(spectators.size()));
				spectators.remove(player);
				addPlayer(player);
			}
		} else if (spectators.contains(p)) {
			spectators.remove(p);
		}
	}
	
	public int getTeamScore(TeamColor team, boolean forceCheck) {
		if (forceCheck || (TimeUtil.getTime()-(teamScoreLast.get(team)) >= TEAM_SCORE_CHECK_TIME)) {
			teamScore.put(team, WorldUtil.countColouredTile(game, team));
			teamScoreLast.put(team, TimeUtil.getTime());
		}
		
		return teamScore.get(team);
	}
	
	public GamePlayer getGamePlayer(Player p) {
		return playerGame.get(p);
	}
	
	public TeamColor getTeam(Player p) {
		return playerTeam.get(p);
	}
	
	/**
	 * 
	 * @param p	The Player that should be added
	 * @param team The team the Player should be added
	 * @return If the Player could be added to this team
	 */
	public boolean setTeam(Player p, TeamColor team) {
		if (getPlayersInTeam(team).size() < team_size) {
			playerTeam.put(p, team);
			
			ChatUtil.sendMessage(p, "You joined Team " + team.getChatColor() + team.getName());
			
			p.setPlayerListName(getTeam(p).getChatColor() + p.getName());
			p.setDisplayName(getTeam(p).getChatColor() + p.getName());
			game.getScoreboard().setTeam(p, team);
			game.getScoreboard().update();
			return true;
		}
		ChatUtil.sendMessage(p, "Team " + team.getChatColor() + team.getName() + ChatUtil.COMMAND_COLOR + " is full");
		return false;
	}
	
	public void addPlayersToTeams() {
		for (Player p: players) {
			if (getTeam(p) == null) {
				TeamColor teamToJoin = teams.stream().min(Comparator.comparingInt(t -> getPlayersInTeam(t).size())).get();
				if(teamToJoin != null && getPlayersInTeam(teamToJoin).size() < team_size)setTeam(p, teamToJoin);
			}
		}
	}
	
	public List<Player> getPlayersInTeam(TeamColor team) {
		List<Player> players = new ArrayList<Player>();
		for (Player p: playerTeam.keySet()) {
			if (getTeam(p) == team) players.add(p);
		}
		return players;
	}
	
	public Location getTeamSpawn(TeamColor team) {
		return game.getGameMap().getTeamSpawn(teams.indexOf(team));
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public List<Player> getSpectators() {
		return spectators;
	}
	
	public List<TeamColor> getTeams() {
		return teams;
	}
	
	public int getTeamSize() {
		return team_size;
	}
}
