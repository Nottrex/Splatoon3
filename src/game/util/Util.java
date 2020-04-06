package game.util;

import java.util.Random;
import java.util.logging.Logger;

import game.Game;
import game.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

public class Util {

	public static Random RANDOM;
	public static Logger LOGGER;
	public static Game GAME;
	public static Plugin PLUGIN;
	
	static {
		RANDOM = new Random();
		LOGGER = Bukkit.getLogger();
	}

	public static void setGame(Game game) {
		GAME = game;
	}
	
	public static void setPlugin(Plugin plugin) {
		PLUGIN = plugin;
	}
	
	public static boolean setBlockTeam(Block block, TeamColor team) {
		for(int i = 0; i < team.getMaterials().length; i++) {
			Material m = team.getMaterials()[i];
			if(block.getType() != m && TeamColor.isMaterial(block, i)) {
				block.setType(m);
				return true;
			}
		}
		return false;
	}


}
