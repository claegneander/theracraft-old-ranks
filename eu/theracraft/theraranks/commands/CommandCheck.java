package eu.theracraft.theraranks.commands;

import eu.theracraft.theraranks.TheraRanks;
import eu.theracraft.theraranks.utilities.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCheck {
   public static void onCommand(TheraRanks plugin, CommandSender sender, String[] args) {
      if (args.length == 1) {
         if (!(sender instanceof Player)) {
            sender.sendMessage("§cUsage: /tr check <player>");
         } else {
            plugin.getRankManager().rankCheck((Player)sender);
         }
      } else if (args.length == 2 && PermissionManager.checkOther(sender)) {
         Player player = Bukkit.getPlayer(args[1]);
         if (player == null) {
            sender.sendMessage("§cUnable to find player " + args[1] + ".");
         } else {
            plugin.getRankManager().rankCheck((Player)sender, player);
         }
      } else {
         sender.sendMessage("§cUsage: /tr check");
      }

   }
}
