package fr.augustin.utils;

import java.util.HashMap;
import java.util.Map;

public enum Rank {

	JOUEUR(0, "§7§lJoueur §7"),
	VIP(10, "§e§lVIP §e"),
	VIPP(15, "§b§lVIP+ §b"),
	GVIP(20, "§6§lG§e§lVIP §e"),
	OCTOVIP(25, "§6§lOcto§e§lVIP §e"),
	ASSISTANT(30, "§5§lAssistant §5"),
	MODERATEUR(50, "§6§lModérateur §6"),
	MODERATEURP(60, "§c§lModérateur§6§l+ §c"),
	ADMINISTRATEUR(90, "§c§lAdmin §c"),
	FONDATEUR(100, "§4§lFondateur §4");
	
	private int power;
	private String displayName;
	public static Map<Integer, Rank> grade = new HashMap<>();
	
	private Rank(int power, String displayName) {
		this.power = power;
		this.displayName = displayName;
	}
	
	static {
		for(Rank r : Rank.values()) {
			grade.put(r.getPower(), r);
		}
	}
	
	public int getPower() {
		return power;
	}
	
	public String getRank() {
		return displayName;
	}
	
	public static Rank powerToRank(int power) {
		return grade.get(power);
	}
	
}
