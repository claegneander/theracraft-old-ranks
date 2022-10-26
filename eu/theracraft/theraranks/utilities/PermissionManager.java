package eu.theracraft.theraranks.utilities;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

public class PermissionManager {
   public static boolean check(Permissible entity) {
      return entity.hasPermission("tr.check");
   }

   public static boolean checkOther(Permissible entity) {
      return entity.hasPermission("tr.check.other");
   }

   public static boolean update(Permissible entity) {
      return entity.hasPermission("tr.update");
   }

   public static boolean playtime(Permissible entity) {
      return entity.hasPermission("tr.playtime");
   }

   public static boolean playtimeOther(Permissible entity) {
      return entity.hasPermission("tr.playtime.other");
   }

   public static boolean playtimeAdmin(Player player) {
      return player.getUniqueId().toString().equals("b4680525-cb04-4d09-8109-bd0ab01d8850")
         || player.getUniqueId().toString().equals("a248325a-3235-4b6f-a081-17d657469424");
   }

   public static boolean top(Permissible entity) {
      return entity.hasPermission("tr.top");
   }

   public static boolean topStaff(Permissible entity) {
      return entity.hasPermission("tr.top.staff");
   }
}
