package fr.alexcrea.Listener;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.alexcrea.CreatifBuild.Chunk;
import fr.alexcrea.CreatifBuild.ChunkPlayer;
import fr.alexcrea.CreatifBuild.Main;
import fr.alexcrea.CreatifBuild.OfflinePlayer;

public class PlayerListener implements Listener {
	
	@EventHandler
	public static void test(PlayerJoinEvent e) {
		UUID p = e.getPlayer().getUniqueId();
		OfflinePlayer.addplayer(e.getPlayer());
		for(ChunkPlayer cp : Main.ListPlayer) {
			if(cp.getP().equals(p)) {return;}
		}
		for(Chunk chunk : Main.ListChunk) {
			if(Main.GetChunkName(chunk).equalsIgnoreCase("-16;6")) {chunk.AddCoP(p);}
		}
		Main.ListPlayer.add(new ChunkPlayer(p, 10));
	}
}
