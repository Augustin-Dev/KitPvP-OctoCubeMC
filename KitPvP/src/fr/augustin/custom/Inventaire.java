package fr.augustin.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.augustin.inv.GradeBoutique;
import fr.augustin.inv.KitJoueur;
import fr.augustin.inv.KitRank;
import fr.augustin.inv.MapSelector;
import fr.augustin.inv.ConfirmAchat; 
import fr.augustin.inv.MenuKitGrade;
import fr.augustin.inv.MenuKitRank;
import fr.augustin.inv.MenuP;
import fr.augustin.inv.SelectSS;

public class Inventaire {

	private Inventory inv;
	
	public Inventaire(int size, String name) {
		
		inv = Bukkit.createInventory(null, size, name);
		
	}
	
	public void menu() {
		for(MenuP item : MenuP.values()) {
			inv.setItem(item.getSlot(), item.getItem());
		}
	}
	
	
	
	public void menukit() {
		for(KitRank item : KitRank.values()) {
			inv.setItem(item.getSlot(), item.getItem());
		}
	}
	
	public void selectss() {
		for(SelectSS item : SelectSS.values()) {
			inv.setItem(item.getSlot(), item.getItem());
		}
	}
	
	public void menukitJoueur() {
		for(KitJoueur item : KitJoueur.values()) {
			inv.setItem(item.getSlot(), item.getItem());
		}
	}
	
	public void menugradeBoutique() {
		for(GradeBoutique item : GradeBoutique.values()) {
			inv.setItem(item.getSlot(), item.getItem());
		}
	}
	
	public void mkitgrade() {
		for(MenuKitGrade item : MenuKitGrade.values()) {
			inv.setItem(item.getSlot(), item.getItem());
		}
	}
	public void mapSelector() {
		for(MapSelector item : MapSelector.values()) {
			inv.setItem(item.getSlot(), item.getItem());
		}
	}
	
	public void mkitrank() {
		for(MenuKitRank item : MenuKitRank.values()) {
			inv.setItem(item.getSlot(), item.getItem());
		}
	}
	
	public void ConfirmAchats() {
		for(ConfirmAchat item : ConfirmAchat.values()) {
			inv.setItem(item.getSlot(), item.getItem());
		}
	}
	
	public void openInventaire(Player player) {
		
		player.openInventory(inv);
		
	}
	
}
