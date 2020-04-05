package game.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {
	public static ItemStack addChoosenEffect(ItemStack item) {
		return new ItemBuilder(item).enchantment(Enchantment.MENDING).build();
	}
}
