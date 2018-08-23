package fr.alexcrea.cmd;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.alexcrea.CreatifBuild.Chunk;
import fr.alexcrea.CreatifBuild.ChunkPlayer;
import fr.alexcrea.CreatifBuild.Main;

public class ClaimChunk implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			for(Chunk chunk : Main.ListChunk) {
				if(chunk.getX() == p.getLocation().getChunk().getX() && chunk.getY() == p.getLocation().getChunk().getZ()) {
					if(chunk.getP().equals(null)) {}
					else if(chunk.getP().equals(p.getUniqueId())) {p.sendMessage("§açe chunk t'appartin déja");return false;}
					else {p.sendMessage("§cçe chunk appartin déja a §e"+Bukkit.getOfflinePlayer(chunk.getP()).getName());return false;}
				}
			}
			for(ChunkPlayer cp : Main.ListPlayer) {
				try {
					if(cp.getP().equals(p.getUniqueId())) {
						if(cp.getChunkLeft() == 0) {p.sendMessage("§4Tu a pris tout t'est chunk");return false;}
						Location loc = p.getLocation();
						int TPX = loc.getBlockX();
						int TPY = loc.getBlockY();
						int TPZ = loc.getBlockZ();
						p.sendMessage("§2Tu a Claim Le Chunk §r, §e"+(cp.getChunkLeft()-1)+" §2Claim Réstant");cp.addChunk(new Chunk(p.getUniqueId(), loc.getChunk().getX(), loc.getChunk().getZ(), TPX, TPY, TPZ));return false;}	
				} catch (Exception e) {e.printStackTrace();}
			}
		}
		return false;
	}

}
