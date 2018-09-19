package fr.augustin.inv;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

public enum MenuKitRank {

	BRONZE(0, new ItemStack(Material.LEATHER_CHESTPLATE), "§8§lBronze"),
	ARGENT(2, new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§7§lArgent"),
	OR(4, new ItemStack(Material.IRON_CHESTPLATE), "§e§lOr"),
	DIAMAND(6, new ItemStack(Material.GOLD_CHESTPLATE), "§b§lDiamand"),
	EMERALD(8, new ItemStack(Material.DIAMOND_CHESTPLATE), "§a§lEmerald");
	
	private int slot;
	private ItemStack it;
	private String name;
	
	private MenuKitRank(int slot, ItemStack it, String name) {
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
