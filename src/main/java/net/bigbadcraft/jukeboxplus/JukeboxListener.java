package main.java.net.bigbadcraft.jukeboxplus;

import main.java.net.bigbadcraft.jukeboxplus.IconMenu.OptionClickEvent;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class JukeboxListener implements Listener {

	private JukeboxPlus plugin;
	
	public JukeboxListener(JukeboxPlus plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent event) {
		
		final Player player = event.getPlayer();
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.JUKEBOX) {
				if (player.hasPermission("jukeboxplus.use")) {
					
					event.setCancelled(true);
					
					final Jukebox jukebox = (Jukebox) event.getClickedBlock().getState();
					final Location jukeboxLocation = jukebox.getLocation();
					
					IconMenu menu = new IconMenu("Select a disc", 18, new IconMenu.OptionClickEventHandler() {
						
						@Override
						public void onOptionClick(OptionClickEvent event) {
							
							String predictedName = event.getName();
							
							if (predictedName.equals("13")) {
								plugin.play(player, jukebox, Material.GOLD_RECORD); 
								plugin.withdraw(player, jukebox);
								remove(jukeboxLocation);
							} else if (predictedName.equals("Cat")) {
								plugin.play(player, jukebox, Material.GREEN_RECORD); 
								plugin.withdraw(player, jukebox);
								remove(jukeboxLocation);
							} else if (predictedName.equals("Blocks")) {
								plugin.play(player, jukebox, Material.RECORD_3); 
								plugin.withdraw(player, jukebox);
								remove(jukeboxLocation);
							} else if (predictedName.equals("Chirp")) {
								plugin.play(player, jukebox, Material.RECORD_4); 
								plugin.withdraw(player, jukebox);
								remove(jukeboxLocation);
							} else if (predictedName.equals("Far")) {
								plugin.play(player, jukebox, Material.RECORD_5); 
								plugin.withdraw(player, jukebox);
								remove(jukeboxLocation);
							} else if (predictedName.equals("Mall")) {
								plugin.play(player, jukebox, Material.RECORD_6); 
								plugin.withdraw(player, jukebox);
								remove(jukeboxLocation);
							} else if (predictedName.equals("Mellohi")) {
								plugin.play(player, jukebox, Material.RECORD_7); 
								plugin.withdraw(player, jukebox);
								remove(jukeboxLocation);
							} else if (predictedName.equals("Stal")) {
								plugin.play(player, jukebox, Material.RECORD_8); 
								plugin.withdraw(player, jukebox);
								remove(jukeboxLocation);
							} else if (predictedName.equals("Strad")) {
								plugin.play(player, jukebox, Material.RECORD_9); 
								plugin.withdraw(player, jukebox);
								remove(jukeboxLocation);
							} else if (predictedName.equals("Ward")) {
								plugin.play(player, jukebox, Material.RECORD_10); 
								plugin.withdraw(player, jukebox);
								remove(jukeboxLocation);
							} else if (predictedName.equals("Eleven")) {
								plugin.play(player, jukebox, Material.RECORD_11); 
								plugin.withdraw(player, jukebox);
								remove(jukeboxLocation);
							} else if (predictedName.equals("Wait")) {
								plugin.play(player, jukebox, Material.RECORD_12); 
								plugin.withdraw(player, jukebox);
								remove(jukeboxLocation);
							} else if (predictedName.equals("Play")) {
								if (!jukebox.isPlaying()) {
									if (plugin.jukeboxTrack.containsKey(jukeboxLocation)) {
										jukebox.setPlaying(plugin.jukeboxTrack.get(jukeboxLocation));
										player.sendMessage(JukeboxPlus.PREFIX + "Playing " + ChatColor.RED + Disc.getEnumName(plugin.jukeboxTrack.get(jukeboxLocation))
												+ ChatColor.WHITE + ".");
									}
								}  else {
									player.sendMessage(JukeboxPlus.PREFIX + "A track is already playing.");
								}
							} else if (predictedName.equals("Stop")) {
								if (jukebox.isPlaying()) {
									if (!plugin.jukeboxTrack.containsKey(jukeboxLocation)) {
										plugin.jukeboxTrack.put(jukeboxLocation, jukebox.getPlaying());
										player.sendMessage(JukeboxPlus.PREFIX + "Stopped playing " + ChatColor.RED + Disc.getEnumName(jukebox.getPlaying()) 
												+ ChatColor.WHITE + ".");
									}
									jukebox.setPlaying(null);
								} else {
									player.sendMessage(JukeboxPlus.PREFIX + "There are no tracks to stop.");
								}
							} else if (predictedName.equals("Previous")) {
								if (jukebox.isPlaying()) {
									int currentTrackId = Disc.getEnumId(jukebox.getPlaying());
									jukebox.setPlaying(Disc.getRecord(currentTrackId != 1 ? currentTrackId - 1 : null));
									plugin.withdraw(player, jukebox);
								}
							} else if (predictedName.equals("Next")) {
								if (jukebox.isPlaying()) {
									int currentTrackId = Disc.getEnumId(jukebox.getPlaying());
									jukebox.setPlaying(Disc.getRecord(currentTrackId != 12 ? currentTrackId + 1 : null));
									plugin.withdraw(player, jukebox);
								}
							}
							
							event.setWillDestroy(true);
							event.setWillClose(true);
							
						}
					}, plugin)
					.setOption(0, new ItemStack(Material.WOOL, 1, (short) 7), "Previous", "Play previous track.")
					.setOption(1, plugin.getDiscItem(Disc.THIRTEEN), "13", "Click to play.")
					.setOption(2, plugin.getDiscItem(Disc.CAT), "Cat", "Click to play.")
					.setOption(3, plugin.getDiscItem(Disc.BLOCKS), "Blocks", "Click to play.")
					.setOption(4, plugin.getDiscItem(Disc.CHIRP), "Chirp", "Click to play.")
					.setOption(5, plugin.getDiscItem(Disc.FAR), "Far", "Click to play.")
					.setOption(6, new ItemStack(Material.WOOL, 1, (short) 5), "Play", "Resume track.")
					.setOption(7, new ItemStack(Material.WOOL, 1, (short) 14), "Stop", "Pause track.")
					.setOption(8, new ItemStack(Material.WOOL, 1, (short) 8), "Next", "Play next track.")
					.setOption(10, plugin.getDiscItem(Disc.MALL), "Mall", "Click to play.")
					.setOption(11, plugin.getDiscItem(Disc.MELLOHI), "Mellohi", "Click to play.")
					.setOption(12, plugin.getDiscItem(Disc.STAL), "Stal", "Click to play.")
					.setOption(13, plugin.getDiscItem(Disc.STRAD), "Strad", "Click to play.")
					.setOption(14, plugin.getDiscItem(Disc.WARD), "Ward", "Click to play.")
					.setOption(15, plugin.getDiscItem(Disc.ELEVEN), "Eleven", "Click to play.")
					.setOption(16, plugin.getDiscItem(Disc.WAIT), "Wait", "Click to play.");
					menu.open(player);
				}
			}
		}
	}
	
	// Enables user to use the stop and play function
	private void remove(Location location) {
		if (plugin.jukeboxTrack.containsKey(location)) {
			plugin.jukeboxTrack.remove(location);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBreak(BlockBreakEvent event) {
		if (plugin.jukeboxTrack.containsKey(event.getBlock().getLocation())) {
			plugin.jukeboxTrack.remove(event.getBlock().getLocation());
		}
	}
}
