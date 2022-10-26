package eu.theracraft.theraranks.commands;

import com.gmail.nossr50.mcmmo.acf.InvalidCommandArgument;
import eu.theracraft.theraranks.TheraRanks;
import eu.theracraft.theraranks.utilities.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPlaytime implements CommandExecutor {
   private TheraRanks plugin;

   public CommandPlaytime(TheraRanks plugin) {
      this.plugin = plugin;
      Bukkit.getPluginCommand("playtime").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      String[] newArgs = new String[args.length + 1];
      newArgs[0] = "playtime";
      System.arraycopy(args, 0, newArgs, 1, args.length);
      onCommand(this.plugin, sender, newArgs);
      PermissionManager.playtime(sender);
      return true;
   }

   public static void onCommand(TheraRanks plugin, CommandSender sender, String[] args) {
      if (args.length == 1) {
         if (sender instanceof Player) {
            sender.sendMessage("§2" + sender.getName() + "'s current playtime is §6" + plugin.getPlayTimeManager().formatTime((Player)sender));
         } else {
            sender.sendMessage("§cThis command is meant for players ONLY.");
         }
      } else if (args.length == 2) {
         if (!PermissionManager.playtimeOther(sender)) {
            sender.sendMessage("§cUsage: /tr playtime");
         } else {
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
               sender.sendMessage("§cUnable to find player " + args[1] + ".");
            } else {
               sender.sendMessage("§2" + player.getName() + "'s current playtime is: §6" + plugin.getPlayTimeManager().formatTime(player));
            }
         }
      } else if (args.length == 3) {
         if (!(sender instanceof Player)) {
            sender.sendMessage("§cUsage: /tr playtime <player>");
            return;
         }

         if (!PermissionManager.playtimeAdmin((Player)sender)) {
            sender.sendMessage("§cUsage: /tr playtime");
            return;
         }

         String var11 = args[2].toLowerCase();
         switch(var11) {
            case "set":
               sender.sendMessage("ge moe wel een player en een nieuwe value geve eh kut\n-Johan");
               break;
            case "add":
               sender.sendMessage("ge moe wel een player en een nieuwe value geve eh kut\n-Johan");
               break;
            case "take":
               sender.sendMessage("ge moe wel een player en een nieuwe value geve eh kut\n-Johan");
               break;
            case "reset":
               Player player = Bukkit.getPlayer(args[1]);
               if (player == null) {
                  sender.sendMessage("§cUnable to find player " + args[1] + ".");
               } else {
                  plugin.getPlayTimeManager().resetPlayTime(player);
                  sender.sendMessage("§2Reset of §6" + player.getName() + "§2's playtime has been completed.");
               }
         }
      } else if (args.length == 4) {
         if (!(sender instanceof Player)) {
            sender.sendMessage("§cUsage: /tr playtime <player>");
            return;
         }

         if (!PermissionManager.playtimeAdmin((Player)sender)) {
            sender.sendMessage("§cUsage: /tr playtime");
            return;
         }

         if (args[2].toLowerCase().equals("reset")) {
            sender.sendMessage("ge moe wel alleen een player geve bij deze cmd eh kut\n-Johan");
            return;
         }

         Player player = Bukkit.getPlayer(args[1]);
         if (player == null) {
            sender.sendMessage("§cUnable to find player " + args[1] + ".");
            return;
         }

         long value;
         try {
            value = Long.parseLong(args[3]);
         } catch (NumberFormatException var10) {
            sender.sendMessage("§cInvalid number: " + args[3]);
            return;
         }

         String e = args[2].toLowerCase();
         switch(e) {
            case "set":
               plugin.getPlayTimeManager().setPlayTime(player, value);
               sender.sendMessage("§2Setting of §6" + player.getName() + "§2's playtime has been completed.");
               break;
            case "add":
               plugin.getPlayTimeManager().addPlayTime(player, value);
               sender.sendMessage("§2Adding to §6" + player.getName() + "§2's playtime has been completed.");
               break;
            case "take":
               try {
                  plugin.getPlayTimeManager().takePlayTime(player, value);
               } catch (InvalidCommandArgument var9) {
                  sender.sendMessage(var9.getMessage());
               }

               sender.sendMessage("§2Taking from §6" + player.getName() + "§2's playtime has been completed.");
               break;
            case "reset":
               sender.sendMessage("ge moe wel alleen een player geve bij deze cmd eh kut\n-Johan");
         }
      } else if (sender instanceof Player) {
         sender.sendMessage("§cUsage: /tr playtime");
      } else {
         sender.sendMessage("§cUsage: /tr playtime <player>");
      }

   }
}
