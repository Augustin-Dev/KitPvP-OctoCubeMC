package fr.augustin.utils;

import java.util.HashMap;
import java.util.Map;

public enum GhostRank {

	JOUEUR(0),
	ASSISTANT(30),
	MODERATEUR(50),
	MODERATEURP(60),
	ADMINISTRATEUR(90),
	FONDATEUR(100);
	
	private int power;
	public static Map<Integer, GhostRank> ghostrank = new HashMap<>();
	
	private GhostRank(int power) {
		this.power = power;
	}
	
	static {
		for(GhostRank rs : GhostRank.values()) {
			ghostrank.put(rs.getPower(), rs);
		}
	}
	
	public int getPower() {
		return power;
	}
	
	
	public static GhostRank powerToRank(int power) {
		return ghostrank.get(power);
	}
	
}
