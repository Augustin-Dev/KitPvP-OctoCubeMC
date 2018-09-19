package fr.augustin.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.augustin.Main;
import fr.augustin.inv.JoinPlayer;
import fr.augustin.utils.GhostRank;
import fr.augustin.utils.Rank;

public class CommandeBuildMod implements CommandExecutor {
	
	private Main main;

	public CommandeBuildMod(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			Rank rank = main.sql.getRank(player);
			GhostRank ghostrank = main.sql.getGhostRank(player);
			
			if(rank.getPower() >= 90 || ghostrank.getPower() >= 90) {
				
				if(!main.buildmod.contains(player)) {
					
					main.buildmod.add(player);
					player.setGameMode(GameMode.CREATIVE);
					main.title.sendActionBar(player, "§a§lVous êtes en BuildMod");
					player.getInventory().clear();
					player.getInventory().setArmorContents(null);
					
				}else {
					main.buildmod.remove(player);
					player.setGameMode(GameMode.ADVENTURE);
					main.title.sendActionBar(player, "§c§lVous n'êtes plus en BuildMod");
					Location spawn = new Location(Bukkit.getWorld("world"), 3.596, 52.06250, -1.451, 89.9f, -1.2f);
					player.teleport(spawn);
					main.title.sendTitle(player, "§cOctoCubeMC", "§bBienvenue !", 70);
					player.setFoodLevel(20);
					player.getInventory().setItem(4, JoinPlayer.MENU.getItem());
				}
				
			}else {
				player.sendMessage("§8§l>> §cVous devez avoir au moins le grade §c§lAdmin §c!");
			}
			
		}
		
		return false;
	}

}
