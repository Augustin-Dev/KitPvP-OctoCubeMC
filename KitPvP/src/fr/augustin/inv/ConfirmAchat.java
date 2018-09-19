package fr.augustin.inv;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

public enum ConfirmAchat {

	INFO(4, new ItemStack(Material.PAPER), "§9Information"),
	ACCEPTER(2, new ItemStack(Material.EMERALD_BLOCK), "§a§lAccepter l'achat"),
	REFUSER(6, new ItemStack(Material.REDSTONE_BLOCK), "§c§lRefuser l'achat");
	
	private int slot;
	private ItemStack it;
	private String name;
	
	private ConfirmAchat(int slot, ItemStack it, String name) {
		this.slot = slot;
		this.it = it;
		this.name = name;
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
	
	public ItemStack getItem() {
		ItemStack i = it;
		ItemMeta iM = it.getItemMeta();
		iM.setDisplayName(name);
		it.setItemMeta(iM);
		return i;
	}
	
}
