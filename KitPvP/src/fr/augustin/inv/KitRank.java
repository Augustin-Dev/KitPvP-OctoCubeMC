package fr.augustin.inv;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

public enum KitRank {

	GARDE(3, new ItemStack(Material.GOLD_CHESTPLATE), "§9Kit Grade"),
	RANK(5, new ItemStack(Material.IRON_CHESTPLATE), "§9Kit rank");
	
	private int slot;
	private ItemStack it;
	private String name;
	
	private KitRank(int slot, ItemStack it, String name) {
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
