package game.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtil {
	public static Location getLocation(FileConfiguration config, String name) {
		return new Location(new WorldCreator(config.getString(name + ".world")).createWorld(), config.getDouble(name + ".x"), config.getDouble(name + ".y"), config.getDouble(name + ".z"), (float) config.getDouble(name + ".pitch"), (float) config.getDouble(name + ".yaw"));
	}
}
