package fr.alexcrea.cmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.alexcrea.CreatifBuild.Chunk;
import fr.alexcrea.CreatifBuild.Main;

public class SetClaimSpawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			IsSetClaim(p,Main.getChunk(p.getLocation().getChunk().getX()+";"+p.getLocation().getChunk().getZ()));
		}
		return false;
	}

	private void IsSetClaim(Player p,Chunk chunk) {
		if(!Main.GetPlayer(chunk.getP()).equals(Main.GetPlayer(p.getUniqueId())) && !p.hasPermission("Build.Admin")) 
		{p.sendMessage("§Ctu n'as pas la permission");return;}
		Location loc = p.getLocation();
		chunk.setClaimSpawn(loc);
		p.sendMessage("§bTu a bien mit le spawn du claim en §e"+(int) loc.getX()+" "+(int) loc.getY()+" "+(int) loc.getZ());
	}

}
