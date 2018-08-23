package fr.alexcrea.CreatifBuild;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.alexcrea.Listener.BlockListener;
import fr.alexcrea.Listener.ClaimInfoInventoryListener;
import fr.alexcrea.Listener.ClaimListInventoryListener;
import fr.alexcrea.Listener.PlayerListener;

public class Main extends JavaPlugin{
	
	public static ArrayList<ChunkPlayer> ListPlayer = new ArrayList<>();
	public static ArrayList<Chunk> ListChunk = new ArrayList<>();
	
	//private static Metrics metrics;
	
    public void onEnable(){
    	
		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new PlayerListener(), this);
		pm.registerEvents(new ClaimInfoInventoryListener(), this);
		pm.registerEvents(new ClaimListInventoryListener(), this);
		
    	getCommand("Claim").setExecutor(new fr.alexcrea.cmd.ClaimChunk());
    	getCommand("UnClaim").setExecutor(new fr.alexcrea.cmd.UnClaimChunk());
    	getCommand("AddCoB").setExecutor(new fr.alexcrea.cmd.AddCoBuilder());
    	getCommand("RemoveCoB").setExecutor(new fr.alexcrea.cmd.RemoveCoBuilder());
    	getCommand("ClaimList").setExecutor(new fr.alexcrea.cmd.ClaimList());
    	getCommand("ClaimInfo").setExecutor(new fr.alexcrea.cmd.ClaimInfo());
    	getCommand("JoinClaim").setExecutor(new fr.alexcrea.cmd.JoinCLain());
    	getCommand("LeaveClaim").setExecutor(new fr.alexcrea.cmd.LeaveClaim());
    	getCommand("SetClaimSpawn").setExecutor(new fr.alexcrea.cmd.SetClaimSpawn());
    	
    	try {
			File PlayersLists = new File("Creative//Players");
			File ChunkList = new File("Creative//ChunkList");
			PlayersLists.mkdirs();
			ChunkList.mkdirs();
			for(File f : PlayersLists.listFiles()) {
				if(f.getName().endsWith(".yml")) {
					Scanner sc = new Scanner(f);
					UUID p = UUID.fromString(sc.nextLine());
					int ChunkLeft = Integer.parseInt(sc.nextLine());
					ChunkPlayer cp = new ChunkPlayer(p, ChunkLeft);
					boolean XTrurn = true;
					String st = "";
					while (sc.hasNextLine()) {
						try {
							String stt = sc.nextLine();
							if(stt != null) {
								if(XTrurn) {
									st = stt;
								}else {
									st = st+";"+stt+".yml";
									cp.FirstaddChunk(ReadChunk(st));
								}
								XTrurn = !XTrurn;
							}
						} catch (Exception e) {e.printStackTrace();}
					}
					ListPlayer.add(cp);
					sc.close();
				}
			}
		} catch (Exception e) {e.printStackTrace();}
    }

	public static void ResetChunk(int x, int z) {
		File f = new File("Creative//ChunkList//"+x+";"+z+".yml");
		if(f.exists()) {
			f.delete();
		}
	}
	public static Chunk ReadChunk(String ChunkName) throws FileNotFoundException{
		Scanner sc = new Scanner(new File("Creative//ChunkList//"+ChunkName));
		UUID p = UUID.fromString(sc.nextLine());
		int X = Integer.parseInt(sc.nextLine());
		int Y = Integer.parseInt(sc.nextLine());
		double TPX = Double.parseDouble(sc.nextLine());
		double TPY = Double.parseDouble(sc.nextLine());
		double TPZ = Double.parseDouble(sc.nextLine());
		Chunk chunk = new Chunk(p, X, Y, TPX, TPY, TPZ);
		while (sc.hasNextLine()) {
			try {
				chunk.AddCoP(UUID.fromString(sc.nextLine()));
			} catch (Exception e) {e.printStackTrace();}
		}
		sc.close();
		return chunk;
	}
	
	public static ChunkPlayer GetPlayer(UUID uuid) {
		for(ChunkPlayer cp : ListPlayer) {
			if(cp.getP().equals(uuid)) {return cp;}
		}
		return null;
	}
	
	public static ArrayList<ChunkPlayer> ReadChunkCoP(String ChunkName) throws FileNotFoundException{
		Scanner sc = new Scanner(new File("Creative//ChunkList//"+ChunkName+".yml"));
		ArrayList<ChunkPlayer> ListCoP = new ArrayList<>();
		sc.nextLine();
		sc.nextLine();
		sc.nextLine();
		sc.nextLine();
		sc.nextLine();
		sc.nextLine();
		while (sc.hasNextLine()) {
			try {
				ListCoP.add(GetPlayer(UUID.fromString(sc.nextLine())));
			} catch (Exception e) {e.printStackTrace();}
		}
		sc.close();
		return ListCoP;
	}
	public static String GetChunkName(Chunk chunk) {
		return chunk.getX()+";"+chunk.getY();
	}
	public static boolean IsCoP(String ChunkName,ChunkPlayer Player) {
		try{
			for(ChunkPlayer cp : ReadChunkCoP(ChunkName)) {
				if(cp.equals(Player)) {return true;}
			}
			return false;
		}catch (Exception e) {e.printStackTrace();}
		return false;
	}

	public static Chunk getChunk(String ChunkName) {
		for(Chunk chunk : ListChunk) {
			if(GetChunkName(chunk).equalsIgnoreCase(ChunkName)) {return chunk;}
		}
		return null;
	}
}
