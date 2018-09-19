package fr.augustin.event;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoM extends BukkitRunnable{

	int timer = 600;
	
	@Override
	public void run() {
		
		timer--;
		
		if(timer == 300) {
			
			Bukkit.broadcastMessage("§a§lBoutique: §9Pour acheter des grades ou des keys, rendez-vous sur : §eoctocubemc.com §9!");
			Bukkit.broadcastMessage("§a§lBoutique: §cUn code de §6-§e5 §9% §cest disponible sur le site ! §bProfitez-en !!!");
			
		}
		
		if(timer == 0) {
			
			timer += 600;
			
		}
		
	}

	
	
}
