package fr.augustin.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.augustin.Main;
import fr.augustin.inv.SelectSS;
import fr.augustin.utils.Rank;

public class Sanction implements Listener {

	private Main main;
	
	public Sanction(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onClickSanction(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		ItemStack it = event.getCurrentItem();
		Inventory inv = event.getInventory();
		
		Rank rank = main.sql.getRank(player);
			
			if(it != null && it.equals(SelectSS.SPAMFLOOD.getItem())) {
				event.setCancelled(true);
				player.closeInventory();
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute "+inv.getName().substring(13) + " 30m Spam / Flood");
				
				return;
			
		}
			if(it != null && it.equals(SelectSS.PROVOCATION.getItem())) {
				event.setCancelled(true);
				player.closeInventory();
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute "+inv.getName().substring(13) + " 30m Provocation");
				
				return;
				
			}
			if(it != null && it.equals(SelectSS.LANGAGE.getItem())) {
				event.setCancelled(true);
				player.closeInventory();
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute "+inv.getName().substring(13) + " 1h Langage");
				
				return;
				
			}
			if(it != null && it.equals(SelectSS.INSULTE.getItem())) {
				event.setCancelled(true);
				player.closeInventory();
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute "+inv.getName().substring(13) + " 2h insulte");
				
				return;
				
			}
			if(it != null && it.equals(SelectSS.CHEAT.getItem())) {
				event.setCancelled(true);
				player.closeInventory();
				
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban "+inv.getName().substring(13) + " 30d Cheat");
				
				return;
				
			}
			if(it != null && it.equals(SelectSS.DOXRAT.getItem())) {
				event.setCancelled(true);
				player.closeInventory();
				
				String targets = inv.getName().substring(13);
				Player target = Bukkit.getPlayer(targets);
				
				
				Rank t = main.sql.getRank(target);
				
				if(t.getPower() >= 10) {
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban "+inv.getName().substring(13) + " 30y DOX / DDOS / RAT");
					
				}else {
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban "+inv.getName().substring(13) + " DOX / DDOS / RAT");
					
				}
				
				return;
				
			}
		
	}

}
