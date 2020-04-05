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
	
	@SuppressWarnings("deprecation")
	public static boolean setBlockTeam(Block block, TeamColor team) {
		if(block.getType() == Material.STAINED_CLAY && block.getData() != team.getDyeColor().getData()) {
			block.setData(team.getDyeColor().getData());
			return true;
		} else if(block.getType() == Material.STAINED_GLASS && block.getData() != team.getDyeColor().getData()) {
			block.setData(team.getDyeColor().getData());
			return true;
		} else if(block.getType() ==  Material.WOOL && block.getData() != team.getDyeColor().getData()) {
			block.setData(team.getDyeColor().getData());
			return true;
		} else if(block.getType() != team.getBlockMaterial() && isOreBlock(block)) {
			block.setType(team.getBlockMaterial());
			return true;
		} 
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean isBlockTeam(Block block, TeamColor team){
		if(block.getType() == Material.STAINED_CLAY && block.getData() == team.getDyeColor().getData())return true;
		else if(block.getType() == Material.STAINED_GLASS && block.getData() == team.getDyeColor().getData())return true;
		else if(block.getType() == team.getBlockMaterial())return true;
		else if(block.getType() ==  Material.WOOL && block.getData() == team.getDyeColor().getData())return true;
		return false;
	}
	
	public static boolean isOreBlock(Block b) {
		for (TeamColor c: TeamColor.values()) {
			if (c.getBlockMaterial() == b.getType()) return true;
		}
		return false;
	}
}
