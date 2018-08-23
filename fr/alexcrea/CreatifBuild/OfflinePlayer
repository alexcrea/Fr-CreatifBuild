package fr.alexcrea.CreatifBuild;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.entity.Player;

public class OfflinePlayer {
	
	public static void addplayer(Player p) {
		File file = new File("Creative//UUIDPlayer//"+p.getName()+".yml");
		file.mkdirs();
		if(file.exists()) {file.delete();}
		try {
			file.createNewFile();
			Formatter fc = new Formatter(file);
			fc.format("%s", p.getUniqueId()+"");
			fc.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	public static ChunkPlayer GetCPlayer(String PlayerName) {
		return Main.GetPlayer(GetPlayerUUID(PlayerName));
	}
	public static UUID GetPlayerUUID(String PlayerName) {
		try {
			Scanner sc = new Scanner(new File("Creative//UUIDPlayer//"+PlayerName+".yml"));
			UUID pUUID = UUID.fromString(sc.nextLine());
			sc.close();
			return pUUID;
		} catch (FileNotFoundException e) {return null;}
	}
}
