package eu.theracraft.theraranks.commands;

import eu.theracraft.theraranks.TheraRanks;
import eu.theracraft.theraranks.utilities.PermissionManager;
import eu.theracraft.theraranks.utilities.PlayTimeManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTop {
   public static void onCommand(TheraRanks plugin, CommandSender sender) {
      StringBuilder builder = new StringBuilder();
      LinkedHashMap<UUID, Long> map = plugin.getPlayTimeManager().getTopPlayTimes();
      if (!PermissionManager.topStaff(sender)) {
      }

      boolean mentioned = false;
      List<UUID> keySet = new ArrayList(map.keySet());

      for(int i = 0; i < 10; ++i) {
         if (sender instanceof Player) {
            Player player = (Player)sender;
            if (player.getUniqueId().equals(keySet.get(i))) {
               builder.append(i + 1)
                  .append(" | ")
                  .append(player.getDisplayName())
                  .append(" - ")
                  .append(PlayTimeManager.formatTime(map.get(player.getUniqueId())))
                  .append("\n");
               mentioned = true;
               continue;
            }
         }

         OfflinePlayer player = Bukkit.getOfflinePlayer((UUID)keySet.get(i));
         builder.append(i + 1)
            .append(" | ")
            .append(player.getName())
            .append(" - ")
            .append(PlayTimeManager.formatTime(map.get(player.getUniqueId())))
            .append("\n");
      }

      if (!mentioned && sender instanceof Player) {
         Player player = (Player)sender;
         int pos = keySet.indexOf(player.getUniqueId());
         if (pos != -1) {
            builder.append(pos + 1)
               .append(" | ")
               .append(player.getDisplayName())
               .append(" - ")
               .append(PlayTimeManager.formatTime(map.get(player.getUniqueId())))
               .append("\n");
         }
      }

   }
}
