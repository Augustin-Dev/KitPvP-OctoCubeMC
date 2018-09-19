package fr.augustin.duel;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.augustin.Main;
import fr.augustin.inv.JoinPlayer;

public class DuelGame extends BukkitRunnable{
	
	private Main main;
	int timer = 120;

	public DuelGame(Main main) {
		this.main = main;
	}

	@Override
	public void run() {
		
		timer--;
		
		if(timer == 60) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (main.duel.contains(p)) {
				main.title.sendActionBar(p, "§b1VS1: §c§lIl vous reste 1 Minute !");
			}
		}
		}
		
		if(main.duel.size() == 2) {
			this.cancel();
			return;
		}
		
		if(timer == 0) {
			
			this.cancel();
			return;
		}
		
	}

}
