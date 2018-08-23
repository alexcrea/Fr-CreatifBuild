package fr.alexcrea.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.alexcrea.CreatifBuild.Chunk;
import fr.alexcrea.CreatifBuild.Main;

public class BlockListener implements Listener {

	@EventHandler
	public static void OnBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if(e.getBlock().getType().equals(Material.TNT) && !p.hasPermission("Build.TNT")) {e.setCancelled(true);p.sendMessage("§cVous n'avait pas l'otorisation d'utilisé la tnt");return;}
		int X = e.getBlock().getLocation().getChunk().getX();
		int Z = e.getBlock().getLocation().getChunk().getZ();
		try {
			Chunk chunk = Main.getChunk(X+";"+Z);
			if(chunk.getP().equals(p.getUniqueId()) || chunk.getCop().contains(p.getUniqueId())) {
			}else {
				e.setCancelled(true);
				p.sendMessage("§cVous n'avait pas l'otorisation de faite Construire ici");
			}
		} catch (Exception e1) {
			e.setCancelled(true);
			p.sendMessage("§cVous n'avait pas l'otorisation de faite Construire ici");
		}
	}
	@EventHandler
	public static void OnBlockPlace(BlockBreakEvent e) {
		Player p = e.getPlayer();
		int X = e.getBlock().getLocation().getChunk().getX();
		int Z = e.getBlock().getLocation().getChunk().getZ();
		try {
			Chunk chunk = Main.getChunk(X+";"+Z);
			if(chunk.getP().equals(p.getUniqueId()) || chunk.getCop().contains(p.getUniqueId())) {
			}else {
				e.setCancelled(true);
				p.sendMessage("§cVous n'avait pas l'otorisation de détruire ici");
			}
		} catch (Exception e1) {
			e.setCancelled(true);
			p.sendMessage("§cVous n'avait pas l'otorisation de détruire ici");
		}
	}
	@EventHandler
	public static void CancelleDangerousItem(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if(e.getPlayer().hasPermission("Build.Admin")) {return;}
			if(e.getPlayer().getItemInHand().getType().equals(Material.FLINT_AND_STEEL)) {e.setCancelled(true);return;}
			if(e.getPlayer().getItemInHand().getType().equals(Material.LAVA_BUCKET)) {e.setCancelled(true);return;}
			if(e.getPlayer().getItemInHand().getType().equals(Material.MONSTER_EGG)) {e.setCancelled(true);return;}
			if(e.getPlayer().getItemInHand().getType().equals(Material.EXPLOSIVE_MINECART)) {e.setCancelled(true);return;}
		}
	}
}
