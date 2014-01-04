package main.java.net.bigbadcraft.jukeboxplus;

import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class JukeboxPlus extends JavaPlugin {

	public HashMap<Location, Material> jukeboxTrack = new HashMap<Location, Material>();
	
	/*
	 * Disabled economy due to issues with IconMenu
	 */
	//public static Economy economy = null;
	
	//public boolean enableEconomy;
	//public int costPerDisc;

	@Override
	public void onEnable() {
		/*
		saveDefaultConfig();
		
		enableEconomy = getConfig().getBoolean("enableEconomy");
		costPerDisc = getConfig().getInt("costPerDisc");
		
		if (enableEconomy) {
			setupEconomy();
			info("Economy successfully enabled!");
		}
		*/
		
		getServer().getPluginManager().registerEvents(new JukeboxListener(this), this);
		
		try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
           Bukkit.getLogger().severe("JukeboxPlus failed to submit data.");
        }
		
	}
	
	/*
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}
	*/
	
	@Override
	public void onDisable() {
		jukeboxTrack.clear();
	}
	
	public ItemStack getDiscItem(Disc disc) {
		switch(Disc.getId(disc)) {
		case 1:
			return new ItemStack(Material.GOLD_RECORD, 1);
		case 2:
			return new ItemStack(Material.GREEN_RECORD, 1);
		case 3:
			return new ItemStack(Material.RECORD_3, 1);
		case 4:
			return new ItemStack(Material.RECORD_4, 1);
		case 5:
			return new ItemStack(Material.RECORD_5, 1);
		case 6:
			return new ItemStack(Material.RECORD_6, 1);
		case 7:
			return new ItemStack(Material.RECORD_7, 1);
		case 8:
			return new ItemStack(Material.RECORD_8, 1);
		case 9:
			return new ItemStack(Material.RECORD_9, 1);
		case 10:
			return new ItemStack(Material.RECORD_10, 1);
		case 11:
			return new ItemStack(Material.RECORD_11, 1);
		case 12:
			return new ItemStack(Material.RECORD_12, 1);
		default: return null;
		}
	}
	
	public void info(String msg) {
		Bukkit.getLogger().info(msg);
	}
	
	public void send(Player player, String discName) {
		player.sendMessage(ChatColor.GREEN + discName + ChatColor.WHITE + " is now playing.");
	}
	
	public void play(Jukebox jukebox, Material material) {
		jukebox.setPlaying(material);
	}
	
	/*
	public void withdraw(Player player) {
		if (enableEconomy) {
			if (JukeboxPlus.economy.getBalance(player.getName()) >= costPerDisc) {
				JukeboxPlus.economy.withdrawPlayer(player.getName(), costPerDisc);
				player.sendMessage("[JukeboxPlus] $" + costPerDisc + " has been taken out of your account.");
			}
		}
	}
	*/
}
