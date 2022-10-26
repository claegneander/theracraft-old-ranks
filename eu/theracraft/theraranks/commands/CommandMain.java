package eu.theracraft.theraranks.commands;

import eu.theracraft.theraranks.TheraRanks;
import eu.theracraft.theraranks.utilities.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMain implements CommandExecutor {
   public TheraRanks plugin;

   public CommandMain(TheraRanks plugin) {
      this.plugin = plugin;
      Bukkit.getPluginCommand("tr").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (args.length == 0) {
         this.sendUsages(sender);
      } else {
         String var5 = args[0].toLowerCase();
         switch(var5) {
            case "update":
               if (PermissionManager.update(sender)) {
                  CommandUpdate.onCommand(this.plugin, sender, args);
               } else {
                  this.sendUsages(sender);
               }
               break;
            case "check":
               if (PermissionManager.check(sender)) {
                  CommandCheck.onCommand(this.plugin, sender, args);
               } else {
                  this.sendUsages(sender);
               }
               break;
            case "playtime":
               if (PermissionManager.playtime(sender)) {
                  CommandPlaytime.onCommand(this.plugin, sender, args);
               } else {
                  this.sendUsages(sender);
               }
               break;
            case "top":
               if (PermissionManager.top(sender)) {
                  CommandTop.onCommand(this.plugin, sender);
               } else {
                  this.sendUsages(sender);
               }
               break;
            default:
               this.sendUsages(sender);
         }
      }

      return true;
   }

   private void sendUsages(CommandSender sender) {
      StringBuilder builder = new StringBuilder();
      if (PermissionManager.check(sender) && sender instanceof Player) {
         builder.append("§6/tr check")
            .append(" §7- ")
            .append("§2Check the requirements and progress to your next rankup WITHOUT taking any items")
            .append("\n");
      }

      if (PermissionManager.checkOther(sender)) {
         builder.append("§6/tr check <player>").append(" §7- ").append("§2Check the requirements and progress to another player's next rankup").append("\n");
      }

      if (PermissionManager.update(sender) && sender instanceof Player) {
         builder.append("§6/tr update")
            .append(" §7- ")
            .append("§2Update the requirements for your rankup, this WILL take required items from your inv")
            .append("\n");
      }

      if (PermissionManager.playtime(sender) && sender instanceof Player) {
         builder.append("§6/tr playtime").append(" §7- ").append("§2Display your current playtime").append("\n");
      }

      if (PermissionManager.playtimeOther(sender)) {
         builder.append("§6/tr playtime <player>").append(" §7- ").append("§2Display another player's current playtime").append("\n");
      }

      if (PermissionManager.playtimeOther(sender)) {
         builder.append("§6/tr top").append(" §7- ").append("§2Display a list of the highest playtimes").append("\n");
      }

      String message = builder.toString().length() == 0 ? "" : builder.substring(0, builder.toString().length() - 1);
      if (builder.length() == 0) {
         sender.sendMessage("Unknown command. Type \"/help\" for help.");
      } else if (builder.length() == 1) {
         sender.sendMessage("§2Usage: " + message);
      } else {
         sender.sendMessage("§2Usages:\n" + message);
      }

   }
}
