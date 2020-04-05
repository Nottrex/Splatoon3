package game;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public enum TeamColor {
	WHITE("White", Material.IRON_BLOCK, ChatColor.WHITE, DyeColor.WHITE), 
	BLACK("Black", Material.COAL_BLOCK, ChatColor.BLACK, DyeColor.BLACK), 
	BLUE("Blue", Material.LAPIS_BLOCK, ChatColor.BLUE, DyeColor.BLUE), 
	YELLOW("Yellow", Material.GOLD_BLOCK, ChatColor.YELLOW, DyeColor.YELLOW), 
	RED("Red", Material.REDSTONE_BLOCK, ChatColor.RED, DyeColor.RED), 
	GREEN("Green", Material.EMERALD_BLOCK, ChatColor.GREEN, DyeColor.GREEN);

	private String name;
	private Material block;
	private ChatColor color;
	private DyeColor dye;
	
	private TeamColor(String name, Material block, ChatColor color, DyeColor dye) {
		this.name = name;
		this.block = block;
		this.color = color;
		this.dye = dye;
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
	
	public DyeColor getDyeColor() {
		return dye;
	}
}
