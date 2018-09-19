package fr.augustin.inv;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import org.bukkit.Material;

public enum MapSelector {

	SCPACE(2, new ItemStack(Material.GLASS, 1), "§bSpace", "§aMap §9Selector", " ", "§2Map create by §bSirop"),
	PLANE(4, new ItemStack(Material.GRASS, 1), "§2Plane", "§aMap §9Selector", " ", "§2Map create by §bSirop"),
	DESERT(6, new ItemStack(Material.SAND, 1), "§6Désert", "§aMap §9Selector", " ", "§2Map create by §bSirop");

	private int slot;
	private ItemStack it;
	private String name;
	private String lore1;
	private String lore2;
	private String lore3;
	
	private MapSelector(int slot, ItemStack it, String name, String lore1, String lore2, String lore3) {
		this.slot = slot;
		this.it = it;
		this.name = name;
		this.lore1 = lore1;
		this.lore2 = lore2;
		this.lore3 = lore3;
	}
	
	public ItemStack getItem() {
		ItemStack i = it;
		ItemMeta iM = it.getItemMeta();
		iM.setDisplayName(name);
		iM.setLore(Arrays.asList(lore1, lore2, lore3));
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

	public String getLore1() {
		return lore1;
	}

	public String getLore2() {
		return lore2;
	}

	public String getLore3() {
		return lore3;
	}
	
	
	
}
