package game.util;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_9_R1.PlayerConnection;

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
	    IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + header + "\"}");
	    IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
	    PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
	    try
	    {
	      Field field = headerPacket.getClass().getDeclaredField("b");
	      field.setAccessible(true);
	      field.set(headerPacket, tabFoot);
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      connection.sendPacket(headerPacket);
	    }
	}
}
