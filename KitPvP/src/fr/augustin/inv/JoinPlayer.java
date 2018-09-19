package fr.augustin.inv;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

public enum JoinPlayer {

	MENU(new ItemStack(Material.CHEST), "§6§lMenu Principal !");
	
	private ItemStack it;
	private String name;
	
	private JoinPlayer(ItemStack it, String name) {
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
	
	public ItemStack getIt() {
		return it;
	}

	public String getName() {
		return name;
	}
	
	
	
}
