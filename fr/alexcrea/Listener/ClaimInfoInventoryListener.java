package fr.alexcrea.Listener;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.alexcrea.CreatifBuild.Chunk;
import fr.alexcrea.CreatifBuild.ChunkPlayer;
import fr.alexcrea.CreatifBuild.Main;
import fr.alexcrea.CreatifBuild.OfflinePlayer;
import fr.alexcrea.cmd.ClaimInfo;
import fr.alexcrea.cmd.ClaimList;

public class ClaimInfoInventoryListener implements Listener {
	
	private static HashMap<Player, String> NameChunkRemoved = new HashMap<>();
	
	@EventHandler
	public static void OnClaimInfoInventoryClick(InventoryClickEvent e) {
		if(!e.getInventory().getName().startsWith("§eInfo du Chunks ")) {return;}
		e.setCancelled(true);
		Player p = (Player) e.getWhoClicked();
		ChunkPlayer cp = Main.GetPlayer(OfflinePlayer.GetPlayerUUID(e.getInventory().getItem(12).getItemMeta().getDisplayName()));
		switch (e.getCurrentItem().getType()) {
		case SKULL_ITEM:
			ClaimList.OpenClaimInventory(p, cp, 0);
			break;
		case PAPER:
			cp.getChunk(e.getCurrentItem().getItemMeta().getDisplayName()).tp(p);
			break;
		case BARRIER:
			NameChunkRemoved.put(p, e.getInventory().getItem(18).getItemMeta().getDisplayName());
			OpenRemoveClaimConfirmInventory(p);
			break;
		default:
			break;
		}
	}
	
	public static void OpenRemoveClaimConfirmInventory(Player p) {
		Inventory inv = Bukkit.createInventory(p, 27, "§esuprimation du claim "+NameChunkRemoved.get(p));
		
		ItemStack itm = new ItemStack(Material.STAINED_CLAY, 1, (byte) 5);
		ItemMeta itmm = itm.getItemMeta();
		itmm.setDisplayName("§bConfirmé");
		itm.setItemMeta(itmm);
		
		inv.setItem(12, itm);
		
		itm = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
		itmm = itm.getItemMeta();
		itmm.setDisplayName("§cRefusé");
		itm.setItemMeta(itmm);
		
		inv.setItem(14, itm);
		
		p.openInventory(inv);
	}
	
	@EventHandler
	public static void OnConfirmInvClick(InventoryClickEvent e) {
		if(!e.getInventory().getName().startsWith("§esuprimation du claim ")) {return;}
		e.setCancelled(true);
		Player p = (Player) e.getWhoClicked();
		Chunk claim = Main.getChunk(NameChunkRemoved.get(p));
		switch (e.getCurrentItem().getItemMeta().getDisplayName()) {
		case "§cRefusé":
			ClaimInfo.OpenClaimInfo(claim, p);
			break;
		case "§bConfirmé":
			UUID pUUID = claim.getP();
			p.performCommand("unclaim "+NameChunkRemoved.get(p));
			ClaimList.OpenClaimInventory(p, Main.GetPlayer(pUUID), 0);
			break;

		default:
			break;
		}
	}
}
