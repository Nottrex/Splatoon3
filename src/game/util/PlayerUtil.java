package game.util;

import game.GameState;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_15_R1.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;


public class PlayerUtil {
	public static void preparePlayer(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.setGameMode(GameMode.ADVENTURE);
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setFireTicks(0);
		p.setExp(0);
		p.setLevel(0);
		p.setWalkSpeed(0.2f);

		if(Util.GAME.getGameState() == GameState.INGAME) p.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).color(Util.GAME.getPlayerHandler().getTeam(p).getColor()).build());

		for (PotionEffectType type: PotionEffectType.values()) {
			if (type != null && p.hasPotionEffect(type)) p.removePotionEffect(type);
		}
	}
	
	public static void prepareSpectator(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.setGameMode(GameMode.SPECTATOR);
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setFireTicks(0);
		p.setExp(0);
		p.setLevel(0);
				
		for (PotionEffectType type: PotionEffectType.values()) {
			p.removePotionEffect(type);
		}
	}
	
	public static void setTabTitle(Player player, String header, String footer) {
	    if (header == null) header = "";
	    header = ChatColor.translateAlternateColorCodes('&', header);
	
	    if (footer == null) footer = "";
	    footer = ChatColor.translateAlternateColorCodes('&', footer);
	
	    PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
	    IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
	    IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
	    PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter();
	    headerPacket.header = tabTitle;	//TODO: ?
	    headerPacket.footer = tabFoot;	//TODO: ?
		connection.sendPacket(headerPacket);
	}
}
