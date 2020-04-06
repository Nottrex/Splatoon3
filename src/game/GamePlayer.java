package game;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import game.weapon.Weapon;
import game.weapon.WeaponType;
import game.util.TimeUtil;
import game.util.Util;

public class GamePlayer {
	
	public static final Set<Material> transparent = new HashSet<Material>();
	static {
		transparent.add(Material.AIR);
	}
	public static final int SPECIAL_SCORE = 1000;
	public static final int INK_MAX = 100;
	
	private long last_primary, last_secondary;
	private Weapon weapon_primary, weapon_secondary, weapon_special;
	private int ink;
	private boolean sneaking;
	private boolean onTeamColor;
	private int score;
	private int special_score;
	private Player player;
	private Game game;
	
	public GamePlayer(Player player, Game game) {
		this.player = player;
		this.game = game;
		sneaking = false;
		onTeamColor = false;
		ink = INK_MAX;
		score = 0;
		special_score = 0;
		last_primary = 0;
		last_secondary = 0;
		
		weapon_primary = Weapon.getPrimaryWeapons().get(0);
		weapon_secondary = Weapon.getSecondaryWeapons().get(0);
		weapon_special = Weapon.getSpecialWeapons().get(0);
	}
	
	public void action(WeaponType w, Action a) {
		if (sneaking) return;
		switch (w) {
		case PRIMARY:
			if (weapon_primary.getInk(a) <= ink && (TimeUtil.getTime() - last_primary) >= weapon_primary.getTime(a)) {
				addInk(-weapon_primary.getInk(a));
				weapon_primary.action(player, a);
				
				last_primary = TimeUtil.getTime();
			}
			break;
		case SECONDARY:
			if (weapon_secondary.getInk(a) <= ink && (TimeUtil.getTime() - last_secondary) >= weapon_secondary.getTime(a)) {
				addInk(-weapon_secondary.getInk(a));
				weapon_secondary.action(player, a);

				last_secondary = TimeUtil.getTime();
			}
			break;
		case SPECIAL:
			if (special_score >= SPECIAL_SCORE) {
				resetSpecialScore();
				weapon_special.action(player, a);
			}
			break;
		}
	}
	
	public int getInk() {
		return ink;
	}
	
	public void addInk(int ink) {		
		this.ink = Math.min(this.ink + ink, INK_MAX);
		
		if ((20*this.ink)/INK_MAX != (20*(this.ink-ink)/INK_MAX)) player.setFoodLevel((20*this.ink)/INK_MAX); 
	}
	
	
	public boolean isSneaking() {
		return sneaking;
	}
	
	public boolean canUseSpecialWeapon() {
		return special_score >= SPECIAL_SCORE;
	}
	
	public void reset() {
		resetSpecialScore();
		addInk(INK_MAX);
	}
	
	public void resetSpecialScore() {
		special_score = 0;

		player.setExp((1.0f*Math.min(SPECIAL_SCORE, special_score)) / SPECIAL_SCORE);
	}
	
	public double getSpecialScore() {
		return special_score;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addScore(int score) {
		this.score += score;
		this.special_score += score;
		
		if (special_score >= SPECIAL_SCORE && special_score - score < SPECIAL_SCORE) {
			player.playSound(player.getEyeLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
		}

		player.setExp((1.0f*Math.min(SPECIAL_SCORE, special_score)) / SPECIAL_SCORE);
	}

	public void setSneaking(boolean sneaking) {
		if (sneaking == this.sneaking) return;
				
		if (sneaking) {
			player.getInventory().setHeldItemSlot(6);
		} else {
			player.getInventory().setHeldItemSlot(0);
		}
		
		this.sneaking = sneaking;

		updateLeaping();
		updateEffects();
	}
	
	public void setOnTeamColor(boolean onTeamColor) {
		updateLeaping();
		
		if (onTeamColor == this.onTeamColor) return;
		
		this.onTeamColor = onTeamColor;
		
		updateEffects();
	}
	
	private void updateLeaping() {
		TeamColor c = game.getPlayerHandler().getTeam(player);
		
		Block look = player.getTargetBlock(transparent, 3);
		
		if (isSneaking() && (player.hasPotionEffect(PotionEffectType.LEVITATION) || look.getY() >= player.getLocation().getBlockY()) && c.isBlockTeam(look) && (c.isBlockTeam(player.getLocation().subtract(1, 0, 0).getBlock()) || c.isBlockTeam(player.getLocation().subtract(-1, 0, 0).getBlock()) || c.isBlockTeam(player.getLocation().subtract(0, 0, 1).getBlock()) || c.isBlockTeam(player.getLocation().subtract(0, 0, -1).getBlock()))) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 999999, 9, false, false), true);
		} else if (player.hasPotionEffect(PotionEffectType.LEVITATION)) {
			player.removePotionEffect(PotionEffectType.LEVITATION);
		}
	}
	
	private void updateEffects() {
		if (sneaking && onTeamColor) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1, false, false));
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 39, false, false));
			player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999, 2, false, false));
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 0, false, false));
		} else {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			player.removePotionEffect(PotionEffectType.SPEED);
			player.removePotionEffect(PotionEffectType.REGENERATION);
			player.removePotionEffect(PotionEffectType.JUMP);
		}
	}
	
	public boolean isOnTeamColor() {
		return onTeamColor;
	}
	
	public Weapon getPrimaryWeapon() {
		return weapon_primary;
	}
	
	public Weapon getSecondaryWeapon() {
		return weapon_secondary;
	}
	
	public Weapon getSpecialWeapon() {
		return weapon_special;
	}
	
	public void setPrimaryWeapon(Weapon weapon_primary) {
		this.weapon_primary = weapon_primary;
	}
	
	public void setSecondaryWeapon(Weapon weapon_secondary) {
		this.weapon_secondary = weapon_secondary;
	}
	
	public void setSpecialWeapon(Weapon weapon_special) {
		this.weapon_special = weapon_special;
	}
}