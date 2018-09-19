package fr.augustin.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class Structure {

	public void joinViP(PlayerJoinEvent event, Player player) {
		
		if(player.hasPermission("staff.join")){
			event.setJoinMessage("§cStaff "+player.getName() + " §cà rejoint le serveur !");
	}else if(player.hasPermission("vip.join")) {
			
			event.setJoinMessage("§eVIP "+player.getName()+" §eà rejoint le serveur !");
			
		}else if(player.hasPermission("vipp.join")) {
			event.setJoinMessage("§bVIP+ "+player.getName()+" §bà rejoint le serveur !");			
		}else if(player.hasPermission("octovip.join")) {
			event.setJoinMessage("§6Octo§eVIP "+player.getName()+" §eà rejoint le serveur !");						
		}else {
			event.setJoinMessage(null);
			
			
			
		}
		
	}
	
}
