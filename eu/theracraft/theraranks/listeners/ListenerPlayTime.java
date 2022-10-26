package eu.theracraft.theraranks.listeners;

import eu.theracraft.theraranks.TheraRanks;
import eu.theracraft.theraranks.utilities.PlayTimeManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPlayTime implements Listener {
   private PlayTimeManager playTimeManager;

   public ListenerPlayTime(TheraRanks plugin) {
      Bukkit.getPluginManager().registerEvents(this, plugin);
      this.playTimeManager = plugin.getPlayTimeManager();
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      this.playTimeManager.startTimer(event.getPlayer());
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
      this.playTimeManager.stopTimer(event.getPlayer());
   }
}
