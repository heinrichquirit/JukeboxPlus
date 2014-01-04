package main.java.net.bigbadcraft.jukeboxplus;

import java.util.HashMap;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class JukeboxPlus extends JavaPlugin {

	public static final String PREFIX = "[" + ChatColor.RED + "Jukebox+" + ChatColor.WHITE + "] ";
	
	public HashMap<Location, Material> jukeboxTrack = new HashMap<Location, Material>();
	
	public static Economy economy = null;
	
	public boolean enableEconomy;
	public int costPerDisc;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		enableEconomy = getConfig().getBoolean("enableEconomy");
		costPerDisc = getConfig().getInt("costPerDisc");
		
		if (enableEconomy) {
			if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
				setupEconomy();
				Bukkit.getLogger().info("Economy successfully enabled!");
			} else {
				Bukkit.getLogger().severe("Economy support enabled, but did not find Vault to hook into!");
				Bukkit.getLogger().severe("Please re-adjust your configuration file.");
			}
		}
		
		getServer().getPluginManager().registerEvents(new JukeboxListener(this), this);
		
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}
	
	@Override
	public void onDisable() {
		jukeboxTrack.clear();
	}
	
	public ItemStack getDiscItem(Disc disc) {
		switch(Disc.getId(disc)) {
		case 1: return new ItemStack(Material.GOLD_RECORD, 1);
		case 2: return new ItemStack(Material.GREEN_RECORD, 1);
		case 3: return new ItemStack(Material.RECORD_3, 1);
		case 4: return new ItemStack(Material.RECORD_4, 1);
		case 5: return new ItemStack(Material.RECORD_5, 1);
		case 6: return new ItemStack(Material.RECORD_6, 1);
		case 7: return new ItemStack(Material.RECORD_7, 1);
		case 8: return new ItemStack(Material.RECORD_8, 1);
		case 9: return new ItemStack(Material.RECORD_9, 1);
		case 10: return new ItemStack(Material.RECORD_10, 1);
		case 11: return new ItemStack(Material.RECORD_11, 1);
		case 12: return new ItemStack(Material.RECORD_12, 1);
		default: return null;
		}
	}
	
	public void play(Player player, Jukebox jukebox, Material material) {
		if (enableEconomy) {
			if (JukeboxPlus.economy.getBalance(player.getName()) >= costPerDisc) {
				jukebox.setPlaying(material);
			}
		} else {
			jukebox.setPlaying(material);
		}
	}
	
	public void withdraw(Player player, Jukebox box) {
		if (enableEconomy) {
			if (JukeboxPlus.economy.getBalance(player.getName()) >= costPerDisc) {
				JukeboxPlus.economy.withdrawPlayer(player.getName(), costPerDisc);
				player.sendMessage(PREFIX + "Now playing " + ChatColor.RED + Disc.getEnumName(box.getPlaying()) 
						+ ChatColor.WHITE + ". $" + costPerDisc + " has been deducted.");
			} else {
				player.sendMessage(PREFIX + "You do not have enough money.");
				box.setPlaying(null);
			}
		} else {
			player.sendMessage(PREFIX + "Now playing " + ChatColor.RED + Disc.getEnumName(box.getPlaying()) + ChatColor.WHITE + ".");
		}
	}
}
