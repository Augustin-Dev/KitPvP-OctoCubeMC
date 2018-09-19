package fr.augustin.inv;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import org.bukkit.Material;

public enum SelectSS {

	SPAMFLOOD(0, new ItemStack(Material.PAPER), "§3Spam / Flood", "§7Mute: §e30 §6Minutes"),
	PROVOCATION(1, new ItemStack(Material.PAPER), "§9Provocation", "§7Mute: §e30 §6Minutes"),
	LANGAGE(2, new ItemStack(Material.PAPER), "§7Langage", "§7Mute: §e1 §6Heures"),
	INSULTE(3, new ItemStack(Material.PAPER), "§6Insulte", "§7Mute: §e2 §6Heures"),
	CHEAT(4, new ItemStack(Material.DIAMOND_AXE), "§cCheat", "§7Ban: §e30 §6Jours"),
	RECIDIVE(5, new ItemStack(Material.DIAMOND_AXE), "§4Récidive", "§7ban: §eà vie"),
	DOXRAT(6, new ItemStack(Material.DIAMOND_AXE), "§4DOX / RAT / DDOS", "§0Blacklist");
	
	
	private int slot;
	private ItemStack it;
	private String name, lore;
	
	private SelectSS(int slot, ItemStack it, String name, String lore) {
		this.slot = slot;
		this.it = it;
		this.name = name;
		this.lore = lore;
	}

	public ItemStack getItem() {
		ItemStack i = it;
		ItemMeta iM = it.getItemMeta();
		iM.setDisplayName(name);
		iM.setLore(Arrays.asList(lore));
		it.setItemMeta(iM);
		return i;
	}
	
	public int getSlot() {
		return slot;
	}

	public ItemStack getIt() {
		return it;
	}

	public String getName() {
		return name;
	}

	public String getLore() {
		return lore;
	}
	
	
	
}
