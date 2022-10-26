package eu.theracraft.theraranks.utilities;

import com.gmail.nossr50.api.ExperienceAPI;
import eu.theracraft.theraranks.TheraRanks;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RankManager {
   private final Configuration configuration;
   private final PlayTimeManager playTimeManager;
   private Map<OfflinePlayer, Map<RankManager.Key, Integer>> playerData;
   private boolean rankup;

   public RankManager(TheraRanks plugin) {
      this.configuration = plugin.getConfiguration();
      this.playTimeManager = plugin.getPlayTimeManager();
      this.load();
      this.rankup = true;
   }

   public Rank getRank(Player player) {
      boolean op = player.isOp();
      if (op) {
         player.setOp(false);
      }

      Rank rank = Rank.STONE;
      if (player.hasPermission("group.emerald")) {
         rank = Rank.EMERALD;
      } else if (player.hasPermission("group.netherite")) {
         rank = Rank.NETHERITE;
      } else if (player.hasPermission("group.obsidian")) {
         rank = Rank.OBSIDIAN;
      } else if (player.hasPermission("group.diamond")) {
         rank = Rank.DIAMOND;
      } else if (player.hasPermission("group.gold")) {
         rank = Rank.GOLD;
      } else if (player.hasPermission("group.iron")) {
         rank = Rank.IRON;
      } else if (player.hasPermission("group.coal")) {
         rank = Rank.COAL;
      }

      if (op) {
         player.setOp(true);
      }

      return rank;
   }

   public void rankUpdate(Player player) {
      if (!this.playerData.containsKey(player)) {
         this.playerData.put(player, new HashMap());
      }

      Map<RankManager.Key, Integer> playerData = (Map)this.playerData.get(player);
      Rank rank = this.getRank(player);
      this.rankup = true;
      switch(rank) {
         case STONE:
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(player, 3L, TimeUnit.HOURS)) {
               playerData.remove(RankManager.Key.PLAYTIME);
               playerData.put(RankManager.Key.PLAYTIME, 1);
            }

            RankManager.Key key = RankManager.Key.MCMMO;
            int amount = 150;
            if (playerData.getOrDefault(key, 0) == 0 && this.checkMcmmo(player, amount)) {
               playerData.remove(key);
               playerData.put(key, 1);
            }

            key = RankManager.Key.COAL;
            amount = 64;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.COAL, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.LOG;
            amount = 16;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItemLogs(player, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }
            break;
         case COAL:
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(player, 6L, TimeUnit.HOURS)) {
               playerData.remove(RankManager.Key.PLAYTIME);
               playerData.put(RankManager.Key.PLAYTIME, 1);
            }

            RankManager.Key key = RankManager.Key.MCMMO;
            int amount = 400;
            if (playerData.getOrDefault(key, 0) == 0 && this.checkMcmmo(player, amount)) {
               playerData.remove(key);
               playerData.put(key, 1);
            }

            key = RankManager.Key.IRON;
            amount = 128;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.IRON_INGOT, 128 - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.COAL;
            amount = 256;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.COAL, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }
            break;
         case IRON:
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(player, 1L, TimeUnit.DAYS)) {
               playerData.remove(RankManager.Key.PLAYTIME);
               playerData.put(RankManager.Key.PLAYTIME, 1);
            }

            RankManager.Key key = RankManager.Key.MCMMO;
            int amount = 700;
            if (playerData.getOrDefault(key, 0) == 0 && this.checkMcmmo(player, amount)) {
               playerData.remove(key);
               playerData.put(key, 1);
            }

            key = RankManager.Key.GOLD;
            amount = 320;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.GOLD_INGOT, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.CARROT;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.CARROT, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.POTATO;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.POTATO, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.BEETROOT;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.BEETROOT, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.WHEAT;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.WHEAT, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }
            break;
         case GOLD:
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(player, 3L, TimeUnit.DAYS)) {
               playerData.remove(RankManager.Key.PLAYTIME);
               playerData.put(RankManager.Key.PLAYTIME, 1);
            }

            RankManager.Key key = RankManager.Key.MCMMO;
            int amount = 1000;
            if (playerData.getOrDefault(key, 0) == 0 && this.checkMcmmo(player, amount)) {
               playerData.remove(key);
               playerData.put(key, 1);
            }

            key = RankManager.Key.DIAMOND;
            amount = 320;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.DIAMOND, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.GLOW_BERRY;
            amount = 640;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.GLOW_BERRIES, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.MELON;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.MELON, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.PUMPKIN;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.PUMPKIN, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.CAKE;
            amount = 64;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.CAKE, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }
            break;
         case DIAMOND:
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(player, 5L, TimeUnit.DAYS)) {
               playerData.remove(RankManager.Key.PLAYTIME);
               playerData.put(RankManager.Key.PLAYTIME, 1);
            }

            RankManager.Key key = RankManager.Key.MCMMO;
            int amount = 1700;
            if (playerData.getOrDefault(key, 0) == 0 && this.checkMcmmo(player, amount)) {
               playerData.remove(key);
               playerData.put(key, 1);
            }

            key = RankManager.Key.OBSIDIAN;
            amount = 320;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.OBSIDIAN, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.POINTED_DRIPSTONE;
            amount = 1600;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.POINTED_DRIPSTONE, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.AMETHYST_CLUSTER;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.AMETHYST_CLUSTER, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.COPPER_ORE;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.COPPER_ORE, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.CALCITE;
            amount = 640;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.CALCITE, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }
            break;
         case OBSIDIAN:
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(player, 7L, TimeUnit.DAYS)) {
               playerData.remove(RankManager.Key.PLAYTIME);
               playerData.put(RankManager.Key.PLAYTIME, 1);
            }

            RankManager.Key key = RankManager.Key.MCMMO;
            int amount = 2500;
            if (playerData.getOrDefault(key, 0) == 0 && this.checkMcmmo(player, amount)) {
               playerData.remove(key);
               playerData.put(key, 1);
            }

            key = RankManager.Key.NETHERITE_INGOT;
            amount = 64;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.NETHERITE_INGOT, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.BEEF;
            amount = 1920;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.BEEF, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.MUTTON;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.MUTTON, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.PORK;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.PORKCHOP, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.CHICKEN;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.CHICKEN, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.RABBIT;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.RABBIT, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }
            break;
         case NETHERITE:
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(player, 9L, TimeUnit.DAYS)) {
               playerData.remove(RankManager.Key.PLAYTIME);
               playerData.put(RankManager.Key.PLAYTIME, 1);
            }

            RankManager.Key key = RankManager.Key.MCMMO;
            int amount = 5000;
            if (playerData.getOrDefault(key, 0) == 0 && this.checkMcmmo(player, amount)) {
               playerData.remove(key);
               playerData.put(key, 1);
            }

            key = RankManager.Key.NETHER_STAR;
            amount = 64;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.NETHER_STAR, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }

            key = RankManager.Key.EMERALD_BLOCK;
            amount = 2240;
            if (playerData.getOrDefault(key, 0) != amount) {
               int newValue = playerData.getOrDefault(key, 0) + this.checkItem(player, Material.EMERALD_BLOCK, amount - playerData.getOrDefault(key, 0));
               playerData.remove(key);
               playerData.put(key, newValue);
            }
      }

      switch(rank) {
         case STONE:
            player.sendMessage("§3-----------------------");
            player.sendMessage("§2You are viewing the path to '§6Coal§2' for " + player.getName() + ".");
            player.sendMessage("§3-----------------------");
            player.sendMessage("§7Requirements:");
            StringBuilder builder = new StringBuilder();
            builder.append("§9Play for 3 hours: ");
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.playTimeManager.formatTime(player))
                  .append(" / 3 hours (")
                  .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(player), 10800000L))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9150 mcmmo powerlevel: ");
            if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(ExperienceAPI.getPowerLevel(player))
                  .append(" / 150 (")
                  .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(player), 150))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 64 Coal: ");
            if (playerData.getOrDefault(RankManager.Key.COAL, 0) == 64) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.COAL, 0)))
                  .append(" / 64 (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.COAL, 0), 64))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 16 logs: ");
            if (playerData.getOrDefault(RankManager.Key.LOG, 0) == 16) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.LOG, 0)))
                  .append(" / 16 (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.LOG, 0), 16))
                  .append("%)");
               this.rankup = false;
            }

            for(String s : builder.toString().split("\n")) {
               player.sendMessage(s);
            }
            break;
         case COAL:
            player.sendMessage("§3-----------------------");
            player.sendMessage("§2You are viewing the path to '§6Iron§2' for " + player.getName() + ".");
            player.sendMessage("§3-----------------------");
            player.sendMessage("§7Requirements:");
            StringBuilder builder = new StringBuilder();
            builder.append("§9Play for 6 hours: ");
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.playTimeManager.formatTime(player))
                  .append(" / 6 hours (")
                  .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(player), 21600000L))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9400 mcmmo powerlevel: ");
            if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(ExperienceAPI.getPowerLevel(player))
                  .append(" / 400 (")
                  .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(player), 400))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 2x 64 Iron Ingot: ");
            if (playerData.getOrDefault(RankManager.Key.IRON, 0) == 128) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.IRON, 0)))
                  .append(" / ")
                  .append(this.formatItems(128))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.IRON, 0), 128))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 4x 64 Coal: ");
            if (playerData.getOrDefault(RankManager.Key.COAL, 0) == 256) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.COAL, 0)))
                  .append(" / ")
                  .append(this.formatItems(256))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.COAL, 0), 256))
                  .append("%)");
               this.rankup = false;
            }

            for(String s : builder.toString().split("\n")) {
               player.sendMessage(s);
            }
            break;
         case IRON:
            player.sendMessage("§3-----------------------");
            player.sendMessage("§2You are viewing the path to '§6Gold§2' for " + player.getName() + ".");
            player.sendMessage("§3-----------------------");
            player.sendMessage("§7Requirements:");
            StringBuilder builder = new StringBuilder();
            builder.append("§9Play for 1 day: ");
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.playTimeManager.formatTime(player))
                  .append(" / 1 day (")
                  .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(player), 86400000L))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9700 mcmmo powerlevel: ");
            if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(ExperienceAPI.getPowerLevel(player))
                  .append(" / 700 (")
                  .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(player), 700))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 5x 64 Gold Ingot: ");
            if (playerData.getOrDefault(RankManager.Key.GOLD, 0) == 320) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.GOLD, 0)))
                  .append(" / ")
                  .append(this.formatItems(320))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.GOLD, 0), 320))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 5x 64 Carrots: ");
            if (playerData.getOrDefault(RankManager.Key.CARROT, 0) == 320) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.CARROT, 0)))
                  .append(" / ")
                  .append(this.formatItems(320))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.CARROT, 0), 320))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 5x 64 Potato: ");
            if (playerData.getOrDefault(RankManager.Key.POTATO, 0) == 320) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.POTATO, 0)))
                  .append(" / ")
                  .append(this.formatItems(320))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.POTATO, 0), 320))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 5x 64 Beetroot: ");
            if (playerData.getOrDefault(RankManager.Key.BEETROOT, 0) == 320) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.BEETROOT, 0)))
                  .append(" / ")
                  .append(this.formatItems(320))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.BEETROOT, 0), 320))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 5x 64 Wheat: ");
            if (playerData.getOrDefault(RankManager.Key.WHEAT, 0) == 320) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.WHEAT, 0)))
                  .append(" / ")
                  .append(this.formatItems(320))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.WHEAT, 0), 320))
                  .append("%)");
               this.rankup = false;
            }

            for(String s : builder.toString().split("\n")) {
               player.sendMessage(s);
            }
            break;
         case GOLD:
            player.sendMessage("§3-----------------------");
            player.sendMessage("§2You are viewing the path to '§6Diamond§2' for " + player.getName() + ".");
            player.sendMessage("§3-----------------------");
            player.sendMessage("§7Requirements:");
            StringBuilder builder = new StringBuilder();
            builder.append("§9Play for 3 days: ");
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.playTimeManager.formatTime(player))
                  .append(" / 3 days (")
                  .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(player), 259200000L))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§91000 mcmmo powerlevel: ");
            if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(ExperienceAPI.getPowerLevel(player))
                  .append(" / 1000 (")
                  .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(player), 1000))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 5x 64 Diamonds: ");
            if (playerData.getOrDefault(RankManager.Key.DIAMOND, 0) == 320) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.DIAMOND, 0)))
                  .append(" / ")
                  .append(this.formatItems(320))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.DIAMOND, 0), 320))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 10x 64 Glow Berries: ");
            if (playerData.getOrDefault(RankManager.Key.GLOW_BERRY, 0) == 640) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.GLOW_BERRY, 0)))
                  .append(" / ")
                  .append(this.formatItems(640))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.GLOW_BERRY, 0), 640))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 10x 64 Melons: ");
            if (playerData.getOrDefault(RankManager.Key.MELON, 0) == 640) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.MELON, 0)))
                  .append(" / ")
                  .append(this.formatItems(640))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.MELON, 0), 640))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 10x 64 Beetroot: ");
            if (playerData.getOrDefault(RankManager.Key.PUMPKIN, 0) == 640) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.PUMPKIN, 0)))
                  .append(" / ")
                  .append(this.formatItems(640))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.PUMPKIN, 0), 640))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 64 Cakes: ");
            if (playerData.getOrDefault(RankManager.Key.CAKE, 0) == 64) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.CAKE, 0)))
                  .append(" / ")
                  .append(this.formatItems(64))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.CAKE, 0), 64))
                  .append("%)");
               this.rankup = false;
            }

            for(String s : builder.toString().split("\n")) {
               player.sendMessage(s);
            }
            break;
         case DIAMOND:
            player.sendMessage("§3-----------------------");
            player.sendMessage("§2You are viewing the path to '§6Obsidian§2' for " + player.getName() + ".");
            player.sendMessage("§3-----------------------");
            player.sendMessage("§7Requirements:");
            StringBuilder builder = new StringBuilder();
            builder.append("§9Play for 5 days: ");
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.playTimeManager.formatTime(player))
                  .append(" / 5 days (")
                  .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(player), 432000000L))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§91700 mcmmo powerlevel: ");
            if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(ExperienceAPI.getPowerLevel(player))
                  .append(" / 1700 (")
                  .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(player), 1700))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 5x 64 Obsidian: ");
            if (playerData.getOrDefault(RankManager.Key.OBSIDIAN, 0) == 320) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.OBSIDIAN, 0)))
                  .append(" / ")
                  .append(this.formatItems(320))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.OBSIDIAN, 0), 320))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 25x 64 Pointed Dripstone: ");
            if (playerData.getOrDefault(RankManager.Key.POINTED_DRIPSTONE, 0) == 1600) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.POINTED_DRIPSTONE, 0)))
                  .append(" / ")
                  .append(this.formatItems(1600))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.POINTED_DRIPSTONE, 0), 1600))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 25x 64 Amethyst Cluster: ");
            if (playerData.getOrDefault(RankManager.Key.AMETHYST_CLUSTER, 0) == 1600) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.AMETHYST_CLUSTER, 0)))
                  .append(" / ")
                  .append(this.formatItems(1600))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.AMETHYST_CLUSTER, 0), 1600))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 25x 64 Copper Ore: ");
            if (playerData.getOrDefault(RankManager.Key.COPPER_ORE, 0) == 1600) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.COPPER_ORE, 0)))
                  .append(" / ")
                  .append(this.formatItems(1600))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.COPPER_ORE, 0), 1600))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 10x 64 Calcite: ");
            if (playerData.getOrDefault(RankManager.Key.CALCITE, 0) == 640) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.CALCITE, 0)))
                  .append(" / ")
                  .append(this.formatItems(640))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.CALCITE, 0), 640))
                  .append("%)");
               this.rankup = false;
            }

            for(String s : builder.toString().split("\n")) {
               player.sendMessage(s);
            }
            break;
         case OBSIDIAN:
            player.sendMessage("§3-----------------------");
            player.sendMessage("§2You are viewing the path to '§6Netherite§2' for " + player.getName() + ".");
            player.sendMessage("§3-----------------------");
            player.sendMessage("§7Requirements:");
            StringBuilder builder = new StringBuilder();
            builder.append("§9Play for 7 days: ");
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.playTimeManager.formatTime(player))
                  .append(" / 7 days (")
                  .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(player), 604800000L))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§92500 mcmmo powerlevel: ");
            if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(ExperienceAPI.getPowerLevel(player))
                  .append(" / 2500 (")
                  .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(player), 2500))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 64 Netherite: ");
            if (playerData.getOrDefault(RankManager.Key.NETHERITE_INGOT, 0) == 64) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.NETHERITE_INGOT, 0)))
                  .append(" / ")
                  .append(this.formatItems(64))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.NETHERITE_INGOT, 0), 64))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 30x 64 Raw Beef: ");
            if (playerData.getOrDefault(RankManager.Key.BEEF, 0) == 1920) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.BEEF, 0)))
                  .append(" / ")
                  .append(this.formatItems(1920))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.BEEF, 0), 1920))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 30x 64 Raw Mutton: ");
            if (playerData.getOrDefault(RankManager.Key.MUTTON, 0) == 1920) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.MUTTON, 0)))
                  .append(" / ")
                  .append(this.formatItems(1920))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.MUTTON, 0), 1920))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 30x 64 Raw Pork: ");
            if (playerData.getOrDefault(RankManager.Key.PORK, 0) == 1920) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.PORK, 0)))
                  .append(" / ")
                  .append(this.formatItems(1920))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.PORK, 0), 1920))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 30x 64 Raw Chicken: ");
            if (playerData.getOrDefault(RankManager.Key.CHICKEN, 0) == 1920) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.CHICKEN, 0)))
                  .append(" / ")
                  .append(this.formatItems(1920))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.CHICKEN, 0), 1920))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 30x 64 Raw Rabbit: ");
            if (playerData.getOrDefault(RankManager.Key.RABBIT, 0) == 1920) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.RABBIT, 0)))
                  .append(" / ")
                  .append(this.formatItems(1920))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.RABBIT, 0), 1920))
                  .append("%)");
               this.rankup = false;
            }

            for(String s : builder.toString().split("\n")) {
               player.sendMessage(s);
            }
            break;
         case NETHERITE:
            player.sendMessage("§3-----------------------");
            player.sendMessage("§2You are viewing the path to '§6Emerald§2' for " + player.getName() + ".");
            player.sendMessage("§3-----------------------");
            player.sendMessage("§7Requirements:");
            StringBuilder builder = new StringBuilder();
            builder.append("§9Play for 9 days: ");
            if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.playTimeManager.formatTime(player))
                  .append(" / 9 days (")
                  .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(player), 777600000L))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§95000 mcmmo powerlevel: ");
            if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(ExperienceAPI.getPowerLevel(player))
                  .append(" / 5000 (")
                  .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(player), 5000))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 64 Nether Star: ");
            if (playerData.getOrDefault(RankManager.Key.NETHER_STAR, 0) == 64) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.NETHER_STAR, 0)))
                  .append(" / ")
                  .append(this.formatItems(64))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.NETHER_STAR, 0), 64))
                  .append("%)");
               this.rankup = false;
            }

            builder.append("\n").append("§9Obtain 35x 64 Emerald Blocks: ");
            if (playerData.getOrDefault(RankManager.Key.EMERALD_BLOCK, 0) == 64) {
               builder.append("§a✓");
            } else {
               builder.append("§c✗")
                  .append("\n")
                  .append("§7-  §9")
                  .append(this.formatItems(playerData.getOrDefault(RankManager.Key.EMERALD_BLOCK, 0)))
                  .append(" / ")
                  .append(this.formatItems(2240))
                  .append(" (")
                  .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.EMERALD_BLOCK, 0), 2240))
                  .append("%)");
               this.rankup = false;
            }

            for(String s : builder.toString().split("\n")) {
               player.sendMessage(s);
            }
            break;
         case EMERALD:
            player.sendMessage("§3-----------------------");
            player.sendMessage("§2Unable to find a path after '§6Emerald§2' for " + player.getName() + ".");
            player.sendMessage("§3-----------------------");
            if (rank.equals(Rank.EMERALD)) {
               this.rankup = false;
            }
      }

      if (this.rankup) {
         this.playerData.remove(player);
         player.sendMessage(" ");
         player.sendMessage(" ");
         player.sendMessage("§3-----------------------");
         player.sendMessage("§aYou have completed all your rankup requirements!. You have been ranked up!");
         player.sendMessage("§3-----------------------");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " promote mcmmo");
         if (rank.equals(Rank.NETHERITE)) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast " + player.getName() + " just reached §2Emerald§a!");
         }

      }
   }

   public void rankCheck(Player player) {
      this.rankCheck(player, player);
   }

   public void rankCheck(Player sender, Player target) {
      if (!this.playerData.containsKey(target)) {
         this.playerData.put(target, new HashMap());
      }

      Map<RankManager.Key, Integer> playerData = (Map)this.playerData.get(target);
      Rank rank = this.getRank(target);
      if (rank == null) {
         sender.sendMessage("rank == null");
      } else {
         switch(rank) {
            case STONE:
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(target, 3L, TimeUnit.HOURS)) {
                  playerData.remove(RankManager.Key.PLAYTIME);
                  playerData.put(RankManager.Key.PLAYTIME, 1);
               }

               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 0 && this.checkMcmmo(target, 150)) {
                  playerData.remove(RankManager.Key.MCMMO);
                  playerData.put(RankManager.Key.MCMMO, 1);
               }

               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§2You are viewing the path to '§6Coal§2' for " + target.getName() + ".");
               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§7Requirements:");
               StringBuilder builder = new StringBuilder();
               builder.append("§9Play for 3 hours: ");
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.playTimeManager.formatTime(target))
                     .append(" / 3 hours (")
                     .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(target), 10800000L))
                     .append("%)");
               }

               builder.append("\n").append("§9150 mcmmo powerlevel: ");
               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(ExperienceAPI.getPowerLevel(target))
                     .append(" / 150 (")
                     .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(target), 150))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 64 Coal: ");
               if (playerData.getOrDefault(RankManager.Key.COAL, 0) == 64) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.COAL, 0)))
                     .append(" / 64 (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.COAL, 0), 64))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 16 logs: ");
               if (playerData.getOrDefault(RankManager.Key.LOG, 0) == 16) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.LOG, 0)))
                     .append(" / 16 (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.LOG, 0), 16))
                     .append("%)");
               }

               for(String s : builder.toString().split("\n")) {
                  sender.sendMessage(s);
               }
               break;
            case COAL:
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(target, 6L, TimeUnit.HOURS)) {
                  playerData.remove(RankManager.Key.PLAYTIME);
                  playerData.put(RankManager.Key.PLAYTIME, 1);
               }

               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 0 && this.checkMcmmo(target, 400)) {
                  playerData.remove(RankManager.Key.MCMMO);
                  playerData.put(RankManager.Key.MCMMO, 1);
               }

               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§2You are viewing the path to '§6Iron§2' for " + target.getName() + ".");
               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§7Requirements:");
               StringBuilder builder = new StringBuilder();
               builder.append("§9Play for 6 hours: ");
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.playTimeManager.formatTime(target))
                     .append(" / 6 hours (")
                     .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(target), 21600000L))
                     .append("%)");
               }

               builder.append("\n").append("§9400 mcmmo powerlevel: ");
               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(ExperienceAPI.getPowerLevel(target))
                     .append(" / 400 (")
                     .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(target), 400))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 2x 64 Iron Ingot: ");
               if (playerData.getOrDefault(RankManager.Key.IRON, 0) == 128) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.IRON, 0)))
                     .append(" / ")
                     .append(this.formatItems(128))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.IRON, 0), 128))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 4x 64 Coal: ");
               if (playerData.getOrDefault(RankManager.Key.COAL, 0) == 256) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.COAL, 0)))
                     .append(" / ")
                     .append(this.formatItems(256))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.COAL, 0), 256))
                     .append("%)");
               }

               for(String s : builder.toString().split("\n")) {
                  sender.sendMessage(s);
               }
               break;
            case IRON:
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(target, 5L, TimeUnit.DAYS)) {
                  playerData.remove(RankManager.Key.PLAYTIME);
                  playerData.put(RankManager.Key.PLAYTIME, 1);
               }

               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 0 && this.checkMcmmo(target, 700)) {
                  playerData.remove(RankManager.Key.MCMMO);
                  playerData.put(RankManager.Key.MCMMO, 1);
               }

               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§2You are viewing the path to '§6Gold§2' for " + target.getName() + ".");
               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§7Requirements:");
               StringBuilder builder = new StringBuilder();
               builder.append("§9Play for 1 day: ");
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.playTimeManager.formatTime(target))
                     .append(" / 1 day (")
                     .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(target), 86400000L))
                     .append("%)");
               }

               builder.append("\n").append("§9700 mcmmo powerlevel: ");
               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(ExperienceAPI.getPowerLevel(target))
                     .append(" / 700 (")
                     .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(target), 700))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 5x 64 Gold Ingot: ");
               if (playerData.getOrDefault(RankManager.Key.GOLD, 0) == 256) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.GOLD, 0)))
                     .append(" / ")
                     .append(this.formatItems(320))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.GOLD, 0), 320))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 4x 64 Carrots: ");
               if (playerData.getOrDefault(RankManager.Key.CARROT, 0) == 320) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.CARROT, 0)))
                     .append(" / ")
                     .append(this.formatItems(320))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.CARROT, 0), 320))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 5x 64 Potato: ");
               if (playerData.getOrDefault(RankManager.Key.POTATO, 0) == 320) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.POTATO, 0)))
                     .append(" / ")
                     .append(this.formatItems(320))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.POTATO, 0), 320))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 5x 64 Beetroot: ");
               if (playerData.getOrDefault(RankManager.Key.BEETROOT, 0) == 320) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.BEETROOT, 0)))
                     .append(" / ")
                     .append(this.formatItems(320))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.BEETROOT, 0), 320))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 5x 64 Wheat: ");
               if (playerData.getOrDefault(RankManager.Key.WHEAT, 0) == 320) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.WHEAT, 0)))
                     .append(" / ")
                     .append(this.formatItems(320))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.WHEAT, 0), 320))
                     .append("%)");
               }

               for(String s : builder.toString().split("\n")) {
                  sender.sendMessage(s);
               }
               break;
            case GOLD:
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(target, 10L, TimeUnit.DAYS)) {
                  playerData.remove(RankManager.Key.PLAYTIME);
                  playerData.put(RankManager.Key.PLAYTIME, 1);
               }

               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 0 && this.checkMcmmo(target, 1000)) {
                  playerData.remove(RankManager.Key.MCMMO);
                  playerData.put(RankManager.Key.MCMMO, 1);
               }

               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§2You are viewing the path to '§6Diamond§2' for " + target.getName() + ".");
               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§7Requirements:");
               StringBuilder builder = new StringBuilder();
               builder.append("§9Play for 3 days: ");
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.playTimeManager.formatTime(target))
                     .append(" / 3 days (")
                     .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(target), 259200000L))
                     .append("%)");
               }

               builder.append("\n").append("§91000 mcmmo powerlevel: ");
               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(ExperienceAPI.getPowerLevel(target))
                     .append(" / 1000 (")
                     .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(target), 1000))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 5x 64 Diamonds: ");
               if (playerData.getOrDefault(RankManager.Key.DIAMOND, 0) == 320) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.DIAMOND, 0)))
                     .append(" / ")
                     .append(this.formatItems(320))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.DIAMOND, 0), 320))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 10x 64 Glow Berries: ");
               if (playerData.getOrDefault(RankManager.Key.GLOW_BERRY, 0) == 640) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.GLOW_BERRY, 0)))
                     .append(" / ")
                     .append(this.formatItems(640))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.GLOW_BERRY, 0), 640))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 10x 64 Melons: ");
               if (playerData.getOrDefault(RankManager.Key.MELON, 0) == 640) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.MELON, 0)))
                     .append(" / ")
                     .append(this.formatItems(640))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.MELON, 0), 640))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 10x 64 Pumpkins: ");
               if (playerData.getOrDefault(RankManager.Key.PUMPKIN, 0) == 640) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.PUMPKIN, 0)))
                     .append(" / ")
                     .append(this.formatItems(640))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.PUMPKIN, 0), 640))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 64 Cakes: ");
               if (playerData.getOrDefault(RankManager.Key.CAKE, 0) == 64) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.CAKE, 0)))
                     .append(" / ")
                     .append(this.formatItems(64))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.CAKE, 0), 64))
                     .append("%)");
               }

               for(String s : builder.toString().split("\n")) {
                  sender.sendMessage(s);
               }
               break;
            case DIAMOND:
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(target, 5L, TimeUnit.DAYS)) {
                  playerData.remove(RankManager.Key.PLAYTIME);
                  playerData.put(RankManager.Key.PLAYTIME, 1);
               }

               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 0 && this.checkMcmmo(target, 1700)) {
                  playerData.remove(RankManager.Key.MCMMO);
                  playerData.put(RankManager.Key.MCMMO, 1);
               }

               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§2You are viewing the path to '§6Obsidian§2' for " + target.getName() + ".");
               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§7Requirements:");
               StringBuilder builder = new StringBuilder();
               builder.append("§9Play for 5 days: ");
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.playTimeManager.formatTime(target))
                     .append(" / 5 days (")
                     .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(target), 432000000L))
                     .append("%)");
               }

               builder.append("\n").append("§91700 mcmmo powerlevel: ");
               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(ExperienceAPI.getPowerLevel(target))
                     .append(" / 1700 (")
                     .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(target), 1700))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 5x 64 Obsidian: ");
               if (playerData.getOrDefault(RankManager.Key.OBSIDIAN, 0) == 320) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.OBSIDIAN, 0)))
                     .append(" / ")
                     .append(this.formatItems(320))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.OBSIDIAN, 0), 320))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 25x 64 Pointed Dripstone: ");
               if (playerData.getOrDefault(RankManager.Key.POINTED_DRIPSTONE, 0) == 1600) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.POINTED_DRIPSTONE, 0)))
                     .append(" / ")
                     .append(this.formatItems(1600))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.POINTED_DRIPSTONE, 0), 1600))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 25x 64 Amethyst Cluster: ");
               if (playerData.getOrDefault(RankManager.Key.AMETHYST_CLUSTER, 0) == 1600) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.AMETHYST_CLUSTER, 0)))
                     .append(" / ")
                     .append(this.formatItems(1600))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.AMETHYST_CLUSTER, 0), 1600))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 25x 64 Copper Ore: ");
               if (playerData.getOrDefault(RankManager.Key.COPPER_ORE, 0) == 1600) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.COPPER_ORE, 0)))
                     .append(" / ")
                     .append(this.formatItems(1600))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.COPPER_ORE, 0), 1600))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 10x 64 Calcite: ");
               if (playerData.getOrDefault(RankManager.Key.CALCITE, 0) == 640) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.CALCITE, 0)))
                     .append(" / ")
                     .append(this.formatItems(640))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.CALCITE, 0), 640))
                     .append("%)");
               }

               for(String s : builder.toString().split("\n")) {
                  sender.sendMessage(s);
               }
               break;
            case OBSIDIAN:
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(target, 7L, TimeUnit.DAYS)) {
                  playerData.remove(RankManager.Key.PLAYTIME);
                  playerData.put(RankManager.Key.PLAYTIME, 1);
               }

               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 0 && this.checkMcmmo(target, 2500)) {
                  playerData.remove(RankManager.Key.MCMMO);
                  playerData.put(RankManager.Key.MCMMO, 1);
               }

               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§2You are viewing the path to '§6Netherite§2' for " + target.getName() + ".");
               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§7Requirements:");
               StringBuilder builder = new StringBuilder();
               builder.append("§9Play for 7 days: ");
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.playTimeManager.formatTime(target))
                     .append(" / 7 days (")
                     .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(target), 604800000L))
                     .append("%)");
               }

               builder.append("\n").append("§92500 mcmmo powerlevel: ");
               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(ExperienceAPI.getPowerLevel(target))
                     .append(" / 2500 (")
                     .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(target), 2500))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 64 Netherite: ");
               if (playerData.getOrDefault(RankManager.Key.NETHERITE_INGOT, 0) == 64) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.NETHERITE_INGOT, 0)))
                     .append(" / ")
                     .append(this.formatItems(64))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.NETHERITE_INGOT, 0), 64))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 30x 64 Raw Beef: ");
               if (playerData.getOrDefault(RankManager.Key.BEEF, 0) == 1920) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.BEEF, 0)))
                     .append(" / ")
                     .append(this.formatItems(1920))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.BEEF, 0), 1920))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 30x 64 Raw Mutton: ");
               if (playerData.getOrDefault(RankManager.Key.MUTTON, 0) == 1920) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.MUTTON, 0)))
                     .append(" / ")
                     .append(this.formatItems(1920))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.MUTTON, 0), 1920))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 30x 64 Raw Pork: ");
               if (playerData.getOrDefault(RankManager.Key.PORK, 0) == 1920) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.PORK, 0)))
                     .append(" / ")
                     .append(this.formatItems(1920))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.PORK, 0), 1920))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 30x 64 Raw Chicken: ");
               if (playerData.getOrDefault(RankManager.Key.CHICKEN, 0) == 1920) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.CHICKEN, 0)))
                     .append(" / ")
                     .append(this.formatItems(1920))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.CHICKEN, 0), 1920))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 30x 64 Raw Rabbit: ");
               if (playerData.getOrDefault(RankManager.Key.RABBIT, 0) == 1920) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.RABBIT, 0)))
                     .append(" / ")
                     .append(this.formatItems(1920))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.RABBIT, 0), 1920))
                     .append("%)");
               }

               for(String s : builder.toString().split("\n")) {
                  sender.sendMessage(s);
               }
               break;
            case NETHERITE:
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 0 && this.checkPlaytime(target, 9L, TimeUnit.DAYS)) {
                  playerData.remove(RankManager.Key.PLAYTIME);
                  playerData.put(RankManager.Key.PLAYTIME, 1);
               }

               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 0 && this.checkMcmmo(target, 5000)) {
                  playerData.remove(RankManager.Key.MCMMO);
                  playerData.put(RankManager.Key.MCMMO, 1);
               }

               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§2You are viewing the path to '§6Emerald§2' for " + target.getName() + ".");
               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§7Requirements:");
               StringBuilder builder = new StringBuilder();
               builder.append("§9Play for 9 days: ");
               if (playerData.getOrDefault(RankManager.Key.PLAYTIME, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.playTimeManager.formatTime(target))
                     .append(" / 9 days (")
                     .append(Math.floorDiv(100L * this.playTimeManager.getPlayTime(target), 777600000L))
                     .append("%)");
               }

               builder.append("\n").append("§95000 mcmmo powerlevel: ");
               if (playerData.getOrDefault(RankManager.Key.MCMMO, 0) == 1) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(ExperienceAPI.getPowerLevel(target))
                     .append(" / 5000 (")
                     .append(Math.floorDiv(100 * ExperienceAPI.getPowerLevel(target), 5000))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 64 Nether Star: ");
               if (playerData.getOrDefault(RankManager.Key.NETHERITE_INGOT, 0) == 64) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.NETHERITE_INGOT, 0)))
                     .append(" / ")
                     .append(this.formatItems(64))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.NETHERITE_INGOT, 0), 64))
                     .append("%)");
               }

               builder.append("\n").append("§9Obtain 35x 64 Emerald Blocks: ");
               if (playerData.getOrDefault(RankManager.Key.EMERALD_BLOCK, 0) == 2240) {
                  builder.append("§a✓");
               } else {
                  builder.append("§c✗")
                     .append("\n")
                     .append("§7-  §9")
                     .append(this.formatItems(playerData.getOrDefault(RankManager.Key.EMERALD_BLOCK, 0)))
                     .append(" / ")
                     .append(this.formatItems(2240))
                     .append(" (")
                     .append(Math.floorDiv(100 * playerData.getOrDefault(RankManager.Key.EMERALD_BLOCK, 0), 2240))
                     .append("%)");
               }

               for(String s : builder.toString().split("\n")) {
                  sender.sendMessage(s);
               }
               break;
            case EMERALD:
               sender.sendMessage("§3-----------------------");
               sender.sendMessage("§2Unable to find a path after '§6Emerald§2' for " + target.getName() + ".");
               sender.sendMessage("§3-----------------------");
         }

      }
   }

   private boolean checkPlaytime(Player player, long req, TimeUnit unit) {
      return this.playTimeManager.getPlayTime(player) >= unit.toMillis(req);
   }

   private boolean checkMcmmo(Player player, int req) {
      return ExperienceAPI.getPowerLevel(player) >= req;
   }

   private int checkItem(Player player, Material material, int req) {
      if (req != 0 && player.getInventory().contains(material)) {
         int first = player.getInventory().first(material);
         ItemStack stack = player.getInventory().getItem(first);
         if (stack.getAmount() <= req) {
            player.getInventory().clear(first);
            return stack.getAmount() + this.checkItem(player, material, req - stack.getAmount());
         } else {
            int val = stack.getAmount() - req;
            stack.setAmount(val);
            player.getInventory().setItem(first, stack);
            return req;
         }
      } else {
         return 0;
      }
   }

   private int checkItemLogs(Player player, int req) {
      if (req == 0) {
         return 0;
      } else {
         List<Material> logList = Arrays.asList(
            Material.ACACIA_LOG,
            Material.ACACIA_WOOD,
            Material.BIRCH_LOG,
            Material.BIRCH_WOOD,
            Material.DARK_OAK_LOG,
            Material.DARK_OAK_WOOD,
            Material.JUNGLE_LOG,
            Material.JUNGLE_WOOD,
            Material.OAK_LOG,
            Material.OAK_WOOD,
            Material.SPRUCE_LOG,
            Material.SPRUCE_WOOD,
            Material.CRIMSON_HYPHAE,
            Material.CRIMSON_STEM,
            Material.WARPED_HYPHAE,
            Material.WARPED_STEM,
            Material.STRIPPED_ACACIA_LOG,
            Material.STRIPPED_ACACIA_WOOD,
            Material.STRIPPED_BIRCH_LOG,
            Material.STRIPPED_BIRCH_WOOD,
            Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_DARK_OAK_WOOD,
            Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_JUNGLE_WOOD,
            Material.STRIPPED_OAK_LOG,
            Material.STRIPPED_OAK_WOOD,
            Material.STRIPPED_SPRUCE_LOG,
            Material.STRIPPED_SPRUCE_WOOD,
            Material.STRIPPED_CRIMSON_HYPHAE,
            Material.STRIPPED_CRIMSON_STEM,
            Material.STRIPPED_WARPED_HYPHAE,
            Material.STRIPPED_WARPED_STEM
         );
         int first = Integer.MAX_VALUE;

         for(Material material : logList) {
            if (player.getInventory().contains(material) && player.getInventory().first(material) < first) {
               first = player.getInventory().first(material);
            }
         }

         if (first == Integer.MAX_VALUE) {
            return 0;
         } else {
            ItemStack stack = player.getInventory().getItem(first);
            if (stack.getAmount() <= req) {
               player.getInventory().clear(first);
               return stack.getAmount() + this.checkItemLogs(player, req - stack.getAmount());
            } else {
               int val = stack.getAmount() - req;
               stack.setAmount(val);
               player.getInventory().setItem(first, stack);
               return req;
            }
         }
      }
   }

   private String formatItems(int amount) {
      if (amount < 64) {
         return String.valueOf(amount);
      } else if (amount % 64 == 0) {
         int stacks = Math.floorDiv(amount, 64);
         return stacks + " stacks";
      } else {
         int stacks = Math.floorDiv(amount, 64);
         return stacks + " stacks and " + (amount - stacks * 64);
      }
   }

   public void save() {
      this.configuration.getData().set("playerdata", null);

      for(OfflinePlayer player : this.playerData.keySet()) {
         String data = Encrypter.encryptPlayerData((Map<RankManager.Key, Integer>)this.playerData.get(player));
         this.configuration.getData().set("playerdata." + player.getUniqueId().toString(), data);
      }

   }

   private void load() {
      this.playerData = new HashMap();
      if (this.configuration.getData().isConfigurationSection("playerdata")) {
         for(String key : this.configuration.getData().getConfigurationSection("playerdata").getKeys(false)) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(key));
            Map<RankManager.Key, Integer> data = Encrypter.decryptPlayerData(player, this.configuration.getData().getString("playerdata." + key));
            this.playerData.put(player, data);
         }

      }
   }

   static enum Key {
      PLAYTIME,
      MCMMO,
      COAL,
      LOG,
      IRON,
      GOLD,
      CARROT,
      POTATO,
      BEETROOT,
      WHEAT,
      DIAMOND,
      GLOW_BERRY,
      MELON,
      PUMPKIN,
      CAKE,
      OBSIDIAN,
      POINTED_DRIPSTONE,
      AMETHYST_CLUSTER,
      COPPER_ORE,
      CALCITE,
      NETHERITE_INGOT,
      BEEF,
      MUTTON,
      PORK,
      CHICKEN,
      RABBIT,
      NETHER_STAR,
      EMERALD_BLOCK;
   }
}
