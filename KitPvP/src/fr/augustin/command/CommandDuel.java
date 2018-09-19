package fr.augustin.command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandDuel implements CommandExecutor {

	private Map<Player, Player> players = new HashMap<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(args.length == 0) {
				player.sendMessage("§6OctoDuel > §c/duel <player>");
				player.sendMessage("§6OctoDuel > §c/duel accept");
				player.sendMessage("§6OctoDuel > §c/duel deny");
				return true;
			}
			
			if(args.length == 1) {
				
				String targetName = args[0];
				
				if(args[0].equalsIgnoreCase("accept")) {
					
					if(players.containsKey(player)) {
						
						player.sendMessage("§6OctoDuel > §a§lLe duel se lance !");
						Player fplayer = players.get(player);
						player.sendMessage("§cAdversaire: §9"+fplayer.getName());
						fplayer.sendMessage("§6OctoDuel > §a§lLe duel se lance !");
						fplayer.sendMessage("§cAdversaire: §9"+player.getName());
						
						players.remove(player);
						
					}
					
				}else if(args[0].equalsIgnoreCase("deny")) {
					
					if(players.containsKey(player)) {
						
						player.sendMessage("§6OctoDuel > §c§lVous avez refuser le Duel !");
						Player fplayer = players.get(player);
						fplayer.sendMessage("§6OctoDuel > §b"+player.getName()+" §cà refuser le duel !");
						
						players.remove(player);
						
						
					}
					
				}else if(Bukkit.getPlayer(targetName) != null) {
					
					Player target = Bukkit.getPlayer(targetName);
					
					if(players.containsKey(target)) {
						
						player.sendMessage("§6OctoDuel > §cCe joueur à déjà une demande de duel !");
						return true;
					}
					
					players.put(player, target);
					player.sendMessage("§6OctoDuel > §a§lVous avez demander un duel à §b"+targetName);
					target.sendMessage("§6OctoDuel > §a§lDemande de duel de §e"+player.getName());
					
					TextComponent h = new TextComponent("§7[");
					TextComponent hh = new TextComponent("§7]");
					TextComponent accept = new TextComponent("§a§lAccept");
					TextComponent deny = new TextComponent("§c§lDeny");
					accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel accept"));
					deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel deny"));
					target.spigot().sendMessage(h,accept,hh,h,deny,hh);
					
				}else {
					player.sendMessage("§6OctoDuel > §cle joueur §b"+targetName+" §cn'est pas en ligne !");
				}
				
			}
			
		}
		
		return false;
	}

}
