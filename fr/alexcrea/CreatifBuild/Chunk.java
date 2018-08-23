package fr.alexcrea.CreatifBuild;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Chunk {
	
	private int X;
	private int Y;
	private double TPX;
	private double TPY;
	private double TPZ;
	private UUID p;
	private ArrayList<UUID> CoP;
	
	public Chunk(UUID p,int X,int Y,double TPX,double TPY, double TPZ) {
		try {
			this.setP(p);
			this.setX(X);
			this.setY(Y);
			this.setTPX(TPX);
			this.setTPY(TPY);
			this.setTPZ(TPZ);
			this.CoP = new ArrayList<>();
			this.ReWirteChunk();
		} catch (Exception e) {e.printStackTrace();}
	}

	public void ReWirteChunk(){
		File ChunkFile = new File("Creative//ChunkList//"+this.X+";"+this.Y+".yml");
		ChunkFile.mkdirs();
		if(ChunkFile.exists()) {ChunkFile.delete();}
		if(this.p != null) {
			try {
			ChunkFile.createNewFile();
			Formatter fc;
			fc = new Formatter(ChunkFile);
			fc.format("%s", this.p+"\n");
			fc.format("%s", this.X+"\n");
			fc.format("%s", this.Y+"\n");
			fc.format("%s", this.TPX+"\n");
			fc.format("%s", this.TPY+"\n");
			fc.format("%s", this.TPZ+"\n");
			if(CoP != null) {
				for(UUID uuid : CoP) {
					fc.format("%s", uuid+"\n");
				}
			}
			fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public UUID getP() {return p;}
	public void setP(UUID p){this.p = p;}
	public void setPR(UUID p){this.p = p;ReWirteChunk();}
	public int getY() {return Y;}
	public void setY(int y) {Y = y;}
	public int getX() {return X;}
	public void setX(int x) {X = x;}
	public void AddCoP(UUID p){this.CoP.add(p);ReWirteChunk();}
	public void RemoveCoP(UUID p){if(this.CoP.contains(p)) {this.CoP.remove(p);ReWirteChunk();}}
	public ArrayList<UUID> getCop(){return CoP;}
	public double getTPY() {return TPY;}
	public void setTPY(double tPY) {TPY = tPY;ReWirteChunk();}
	public double getTPZ() {return TPZ;}
	public void setTPZ(double tPZ) {TPZ = tPZ;ReWirteChunk();}
	public double getTPX() {return TPX;}
	public void setTPX(double tPX) {TPX = tPX;ReWirteChunk();}
	
	public void setClaimSpawn(Location loc) {
		this.setTPX(loc.getBlockX());
		this.setTPY(loc.getBlockY());
		this.setTPZ(loc.getBlockZ());
	}
	
	public void tp(Player p) {
		p.teleport(new Location(p.getWorld(), this.getTPX(), this.getTPY(), this.getTPZ()));
	}
}