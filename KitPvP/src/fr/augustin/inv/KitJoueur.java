package fr.augustin.inv;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

public enum KitJoueur {

	SUPERFLY(3, new ItemStack(Material.FEATHER), "§7§lJoueur"),
	SUPERJUMP(5, new ItemStack(Material.BLAZE_ROD), "§6§lSuper Joueur");
	
	private int slot;
	private ItemStack it;
	private String name;
	
	private KitJoueur(int slot, ItemStack it, String name) {
		
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
