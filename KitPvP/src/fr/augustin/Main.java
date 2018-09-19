package fr.augustin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import fr.augustin.Achat.AchatHistory;
import fr.augustin.cache.PlayerData;
import fr.augustin.cache.PlayerDataManager;
import fr.augustin.command.CommandCompte;
import fr.augustin.command.CommandDuel;
import fr.augustin.command.CommandSS;
import fr.augustin.command.CommandeBuildMod;
import fr.augustin.event.AutoM;
import fr.augustin.event.PlayerListener;
import fr.augustin.event.Sanction;
import fr.augustin.sql.SQL;
import fr.augustin.utils.ScoreboardSign;
import fr.augustin.utils.Scroller;
import fr.augustin.utils.Structure;
import fr.augustin.utils.Title;
import fr.augustin.utils.TitleManager;
public class Main extends JavaPlugin{
	
	public Map<Player, ScoreboardSign> boards = new HashMap<>();
	public SQL sql;
	public Connection connection;
	public PlayerDataManager dataManager = new PlayerDataManager(this);
	public Map<Player, PlayerData> dataPlayers = new HashMap<>();
	public ArrayList<Player> tuto = new ArrayList<>();
	public Map<Player, String> haskit = new HashMap<>();
	public Map<Player, String> map = new HashMap<>();
	public Map<Player, Integer> timerco = new HashMap<>();
	public Title title;
	public List<Player> discoArmor = new ArrayList<>();
	public Map<Player, Integer> series = new HashMap<>();
	public Map<Player, Integer> combo = new HashMap<>();
	public List<Player> duel = new ArrayList<>();
	public List<Location> spawnduel = new ArrayList<>();
	public List<Player> buildmod = new ArrayList<>();
	public Map<Player, Player> ssp = new HashMap<>();
	
	
	Scroller scroller;
	@Override
	public void onEnable() {
		
		title = new Title();
		sql = new SQL(this);
		new AchatHistory(this);
		
		System.out.println("Plugin KitPvP is started !");
		
	
		getCommand("ss").setExecutor(new CommandSS(this));
		getCommand("compte").setExecutor(new CommandCompte(this));
		getCommand("buildmod").setExecutor(new CommandeBuildMod(this));
		getCommand("duel").setExecutor(new CommandDuel());
		getConnectionSQL();
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					TitleManager.setPlayerList(p, "§cOctoCubeMC §8V.1"+"\n"+"§7"+Bukkit.getOnlinePlayers().size()+"§c/§7250"+"\n", "\n"+"§bBienvenue sur §cOctoCubeMC §b!");
		}
			}
		}.runTaskTimer(this, 0, 20);
		
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new Sanction(this), this);
		new Structure();
		
		getMessageAUTO();
		
		spawnduel.add(new Location(Bukkit.getWorld("world"), -41.157, 42.06250, -1.513, 89.7f, 1.8f));
		spawnduel.add(new Location(Bukkit.getWorld("world"), -53.795, 42.06250, -1.480, -90.3f, 2.7f));
		
		
	}


	public void getConnectionSQL() {
		try {
			if(!isConnected()) {
				connect();
			}
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isConnected() {
		return this.connection != null;
	}


	private void connect()throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName("");
		dataSource.setAutoReconnect(true);
		dataSource.setDatabaseName("");
		dataSource.setUser("");
		dataSource.setPassword("");
		
		connection = dataSource.getConnection();
		System.out.println("[KitPvP] Base de donnée connecter!");
		
	}
	


	private void getMessageAUTO() {
		
		AutoM autom = new AutoM();
		autom.runTaskTimer(this, 0, 20);
		
	}

	@Override
	public void onDisable() {
		
		disconnectSQL();
		
		System.out.println("Plugin KitPvP is disable !");
		
	}

	private void disconnectSQL() {
		
		if(isConnected()) {
			try {
				this.connection.close();
				System.out.println("[KitPvP] base de donnée deconnecter !");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
