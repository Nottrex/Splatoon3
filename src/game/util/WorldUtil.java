package game.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import game.Game;
import game.TeamColor;
import org.bukkit.*;
import org.bukkit.block.Block;

public class WorldUtil {

	public static void copyWorld(File source, File target){
	     try {
	         ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
	         if(!ignore.contains(source.getName())) {
	             if(source.isDirectory()) {
	                 if(!target.exists())
	                 target.mkdirs();
	                 String files[] = source.list();
	                 for (String file : files) {
	                     File srcFile = new File(source, file);
	                     File destFile = new File(target, file);
	                     copyWorld(srcFile, destFile);
	                 }
	             } else {
	                 InputStream in = new FileInputStream(source);
	                 OutputStream out = new FileOutputStream(target);
	                 byte[] buffer = new byte[1024];
	                 int length;
	                 while ((length = in.read(buffer)) > 0)
	                     out.write(buffer, 0, length);
	                 in.close();
	                 out.close();
	             }
	         }
	     } catch (IOException e) {
	  
	     }
	 }
	
	public static World changeWorld(String source, String target){
		World wtarget;
		World wsource = Bukkit.createWorld(new WorldCreator(source));
		
		wtarget = Bukkit.createWorld(new WorldCreator(target));
		copyWorld(wsource.getWorldFolder(), wtarget.getWorldFolder());
		
		Bukkit.unloadWorld(wsource, false);
		Bukkit.unloadWorld(wtarget, false);
		wtarget = Bukkit.createWorld(new WorldCreator(target));
		
		return wtarget;
	}
	
	public static void prepareWorld(World w) {
		w.setTime(12000);
		w.setDifficulty(Difficulty.EASY);
		w.setGameRuleValue("doDaylightCycle", "false");
		w.setMonsterSpawnLimit(0);
		w.setWeatherDuration(999999);
		w.setStorm(false);
		w.setThundering(false);
		w.setWaterAnimalSpawnLimit(0);
		w.setSpawnFlags(false, false);
		
		w.getEntities().clear();
	}
	
	public static int countTile(Game game){
		int tiles = 0;
		
		for(int x = Math.min(game.getGameMap().getFirstMapCorner().getBlockX(), game.getGameMap().getSecondMapCorner().getBlockX()); x < Math.max(game.getGameMap().getFirstMapCorner().getBlockX(), game.getGameMap().getSecondMapCorner().getBlockX()); x++){
			for(int y = Math.min(game.getGameMap().getFirstMapCorner().getBlockY(), game.getGameMap().getSecondMapCorner().getBlockY()); y < Math.max(game.getGameMap().getFirstMapCorner().getBlockY(), game.getGameMap().getSecondMapCorner().getBlockY()); y++){
				for(int z = Math.min(game.getGameMap().getFirstMapCorner().getBlockZ(), game.getGameMap().getSecondMapCorner().getBlockZ()); z < Math.max(game.getGameMap().getFirstMapCorner().getBlockZ(), game.getGameMap().getSecondMapCorner().getBlockZ()); z++){
					if(new Location(game.getGameMap().getFirstMapCorner().getWorld(), x, y, z).getBlock().getType() == Material.LEGACY_STAINED_CLAY)tiles++;
					else if(new Location(game.getGameMap().getFirstMapCorner().getWorld(), x, y, z).getBlock().getType() == Material.LEGACY_STAINED_GLASS)tiles++;
					if(new Location(game.getGameMap().getFirstMapCorner().getWorld(), x, y, z).getBlock().getType() == Material.IRON_BLOCK)tiles++;
					if(new Location(game.getGameMap().getFirstMapCorner().getWorld(), x, y, z).getBlock().getType() == Material.LEGACY_WOOL)tiles++;
				}
			}
		}
		return tiles;
	}
	
	@SuppressWarnings("deprecation")
	public static int countColouredTile(Game game, TeamColor c){
		int tiles = 0;
		
		for(int x = Math.min(game.getGameMap().getFirstMapCorner().getBlockX(), game.getGameMap().getSecondMapCorner().getBlockX()); x < Math.max(game.getGameMap().getFirstMapCorner().getBlockX(), game.getGameMap().getSecondMapCorner().getBlockX()); x++){
			for(int y = Math.min(game.getGameMap().getFirstMapCorner().getBlockY(), game.getGameMap().getSecondMapCorner().getBlockY()); y < Math.max(game.getGameMap().getFirstMapCorner().getBlockY(), game.getGameMap().getSecondMapCorner().getBlockY()); y++){
				for(int z = Math.min(game.getGameMap().getFirstMapCorner().getBlockZ(), game.getGameMap().getSecondMapCorner().getBlockZ()); z < Math.max(game.getGameMap().getFirstMapCorner().getBlockZ(), game.getGameMap().getSecondMapCorner().getBlockZ()); z++){
					Block block = new Location(game.getGameMap().getFirstMapCorner().getWorld(), x, y, z).getBlock();
					if(block.getType() == Material.LEGACY_STAINED_CLAY && block.getData() == c.getDyeColor().getDyeData())tiles++;
					else if(block.getType() == Material.LEGACY_STAINED_GLASS && block.getData() == c.getDyeColor().getDyeData())tiles++;
					else if(block.getType() == c.getBlockMaterial())tiles++;
					else if(block.getType() == Material.LEGACY_WOOL && block.getData() == c.getDyeColor().getDyeData())tiles++;
				}
			}
		}
		return tiles;
	}
	
}
