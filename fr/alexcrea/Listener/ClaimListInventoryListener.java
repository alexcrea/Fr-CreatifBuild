package fr.alexcrea.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.alexcrea.CreatifBuild.ChunkPlayer;
import fr.alexcrea.CreatifBuild.Main;
import fr.alexcrea.CreatifBuild.OfflinePlayer;
import fr.alexcrea.cmd.ClaimInfo;
import fr.alexcrea.cmd.ClaimList;

public class ClaimListInventoryListener implements Listener {
	@EventHandler
	public static void OnClaimListInventoryClick(InventoryClickEvent e) {
		if(!e.getInventory().getName().startsWith("§eChunks de ")) {return;}
		e.setCancelled(true);
		Player p = (Player) e.getWhoClicked();
		ChunkPlayer cp = Main.GetPlayer(OfflinePlayer.GetPlayerUUID(e.getInventory().getItem(0).getItemMeta().getDisplayName()));
		switch (e.getCurrentItem().getType()) {
		case ARROW:
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§ePage Précédente")) {
				char dchar = e.getInventory().getName().toCharArray()[e.getInventory().getName().length()-1];
				int i = Integer.parseInt(""+dchar);
				
				ClaimList.OpenClaimInventory(p, cp, i-2);
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§ePage Suivante")){
				char dchar = e.getInventory().getName().toCharArray()[e.getInventory().getName().length()-1];
				int i = Integer.parseInt(""+dchar);
				
				ClaimList.OpenClaimInventory(p, cp, (i));
			}
			break;
		case PAPER:
			if(e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§e")) {return;}
			else {
				ClaimInfo.OpenClaimInfo(Main.getChunk(e.getCurrentItem().getItemMeta().getDisplayName()), p);
			}
		default:
			break;
		}
	}
	
}
