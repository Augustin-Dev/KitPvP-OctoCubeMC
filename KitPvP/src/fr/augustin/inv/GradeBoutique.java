package fr.augustin.inv;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum GradeBoutique {

	VIP(2, new ItemStack(Material.GOLD_INGOT, 1), "§e§lVIP", "vip.tab", 324),
	VIPP(4, new ItemStack(Material.DIAMOND, 1), "§b§lVIP+", "vipp.tab", 540),
	OCTOVIP(6, new ItemStack(Material.EMERALD, 1), "§6§lOcto§e§lVIP", "octovip.tab", 935);
	
	int slot;
	ItemStack it;
	String name;
	String perm;
	int tokens;
	
	private GradeBoutique(int slot, ItemStack it, String name, String perm, int tokens) {
		this.slot = slot;
		this.it = it;
		this.name = name;
		this.perm = perm;
		this.tokens = tokens;
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

	public String getPerm() {
		return perm;
	}

	public int getTokens() {
		return tokens;
	}
	
	
	
}
