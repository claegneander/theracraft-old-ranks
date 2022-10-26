package eu.theracraft.theraranks.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Encrypter {
   public static String encryptPlayerData(Map<RankManager.Key, Integer> map) {
      try {
         ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
         ObjectOutputStream stream = new ObjectOutputStream(byteStream);
         stream.writeObject(map);
         stream.close();
         return Base64.getEncoder().encodeToString(byteStream.toByteArray());
      } catch (Exception var3) {
         var3.printStackTrace();
         return "";
      }
   }

   public static Map<RankManager.Key, Integer> decryptPlayerData(OfflinePlayer player, String base64) {
      try {
         ByteArrayInputStream byteStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
         ObjectInputStream stream = new ObjectInputStream(byteStream);
         Object obj = stream.readObject();
         if (!(obj instanceof Map)) {
            Bukkit.getLogger().severe("&4Playerdata of " + player.getName() + " (" + player.getUniqueId().toString() + ") was corrupted!");
            Bukkit.getLogger().severe(base64);
            return new HashMap();
         } else {
            return (Map<RankManager.Key, Integer>)obj;
         }
      } catch (Exception var5) {
         var5.printStackTrace();
         return null;
      }
   }
}
