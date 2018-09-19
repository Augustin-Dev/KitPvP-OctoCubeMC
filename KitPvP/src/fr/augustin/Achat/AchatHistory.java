package fr.augustin.Achat;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.augustin.Main;

public class AchatHistory {
	
	private File achat;
	private FileConfiguration cfg;
	private Main main;
	
	public AchatHistory(Main main) {
		this.main = main;
		this.achat = new File("plugins/KitPvP", "achat.yml");
		this.cfg = YamlConfiguration.loadConfiguration(this.achat);
		if(!this.achat.exists()) {
			this.cfg.options().header("Donnée d'achat des Joueurs !");
		}try {
			this.cfg.save(this.achat);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public File getPlayers()
	  {
	    return this.achat;
	  }
	  
	  public FileConfiguration getConfiguration()
	  {
	    return this.cfg;
	  }
	  
	  public void saveConfiguration()
	  {
	    try
	    {
	      this.cfg.save(this.achat);
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	  }
	  
	  public void createPlayerData(Player p)
	  {
	    new AchatHistory(this.main);
	    
	    this.cfg.createSection(p.getName() + ".hasAchat");
	    this.cfg.createSection(p.getName() + ".Achat");
	    
	    this.cfg.set(p.getName() + ".hasAchat", true);
	    
	    String[] sr = { "Grade VIP", "Booster 2" };
	    this.cfg.set(p.getName() + ".Achat", Arrays.asList(sr));
	    try
	    {
	      this.cfg.save(this.achat);
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	  }
	  
	  public boolean ContainAchat(Player player) {
		  new AchatHistory(this.main);
		    if (this.cfg.contains(player.getName() + ".hasAchat") == true) {
		      return true;
		    }
		    return false;
	  }

}
