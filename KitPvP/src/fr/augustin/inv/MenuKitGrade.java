package fr.augustin.inv;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

public enum MenuKitGrade {

	JOUEUR(1, new ItemStack(Material.LEATHER_CHESTPLATE), "§7§lJoueur"),
	VIP(3, new ItemStack(Material.IRON_CHESTPLATE), "§e§lVIP"),
	VIPP(5, new ItemStack(Material.GOLD_CHESTPLATE), "§b§lVIP+"),
	OCTOVIP(7, new ItemStack(Material.DIAMOND_CHESTPLATE), "§6§lOcto§e§lVIP");
	
	private int slot;
	private ItemStack it;
	private String name;
	
	private MenuKitGrade(int slot, ItemStack it, String name) {
		this.slot = slot;
		this.it = it;
		this.name = name;
	}
	
	public ItemStack getItem() {
		ItemStack i = it;
		ItemMeta iM = it.getItemMeta();
		iM.setDisplayName(name);
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
	
}
