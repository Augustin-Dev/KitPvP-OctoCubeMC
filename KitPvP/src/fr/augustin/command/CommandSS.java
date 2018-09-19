package fr.augustin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.augustin.Main;
import fr.augustin.custom.Inventaire;
import fr.augustin.utils.GhostRank;
import fr.augustin.utils.Rank;

public class CommandSS implements CommandExecutor {

	private Main main;
	
	public CommandSS(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			Rank rank = main.sql.getRank(player);
			GhostRank ghostrank = main.sql.getGhostRank(player);
			
			if(rank.getPower() >= 30 | ghostrank.getPower() >= 30) {
				
				if(args.length == 0) {
					
					player.sendMessage("§8§l>> §7Selectionnner un Joueur !");
					
				}
				
				if(args.length == 1) {
					
					String TargetName = args[0];
					
					if(Bukkit.getPlayer(TargetName) != null) {
						
						Player target = Bukkit.getPlayer(TargetName);
						
						Rank trank = main.sql.getRank(target);
						
						if(!(trank.getPower() >= 30) || rank.getPower() >= 90 || ghostrank.getPower() >= 90) {
							
							//if(!(player.getName() == target.getName())) {
								
								Inventaire ss = new Inventaire(9, "§cSanction §e"+target.getName());
								ss.selectss();
								ss.openInventaire(player);
								
								main.ssp.put(player, target);
								
						//	}else {
							//	player.sendMessage("§8§l>> §c§lVous ne pouvais pas vous sanctionner !");
							//}
							
						}else {
							
							player.sendMessage("§8§l>> §c§lVous ne pouvez pas sanctionner un membre du staff !");
							
						}
						
					}
					
				}
				
			}else {
				
				player.sendMessage("§8§l>> §c§lVous devez être un membre du staff pour executer cette commande !");
				
			}
			
			
		}
		
		return false;
	}

}
