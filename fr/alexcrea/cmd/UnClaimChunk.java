package fr.alexcrea.cmd;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.alexcrea.CreatifBuild.Chunk;
import fr.alexcrea.CreatifBuild.ChunkPlayer;
import fr.alexcrea.CreatifBuild.Main;
import fr.alexcrea.CreatifBuild.OfflinePlayer;

public class UnClaimChunk implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			int X = p.getLocation().getChunk().getX();
			int Z = p.getLocation().getChunk().getZ();
			ArrayList<ChunkPlayer> ListPlayer = Main.ListPlayer;
			if(!p.hasPermission("Build.RemoveOtherClaim") || args.length == 0) {
				ChunkPlayer cp = null;
				Chunk chunk = null;
				for(ChunkPlayer vcp : ListPlayer) {
					if(vcp.getP().equals(p.getUniqueId())) {
						cp = vcp;
					}
				}
				for(Chunk vchunk : cp.getListChunk()) {
					if(vchunk.getX() == X && vchunk.getY() == Z) {chunk = vchunk;}
				}
				if(cp.removeChunk(chunk)) {
					p.sendMessage("§2Ce chunk a bien aitait suprimé de la list de vos chunks");
				}else {p.sendMessage("§4çe chunk ne vous appartien pas");}
			}else {
				try{
					ChunkPlayer cp = OfflinePlayer.GetCPlayer(args[0]);
					Chunk chunk = null;
					for(Chunk vchunk : cp.getListChunk()) {
						if(vchunk.getX() == X && vchunk.getY() == Z) {chunk = vchunk;}
					}if(cp.removeChunk(chunk)) {
						p.sendMessage("§2Ce chunk a bien aitait suprimé de la list des chunks de §e"+args[0]);
					}else {p.sendMessage("§4çe chunk n'apartien pas a §e"+args[0]);}
				}catch (Exception e) {
					Chunk chunk = Main.getChunk(args[0]);
					ChunkPlayer cp = Main.GetPlayer(chunk.getP());
					if(!p.hasPermission("Build.RemoveOtherClaim") && !cp.equals(Main.GetPlayer(p.getUniqueId()))) {p.sendMessage("§4çe chunk ne vous appartien pas");return false;}
					cp.removeChunk(chunk);
					if(cp.equals(Main.GetPlayer(p.getUniqueId()))){
						p.sendMessage("§2Ce chunk a bien aitait suprimé de la list de vos chunks");return false;
					}
					p.sendMessage("§2Ce chunk a bien aitait suprimé de la list des chunks de §e"+Bukkit.getOfflinePlayer(chunk.getP()).getName());
				}
			}
		}
		return false;
	}

}