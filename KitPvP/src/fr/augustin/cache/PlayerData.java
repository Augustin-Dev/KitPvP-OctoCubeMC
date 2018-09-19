package fr.augustin.cache;

import fr.augustin.utils.GhostRank;
import fr.augustin.utils.Rank;

public class PlayerData {

	private int kill;
	private int dead;
	private int token;
	private int octocoins;
	private Rank rank;
	private GhostRank ghostrank;
	
	public int getKill() {
		return kill;
	}
	public void setKill(int kill) {
		this.kill = kill;
	}
	public int getDead() {
		return dead;
	}
	public void setDead(int dead) {
		this.dead = dead;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}
	public int getOctocoins() {
		return octocoins;
	}
	public void setOctocoins(int octocoins) {
		this.octocoins = octocoins;
	}
	public Rank getRank() {
		return rank;
	}
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	public GhostRank getGhostrank() {
		return ghostrank;
	}
	public void setGhostrank(GhostRank ghostrank) {
		this.ghostrank = ghostrank;
	}
	
	
	
}
