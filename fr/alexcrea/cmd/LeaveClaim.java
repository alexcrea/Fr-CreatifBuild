package fr.alexcrea.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.alexcrea.CreatifBuild.Chunk;
import fr.alexcrea.CreatifBuild.Main;

public class LeaveClaim implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Chunk chunk = null;
			Player p = (Player)sender;
			if(args.length == 0) {chunk = Main.getChunk(p.getLocation().getChunk().getX()+";"+p.getLocation().getChunk().getZ());}
			else {chunk = Main.getChunk(args[0]);}
			if(!chunk.getCop().contains(p.getUniqueId())) {chunk.RemoveCoP(p.getUniqueId());}
		}else {
			sender.sendMessage("§ctu n'est pas un joueurs , BAKA c'est toi moi du future");
			sender.sendMessage("§e ici moi du passé je suit nul xD");
		}
		return false;
	}

}
