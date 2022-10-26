package eu.theracraft.theraranks.commands;

import eu.theracraft.theraranks.TheraRanks;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUpdate {
   public static void onCommand(TheraRanks plugin, CommandSender sender, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("This command can only be used by players!");
      } else {
         if (args.length == 1) {
            plugin.getRankManager().rankUpdate((Player)sender);
         } else {
            sender.sendMessage("§cUsage: /tr update");
         }

      }
   }
}
