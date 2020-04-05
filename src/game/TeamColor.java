package game;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public enum TeamColor {
	WHITE("White", Material.IRON_BLOCK, ChatColor.WHITE, Material.WHITE_WOOL, Material.WHITE_STAINED_GLASS, Material.WHITE_TERRACOTTA),
	BLACK("Black", Material.COAL_BLOCK, ChatColor.BLACK, Material.BLACK_WOOL, Material.BLACK_STAINED_GLASS, Material.BLACK_TERRACOTTA),
	BLUE("Blue", Material.LAPIS_BLOCK, ChatColor.BLUE, Material.BLUE_WOOL, Material.BLUE_STAINED_GLASS, Material.BLUE_TERRACOTTA),
	YELLOW("Yellow", Material.GOLD_BLOCK, ChatColor.YELLOW, Material.YELLOW_WOOL, Material.YELLOW_STAINED_GLASS, Material.YELLOW_TERRACOTTA),
	RED("Red", Material.REDSTONE_BLOCK, ChatColor.RED, Material.RED_WOOL, Material.RED_STAINED_GLASS, Material.RED_TERRACOTTA),
	GREEN("Green", Material.EMERALD_BLOCK, ChatColor.GREEN, Material.GREEN_WOOL, Material.GREEN_STAINED_GLASS, Material.GREEN_TERRACOTTA);

	private String name;
	private Material block;
	private ChatColor color;
	private Material wool, glass, clay;
	
	private TeamColor(String name, Material block, ChatColor color, Material wool, Material glass, Material clay) {
		this.name = name;
		this.block = block;
		this.color = color;
		this.wool = wool;
		this.glass = glass;
		this.clay = clay;
	}
	
	public ChatColor getChatColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
		
	public Material getBlockMaterial() {
		return block;
	}
	
	public Material getWool() {
		return wool;
	}

	public Material getStainedGlass() {
		return glass;
	}

	public Material getStainedClay() {
		return clay;
	}
}
