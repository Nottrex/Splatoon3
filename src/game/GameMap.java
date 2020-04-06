package game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import game.util.ConfigUtil;
import game.util.Util;
import game.util.WorldUtil;

public class GameMap {
	private Location corner1, corner2;
	private String name;
	private List<Location> teamSpawns;
	private int team_count;
	private int team_size;
	
	private GameMap(String name, Plugin plugin) {
		teamSpawns = new ArrayList<Location>();
		
		File f = new File(plugin.getDataFolder(), name+".yml");

		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);

		name = config.getString("Worldname");
		team_count = config.getInt("teams");
		team_size = config.getInt("players");
		corner1 = ConfigUtil.getLocation(config, "corner1");
		corner2 = ConfigUtil.getLocation(config, "corner2");

		for(int i = 0; i < team_count; i++){
			teamSpawns.add(ConfigUtil.getLocation(config, "teamSpawn"+i));
		}

		World map = WorldUtil.changeWorld(corner1.getWorld().getName(), "world_copy");

		WorldUtil.prepareWorld(map);

		corner1.setWorld(map);
		corner2.setWorld(map);
		for (Location l: teamSpawns) {
			l.setWorld(map);
		}

		
		if (team_count == 2) {
			Location black = teamSpawns.get(0);
			Location white = teamSpawns.get(1);
			
			for (int x = Math.min(corner1.getBlockX(), corner2.getBlockX()); x <= Math.max(corner1.getBlockX(), corner2.getBlockX()); x++) {
				for (int y = Math.min(corner1.getBlockY(), corner2.getBlockY()); y <= Math.max(corner1.getBlockY(), corner2.getBlockY()); y++) {
					for (int z = Math.min(corner1.getBlockZ(), corner2.getBlockZ()); z <= Math.max(corner1.getBlockZ(), corner2.getBlockZ()); z++) {
						if ((Math.abs(x-black.getBlockX()) + Math.abs(y-black.getBlockY()) + Math.abs(z-black.getBlockZ())) < (Math.abs(x-white.getBlockX()) + Math.abs(y-white.getBlockY()) + Math.abs(z-white.getBlockZ()))) {
							Util.setBlockTeam(black.getWorld().getBlockAt(x, y, z), TeamColor.BLACK);
						} else if ((Math.abs(x-black.getBlockX()) + Math.abs(y-black.getBlockY()) + Math.abs(z-black.getBlockZ())) > (Math.abs(x-white.getBlockX()) + Math.abs(y-white.getBlockY()) + Math.abs(z-white.getBlockZ()))) {
							Util.setBlockTeam(black.getWorld().getBlockAt(x, y, z), TeamColor.WHITE);
						} else {
							Util.setBlockTeam(black.getWorld().getBlockAt(x, y, z), Util.RANDOM.nextBoolean() ? TeamColor.WHITE : TeamColor.BLACK);
						}
					}
				}
			}
		}
	}
	
	public int getTeamCount() {
		return team_count;
	}
	
	public int getTeamSize() {
		return team_size;
	}
	
	public Location getFirstMapCorner() {
		return corner1;
	}
	
	public Location getSecondMapCorner() {
		return corner2;
	}
	
	public String getName() {
		return name;
	}
	
	public Location getTeamSpawn(int team) {
		if (team < 0 || team >= team_count) return null;
		
		return teamSpawns.get(team);
	}
	
	public Location getCenter() {
		return corner1.add(corner2).multiply(0.5);
	}

	public static GameMap getMap(String name, Plugin plugin) {
		File f = new File(plugin.getDataFolder(), name+".yml");
		if(f.exists()){
			Util.LOGGER.log(Level.INFO, "MapConfiguration found: " + name);
			return new GameMap(name, plugin);
		} else {
			Util.LOGGER.log(Level.WARNING, "MapConfiguration not found: " + name);
			return null;
		}
	}
}
