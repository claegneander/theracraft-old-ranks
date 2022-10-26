package eu.theracraft.theraranks.utilities;

import com.gmail.nossr50.mcmmo.acf.InvalidCommandArgument;
import eu.theracraft.theraranks.TheraRanks;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayTimeManager {
   private Configuration configuration;
   private Map<UUID, Long> onlineTime;
   private Map<Player, Long> playerTimer;

   public PlayTimeManager(TheraRanks plugin) {
      this.configuration = plugin.getConfiguration();
      this.playerTimer = new HashMap();
      this.load();
   }

   public void startTimer(Player player) {
      this.playerTimer.put(player, System.currentTimeMillis());
   }

   public void stopTimer(Player player) {
      if (this.playerTimer.containsKey(player)) {
         long time = this.onlineTime.getOrDefault(player.getUniqueId(), 0L);
         long currentTime = System.currentTimeMillis() - this.playerTimer.remove(player);
         time += currentTime;
         this.onlineTime.put(player.getUniqueId(), time);
      }
   }

   public long getPlayTime(Player player) {
      this.stopTimer(player);
      this.startTimer(player);
      return this.onlineTime.getOrDefault(player.getUniqueId(), 0L);
   }

   public long getPlayTime(OfflinePlayer player) {
      return player.getPlayer() != null ? this.getPlayTime(player.getPlayer()) : this.onlineTime.getOrDefault(player.getUniqueId(), 0L);
   }

   public void setPlayTime(Player player, long time) {
      this.stopTimer(player);
      this.startTimer(player);
      this.onlineTime.put(player.getUniqueId(), time);
   }

   public void addPlayTime(Player player, long time) {
      this.stopTimer(player);
      this.startTimer(player);
      time += this.onlineTime.getOrDefault(player.getUniqueId(), 0L);
      this.onlineTime.put(player.getUniqueId(), time);
   }

   public void takePlayTime(Player player, long time) throws InvalidCommandArgument {
      this.stopTimer(player);
      this.startTimer(player);
      long newTime = this.onlineTime.getOrDefault(player.getUniqueId(), 0L) - time;
      if (newTime < 0L) {
         throw new InvalidCommandArgument("§cError: The player doesn't have this much playtime yet!");
      } else {
         this.onlineTime.put(player.getUniqueId(), newTime);
      }
   }

   public void resetPlayTime(Player player) {
      this.stopTimer(player);
      this.startTimer(player);
      this.onlineTime.remove(player.getUniqueId());
   }

   public LinkedHashMap<UUID, Long> getTopPlayTimes() {
      this.save();
      Map<UUID, Long> unSortedMap = new HashMap();
      if (this.configuration.getData().isConfigurationSection("playtime")) {
         this.configuration
            .getData()
            .getConfigurationSection("playtime")
            .getKeys(false)
            .forEach(key -> unSortedMap.put(UUID.fromString(key), this.configuration.getData().getLong("playtime." + key)));
      }

      LinkedHashMap<UUID, Long> sortedMap = new LinkedHashMap();
      unSortedMap.entrySet().stream().sorted(Entry.comparingByValue()).forEachOrdered(x -> sortedMap.put((UUID)x.getKey(), (Long)x.getValue()));
      return sortedMap;
   }

   public void save() {
      new HashMap(this.playerTimer).keySet().forEach(this::stopTimer);
      Bukkit.getOnlinePlayers().forEach(this::startTimer);
      this.configuration.getData().set("playtime", null);

      for(UUID key : this.onlineTime.keySet()) {
         this.configuration.getData().set("playtime." + key.toString(), this.onlineTime.getOrDefault(key, 0L));
      }

   }

   private void load() {
      this.onlineTime = new HashMap();
      if (!this.configuration.getData().isConfigurationSection("playtime")) {
         this.configuration.getData().set("playtime", null);
      } else {
         for(String key : this.configuration.getData().getConfigurationSection("playtime").getKeys(false)) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(key));
            this.onlineTime.put(player.getUniqueId(), this.configuration.getData().getLong("playtime." + key));
         }

      }
   }

   public void shutdown() {
      new HashMap(this.playerTimer).keySet().forEach(this::stopTimer);
   }

   public String formatTime(Player player) {
      return formatTime(this.getPlayTime(player));
   }

   public static String formatTime(long playTime) {
      long days = Math.floorDiv(playTime, 86400000L);
      long hours = Math.floorDiv(playTime - days * 86400000L, 3600000L);
      long minutes = Math.floorDiv(playTime - days * 86400000L - hours * 3600000L, 60000L);
      StringBuilder builder = new StringBuilder();
      if (days == 1L) {
         builder.append("1 day, ");
      } else if (days != 0L) {
         builder.append(days).append(" days, ");
      }

      if (hours == 1L) {
         builder.append("1 hour and ");
      } else {
         builder.append(hours).append(" hours and ");
      }

      if (minutes != 1L && (minutes != 0L || hours != 0L || days != 0L)) {
         builder.append(minutes).append(" minutes");
      } else {
         builder.append("1 minute");
      }

      return builder.toString();
   }
}
