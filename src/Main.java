import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	//test

	public void onEnable() {
		load_Config();
	}

	public void onDisable() {
		save_Config();
	}

	public void load_Config() {
		FileConfiguration config = getConfig();

	}

	public void save_Config() {
		saveConfig();
	}
}
