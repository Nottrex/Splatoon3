package game;

import java.util.ArrayList;
import java.util.List;

import game.listener.PlayerToggleSneakListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import game.events.GameFinishEvent;
import game.events.GameStateFinishEvent;
import game.gamestate.GameStateBase;
import game.gamestate.GameStateCountdown;
import game.gamestate.GameStateEnd;
import game.gamestate.GameStateIngame;
import game.gamestate.GameStateLobby;
import game.listener.CancelledListener;
import game.listener.EntityDamageListener;
import game.listener.InventoryClickListener;
import game.listener.PingListener;
import game.listener.PlayerChatListener;
import game.listener.PlayerDeathListener;
import game.listener.PlayerInteractListener;
import game.listener.PlayerJoinListener;
import game.listener.PlayerLeaveListener;
import game.listener.PlayerMoveListener;
import game.listener.PlayerRespawnListener;
import game.listener.ProjectileHitListener;
import game.util.ConfigUtil;
import game.util.Util;
import game.util.WorldUtil;

public class Game implements Listener {
	public Plugin plugin;
	
	private PlayerHandler playerHandler;
	private GameState gamestate;
	private Location lobby;
	private GameMap map;	
	private GameStateBase state;
	private ScoreBoard scoreboard;	
	
	public Game(Plugin plugin) {
		this.plugin = plugin;
		Util.setGame(this);
		
		gamestate = GameState.UNSTARTET;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this), plugin);	
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), plugin);	
		Bukkit.getPluginManager().registerEvents(new CancelledListener(this), plugin);
		Bukkit.getPluginManager().registerEvents(new PingListener(this), plugin);
		Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(this), plugin);
		Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this, plugin), plugin);
		Bukkit.getPluginManager().registerEvents(new PlayerRespawnListener(this), plugin);
		Bukkit.getPluginManager().registerEvents(new PlayerChatListener(this), plugin);
		Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(this), plugin);
		Bukkit.getPluginManager().registerEvents(new PlayerToggleSneakListener(this), plugin);
		Bukkit.getPluginManager().registerEvents(new ProjectileHitListener(this), plugin);
		Bukkit.getPluginManager().registerEvents(new EntityDamageListener(this), plugin);
		Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), plugin);
		
		loadLobby();
	}
	
	public void setGameState(GameState gamestate) {		
		if (this.gamestate == gamestate) return; 
		state.stop();

		this.gamestate = gamestate;
		
		switch (gamestate) {
		case LOBBY:
			state = new GameStateLobby(this, plugin);
			break;
		case COUNTDOWN:
			state = new GameStateCountdown(this, plugin);
			break;
		case INGAME:
			state = new GameStateIngame(this, plugin);
			break;
		case END:
			state = new GameStateEnd(this, plugin);
			break;
		case UNSTARTET:
			state = null;
			stop();
			return;
		}
		
		for (Player p: Bukkit.getOnlinePlayers()) {
			if (playerHandler.isPlayer(p)) {
				state.preparePlayer(p, GameStateBase.PrepareType.GAMESTATE_CHANGE);
			} else {
				state.prepareSpectator(p, GameStateBase.PrepareType.GAMESTATE_CHANGE);
			}				
		}
	}
	
	public void start() {		
		selectRandomMaps();
		
		playerHandler = new PlayerHandler(this, getTeamCount(), getTeamSize());
		scoreboard = new ScoreBoard(this);
		
		gamestate = GameState.LOBBY;
		state = new GameStateLobby(this, plugin);
		
		for (Player p: Bukkit.getOnlinePlayers()) {
			playerHandler.addPlayer(p);
		}
	}

	public void start(String mapName) {
		setGameMap(mapName);

		playerHandler = new PlayerHandler(this, getTeamCount(), getTeamSize());
		scoreboard = new ScoreBoard(this);

		gamestate = GameState.LOBBY;
		state = new GameStateLobby(this, plugin);

		for (Player p: Bukkit.getOnlinePlayers()) {
			playerHandler.addPlayer(p);
		}
	}

	@EventHandler
	public void onGameStateFinish(GameStateFinishEvent event) {
		switch (event.getGameState()) {
		case LOBBY:
			setGameState(GameState.COUNTDOWN);
			break;
		case COUNTDOWN:
			setGameState(GameState.INGAME);
			break;
		case INGAME:
			setGameState(GameState.END);
			break;
		case END:
			setGameState(GameState.UNSTARTET);
			break;
		default:
		}
	}
	
	public void stop() {	
		if (state != null) {
			state.stop();
			state = null;
		}
		
		Inventories.cleanUp();
		scoreboard.cleanUp();
		
		for (Player p: Bukkit.getOnlinePlayers()) {
			p.setPlayerListName(p.getName());
			p.setDisplayName(p.getName());
			
			if (p.getWorld().equals(map.getFirstMapCorner().getWorld())) {
				p.teleport(lobby);
			}
			
			for (Player p2: Bukkit.getOnlinePlayers()) {
				p2.showPlayer(p);
			}
		}
		
		Bukkit.unloadWorld(map.getFirstMapCorner().getWorld(), false);
		
		gamestate = GameState.UNSTARTET;
		plugin.getServer().getPluginManager().callEvent(new GameFinishEvent());
	}
	
	
	private void loadLobby() {
		FileConfiguration config = plugin.getConfig();
		lobby = ConfigUtil.getLocation(config, "Lobby");
		
		World lobby_world = WorldUtil.changeWorld(lobby.getWorld().getName(), "lobby_copy");
		lobby_world.setSpawnLocation(lobby.getBlockX(), lobby.getBlockY(), lobby.getBlockZ());
		
		WorldUtil.prepareWorld(lobby_world);
		
		lobby.setWorld(lobby_world);
	}
	
	private void selectRandomMaps() {
		List<String> maps = new ArrayList<String>();
		
		FileConfiguration config = plugin.getConfig();
		
		for(int i = 0; i < config.getInt("MapsCount"); i++) {
			maps.add( config.getString("Map"+i));
		}
    	
		setGameMap(maps.get(Util.RANDOM.nextInt(maps.size())));
	}

	public boolean setGameMap(String name) {
		GameMap map = GameMap.getMap(name, plugin);
		if(map != null) {
			this.map = map;
			return true;
		}

		return false;
	}

	public Location getLobby(){
		return lobby;
	}
	
	public GameState getGameState() {
		return gamestate;
	}
	
	public GameStateBase getState() {
		return state;
	}
	
	public int getTeamSize() {
		return map.getTeamSize();
	}
	
	public int getTeamCount() {
		return map.getTeamCount();
	}
	
	public GameMap getGameMap() {
		return map;
	}
	
	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}
	
	public ScoreBoard getScoreboard() {
		return scoreboard;
	}
}
