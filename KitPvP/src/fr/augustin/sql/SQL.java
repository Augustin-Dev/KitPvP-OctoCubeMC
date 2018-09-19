package fr.augustin.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import fr.augustin.Main;
import fr.augustin.cache.PlayerData;
import fr.augustin.utils.GhostRank;
import fr.augustin.utils.Rank;

public class SQL {

	private Main main;

	public SQL(Main main) {
		this.main = main;
	}

	public void createAccount(Player player) {
		if (!hasAccount(player)) {
			// INSERT

			try {
				PreparedStatement q = main.connection
						.prepareStatement("INSERT INTO membre(player,kills,dead,token,octocoins,grade,ghostrank) VALUES (?,?,?,?,?,?,?)");
				q.setString(1, player.getName().toString());
				q.setInt(2, 0);
				q.setInt(3, 0);
				q.setInt(4, 0);
				q.setInt(5, 150);
				q.setInt(6, Rank.JOUEUR.getPower());
				q.setInt(7, GhostRank.JOUEUR.getPower());
				q.execute();
				q.close();
				main.title.sendActionBar(player, "§cOctoCubeMC: §aVotre compte à bien été créer !");
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public boolean hasAccount(Player player) {
		// SELECT

		try {
			PreparedStatement q = main.connection.prepareStatement("SELECT player FROM membre WHERE player = ?");
			q.setString(1, player.getName().toString());
			ResultSet resultat = q.executeQuery();
			boolean hasAccount = resultat.next();
			q.close();
			main.title.sendActionBar(player, "§cOctoCubeMC: §9Votre compte à bien été charger !");
			return hasAccount;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public int getKill(Player player) {
		// SELECT
		if (main.dataPlayers.containsKey(player)) {
			PlayerData dataP = main.dataPlayers.get(player);
			return dataP.getKill();
		}

		return 0;
	}
	
	public int getToken(Player player) {
		// SELECT
		if (main.dataPlayers.containsKey(player)) {
			PlayerData dataP = main.dataPlayers.get(player);
			return dataP.getToken();
		}

		return 0;
	}
	
	public int getOctoCoins(Player player) {
		// SELECT
		if (main.dataPlayers.containsKey(player)) {
			PlayerData dataP = main.dataPlayers.get(player);
			return dataP.getOctocoins();
		}

		return 0;
	}

	public int getDead(Player player) {
		// SELECT
		if (main.dataPlayers.containsKey(player)) {
			PlayerData dataP = main.dataPlayers.get(player);
			return dataP.getDead();
		}

		return 0;
	}
	
	public void setRank(Player player, Rank rank) {
		if(main.dataPlayers.containsKey(player)) {
			PlayerData dataP = main.dataPlayers.get(player);
			dataP.setRank(rank);
			main.dataPlayers.remove(player);
			main.dataPlayers.put(player, dataP);
		}
	}
	
	public Rank getRank(Player player) {
		if(main.dataPlayers.containsKey(player)) {
			PlayerData dataP = main.dataPlayers.get(player);
			return dataP.getRank();
		}
		return Rank.JOUEUR;
	}
	
	public GhostRank getGhostRank(Player player) {
		if(main.dataPlayers.containsKey(player)) {
			PlayerData dataP = main.dataPlayers.get(player);
			return dataP.getGhostrank();
		}
		return GhostRank.JOUEUR;
	}

	public void addKill(Player player, int amount) {

		if (main.dataPlayers.containsKey(player)) {
			PlayerData dataP = main.dataPlayers.get(player);
			int kill = dataP.getKill() + amount;
			dataP.setKill(kill);
			main.dataPlayers.remove(player);
			main.dataPlayers.put(player, dataP);
		}

	}

	public void addDead(Player player, int amount) {

		if (main.dataPlayers.containsKey(player)) {
			PlayerData dataP = main.dataPlayers.get(player);
			int dead = dataP.getDead() + amount;
			dataP.setDead(dead);
			main.dataPlayers.remove(player);
			main.dataPlayers.put(player, dataP);
		}

	}
	
	public void addToken(Player player, int amount) {

		if (main.dataPlayers.containsKey(player)) {
			PlayerData dataP = main.dataPlayers.get(player);
			int token = dataP.getToken() + amount;
			dataP.setToken(token);
			main.dataPlayers.remove(player);
			main.dataPlayers.put(player, dataP);
		}

	}
	
	public void addOctoCoins(Player player, int amount) {

		if (main.dataPlayers.containsKey(player)) {
			PlayerData dataP = main.dataPlayers.get(player);
			int octocoins = dataP.getOctocoins() + amount;
			dataP.setOctocoins(octocoins);
			main.dataPlayers.remove(player);
			main.dataPlayers.put(player, dataP);
		}

	}
	
	public PlayerData createPlayerData(Player player){
	       
        if(!main.dataPlayers.containsKey(player)){
            try {
                PreparedStatement rs = main.connection.prepareStatement("SELECT kills, dead, token, octocoins, grade, ghostrank FROM membre WHERE player = ?");
                rs.setString(1, player.getName().toString());
                ResultSet resultats = rs.executeQuery();
               
                int kill = 0;
                int dead = 0;
                int token = 0;
                int octocoins = 0;
                Rank rank = Rank.JOUEUR;
                GhostRank ghostrank = GhostRank.JOUEUR;
               
                while(resultats.next()){
                    kill = resultats.getInt("kills");
                    dead = resultats.getInt("dead");
                    token = resultats.getInt("token");
                    octocoins = resultats.getInt("octocoins");
                    rank = Rank.powerToRank(resultats.getInt("grade"));
                    ghostrank = GhostRank.powerToRank(resultats.getInt("ghostrank"));
                }
               
                PlayerData dataP = new PlayerData();
                dataP.setKill(kill);
                dataP.setDead(dead);
                dataP.setToken(token);
                dataP.setOctocoins(octocoins);
                dataP.setRank(rank);
                dataP.setGhostrank(ghostrank);
                return dataP;
               
               
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       
        return new PlayerData();
       
    }
   
    public void updatePlayerData(Player player){
       
        if(main.dataPlayers.containsKey(player)){
           
            PlayerData dataP = main.dataPlayers.get(player);
            int kill = dataP.getKill();
            int dead = dataP.getDead();
            int token = dataP.getToken();
            int octocoins = dataP.getOctocoins();
            Rank rank = dataP.getRank();
            int power = rank.getPower();
            GhostRank ghostrank = dataP.getGhostrank();
            int ghostpower = ghostrank.getPower();
            
            try {
                PreparedStatement rs = main.connection.prepareStatement("UPDATE membre SET ghostrank = ?, grade = ?, octocoins = ?, token = ?, dead = ?, kills = ? WHERE player = ?");
                rs.setInt(1, ghostpower);
                rs.setInt(2, power);
                rs.setInt(3, octocoins);
                rs.setInt(4, token);
                rs.setInt(5, dead);
                rs.setInt(6, kill);
                rs.setString(7, player.getName().toString());
                rs.executeUpdate();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
           
           
        }
       
    }

}
