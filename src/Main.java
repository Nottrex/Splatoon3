import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import game.Game;
import game.GameState;
import game.Item;
import game.ScoreBoard;
import game.events.GameFinishEvent;
import game.util.ChatUtil;
import game.util.TimeUtil;
import game.util.Util;

public class Main extends JavaPlugin implements Listener {
	public static Main INSTANCE;
	
	public static ScoreBoard sb;
	
	public boolean autostart;	
	public Game game;
	
	public void onEnable(){
		INSTANCE = this;
		Util.setPlugin(this);
		TimeUtil.init();
		
		Util.LOGGER.log(Level.INFO,"---------------");
		Util.LOGGER.log(Level.INFO,"Loading Plugin: Splatoon");
		Util.LOGGER.log(Level.INFO,"Plugin by:");
		Util.LOGGER.log(Level.INFO,"  -PhoenixofForce");
		Util.LOGGER.log(Level.INFO,"  -Nottrex");
		Util.LOGGER.log(Level.INFO,"---------------");

		Bukkit.getPluginManager().registerEvents(this, this);
		
		game = new Game(this);
		
		loadConfig();
		
		if (autostart) game.start();
	}
	
	public void onDisable() {
		HandlerList.unregisterAll((Plugin) this);
		if (game.getGameState() != GameState.UNSTARTET) {
			game.stop();
		}
	}
	
	public void loadConfig() {
    	FileConfiguration config = getConfig();
    	autostart = config.getBoolean("AutoStart", true);
    }	

	@EventHandler
	public void onGameFinish(GameFinishEvent e) {
		if (autostart) {
			game.start();
		}
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandlabel, String[] args) {		
		
		//>---| STARTGAME |---<\\
		
		if (commandlabel.equalsIgnoreCase("startgame")) {
			if(!sender.hasPermission("splatoon.start")){
				ChatUtil.sendErrorMessage(sender, ChatUtil.ERROR_NOT_ENOUGH_PERMISSIONS);
				return true;
			 }
			
			if(game.getGameState() == GameState.UNSTARTET) {
				game.start();
			} else if(game.getGameState() == GameState.LOBBY) {
				if (args.length >= 1 && args[0].equals("2")) {
					game.setGameState(GameState.INGAME);
				} else {
					game.setGameState(GameState.COUNTDOWN);
				}
			} else if (game.getGameState() == GameState.COUNTDOWN) {
				game.setGameState(GameState.INGAME);
			} else {
				ChatUtil.sendErrorMessage(sender, "Game already started!");
				return true;
			}
			
			ChatUtil.sendMessage(sender, "You started the Game!");
			return true;
		}
		
		//>---| STOPGAME |---<\\
		
		else if (commandlabel.equalsIgnoreCase("stopgame")) {
			if(!sender.hasPermission("splatoon.stop")){
				ChatUtil.sendErrorMessage(sender, ChatUtil.ERROR_NOT_ENOUGH_PERMISSIONS); 
				return true;
			}
			
			if(game.getGameState() == GameState.UNSTARTET){
				ChatUtil.sendErrorMessage(sender, "Game not started!");
				return true;
			}
			game.stop();
			ChatUtil.sendMessage(sender, "You stopped the Game!");
			return true;
		}

		//>---| FORCEMAP |---<\\

		else if (commandlabel.equalsIgnoreCase("forcemap")) {
			if(!sender.hasPermission("splatoon.forcemap")){
				ChatUtil.sendErrorMessage(sender, ChatUtil.ERROR_NOT_ENOUGH_PERMISSIONS);
				return true;
			}

			if (args.length == 0) {
				ChatUtil.sendErrorMessage(sender, "Not enough arguments!");
				return true;
			}

			if(game.getGameState() != GameState.LOBBY){
				ChatUtil.sendErrorMessage(sender, "Game already started!");
				return true;
			}

			game.stop();
			game.start(args[0]);
			ChatUtil.sendMessage(sender, "You changed the map to " + args[0]);
			return true;
		}
				
		//>---| OP COMMANDS |---<\\
		 		
 		else if (commandlabel.equalsIgnoreCase("splatoon")) {
 			Player p = (Player) sender;
 			if(args.length == 0 || !p.isOp()){
 				return true;
 			}
 			
 			if(args.length >= 2 && args[0].equalsIgnoreCase("give")){
 				try{
 					p.getInventory().addItem(Item.items.get(args[1].toLowerCase()));
 				} catch(Exception e) {
					ChatUtil.sendErrorMessage(sender, String.format("Something went wrong while giving you this item - %s", e.getMessage()));
				}
 			} else if(args[0].equalsIgnoreCase("where")){
 				ChatUtil.sendMessage(p, "You are here: " + ChatUtil.HIGHLIGHT_COLOR + p.getLocation().getWorld().getName());
 			}
 			
 			return true;
 		}
		
		return false;
	}
}