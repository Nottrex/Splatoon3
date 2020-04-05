package game;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import game.weapon.Weapon;
import game.util.ItemUtil;

public class Inventories {
	
	public static void cleanUp() {
		inv.clear();
	}
	
	public static Inventory inv = Bukkit.createInventory(null, 9, "TeamSelection");
	public static Inventory getTeamSelectionInventory(PlayerHandler pl) {
		for (int i = 0; i < pl.getTeams().size(); i++) {
			TeamColor color = pl.getTeams().get(i);
			inv.setItem(i, Item.getTeamItem(color, pl.getPlayersInTeam(color), pl.getTeamSize()));
		}
		
		return inv;
	}
	
	public static Inventory getWeaponSelectionInventory() {
		Inventory inventory = Bukkit.createInventory(null, 9, "WeaponSelection");
		inventory.setItem(2, Item.getWeaponSelectionItemPrimary());
		inventory.setItem(4, Item.getWeaponSelectionItemSecondary());
		inventory.setItem(6, Item.getWeaponSelectionItemSpecial());		
		return inventory;
	}
	
	public static Inventory getWeaponSelectionInventoryPrimary(GamePlayer gp) {
		Inventory inventory = Bukkit.createInventory(null, ((Weapon.getPrimaryWeapons().size()-1)/9) * 9 + 9, "PrimaryWeaponSelection");
		for (int i = 0; i < Weapon.getPrimaryWeapons().size(); i++) {
			if (gp.getPrimaryWeapon().equals(Weapon.getPrimaryWeapons().get(i))) {
				inventory.setItem(i, ItemUtil.addChoosenEffect(Weapon.getPrimaryWeapons().get(i).getItem()));
			} else {
				inventory.setItem(i, Weapon.getPrimaryWeapons().get(i).getItem());
			}
		}
		return inventory;
	}
	
	public static Inventory getWeaponSelectionInventorySecondary(GamePlayer gp) {
		Inventory inventory = Bukkit.createInventory(null, ((Weapon.getSecondaryWeapons().size()-1)/9) * 9 + 9, "SecondaryWeaponSelection");
		for (int i = 0; i < Weapon.getSecondaryWeapons().size(); i++) {
			if (gp.getSecondaryWeapon().equals(Weapon.getSecondaryWeapons().get(i))) {
				inventory.setItem(i, ItemUtil.addChoosenEffect(Weapon.getSecondaryWeapons().get(i).getItem()));
			} else {
				inventory.setItem(i, Weapon.getSecondaryWeapons().get(i).getItem());
			}
		}
		return inventory;
	}
	
	public static Inventory getWeaponSelectionInventorySpecial(GamePlayer gp) {
		Inventory inventory = Bukkit.createInventory(null, ((Weapon.getSpecialWeapons().size()-1)/9) * 9 + 9, "SpecialWeaponSelection");
		for (int i = 0; i < Weapon.getSpecialWeapons().size(); i++) {
			if (gp.getSpecialWeapon().equals(Weapon.getSpecialWeapons().get(i))) {
				inventory.setItem(i, ItemUtil.addChoosenEffect(Weapon.getSpecialWeapons().get(i).getItem()));
			} else {
				inventory.setItem(i, Weapon.getSpecialWeapons().get(i).getItem());
			}
		}
		return inventory;
	}
}
