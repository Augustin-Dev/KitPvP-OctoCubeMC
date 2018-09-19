package fr.augustin.inv;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

public enum MenuP {

	KIT(1, new ItemStack(Material.CHEST), "§3Select kits !"),
	SHOP(3, new ItemStack(Material.GOLD_INGOT), "§eBoutique"),
	DUEL(4, new ItemStack(Material.IRON_SWORD), "§bDuel 1VS1"),
	PARTICLE(5, new ItemStack(Material.FIREWORK), "§cParticule"),
	QUETES(7, new ItemStack(Material.PAPER), "§9Select Quête !");
	
	private int slot;
	private ItemStack it;
	private String name;
	
	private MenuP(int slot, ItemStack it, String name) {
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
