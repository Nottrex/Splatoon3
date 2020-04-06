package game;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Arrays;

public enum TeamColor {
	WHITE("White", ChatColor.WHITE, Color.WHITE, new Material[]{Material.IRON_BLOCK, Material.WHITE_WOOL, Material.WHITE_STAINED_GLASS, Material.WHITE_TERRACOTTA, Material.WHITE_CONCRETE, Material.WHITE_CONCRETE_POWDER}),
	BLACK("Black", ChatColor.BLACK, Color.BLACK, new Material[]{Material.COAL_BLOCK,  Material.BLACK_WOOL, Material.BLACK_STAINED_GLASS, Material.BLACK_TERRACOTTA, Material.BLACK_CONCRETE, Material.BLACK_CONCRETE_POWDER}),
	BLUE("Blue",  ChatColor.BLUE, Color.BLUE, new Material[]{Material.LAPIS_BLOCK, Material.BLUE_WOOL, Material.BLUE_STAINED_GLASS, Material.BLUE_TERRACOTTA, Material.BLUE_CONCRETE, Material.BLUE_CONCRETE_POWDER}),
	YELLOW("Yellow", ChatColor.YELLOW, Color.YELLOW, new Material[]{Material.GOLD_BLOCK, Material.YELLOW_WOOL, Material.YELLOW_STAINED_GLASS, Material.YELLOW_TERRACOTTA, Material.YELLOW_CONCRETE, Material.YELLOW_CONCRETE_POWDER}),
	RED("Red", ChatColor.RED, Color.RED, new Material[]{Material.REDSTONE_BLOCK, Material.RED_WOOL, Material.RED_STAINED_GLASS, Material.RED_TERRACOTTA, Material.RED_CONCRETE, Material.RED_CONCRETE_POWDER}),
	GREEN("Green", ChatColor.GREEN, Color.GREEN, new Material[]{Material.EMERALD_BLOCK, Material.GREEN_WOOL, Material.GREEN_STAINED_GLASS, Material.GREEN_TERRACOTTA, Material.GREEN_CONCRETE, Material.GREEN_CONCRETE_POWDER});

	private String name;
	private ChatColor chatColor;
	private Color color;
	private Material[] materials;	//block, wool, glass, clay, concrete, concretePowder
	
	private TeamColor(String name, ChatColor chatColor, Color color, Material[] materials) {
		this.name = name;
		this.chatColor = chatColor;
		this.color = color;
		this.materials = materials;
	}
	
	public ChatColor getChatColor() {
		return chatColor;
	}

	public Color getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}

	public Material[] getMaterials() {
		return materials;
	}

	public Material getWool() {
		return materials[1];
	}

	public boolean isBlockTeam(Block block){
		return Arrays.stream(materials).anyMatch(m -> m == block.getType());
	}

	public static boolean isMaterial(Block b, int index) {
		return Arrays.stream(TeamColor.values()).anyMatch(c -> c.getMaterials()[index] == b.getType());
	}
}
