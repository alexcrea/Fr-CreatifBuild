package fr.alexcrea.cmd;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.alexcrea.CreatifBuild.Chunk;
import fr.alexcrea.CreatifBuild.Main;
import fr.alexcrea.CreatifBuild.OfflinePlayer;

public class AddCoBuilder implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			int X = p.getLocation().getChunk().getX();
			int Z = p.getLocation().getChunk().getZ();
			if(args.length == 0) {
				p.sendMessage("§cMerci de Séléctioné un joueur");
			}else {
				UUID pu2 = OfflinePlayer.GetPlayerUUID(args[0]);
				if(pu2.equals(p.getUniqueId())) {p.sendMessage("§cvous ne pouvait pas vous rendre CoBuilder Sur votre propre Chunck");}
				Chunk chunk = Main.getChunk(X+";"+Z);
				if(!Main.GetPlayer(p.getUniqueId()).getListChunk().contains(chunk)) {p.sendMessage("§4çe chunk ne vous appartien pas");return false;}
				else if(chunk.getCop().contains(pu2)) {p.sendMessage("§e"+args[0]+" §cest déja un cobuilder de çe chunk");return false;}
				else {chunk.AddCoP(pu2);p.sendMessage("§e"+args[0]+" §aest Bien m'intenant un CoBuilder de çe chunk");}
			}
		}
		return false;
	}

}
