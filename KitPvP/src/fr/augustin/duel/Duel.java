package fr.augustin.duel;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.augustin.Main;
import fr.augustin.utils.ScoreboardSign;

public class Duel extends BukkitRunnable{

	private Main main;
	int timer = 10;
	
	public Duel(Main main) {
		this.main = main;
	}

	@Override
	public void run() {
		
		timer--;
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (main.duel.contains(p)) {
				main.title.sendActionBar(p,
						"§b1VS1: §c§lStart: §e"+timer+"'s");
			}
		}
		
		if (main.duel.size() < 2) {

			for (Player p : Bukkit.getOnlinePlayers()) {
				if (main.duel.contains(p)) {
					main.title.sendActionBar(p,
							"§b1VS1: §c§lIl n'y a pas assez de Joueur !");
					this.cancel();
				}
			}

			this.cancel();
			return;
		}

		
		if(timer == 0) {
			for (int i = 0; i < main.duel.size(); i++) {

				Player duelp = main.duel.get(i);
				Location spawnduel = main.spawnduel.get(i);
				
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (main.duel.contains(p)) {
						main.title.sendTitle(p, "§9", "§b1VS1: §e§lLancement du Duel !", 70);
					}
				}
				
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (main.duel.contains(p)) {
						getDuelKit(p);
					}
				}

				duelp.teleport(spawnduel);

			}
			Player one = main.duel.get(0);
			Player two = main.duel.get(1);
			
			main.title.sendActionBar(one, "§cTon adversaire: §b"+two.getName());
			main.title.sendActionBar(two, "§cTon adversaire: §b"+one.getName());
			
			DuelGame duelgame = new DuelGame(main);
			duelgame.runTaskTimer(main, 0, 20);
			
			this.cancel();
			return;
		}
		
	}

	private void getDuelKit(Player p) {
		
		Potion pt = new Potion(PotionType.INSTANT_HEAL, 2, true);
		
		p.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
		p.getInventory().setItem(1, pt.toItemStack(1));
		p.getInventory().setItem(2, pt.toItemStack(1));
		p.getInventory().setItem(3, pt.toItemStack(1));
		p.getInventory().setItem(4, pt.toItemStack(1));
		p.getInventory().setItem(5, pt.toItemStack(1));
		p.getInventory().setItem(6, pt.toItemStack(1));
		p.getInventory().setItem(7, pt.toItemStack(1));
		p.getInventory().setItem(8, pt.toItemStack(1));
		p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
	}

}
