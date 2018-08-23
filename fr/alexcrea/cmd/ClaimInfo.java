package fr.alexcrea.cmd;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.alexcrea.CreatifBuild.Chunk;
import fr.alexcrea.CreatifBuild.Main;

public class ClaimInfo implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(!(sender instanceof Player)) {if(args.length == 0) {sender.sendMessage("§cChosi un chunk");return false;}}
		Chunk chunk = null;
		try{
			if(args.length == 0) {Player p = (Player) sender;chunk = Main.getChunk(p.getLocation().getChunk().getX()+";"+p.getLocation().getChunk().getZ());}
			else {chunk = Main.getChunk(args[0]);}
		}catch (Exception e) {e.printStackTrace();}
		if(chunk == null) {sender.sendMessage("§cCe chunk n'existe pas ou n'est pas claim");return false;}
		else{
			OpenClaimInfo(chunk, (Player)sender);
			sender.sendMessage("");
			sender.sendMessage("§eles info du Chunks "+Main.GetChunkName(chunk)+" :");
			sender.sendMessage("");
			sender.sendMessage("§eOwner :");
			
			if(Bukkit.getOfflinePlayer(chunk.getP()) == null || Bukkit.getOfflinePlayer(chunk.getP()).getName() == null) {sender.sendMessage("- §eInconue");}
			else{sender.sendMessage("- §e"+Bukkit.getOfflinePlayer(chunk.getP()).getName());}
			if(chunk.getCop().isEmpty()) {return false;}
			sender.sendMessage("");
			sender.sendMessage("§eCoBuilder :");
			sender.sendMessage("");
			for(UUID CoP : chunk.getCop()) {
				if(Bukkit.getOfflinePlayer(CoP) == null || Bukkit.getOfflinePlayer(CoP).getName().equalsIgnoreCase("null")) {sender.sendMessage("- §eInconue");}
				else{sender.sendMessage("- §e"+Bukkit.getOfflinePlayer(CoP).getName());}
			}
		}
		return false;
	}

	public static void OpenClaimInfo(Chunk claim, Player p) {
		Inventory inv = Bukkit.createInventory(p, 27, "§eInfo du Chunks "+Main.GetChunkName(claim));
		
		ItemStack itm = new ItemStack(Material.SKULL_ITEM, 0, (short) 3);
		SkullMeta itmskm = (SkullMeta) itm.getItemMeta();
		itmskm.setOwner(Bukkit.getOfflinePlayer(claim.getP()).getName());
		itmskm.setDisplayName(Bukkit.getOfflinePlayer(claim.getP()).getName());
		itm.setItemMeta(itmskm);
			
		inv.setItem(12, itm);
		
		itm = new ItemStack(Material.BARRIER);
		ItemMeta itmm = itm.getItemMeta();
		itmm.setDisplayName("§cSuprimé çe Chunk de la list des Chunks");
		itm.setItemMeta(itmm);
		
		inv.setItem(14, itm);
		
		itm = new ItemStack(Material.PAPER);
		itmm = itm.getItemMeta();
		ArrayList<String>Lore = new ArrayList<String>();
		Lore.add("§eçe tp en :");
		Lore.add(" x : "+claim.getTPX());
		Lore.add(" y : "+claim.getTPY());
		Lore.add(" z : "+claim.getTPZ());
		itmm.setLore(Lore);
		itmm.setDisplayName(""+Main.GetChunkName(claim));
		itm.setItemMeta(itmm);
		
		inv.setItem(18, itm);
		
		p.openInventory(inv);
	}
	
	public static void OpenClaimCoBList(Chunk claim, int page, Player p) {
		
	}
	
}
