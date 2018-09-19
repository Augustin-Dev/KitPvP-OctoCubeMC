package fr.augustin.command;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.augustin.Main;
import fr.augustin.utils.GhostRank;
import fr.augustin.utils.Rank;

public class CommandCompte implements CommandExecutor {
	
	private Main main;

	public CommandCompte(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			
			Rank rank = main.sql.getRank(player);
			GhostRank ghostrank = main.sql.getGhostRank(player);
			
			if(args.length == 0) {
				
				main.title.sendTitle(player, "§cVotre compte :", "§3Description", 70);
				for(int i = 0; i < 30; i++) {
					player.sendMessage(" ");
				}
				
				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
				
				player.sendMessage("§6--------------------------");
				player.sendMessage("§6Vos Kill: §e"+main.sql.getKill(player));
				player.sendMessage("§cVos Morts: §e"+main.sql.getDead(player));
				player.sendMessage(" ");
				player.sendMessage("§c§lToken: §e"+main.sql.getToken(player));
				player.sendMessage("§6§lOctoCoins: §e"+main.sql.getOctoCoins(player));
				player.sendMessage(" ");
				player.sendMessage("§7Grade: §7[" + rank.getRank() + "§7] (§e"+rank.getPower()+"§7)");
				player.sendMessage("§7Rank: §7[" + getGrade(player) + "§7]");
				player.sendMessage(" ");
				player.sendMessage("§9Ms: §b"+((CraftPlayer)player).getHandle().ping);
				player.sendMessage("§8Votre IP: §c"+player.getAddress().getAddress());
				player.sendMessage(" ");
				player.sendMessage("§7Inscrit le: §b"+simpleDateFormat.format(new Date(player.getFirstPlayed())));
				player.sendMessage("§6--------------------------");
			}
			
			if(args.length == 1) {
				
				if(rank.getPower() >= 90 || ghostrank.getPower() >= 90) {
					
					String targetN = args[0];
					
					if(Bukkit.getPlayer(targetN) != null) {
						
						Player target = Bukkit.getPlayer(targetN);
						
						main.title.sendTitle(player, "§cCompte "+targetN+":", "§3Description", 70);
						for(int i = 0; i < 30; i++) {
							player.sendMessage(" ");
						}
						
						Rank ranks = main.sql.getRank(target);
						
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
						
						player.sendMessage("§6---------------------------------------");
						player.sendMessage("§6Kill: §e"+main.sql.getKill(target));
						player.sendMessage("§cMorts: §e"+main.sql.getDead(target));
						player.sendMessage(" ");
						player.sendMessage("§c§lToken: §e"+main.sql.getToken(target));
						player.sendMessage("§6§lOctoCoins: §e"+main.sql.getOctoCoins(target));
						player.sendMessage(" ");
						player.sendMessage("§7Grade: §7[" + ranks.getRank() + "§7] (§e"+ranks.getPower()+"§7)");
						player.sendMessage("§7Rank: §7[" + getGrade(target) + "§7]");
						player.sendMessage(" ");
						player.sendMessage("§9Ms: §b"+((CraftPlayer)target).getHandle().ping);
						player.sendMessage("§8IP: §c"+target.getAddress().getAddress());
						player.sendMessage(" ");
						player.sendMessage("§7Inscrit le: §b"+simpleDateFormat.format(new Date(target.getFirstPlayed())));
						player.sendMessage("§6---------------------------------------");
					
					}else {
						player.sendMessage("§cLe joueur §e"+targetN+" §cn'est pas connecter !");
					}
					
				}else {
					player.sendMessage("§8§l>> §cVous devez avoir au moins le grade §c§lAdmin §c!");
				}
				
			}
			
		}
		
		return false;
	}

	private String getRank(Player player) {

		if (player.getName().equals("Augustin_")) {
			return "§4§lFondateur";
		}else if(player.getName().equals("X_Storm64")) {
			return "§4§lFondateur";
		} else if (player.isOp()) {
			return "§c§lAdmin";
		} else if (player.hasPermission("modop.tab")) {
			return "§c§lModo§6§l+";
		} else if (player.hasPermission("modo.tab")) {
			return "§6§lModo";
		} else if (player.hasPermission("assistant.tab")) {
			return "§5§lAssistant";
		} else if (player.hasPermission("helpeur.tab")) {
			return "§a§lHelpeur";
		} else if (player.hasPermission("custom.tab")) {
			return "§7§lCustom";
		} else if (player.hasPermission("octovip.tab")) {
			return "§6§lOcto§e§lVIP";
		} else if (player.hasPermission("vipp.tab")) {
			return "§b§lVIP+";
		} else if (player.hasPermission("vip.tab")) {
			return "§e§lVIP";
		} else {
			return "§7§lJoueur";
		}
	}
	
	private String getGrade(Player player) {

		if(main.sql.getKill(player) >= 125000) {
			return "§0§lHématite";
		}else if(main.sql.getKill(player) >= 100000) {
			return "§c§lRubis";
		}else if (main.sql.getKill(player) >= 75000) {
			return "§a§lEmerald";
		} else if (main.sql.getKill(player) >= 45000) {
			return "§b§lDiamand";
		} else if (main.sql.getKill(player) >= 15000) {
			return "§e§lOr";
		} else if (main.sql.getKill(player) >= 5000) {
			return "§7§lArgent";
		} else if(main.sql.getKill(player) > 500){
			return "§6§lBronze";
		}else {
			return "§8§lArgile";
		}
	}

}
