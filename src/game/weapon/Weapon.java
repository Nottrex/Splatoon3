package game.weapon;

import java.util.ArrayList;
import java.util.List;

import game.weapon.primaryweapons.Charger;
import game.weapon.primaryweapons.Roller;
import game.weapon.primaryweapons.Shooter;
import game.weapon.secondaryweapons.BurstBomb;
import game.weapon.secondaryweapons.InkMine;
import game.weapon.secondaryweapons.Sprinkler;
import game.weapon.specialweapons.BombRush;
import game.weapon.specialweapons.Inkstrike;
import game.weapon.specialweapons.Inkzooka;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public abstract class Weapon {	
	
	private static List<Weapon> primary_weapons = new ArrayList<Weapon>();
	private static List<Weapon> secondary_weapons = new ArrayList<Weapon>();
	private static List<Weapon> special_weapons = new ArrayList<Weapon>();
	
	static {
		addPrimaryWeapon(new Shooter());
		addPrimaryWeapon(new Charger());
		addPrimaryWeapon(new Roller());
		
		addSecondaryWeapon(new BurstBomb());
		addSecondaryWeapon(new Sprinkler());
		addSecondaryWeapon(new InkMine());
		
		addSpecialWeapon(new BombRush());
		addSpecialWeapon(new Inkstrike());
		addSpecialWeapon(new Inkzooka());
	}
	
	public static void addPrimaryWeapon(Weapon w) {
		primary_weapons.add(w);
	}
	
	public static void addSecondaryWeapon(Weapon w) {
		secondary_weapons.add(w);
	}
	
	public static void addSpecialWeapon(Weapon w) {
		special_weapons.add(w);
	}
	
	public static List<Weapon> getPrimaryWeapons() {
		return primary_weapons;
	}
	
	public static List<Weapon> getSecondaryWeapons() {
		return secondary_weapons;
	}
	
	public static List<Weapon> getSpecialWeapons() {
		return special_weapons;
	}
	
	private String name;
	private int ink;
	private int time;
	public Weapon(int ink, int time, String name) {
		this.ink = ink;
		this.time = time;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTime(Action a) {
		return time;
	}
	
	public int getInk(Action a) {
		return ink;
	}
	
	public abstract void action(Player p, Action action);
	public abstract ItemStack getItem();
}
