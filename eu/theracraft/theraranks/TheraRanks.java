package eu.theracraft.theraranks;

import eu.theracraft.theraranks.commands.CommandMain;
import eu.theracraft.theraranks.commands.CommandPlaytime;
import eu.theracraft.theraranks.listeners.ListenerAfkStatus;
import eu.theracraft.theraranks.listeners.ListenerPlayTime;
import eu.theracraft.theraranks.utilities.Configuration;
import eu.theracraft.theraranks.utilities.PlayTimeManager;
import eu.theracraft.theraranks.utilities.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TheraRanks extends JavaPlugin {
   private Configuration configuration;
   private PlayTimeManager playTimeManager;
   private RankManager rankManager;

   public void onEnable() {
      this.configuration = new Configuration(this);
      this.playTimeManager = new PlayTimeManager(this);
      this.rankManager = new RankManager(this);
      int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
         this.playTimeManager.save();
         this.rankManager.save();
         this.configuration.saveData();
         Bukkit.getLogger().info("[TheraRanks] Saved data to file");
      }, 400L, 18000L);
      if (id == -1) {
         Bukkit.getLogger().severe("[TheraRanks-DevInfo] §4Unable to schedule saving of the data file!");
      }

      Bukkit.getOnlinePlayers().forEach(this.playTimeManager::startTimer);
      new ListenerPlayTime(this);
      if (Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
         new ListenerAfkStatus(this);
      }

      new CommandPlaytime(this);
      new CommandMain(this);
   }

   public void onDisable() {
      this.playTimeManager.shutdown();
      Bukkit.getScheduler().cancelTasks(this);
      this.playTimeManager.save();
      this.rankManager.save();
      this.configuration.saveData();
   }

   public Configuration getConfiguration() {
      return this.configuration;
   }

   public PlayTimeManager getPlayTimeManager() {
      return this.playTimeManager;
   }

   public RankManager getRankManager() {
      return this.rankManager;
   }
}
