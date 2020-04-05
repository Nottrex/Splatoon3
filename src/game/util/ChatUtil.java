package game.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtil {
	public static final ChatColor CHAT_COLOR 		= ChatColor.GRAY;
	public static final ChatColor COMMAND_COLOR 	= ChatColor.GREEN;
	public static final ChatColor COMMAND_COLOR2 	= ChatColor.AQUA;
	public static final ChatColor ERROR_COLOR 		= ChatColor.RED;
	public static final ChatColor PLAYER_COLOR 		= ChatColor.WHITE;
	public static final ChatColor PLUGIN_COLOR 		= ChatColor.DARK_AQUA;
	public static final ChatColor SYMBOL_COLOR 		= ChatColor.DARK_GRAY;
	public static final ChatColor HIGHLIGHT_COLOR	= ChatColor.YELLOW;
	public static final ChatColor SCOREBOARD_COLOR	= ChatColor.AQUA;
	
	public static final String ERROR_NOT_ENOUGH_PERMISSIONS = "You do not have permission to perform this command!";
	
	public static void sendMessage(CommandSender p, String message) {
		p.sendMessage(getPluginPrefix() + COMMAND_COLOR + message);
	}
	
	public static void sendMessage(String message) {
		Bukkit.broadcastMessage(getPluginPrefix() + COMMAND_COLOR + message);
	}
	
	public static void sendErrorMessage(CommandSender p, String message) {
		p.sendMessage(getPluginPrefix() + ERROR_COLOR + message);
	}
	
	public static void sendErrorMessage(String message) {
		Bukkit.broadcastMessage(getPluginPrefix() + ERROR_COLOR + message);
	}
	
	public static String getPluginPrefix() {
		return SYMBOL_COLOR + "[" + PLUGIN_COLOR + "Splatoon" + SYMBOL_COLOR + "] " + COMMAND_COLOR; 
	}
	
	public static String formatChat(Player p, String message) {
		return p.getDisplayName() + SYMBOL_COLOR + "> " + CHAT_COLOR + message;
	}
}
