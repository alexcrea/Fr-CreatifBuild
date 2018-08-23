package fr.alexcrea.cmd;

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
import fr.alexcrea.CreatifBuild.ChunkPlayer;
import fr.alexcrea.CreatifBuild.Main;
import fr.alexcrea.CreatifBuild.OfflinePlayer;

public class ClaimList implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		Player p = (Player) sender;
		if(!(sender instanceof Player)) {if(args.length == 0) {sender.sendMessage("§cChosi un joueur");return false;}}
		ChunkPlayer cp = null;
		if(args.length == 0) {cp = Main.GetPlayer(p.getUniqueId());}
		else {cp = Main.GetPlayer(OfflinePlayer.GetPlayerUUID(args[0]));}
		if(cp.getListChunk().isEmpty()) {sender.sendMessage("§ele joueurs n'a pas de chunk claim");}
		else{
			OpenClaimInventory(p, cp, 0);
		}
		return false;
	}
	public static void OpenClaimInventory(Player p,ChunkPlayer cp,int Manytime) {
		int many = Manytime;
		Inventory inv = Bukkit.createInventory(p, 54, "§eChunks de "+Bukkit.getOfflinePlayer(cp.getP()).getName()+" Page "+(Manytime+1));
		
		ItemStack itm = new ItemStack(Material.SKULL_ITEM, 0, (short) 3);
		SkullMeta itmskm = (SkullMeta) itm.getItemMeta();
		itmskm.setOwner(Bukkit.getOfflinePlayer(cp.getP()).getName());
		itmskm.setDisplayName(Bukkit.getOfflinePlayer(cp.getP()).getName());
		itm.setItemMeta(itmskm);
		
		inv.setItem(0, itm);
		
		itm = new ItemStack(Material.IRON_FENCE);
		ItemMeta itmm = itm.getItemMeta();
		itmm.setDisplayName("§e");
		itm.setItemMeta(itmm);
		
		inv.setItem(1, itm);
		inv.setItem(10, itm);
		inv.setItem(19, itm);
		inv.setItem(28, itm);
		inv.setItem(37, itm);
		inv.setItem(46, itm);
		
		itm = new ItemStack(Material.PAPER);
		itmm = itm.getItemMeta();
		itmm.setDisplayName("§eil lui reste "+cp.getChunkLeft()+" Chunk a claim");
		itm.setItemMeta(itmm);
		
		inv.setItem(45, itm);
		
		int i = 2;
		for(Chunk chunk : cp.getListChunk()) {
			if(i == 54 && many == 0) {
			itm = new ItemStack(Material.ARROW);
			itmm = itm.getItemMeta();
			itmm.setDisplayName("§ePage Suivante");
			itm.setItemMeta(itmm);
			
			inv.setItem(18, itm);
			p.openInventory(inv);
			return;
			}
			else {
				try {
					if(many == 0) {
						itm = new ItemStack(Material.PAPER);
						itmm = itm.getItemMeta();
						itmm.setDisplayName(Main.GetChunkName(chunk));
						itm.setItemMeta(itmm);
						
						inv.setItem(i, itm);
					}
					i++;
				}catch (Exception e) {}
				switch (i) {
				case 9:
					i=11;break;
				case 18:
					i=20;break;
				case 27:
					i=29;break;
				case 36:
					i=38;break;
				case 45:
					i=47;break;
				case 53:
					if(many > 0) {
						i = 2;
						many--;
						itm = new ItemStack(Material.ARROW);
						itmm = itm.getItemMeta();
						itmm.setDisplayName("§ePage Précédente");
						itm.setItemMeta(itmm);
						
						inv.setItem(27, itm);
					}
				default:
					break;
				}
			}
		}
		p.openInventory(inv);
	}
	public static void RefrechListInventory(UUID PlayerInventoryUUID) {RefrechListInventory(""+Bukkit.getOfflinePlayer(PlayerInventoryUUID).getName());}
	public static void RefrechListInventory(String PlayerInventoryName) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			try {
				if(p.getOpenInventory().getTitle().equalsIgnoreCase("§eChunks de "+PlayerInventoryName)) {continue;}
				ChunkPlayer cp = Main.GetPlayer(OfflinePlayer.GetPlayerUUID(p.getOpenInventory().getItem(0).getItemMeta().getDisplayName()));
				char dchar = p.getOpenInventory().getTitle().toCharArray()[p.getOpenInventory().getTitle().length()-1];
				int Manytime = Integer.parseInt(""+(dchar-1));
				OpenClaimInventory(p, cp, Manytime);
			} catch (Exception e) {}
		}
	}
}
