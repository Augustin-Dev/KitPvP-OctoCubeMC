package fr.augustin.cache;

import org.bukkit.entity.Player;

import fr.augustin.Main;

public class PlayerDataManager {
	
	private Main main;

	public PlayerDataManager(Main main) {
		this.main = main;
	}
	
	public void loadPlayerData(Player player){
        if(!main.dataPlayers.containsKey(player)){
            PlayerData pData = main.sql.createPlayerData(player);
            main.dataPlayers.put(player, pData);
        }
    }
   
    public void savePlayerData(Player player){
        if(main.dataPlayers.containsKey(player)){
            main.sql.updatePlayerData(player);
        }      
    }

}
