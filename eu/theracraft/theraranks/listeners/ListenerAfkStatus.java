package eu.theracraft.theraranks.listeners;

import eu.theracraft.theraranks.TheraRanks;
import eu.theracraft.theraranks.utilities.PlayTimeManager;
import java.util.UUID;
import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ListenerAfkStatus implements Listener {
   private PlayTimeManager playTimeManager;

   public ListenerAfkStatus(TheraRanks plugin) {
      Bukkit.getPluginManager().registerEvents(this, plugin);
      this.playTimeManager = plugin.getPlayTimeManager();
   }

   @EventHandler
   public void onAfkStatusChange(AfkStatusChangeEvent event) {
      UUID uuid = event.getAffected().getBase().getUniqueId();
      if (!uuid.equals(UUID.fromString("a248325a-3235-4b6f-a081-17d657469424"))) {
         if (event.getValue()) {
            this.playTimeManager.stopTimer(event.getAffected().getBase());
         } else {
            this.playTimeManager.startTimer(event.getAffected().getBase());
         }

      }
   }
}
