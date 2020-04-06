package game.gamestate;

import game.GamePlayer;
import game.GameState;
import game.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.Plugin;

import game.Game;
import game.util.ChatUtil;
import game.util.PlayerUtil;

import java.util.ArrayList;
import java.util.List;

public class GameStateIngame extends GameStateBase {

	private int taskId;
	private int task_Trail;
	private int task_Ink;
	private PlayerHandler ph;

	private List<Projectile> projectiles;

	private int time = 301;
	public GameStateIngame(Game game, Plugin plugin) {
		super(game, plugin, GameState.INGAME);
		this.ph = game.getPlayerHandler();

		projectiles = new ArrayList<>();

		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				time--;
								
				if (time % 30 == 0) {
					ChatUtil.sendMessage("The game ends in " + ChatUtil.HIGHLIGHT_COLOR + getTime() + ChatUtil.COMMAND_COLOR + " Minutes");
				}
				
				if (time <= 0) {
					ChatUtil.sendMessage("The game is over");
					finish();
				}
				
				game.getScoreboard().update();
			}
		}, 0L, 20L);

		task_Trail = taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				projectiles.forEach(p -> p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation(), 0, 0.001, 1, 0, 1, new Particle.DustOptions(ph.getTeam((Player) p.getShooter()).getColor(), 1)));
			}
		}, 0L, 2L);

		task_Ink = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			int i = 0;
			@Override
			public void run() {
				i++;
				for (Player p: ph.getPlayers()) {
					GamePlayer gp = ph.getGamePlayer(p);
					if (gp.isSneaking() && gp.isInvisible()) {
						gp.addInk(5);
					} else if (i % 4 == 0){
						gp.addInk(1);
					}
				}
			}
		}, 0L, 2L);
		
		ph.addPlayersToTeams();
		
		if (time < 300) game.getScoreboard().update();
	}

	@Override
	public void stop() {
		Bukkit.getScheduler().cancelTask(taskId);
		Bukkit.getScheduler().cancelTask(task_Ink);
		Bukkit.getScheduler().cancelTask(task_Trail);
	}

	@Override
	public void preparePlayer(Player p, PrepareType type) {
		
		PlayerUtil.preparePlayer(p);
		p.teleport(ph.getTeamSpawn(ph.getTeam(p)));
		
		GamePlayer gp = ph.getGamePlayer(p);
		
		gp.reset();
		
		p.getInventory().setItem(0, gp.getPrimaryWeapon().getItem());
		p.getInventory().setItem(1, gp.getSecondaryWeapon().getItem());
		p.getInventory().setItem(2, gp.getSpecialWeapon().getItem());
	}

	@Override
	public void prepareSpectator(Player p, PrepareType type) {
		PlayerUtil.prepareSpectator(p);
		p.teleport(game.getGameMap().getCenter());
	}

	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}

	public void removeProjectile(Projectile p) {
		projectiles.remove(p);
	}


	public String getTime() {
		String seconds = String.valueOf(time % 60);
		while (seconds.length() < 2) {
			seconds = "0" + seconds;
		}
		return (time / 60) + ":" + seconds;
	}
}
