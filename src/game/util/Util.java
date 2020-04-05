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

	//TODO:

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
		if(block.getType() != team.getStainedClay() && isClayBlock(block)) {
			block.setType(team.getStainedClay());
			return true;
		} else if(block.getType() != team.getStainedGlass() && isGlassBlock(block)) {
			block.setType(team.getStainedGlass());
			return true;
		} else if(block.getType() != team.getWool() && isWoolBlock(block)) {
			block.setType(team.getWool());
			return true;
		} else if(block.getType() != team.getBlockMaterial() && isOreBlock(block)) {
			block.setType(team.getBlockMaterial());
			return true;
		} 
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean isBlockTeam(Block block, TeamColor team){
		if(block.getType() == team.getStainedClay())return true;
		else if(block.getType() == team.getStainedGlass())return true;
		else if(block.getType() == team.getBlockMaterial())return true;
		else if(block.getType() == team.getWool())return true;
		return false;
	}
	
	public static boolean isOreBlock(Block b) {
		for (TeamColor c: TeamColor.values()) {
			if (c.getBlockMaterial() == b.getType()) return true;
		}
		return false;
	}

	public static boolean isGlassBlock(Block b) {
		for (TeamColor c: TeamColor.values()) {
			if (c.getStainedGlass() == b.getType()) return true;
		}
		return false;
	}

	public static boolean isClayBlock(Block b) {
		for (TeamColor c: TeamColor.values()) {
			if (c.getStainedClay() == b.getType()) return true;
		}
		return false;
	}


	public static boolean isWoolBlock(Block b) {
		for (TeamColor c: TeamColor.values()) {
			if (c.getWool() == b.getType()) return true;
		}
		return false;
	}
}
