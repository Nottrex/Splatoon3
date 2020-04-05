package game.util;

import org.bukkit.inventory.ItemStack;

public class ItemUtil {
	public static ItemStack addChoosenEffect(ItemStack item) {
		return EnchantGlow.addGlow(new ItemBuilder(item).lore(ChatUtil.HIGHLIGHT_COLOR + "Choosen").build());
	}
}
