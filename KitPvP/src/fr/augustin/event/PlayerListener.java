package fr.augustin.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.augustin.Main;
import fr.augustin.Achat.AchatHistory;
import fr.augustin.custom.Inventaire;
import fr.augustin.duel.Duel;
import fr.augustin.inv.ConfirmAchat;
import fr.augustin.inv.GradeBoutique;
import fr.augustin.inv.JoinPlayer;
import fr.augustin.inv.KitJoueur;
import fr.augustin.inv.KitRank;
import fr.augustin.inv.MapSelector;
import fr.augustin.inv.MenuKitGrade;
import fr.augustin.inv.MenuKitRank;
import fr.augustin.inv.MenuP;
import fr.augustin.utils.ParticleEffect;
import fr.augustin.utils.Rank;
import fr.augustin.utils.ScoreboardSign;

public class PlayerListener implements Listener {

	private Main main;
	private AchatHistory achat;
	public ArrayList<Player> pp = new ArrayList<>();
	public Map<Player, Integer> coold = new HashMap<Player, Integer>();
	public Map<Player, BukkitRunnable> coolt = new HashMap<Player, BukkitRunnable>();
	public List<Player> desert = new ArrayList<>();
	public Map<Player, Integer> coolf = new HashMap<Player, Integer>();
	public Map<Player, BukkitRunnable> cootf = new HashMap<Player, BukkitRunnable>();

	public PlayerListener(Main main) {
		this.main = main;
		this.achat = new AchatHistory(this.main);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoinPlayer(PlayerJoinEvent event) {

		Player player = event.getPlayer();

		main.getConnectionSQL();

		main.sql.createAccount(player);
		main.dataManager.loadPlayerData(player);
		if (!this.achat.ContainAchat(player))
			this.achat.createPlayerData(player);
		player.setGameMode(GameMode.ADVENTURE);

		joinViP(event, player);
		if (!main.series.containsKey(player))
			main.series.put(player, 0);
		if (!main.combo.containsKey(player))
			main.combo.put(player, 0);

		Location spawn = new Location(Bukkit.getWorld("world"), 3.596, 52.06250, -1.451, 89.9f, -1.2f);
		player.teleport(spawn);
		main.title.sendTitle(player, "§cOctoCubeMC", "§bBienvenue !", 70);
		player.setFoodLevel(20);

		getScoreBoard(player);

		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.getInventory().setItem(4, JoinPlayer.MENU.getItem());

	}

	private boolean getPremium(Player player) {

		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + player.getName());
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

				boolean empty = reader.lines().count() == 0;

				reader.close();
				return empty;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public void joinViP(PlayerJoinEvent event, Player player) {

		Rank rank = main.sql.getRank(player);

		if (rank.getPower() == 100) {
			event.setJoinMessage("§4§lFondateur §4" + player.getName() + " §cà rejoint le serveur !");
		} else if (rank.getPower() >= 30) {
			event.setJoinMessage("§cStaff " + player.getName() + " §cà rejoint le serveur !");
		} else if (rank.getPower() == 10) {

			event.setJoinMessage("§eVIP " + player.getName() + " §eà rejoint le serveur !");

		} else if (rank.getPower() == 15) {
			event.setJoinMessage("§bVIP+ " + player.getName() + " §bà rejoint le serveur !");
		} else if (rank.getPower() == 25) {
			event.setJoinMessage("§6Octo§eVIP " + player.getName() + " §eà rejoint le serveur !");
		} else {
			event.setJoinMessage(null);

		}

	}

	@EventHandler
	public void onLeavePlayer(PlayerQuitEvent event) {

		Player player = event.getPlayer();

		main.getConnectionSQL();

		main.dataManager.savePlayerData(player);
		if (main.haskit.containsKey(player))
			main.haskit.remove(player);
		if (pp.contains(player))
			pp.remove(player);
		if (coold.containsKey(player))
			coold.remove(player);
		if (coolt.containsKey(player))
			coolt.remove(player);
		if (main.timerco.containsKey(player))
			main.timerco.remove(player);
		if (main.series.containsKey(player))
			main.series.remove(player);
		if (main.combo.containsKey(player))
			main.combo.remove(player);
		if (desert.contains(player))
			desert.remove(player);
		if (main.map.containsKey(player))
			main.map.remove(player);
		if (main.buildmod.contains(player))
			main.buildmod.remove(player);
		getDuel(player);
		event.setQuitMessage(null);
	}

	private void getDuel(Player player) {

		if (main.duel.contains(player)) {
			main.duel.remove(player);

			Player winner = main.duel.get(0);
			winner.setHealth(winner.getMaxHealth());
			Location spawn = new Location(Bukkit.getWorld("world"), 3.596, 52.06250, -1.451, 89.9f, -1.2f);
			winner.teleport(spawn);
			winner.getInventory().clear();
			winner.getInventory().setArmorContents(null);
			winner.getInventory().setItem(4, JoinPlayer.MENU.getItem());
			winner.setFoodLevel(20);
			Bukkit.broadcastMessage("§b§l1VS1: §9" + winner.getName() + " §cà gagner le Duel !");
			main.duel.remove(winner);
			return;

		}

	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();

		if (!main.buildmod.contains(player)) {
			event.setCancelled(true);
		}

	}

	private void getScoreBoard(Player player) {

		Rank rank = main.sql.getRank(player);

		ScoreboardSign s = new ScoreboardSign(player, "§cOctoCubeMC §8V.1");
		s.create();
		s.setLine(1, "§7Grade: §7[" + rank.getRank() + "§7]");
		s.setLine(2, "§7Rank: §7[" + getGrade(player) + "§7]");
		s.setLine(3, "§8");
		s.setLine(4, "§6Kill: §e" + main.sql.getKill(player));
		s.setLine(5, "§cMort: §9" + main.sql.getDead(player));
		s.setLine(6, "§9");
		s.setLine(7, "§c§lToken: §e" + main.sql.getToken(player));
		s.setLine(8, "§6§lOctoCoins: §e" + main.sql.getOctoCoins(player));
		s.setLine(9, "§7");
		s.setLine(10, "§3Kit: §bAucun");
		main.boards.put(player, s);
	}

	private String getGrade(Player player) {

		if (main.sql.getKill(player) >= 125000) {
			return "§0§lHématite";
		} else if (main.sql.getKill(player) >= 100000) {
			return "§c§lRubis";
		} else if (main.sql.getKill(player) >= 75000) {
			return "§a§lEmerald";
		} else if (main.sql.getKill(player) >= 45000) {
			return "§b§lDiamand";
		} else if (main.sql.getKill(player) >= 15000) {
			return "§e§lOr";
		} else if (main.sql.getKill(player) >= 5000) {
			return "§7§lArgent";
		} else if (main.sql.getKill(player) > 500) {
			return "§6§lBronze";
		} else {
			return "§8§lArgile";
		}
	}

	@EventHandler
	public void onweather(WeatherChangeEvent event) {

		event.setCancelled(true);

	}

	public Location getArena(Player player) {
		if (main.map.get(player).equals("§6Désert")) {
			return new Location(Bukkit.getWorld("world"), -118.501, 76.06250, -221.412, -0.2f, 2.6f);
		} else if (main.map.get(player).equals("§2Plane")) {
			return new Location(Bukkit.getWorld("world"), -118.501, 76.06250, -221.412, -0.2f, 2.6f);
		} else if (main.map.get(player).equals("§bSpace")) {
			return new Location(Bukkit.getWorld("world"), -118.501, 76.06250, -221.412, -0.2f, 2.6f);
		} else {
			return null;
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {

		if (event.getEntity() instanceof Player) {

			Player player = (Player) event.getEntity();

			if (event.getCause() == DamageCause.FALL) {
				event.setCancelled(true);

				/*
				 * if (player.isOp()) {
				 * 
				 * new BukkitRunnable() { double t = Math.PI / 4; Location loc =
				 * player.getLocation();
				 * 
				 * public void run() { t = t + 0.1 * Math.PI; for (double theta = 0; theta <= 2
				 * * Math.PI; theta = theta + Math.PI / 32) { double x = t * Math.cos(theta);
				 * double y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5; double z = t *
				 * Math.sin(theta); loc.add(x, y, z); for (Player p : Bukkit.getOnlinePlayers())
				 * { ParticleEffect.FIREWORKS_SPARK.display(0, 0, 0, 0, 1, loc, p); }
				 * loc.subtract(x, y, z);
				 * 
				 * theta = theta + Math.PI / 64;
				 * 
				 * x = t * Math.cos(theta); y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5; z =
				 * t * Math.sin(theta); loc.add(x, y, z); for (Player p :
				 * Bukkit.getOnlinePlayers()) { ParticleEffect.SPELL_WITCH.display(0, 0, 0, 0,
				 * 1, loc, p); } loc.subtract(x, y, z); } if (t > 20) { this.cancel(); } }
				 * 
				 * }.runTaskTimer(main, 0, 1);
				 * 
				 * }
				 */

			}

		}

	}

	private void getPlayerSerie(Player player) {

		if (main.series.containsKey(player)) {

			int serietotal = main.series.get(player);

			if (serietotal == 0) {
				return;
			}

			player.sendMessage("§6------------------------------");
			player.sendMessage("       §cOctoCubeMC §8V.1");
			player.sendMessage(" ");
			player.sendMessage("   §3Total Séries Kill(s): §b" + serietotal);
			player.sendMessage("§6------------------------------");
			main.series.remove(player);
			main.series.put(player, 0);

		}

	}

	@EventHandler
	public void onMoveEvent(PlayerMoveEvent event) {

		Player player = event.getPlayer();
		Location loc = player.getLocation();

		if (loc.subtract(0, -0.1, 0).getBlock().getType() == Material.GOLD_PLATE) {

			player.setVelocity(loc.getDirection().multiply(5.5).setY(1.5));

		}

	}

	private void getSerie(Player player) {

		if (main.series.containsKey(player)) {

			main.series.put(player, main.series.get(player) + 1);

			if (main.series.get(player) == 3) {

				Bukkit.broadcastMessage(
						"§cOctoCubeMC: §3" + player.getName() + " §bà fait une séries de §e3 §6Kills §b !");
			} else if (main.series.get(player) == 7) {

				Bukkit.broadcastMessage(
						"§cOctoCubeMC: §3" + player.getName() + " §bà fait une séries de §e7 §6Kills §b !");
			} else if (main.series.get(player) == 12) {

				Bukkit.broadcastMessage(
						"§cOctoCubeMC: §3" + player.getName() + " §bà fait une séries de §e12 §6Kills §b !");
			} else if (main.series.get(player) == 18) {

				Bukkit.broadcastMessage(
						"§cOctoCubeMC: §3" + player.getName() + " §bà fait une séries de §e18 §6Kills §b !");
			} else if (main.series.get(player) == 24) {

				Bukkit.broadcastMessage(
						"§cOctoCubeMC: §3" + player.getName() + " §bà fait une séries de §e24 §6Kills §b !");
			} else if (main.series.get(player) == 30) {

				Bukkit.broadcastMessage(
						"§cOctoCubeMC: §3" + player.getName() + " §bà fait une séries de §e30 §6Kills §b !");
			} else if (main.series.get(player) == 40) {

				Bukkit.broadcastMessage(
						"§cOctoCubeMC: §3" + player.getName() + " §bà fait une séries de §e40 §6Kills §b !");
			} else if (main.series.get(player) == 50) {

				Bukkit.broadcastMessage(
						"§cOctoCubeMC: §3" + player.getName() + " §bà fait une séries de §e50 §6Kills §b !");
			} else if (main.series.get(player) == 100) {

				Bukkit.broadcastMessage(
						"§cOctoCubeMC: §3" + player.getName() + " §bà fait une séries de §e100 §6Kills §b !");
			}

		}

	}

	@EventHandler
	public void onSignColor(SignChangeEvent event) {

		for (int i = 0; i <= 3; i++) {
			String line = event.getLine(i);

			line = ChatColor.translateAlternateColorCodes('&', line);
			event.setLine(i, line);
		}

	}

	@EventHandler
	public void onDuelDead(EntityDamageByEntityEvent event) {

		if (event.getEntity() instanceof Player) {

			Player player = (Player) event.getEntity();

			if (event.getDamager() instanceof Player) {

				if (event.getDamager() instanceof Player) {

					Player killer = (Player) event.getDamager();

					if (!main.duel.contains(player))
						return;
					if (!main.duel.contains(killer))
						return;

					getPlayerCombo(player);
					getCombo(killer);

					if (player.getHealth() <= event.getFinalDamage()) {

						player.setHealth(player.getMaxHealth());
						Location spawn = new Location(Bukkit.getWorld("world"), 3.596, 52.06250, -1.451, 89.9f, -1.2f);
						player.teleport(spawn);
						player.getInventory().clear();
						player.getInventory().setArmorContents(null);
						player.getInventory().setItem(4, JoinPlayer.MENU.getItem());

						player.setFoodLevel(20);
						killer.setHealth(player.getMaxHealth());
						killer.teleport(spawn);
						killer.getInventory().clear();
						killer.getInventory().setArmorContents(null);
						killer.getInventory().setItem(4, JoinPlayer.MENU.getItem());

						killer.setFoodLevel(20);
						main.title.sendActionBar(player, "§b1VS1: §c§lVous avez perdu !");
						main.title.sendActionBar(killer, "§b1VS1: §b!!! §a§lVous avez gagner §b!!!");
						Bukkit.broadcastMessage(
								"§b§l1VS1: §9" + killer.getName() + " §cà gagner le Duel contre §b" + player.getName());
						if (main.duel.contains(player))
							main.duel.remove(player);
						if (main.duel.contains(killer))
							main.duel.remove(killer);
					}
				}
			}

		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDead(EntityDamageByEntityEvent event) {

		if (event.getEntity() instanceof Player) {

			Player player = (Player) event.getEntity();

			if (event.getDamager() instanceof Player) {

				player.getLocation().setY(2.5);

				if (event.getDamager() instanceof Player) {

					Player killer = (Player) event.getDamager();

					if (!main.haskit.containsKey(player))
						return;
					if (!main.haskit.containsKey(killer))
						return;

					getPlayerCombo(player);
					getCombo(killer);

					if (player.getHealth() <= event.getFinalDamage()) {

						player.setHealth(player.getMaxHealth());
						Location spawn = new Location(Bukkit.getWorld("world"), 3.596, 52.06250, -1.451, 89.9f, -1.2f);
						player.teleport(spawn);
						player.getInventory().clear();
						player.getInventory().setArmorContents(null);
						player.getInventory().setItem(4, JoinPlayer.MENU.getItem());

						main.sql.addKill(killer, 1);
						main.sql.addDead(player, 1);
						main.sql.addOctoCoins(killer, 5);

						getPlayerSerie(player);
						getSerie(killer);

						player.setFoodLevel(20);
						killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));

						Bukkit.broadcastMessage(
								"§7[" + main.map.get(player) + "§7] §3" + player.getName() + " §cà été tuer par §b"
										+ killer.getName() + " §8( §9Kit " + main.haskit.get(killer) + "§8)");
						main.title.sendActionBar(killer, "§d+§e5 §c6§lOctoCoins");
						main.boards.get(killer).setLine(4, "§6Kill: §e" + main.sql.getKill(killer));
						main.boards.get(player).setLine(5, "§cMort: §9" + main.sql.getDead(player));
						main.boards.get(killer).setLine(2, "§7Rank: §7[" + getGrade(killer) + "§7]");
						main.boards.get(killer).setLine(8, "§6§lOctoCoins: §e" + main.sql.getOctoCoins(killer));
						main.boards.get(player).setLine(10, "§3Kit: §bAucun");

					}
				}
			}

		}

	}

	private void getPlayerCombo(Player player) {

		if (main.combo.containsKey(player)) {

			int combot = main.combo.get(player);

			if (combot == 0) {
				return;
			}

			main.title.sendActionBar(player, "§cOctoCubeMC: §9Total combo: §e" + combot);
			main.combo.remove(player);
			main.combo.put(player, 0);

		}

	}

	private void getCombo(Player killer) {

		if (main.combo.containsKey(killer)) {

			main.combo.put(killer, main.combo.get(killer) + 1);

			if (main.combo.get(killer) >= 3) {

				main.title.sendTitle(killer, "§6", "§cx" + main.combo.get(killer), 70);

			}

		}

	}

	@EventHandler
	public void onNotBed(PlayerBedEnterEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onCraftPlater(CraftItemEvent event) {
		event.setCancelled(true);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void InvClickEvent(InventoryClickEvent event) {

		Player player = (Player) event.getWhoClicked();
		ItemStack it = event.getCurrentItem();
		Inventory inv = event.getInventory();

		String prefix = "§7§lMenu Principal";

		if (inv.getName().equals("§7§lMenu Principal")) {

			event.setCancelled(true);
			player.closeInventory();

			if (it != null && it.equals(MenuP.KIT.getItem())) {

				player.playSound(player.getLocation(), Sound.ENDERMAN_SCREAM, 7f, 7f);
				Inventaire menukit = new Inventaire(9, "§7Menu kit (grade, rank)");
				menukit.menukit();
				menukit.openInventaire(player);

				return;
			}

			if (it != null && it.equals(MenuP.SHOP.getItem())) {

				Inventaire boutique = new Inventaire(9, "§a§lBoutique");
				boutique.menugradeBoutique();
				boutique.openInventaire(player);
				return;
			}

			if (it != null && it.equals(MenuP.DUEL.getItem())) {

				main.title.sendActionBar(player, "§b1VS1: §c§lJeu en Beta!");
				Location spawnduel = new Location(Bukkit.getWorld("world"), -64.523, 47, -1.485, 89.9f, 0.8f);
				player.teleport(spawnduel);
				return;
			}

			if (it != null && it.equals(MenuP.PARTICLE.getItem())) {

				if (!pp.contains(player)) {
					pp.add(player);
					new BukkitRunnable() {
						double phi = 0;

						public void run() {

							if (!pp.contains(player)) {
								this.cancel();
							}

							if (!Bukkit.getOnlinePlayers().contains(player)) {
								this.cancel();
							}

							phi = phi + Math.PI / 8;
							double x, y, z;

							Location location1 = player.getLocation();
							for (double t = 0; t <= 2 * Math.PI; t = t + Math.PI / 16) {
								for (double i = 0; i <= 1; i = i + 1) {
									x = 0.6 * (2 * Math.PI - t) * 0.5 * Math.cos(t + phi + i * Math.PI);
									y = 0.0 * t;
									z = 0.6 * (2 * Math.PI - t) * 0.5 * Math.sin(t + phi + i * Math.PI);
									location1.add(x, y, z);
									for (Player p : Bukkit.getOnlinePlayers()) {
										ParticleEffect.FLAME.display(0, 0, 0, 0, 1, location1, p);
									}
									location1.subtract(x, y, z);
								}

							}

							if (phi > 1000 * Math.PI) {
								this.cancel();
							}
						}
					}.runTaskTimer(main, 0, 1);

				} else {
					pp.remove(player);
				}

				return;
			}

			if (it != null && it.equals(MenuP.QUETES.getItem())) {
				player.sendMessage(prefix + " §cCette option n'est pas disponible !");
				return;
			}
			return;
		}

		if (inv.getName().equals("§7Menu kit (grade, rank)")) {

			event.setCancelled(true);
			player.closeInventory();

			if (it != null && it.equals(KitRank.GARDE.getItem())) {

				Inventaire mkitgrade = new Inventaire(9, "§7Menu kit Grade");
				mkitgrade.mkitgrade();
				mkitgrade.openInventaire(player);

				return;
			}

			if (it != null && it.equals(KitRank.RANK.getItem())) {
				/*
				 * Inventaire mkitrank = new Inventaire(9, "§7Menu kit Rank");
				 * mkitrank.mkitrank(); mkitrank.openInventaire(player);
				 */
				player.sendMessage("§cDésactiver jusqu'à la §8V.1.5 §c!");
				return;
			}
			return;
		}

		if (inv.getName().equals("§7Menu kit Grade")) {

			event.setCancelled(true);
			player.closeInventory();

			Rank rank = main.sql.getRank(player);

			if (it != null && it.equals(MenuKitGrade.JOUEUR.getItem())) {

				main.haskit.put(player, "§7§lJoueur");

				Inventaire mkitj = new Inventaire(9, "§7Kit Joueur");
				mkitj.menukitJoueur();
				mkitj.openInventaire(player);

				/*
				 * player.sendMessage("§cOctoCubeMC: §9Kit " + main.haskit.get(player) +
				 * " §aSelectionner §9!");
				 * 
				 * player.getInventory().clear(); player.getInventory().setArmorContents(null);
				 * 
				 * Potion p = new Potion(PotionType.INSTANT_HEAL, 2, true);
				 * 
				 * player.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
				 * player.getInventory().setItem(1, p.toItemStack(1));
				 * player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 32));
				 * player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
				 * player.getInventory().setChestplate(new
				 * ItemStack(Material.LEATHER_CHESTPLATE));
				 * player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
				 * player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
				 * main.boards.get(player).setLine(5, "§3Kit: " +
				 * main.haskit.get(player).toString()); respawn(player);
				 * player.setFoodLevel(20);
				 */
				return;
			}

			if (it != null && it.equals(MenuKitGrade.VIP.getItem())) {

				if (rank.getPower() >= 10) {
					main.haskit.put(player, "§e§lVIP");

					Inventaire map = new Inventaire(9, "§2Map §9Selector");
					map.mapSelector();
					map.openInventaire(player);

					/*
					 * player.sendMessage("§cOctoCubeMC: §9Kit " + main.haskit.get(player) +
					 * " §aSelectionner §9!");
					 * 
					 * player.getInventory().clear(); player.getInventory().setArmorContents(null);
					 * Potion p = new Potion(PotionType.INSTANT_HEAL, 2, true);
					 * player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
					 * player.getInventory().setItem(1, p.toItemStack(1));
					 * player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 32));
					 * player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
					 * player.getInventory().setChestplate(new
					 * ItemStack(Material.CHAINMAIL_CHESTPLATE));
					 * player.getInventory().setLeggings(new
					 * ItemStack(Material.CHAINMAIL_LEGGINGS)); player.getInventory().setBoots(new
					 * ItemStack(Material.CHAINMAIL_BOOTS)); main.boards.get(player).setLine(5,
					 * "§3Kit: " + main.haskit.get(player).toString()); respawn(player);
					 * player.setFoodLevel(20);
					 */
				} else {
					player.sendMessage("§cVous n'avez pas le grade §e§lVIP §c!");
					player.sendMessage(
							"§a§lBoutique: §9Achetez-vous votre grade §e§lVIP §9sur notre §aBoutique en ligne §9!");
					player.sendMessage("§coctocubemc.com/shop");
				}

				return;
			}

			if (it != null && it.equals(MenuKitGrade.VIPP.getItem())) {

				if (rank.getPower() >= 15) {
					main.haskit.put(player, "§b§lVIP+");

					Inventaire map = new Inventaire(9, "§2Map §9Selector");
					map.mapSelector();
					map.openInventaire(player);

					/*
					 * player.sendMessage("§cOctoCubeMC: §9Kit " + main.haskit.get(player) +
					 * " §aSelectionner §9!");
					 * 
					 * player.getInventory().clear(); player.getInventory().setArmorContents(null);
					 * Potion p = new Potion(PotionType.INSTANT_HEAL, 2, true);
					 * player.getInventory().setItem(0, new ItemStack(Material.GOLD_SWORD));
					 * player.getInventory().setItem(1, p.toItemStack(1));
					 * player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 32));
					 * player.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
					 * player.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
					 * player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
					 * player.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
					 * main.boards.get(player).setLine(5, "§3Kit: " +
					 * main.haskit.get(player).toString()); respawn(player);
					 * player.setFoodLevel(20);
					 */
				} else {
					player.sendMessage("§cVous n'avez pas le grade §b§lVIP+ §c!");
					player.sendMessage(
							"§a§lBoutique: §9Achetez-vous votre grade §b§lVIP+ §9sur notre §aBoutique en ligne §9!");
					player.sendMessage("§coctocubemc.com/shop");
				}

				return;
			}

			if (it != null && it.equals(MenuKitGrade.OCTOVIP.getItem())) {

				if (rank.getPower() >= 25) {
					main.haskit.put(player, "§6§lOcto§e§lVIP");

					Inventaire map = new Inventaire(9, "§2Map §9Selector");
					map.mapSelector();
					map.openInventaire(player);

					/*
					 * player.sendMessage("§cOctoCubeMC: §9Kit " + main.haskit.get(player) +
					 * " §aSelectionner §9!");
					 * 
					 * player.getInventory().clear(); player.getInventory().setArmorContents(null);
					 * Potion p = new Potion(PotionType.INSTANT_HEAL, 2, true);
					 * player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
					 * player.getInventory().setItem(1, p.toItemStack(1));
					 * player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE));
					 * player.getInventory().setItem(3, new ItemStack(Material.COOKED_BEEF, 32));
					 * player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
					 * player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
					 * player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					 * player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
					 * main.boards.get(player).setLine(5, "§3Kit: " +
					 * main.haskit.get(player).toString()); respawn(player);
					 * player.setFoodLevel(20);
					 */
				} else {
					player.sendMessage("§cVous n'avez pas le grade §6§lOcto§e§lVIP §c!");
					player.sendMessage(
							"§a§lBoutique: §9Achetez-vous votre grade §6§lOcto§e§lVIP §9sur notre §aBoutique en ligne §9!");
					player.sendMessage("§coctocubemc.com/shop");
				}
				return;
			}
			return;
		}

		if (inv.getName().equals("§7Menu kit Rank")) {

			event.setCancelled(true);
			player.closeInventory();

			if (it != null && it.equals(MenuKitRank.BRONZE.getItem())) {
				if (main.sql.getKill(player) >= 500) {
					main.haskit.put(player, "§6§lBronze");

					Inventaire map = new Inventaire(9, "§2Map §9Selector");
					map.mapSelector();
					map.openInventaire(player);
				} else {
					player.sendMessage("§cVous n'avez pas le Rank: §6§lBronze §c!");
				}
				return;
			}

			if (it != null && it.equals(MenuKitRank.ARGENT.getItem())) {

				if (main.sql.getKill(player) >= 5000) {
					main.haskit.put(player, "§7§lArgent");

					Inventaire map = new Inventaire(9, "§2Map §9Selector");
					map.mapSelector();
					map.openInventaire(player);

				} else {
					player.sendMessage("§cVous n'avez pas le Rank: §7§lArgent §c!");
				}

				return;
			}

			if (it != null && it.equals(MenuKitRank.OR.getItem())) {

				if (main.sql.getKill(player) >= 15000) {
					main.haskit.put(player, "§e§lOr");

					Inventaire map = new Inventaire(9, "§2Map §9Selector");
					map.mapSelector();
					map.openInventaire(player);

				} else {
					player.sendMessage("§cVous n'avez pas le Rank: §e§lOr §c!");
				}

				return;
			}

			if (it != null && it.equals(MenuKitRank.DIAMAND.getItem())) {

				if (main.sql.getKill(player) >= 45000) {
					main.haskit.put(player, "§b§lDiamand");

					Inventaire map = new Inventaire(9, "§2Map §9Selector");
					map.mapSelector();
					map.openInventaire(player);

				} else {
					player.sendMessage("§cVous n'avez pas le Rank: §b§lDiamand §c!");
				}

				return;
			}

			if (it != null && it.equals(MenuKitRank.EMERALD.getItem())) {

				if (main.sql.getKill(player) >= 75000) {

					main.haskit.put(player, "§a§lEmerald");
					Inventaire map = new Inventaire(9, "§2Map §9Selector");
					map.mapSelector();
					map.openInventaire(player);

				} else {
					player.sendMessage("§cVous n'avez pas le Rank: §a§lEmerald §c!");
				}

				return;
			}

		}

		if (inv.getName().equals("§7Kit Joueur")) {

			event.setCancelled(true);
			player.closeInventory();

			if (it != null && it.equals(KitJoueur.SUPERFLY.getItem())) {

				main.haskit.put(player, "§7§lJoueur");

				Inventaire map = new Inventaire(9, "§2Map §9Selector");
				map.mapSelector();
				map.openInventaire(player);

				return;
			}
			if (it != null && it.equals(KitJoueur.SUPERJUMP.getItem())) {

				main.haskit.put(player, "§6§lSuper Jump");

				Inventaire map = new Inventaire(9, "§2Map §9Selector");
				map.mapSelector();
				map.openInventaire(player);

				return;
			}

		}

		if (inv.getName().equals("§2Map §9Selector")) {

			event.setCancelled(true);
			player.closeInventory();

			if (it != null && it.equals(MapSelector.DESERT.getItem())) {

				if (desert.size() >= 20) {
					player.sendMessage("§cOctoCubeMC: §c§lLa map §6Désert §c§lest complêt !");
					return;
				}

				getKitD(player);

				return;
			}
			if (it != null && it.equals(MapSelector.SCPACE.getItem())) {
				player.sendMessage("§cOctoCubeMC: §c§lCette map n'est pas encore Disponible !");
				return;
			}
			if (it != null && it.equals(MapSelector.PLANE.getItem())) {
				player.sendMessage("§cOctoCubeMC: §c§lCette map n'est pas encore Disponible !");
				return;
			}

		}

		if (inv.getName().equals("§a§lBoutique")) {

			event.setCancelled(true);
			player.closeInventory();

			Rank rank = main.sql.getRank(player);

			if (it != null && it.equals(GradeBoutique.VIP.getItem())) {

				Inventaire cachat = new Inventaire(9, "§cConfirmer achat §e#000110");
				cachat.ConfirmAchats();
				cachat.openInventaire(player);

				return;
			}

			if (it != null && it.equals(GradeBoutique.VIPP.getItem())) {

				Inventaire cachat = new Inventaire(9, "§cConfirmer achat §e#000115");
				cachat.ConfirmAchats();
				cachat.openInventaire(player);

				return;
			}

			if (it != null && it.equals(GradeBoutique.OCTOVIP.getItem())) {

				Inventaire cachat = new Inventaire(9, "§cConfirmer achat §e#000125");
				cachat.ConfirmAchats();
				cachat.openInventaire(player);

				return;
			}
			return;
		}

		if (inv.getName().equals("§cConfirmer achat §e#000110")) {

			event.setCancelled(true);
			player.closeInventory();
			Rank rank = main.sql.getRank(player);

			if(it != null && it.equals(ConfirmAchat.INFO.getItem())) {
				player.sendMessage("§7§lInformation §e§lVIP§7§l:");
				player.sendMessage(" ");
				return;
			}
			
			if (it != null && it.equals(ConfirmAchat.ACCEPTER.getItem())) {
				if (rank.getPower() < 9) {

					if (main.sql.getToken(player) >= 1000) {

						main.sql.setRank(player, Rank.VIP);
						player.sendMessage("§a§lTu as acheter le grade §e§lVIP §a§l!");
						main.sql.addToken(player, -1000);
						main.boards.get(player).setLine(7, "§c§lToken: §e" + main.sql.getToken(player));

					} else {
						player.sendMessage("§c§lTu n'as pas assez de Token !");
					}

				} else {
					player.sendMessage("§8§l>> §c§lTu as déjà acheter le grade §e§lVIP §c§l!");
				}
				return;
			}

			if (it != null && it.equals(ConfirmAchat.REFUSER.getItem())) {
				player.sendMessage("§a§lBoutique: §c§lVous avez annulé votre achat !");
				return;
			}

			return;
		}
		
		if (inv.getName().equals("§cConfirmer achat §e#000115")) {

			event.setCancelled(true);
			player.closeInventory();
			Rank rank = main.sql.getRank(player);
			
			if(it != null && it.equals(ConfirmAchat.INFO.getItem())) {
				player.sendMessage("§7§lInformation §b§lVIP+§7§l:");
				player.sendMessage(" ");
				return;
			}

			if (it != null && it.equals(ConfirmAchat.ACCEPTER.getItem())) {
				if (rank.getPower() < 14) {

					if (!(rank.getPower() >= 10)) {

						if (main.sql.getToken(player) >= 1500) {

							main.sql.setRank(player, Rank.VIPP);
							player.sendMessage("§a§lTu as acheter le grade §b§lVIP+ §a§l!");
							main.sql.addToken(player, -1500);
							main.boards.get(player).setLine(7, "§c§lToken: §e" + main.sql.getToken(player));
						} else {
							player.sendMessage("§c§lTu n'as pas assez de Token !");
						}
					} else {
						if (main.sql.getToken(player) >= 1275) {

							main.sql.setRank(player, Rank.VIPP);
							player.sendMessage("§a§lTu as acheter le grade §b§lVIP+ §a§l!");
							main.sql.addToken(player, -1275);
							main.boards.get(player).setLine(7, "§c§lToken: §e" + main.sql.getToken(player));
						} else {
							player.sendMessage("§c§lTu n'as pas assez de Token !");
						}
					}
				} else {
					player.sendMessage("§8§l>> §c§lTu as déjà acheter le grade §b§lVIP+ §c§l!");
				}
				return;
			}

			if (it != null && it.equals(ConfirmAchat.REFUSER.getItem())) {
				player.sendMessage("§a§lBoutique: §c§lVous avez annulé votre achat !");
				return;
			}

			return;
		}
		if (inv.getName().equals("§cConfirmer achat §e#000125")) {
			
			event.setCancelled(true);
			player.closeInventory();
			Rank rank = main.sql.getRank(player);
			
			if(it != null && it.equals(ConfirmAchat.INFO.getItem())) {
				player.sendMessage("§7§lInformation §6§lOcto§e§lVIP§7§l:");
				player.sendMessage(" ");
				return;
			}
			
			if (it != null && it.equals(ConfirmAchat.ACCEPTER.getItem())) {
				if (rank.getPower() < 24) {

					if (!(rank.getPower() >= 15)) {

						if (main.sql.getToken(player) >= 2500) {

							main.sql.setRank(player, Rank.OCTOVIP);
							player.sendMessage("§a§lTu as acheter le grade §6§lOcto§e§lVIP §a§l!");
							main.sql.addToken(player, -2500);
							main.boards.get(player).setLine(7, "§c§lToken: §e" + main.sql.getToken(player));
						} else {
							player.sendMessage("§c§lTu n'as pas assez de Token !");
						}
					} else {
						if (main.sql.getToken(player) >= 1875) {

							main.sql.setRank(player, Rank.OCTOVIP);
							player.sendMessage("§a§lTu as acheter le grade §6§lOcto§e§lVIP §a§l!");
							main.sql.addToken(player, -1875);
							main.boards.get(player).setLine(7, "§c§lToken: §e" + main.sql.getToken(player));
						} else {
							player.sendMessage("§c§lTu n'as pas assez de Token !");
						}
					}
				} else {
					player.sendMessage("§8§l>> §c§lTu as déjà acheter le grade §6§lOcto§e§lVIP §c§l!");
				}
				return;
			}
			
			if (it != null && it.equals(ConfirmAchat.REFUSER.getItem())) {
				player.sendMessage("§a§lBoutique: §c§lVous avez annulé votre achat !");
				return;
			}
			
			return;
		}

	}

	private void getKitD(Player player) {

		if (main.haskit.get(player).equals("§7§lJoueur")) {

			main.map.put(player, "§6Désert");
			player.sendMessage("§cOctoCubeMC: §9Kit " + main.haskit.get(player) + " §aSelectionner §9!");

			player.getInventory().clear();
			player.getInventory().setArmorContents(null);

			Potion p = new Potion(PotionType.INSTANT_HEAL, 2, true);

			ItemStack fly = new ItemStack(Material.FEATHER, 1);
			ItemMeta flyM = fly.getItemMeta();
			flyM.setDisplayName("§7§lJoueur");
			fly.setItemMeta(flyM);

			player.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
			player.getInventory().setItem(1, fly);
			player.getInventory().setItem(2, p.toItemStack(1));
			player.getInventory().setItem(3, new ItemStack(Material.COOKED_BEEF, 32));
			player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
			player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
			main.boards.get(player).setLine(10, "§3Kit: " + main.haskit.get(player).toString());
			respawn(player);
			player.setFoodLevel(20);

			return;
		}

		if (main.haskit.get(player).equals("§6§lSuper Jump")) {

			main.map.put(player, "§6Désert");
			player.sendMessage("§cOctoCubeMC: §9Kit " + main.haskit.get(player) + " §aSelectionner §9!");

			player.getInventory().clear();
			player.getInventory().setArmorContents(null);

			Potion p = new Potion(PotionType.INSTANT_HEAL, 2, true);

			player.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
			player.getInventory().setItem(1, p.toItemStack(1));
			player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 32));
			player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
			player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
			main.boards.get(player).setLine(10, "§3Kit: " + main.haskit.get(player).toString());
			respawn(player);
			player.setFoodLevel(20);

			return;
		}

		if (main.haskit.get(player).equals("§7§lJoueur")) {
			main.map.put(player, "§6Désert");
			player.sendMessage("§cOctoCubeMC: §9Kit " + main.haskit.get(player) + " §aSelectionner §9!");

			player.getInventory().clear();
			player.getInventory().setArmorContents(null);

			Potion p = new Potion(PotionType.INSTANT_HEAL, 2, true);

			player.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
			player.getInventory().setItem(1, p.toItemStack(1));
			player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 32));
			player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
			player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
			main.boards.get(player).setLine(10, "§3Kit: " + main.haskit.get(player).toString());
			respawn(player);
			player.setFoodLevel(20);

			return;
		}

		if (main.haskit.get(player).equals("§e§lVIP")) {
			main.map.put(player, "§6Désert");
			player.sendMessage("§cOctoCubeMC: §9Kit " + main.haskit.get(player) + " §aSelectionner §9!");

			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			Potion p = new Potion(PotionType.INSTANT_HEAL, 2, true);
			player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
			player.getInventory().setItem(1, p.toItemStack(1));
			player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 32));
			player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
			player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
			main.boards.get(player).setLine(10, "§3Kit: " + main.haskit.get(player).toString());
			respawn(player);
			player.setFoodLevel(20);

			return;
		}

		if (main.haskit.get(player).equals("§b§lVIP+")) {
			main.map.put(player, "§6Désert");
			player.sendMessage("§cOctoCubeMC: §9Kit " + main.haskit.get(player) + " §aSelectionner §9!");

			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			Potion p = new Potion(PotionType.INSTANT_HEAL, 2, true);
			player.getInventory().setItem(0, new ItemStack(Material.GOLD_SWORD));
			player.getInventory().setItem(1, p.toItemStack(1));
			player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 32));
			player.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
			player.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
			player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
			player.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
			main.boards.get(player).setLine(10, "§3Kit: " + main.haskit.get(player).toString());
			respawn(player);
			player.setFoodLevel(20);

			return;
		}

		if (main.haskit.get(player).equals("§6§lOcto§e§lVIP")) {
			main.map.put(player, "§6Désert");
			player.sendMessage("§cOctoCubeMC: §9Kit " + main.haskit.get(player) + " §aSelectionner §9!");

			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			Potion p = new Potion(PotionType.INSTANT_HEAL, 2, true);
			player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
			player.getInventory().setItem(1, p.toItemStack(1));
			player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE));
			player.getInventory().setItem(3, new ItemStack(Material.COOKED_BEEF, 32));
			player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
			player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
			main.boards.get(player).setLine(10, "§3Kit: " + main.haskit.get(player).toString());
			respawn(player);
			player.setFoodLevel(20);

			return;
		}

	}

	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
	public void onInteractPlayer(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		ItemStack it = event.getItem();
		Action action = event.getAction();

		if (event.getClickedBlock() != null && action == Action.RIGHT_CLICK_BLOCK) {

			BlockState bs = event.getClickedBlock().getState();
			if (bs instanceof Sign) {

				Sign sign = (Sign) bs;

				if (sign.getLine(0).equals("§cOctoCubeMC")) {
					if (sign.getLine(1).equals("§b1vs1")) {

						if (sign.getLine(3).equals("§a§lRejoindre")) {

							if (main.duel.size() == 2) {
								if (!main.duel.contains(player)) {
									main.title.sendActionBar(player, "§b1VS1: §c§lLe 1VS1 à déjà commencé !");
									return;
								}
							}

							if (!main.duel.contains(player)) {
								main.duel.add(player);
								player.getInventory().clear();
								player.getInventory().setArmorContents(null);
								main.title.sendActionBar(player, "§b1VS1: §a§lTu as rejoint !");

								if (main.duel.size() >= 2) {

									new BukkitRunnable() {

										int timer = 15;

										@Override
										public void run() {

											timer--;

											if (main.duel.size() < 2) {

												for (Player p : Bukkit.getOnlinePlayers()) {
													if (main.duel.contains(p)) {
														main.title.sendActionBar(p,
																"§b1VS1: §c§lIl n'y a pas assez de Joueur !");
													}
												}

												this.cancel();
												return;
											}

											if (main.duel.size() == 2) {

												Duel duel = new Duel(main);
												duel.runTaskTimer(main, 0, 20);

												this.cancel();
												return;
											}

											if (timer == 0) {

												Duel duel = new Duel(main);
												duel.runTaskTimer(main, 0, 20);

												this.cancel();
												return;
											}

										}
									}.runTaskTimer(main, 0, 20);

								}

							} else {
								main.duel.remove(player);
								player.getInventory().clear();
								player.getInventory().setArmorContents(null);
								player.getInventory().setItem(4, JoinPlayer.MENU.getItem());

								player.setFoodLevel(20);
								main.title.sendActionBar(player, "§b1VS1: §c§lTu as quitter !");
							}

						}

					}
				}

			}

			return;
		}

		if (it != null && it.getType() == Material.FEATHER
				&& it.getItemMeta().getDisplayName().equals("§b§lSuper Fly")) {

			if (!main.haskit.get(player).equals("§b§lSuper Fly")) {
				main.title.sendActionBar(player, "§cOctoCubeMC: §c§lTu n'as pas le kit: §b§lSuper Fly §c§l!");
				return;
			}

			if (coolf.containsKey(player)) {
				main.title.sendActionBar(player,
						"§b§lSuper Fly: §c§lAttendez §e" + coolf.get(player) + "§c§l' Seconde(s)");
				return;
			}
			main.title.sendActionBar(player, "§b§lSuper Fly: §a§lTu es en Fly !");
			player.setAllowFlight(true);
			player.setFlying(true);
			coolf.put(player, 10);
			cootf.put(player, new BukkitRunnable() {

				@Override
				public void run() {

					coolf.put(player, coolf.get(player) - 1);
					main.title.sendActionBar(player, "§b§lSuper Fly: §e" + coolf.get(player) + "'s");

					if (coolf.get(player) == 0) {
						player.setAllowFlight(false);
						player.setFlying(false);
						main.title.sendActionBar(player, "§b§lSuper Fly: §a§lRecharger !");
						coolf.remove(player);
						cootf.remove(player);
						this.cancel();
						return;
					}

				}
			});
			cootf.get(player).runTaskTimer(main, 0, 20);

			return;
		}

		if (it != null && it.equals(JoinPlayer.MENU.getItem())) {

			Location loc = player.getLocation();
			Location spawn = new Location(Bukkit.getWorld("world"), 3.596, 52.06250, -1.451, 89.9f, -1.2f);

			/*
			 * if (loc.distance(spawn) <= 59) {
			 * player.sendMessage("§7§lMenu Principal: §cVous devez être dans le HALL !");
			 * return; }
			 */

			Inventaire menu = new Inventaire(9, "§7§lMenu Principal");
			menu.menu();
			menu.openInventaire(player);
			return;
		}

	}

	@SuppressWarnings("unused")
	private void respawn(Player player) {
		if (coolt.containsKey(player)) {
			return;
		}
		player.setGameMode(GameMode.SPECTATOR);
		player.teleport(getArena(player));
		coold.put(player, 3);
		coolt.put(player, new BukkitRunnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {

				if (!Bukkit.getOnlinePlayers().contains(player)) {
					this.cancel();
					return;
				}

				coold.put(player, coold.get(player) - 1);
				main.title.sendTitle(player, "§cRespawn", "§e" + coold.get(player) + "'s", 70);

				if (coold.get(player) == 0) {
					player.setHealth(player.getMaxHealth());
					player.setGameMode(GameMode.ADVENTURE);
					main.title.sendTitle(player, "§cOctoCubeMC", "§bBon jeu", 20);
					player.teleport(getArena(player));
					coolt.remove(player);
					coold.remove(player);
					this.cancel();
					return;
				}

			}
		});
		coolt.get(player).runTaskTimer(main, 0, 20);
	}

	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event) {

		Player player = event.getPlayer();
		if (!main.buildmod.contains(player)) {
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void onBreakBlock(BlockBreakEvent event) {

		Player player = event.getPlayer();
		if (!main.buildmod.contains(player)) {
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		Rank rank = main.sql.getRank(player);

		event.setFormat(
				"§7[" + getGrade(player) + "§7] " + rank.getRank() + player.getName() + " §f: " + event.getMessage());

	}

}
