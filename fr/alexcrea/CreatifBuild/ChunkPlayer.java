package fr.alexcrea.CreatifBuild;

import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.UUID;

import org.bukkit.Bukkit;

import fr.alexcrea.cmd.ClaimList;

public class ChunkPlayer {
	
	private UUID p;
	private int ChunkLeft;
	private  ArrayList<Chunk> ListChunk;
	
	public ChunkPlayer(UUID p,int ChunkLeft) {
		this.setP(p);
		this.setChunkLeft(ChunkLeft);
		this.setListChunk(new ArrayList<>());
		ReWirteFile();
	}

	private void ReWirteFile() {
		try{
			File PlayerFile = new File("Creative//Players//"+this.p+".yml");
			PlayerFile.mkdirs();
			if(PlayerFile.exists()) {PlayerFile.delete();}
			PlayerFile.createNewFile();
			Formatter fc = new Formatter(PlayerFile);
			fc.format("%s", this.p+"\n");
			fc.format("%s", this.ChunkLeft+"\n");
			if(ListChunk != null && ListChunk.size() > 0) {
				for(Chunk chunk : ListChunk) {
					fc.format("%s", chunk.getX()+"\n");
					fc.format("%s", chunk.getY()+"\n");
				}
			}
			fc.close();
		}catch (Exception e) {e.printStackTrace();}
	}
	
	public int getChunkLeft() {return ChunkLeft;}
	public void setChunkLeft(int chunkLeft) {ChunkLeft = chunkLeft;}
	public UUID getP() {return p;}
	public void setP(UUID p) {this.p = p;}
	public boolean addChunk(Chunk chunk) {if(this.ChunkLeft != 0) {this.ChunkLeft--;FirstaddChunk(chunk);ClaimList.RefrechListInventory(Bukkit.getOfflinePlayer(this.getP()).getName());return true;}else{return false;}}
	public boolean removeChunk(Chunk chunk) {if(this.ListChunk.contains(chunk)) {chunk.setPR(null);this.ListChunk.remove(chunk);this.ChunkLeft++;Main.ListChunk.remove(chunk);Main.ResetChunk(chunk.getX(),chunk.getY());ReWirteFile();ClaimList.RefrechListInventory(Bukkit.getOfflinePlayer(this.getP()).getName());return true;}else{return false;}}
	public ArrayList<Chunk> getListChunk() {return ListChunk;}
	public void setListChunk(ArrayList<Chunk> listChunk) {ListChunk = listChunk;ReWirteFile();ClaimList.RefrechListInventory(Bukkit.getOfflinePlayer(this.getP()).getName());}

	public Chunk getChunk(String Name){
		for(Chunk chunk : this.ListChunk) {
			if(Main.GetChunkName(chunk).equalsIgnoreCase(Name)) {
				return chunk;
			}
		}
		return null;
	}
	
	public void FirstaddChunk(Chunk chunk) {ListChunk.add(chunk);Main.ListChunk.add(chunk);ReWirteFile();return;}
}
