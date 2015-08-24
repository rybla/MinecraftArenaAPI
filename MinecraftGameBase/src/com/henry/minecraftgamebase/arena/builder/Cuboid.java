package com.henry.minecraftgamebase.arena.builder;

import org.bukkit.Location;
import org.bukkit.World;

import com.henry.minecraftgamebase.Main;

public class Cuboid {

	public double x1, y1, z1, x2, y2, z2;

	public Location corner1;
	public Location corner2;

	public Cuboid(double x1, double y1, double z1, double x2, double y2,
			double z2) {
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;

		this.corner1 = new Location(Main.plugin.getServer().getWorlds().get(0),
				x1, y1, z1);
		this.corner2 = new Location(Main.plugin.getServer().getWorlds().get(0),
				x2, y2, z2);
	}

	public Cuboid(Cuboid c) {
		this.corner1 = c.corner1;
		this.corner2 = c.corner2;
	}

	public Location getCenterForSpawning() {
		World w = corner1.getWorld();
		double ax = Math.abs(corner1.getX() - corner2.getX()) / 2;
		double ay = corner1.getY() > corner2.getY() ? corner2.getY() : corner1
				.getY();
		double az = Math.abs(corner1.getZ() - corner2.getZ()) / 2;

		for (int x = 0; x < 100; x++) {
			if (w.getBlockAt((int) ax, (int) ay, (int) az).isEmpty()) {
				break;
			} else {
				ay++;
			}
		}

		return new Location(corner1.getWorld(), ax, ay, az);
	}

	public Location getCenter() {
		double ax = Math.abs(corner1.getX() - corner2.getX()) / 2;
		double ay = Math.abs(corner1.getY() - corner2.getY()) / 2;
		double az = Math.abs(corner1.getZ() - corner2.getZ()) / 2;
		return new Location(corner1.getWorld(), ax, ay, az);
	}

	public boolean containsPoint(Location l) {
		if (isBetween(l.getX(), x1, x2) && isBetween(l.getY(), y1, y2)
				&& isBetween(l.getZ(), z1, z2)) {
			return true;
		}
		return false;
	}

	public boolean containsCuboid(Cuboid c) {
		if (this.containsPoint(c.corner1) || this.containsPoint(c.corner2)) {
			return true;
		}
		return false;
	}

	private boolean isBetween(double x, double a, double b) {
		if (b > a) {
			double c;
			c = a;
			a = b;
			b = c;
		}
		if (x <= a && x >= b) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		return String.valueOf(x1) + ":" + String.valueOf(y1) + "::"
				+ String.valueOf(z1) + "+" + String.valueOf(x2) + ":"
				+ String.valueOf(y2) + "::" + String.valueOf(z2);
	}

	public static Cuboid fromString(String str) {
		String s1 = str.substring(0, str.indexOf("+") - 1);
		String s2 = str.substring(str.indexOf("+"), str.length() - 1);

		return new Cuboid(
				Double.valueOf(s1.substring(0, s1.indexOf(":") - 1)),
				Double.valueOf(s1.substring(s1.indexOf(":"), s1.indexOf("::"))),
				Double.valueOf(s1.substring(s1.indexOf("::"), s1.length() - 1)),
				Double.valueOf(s1.substring(0, s2.indexOf(":") - 1)),
				Double.valueOf(s1.substring(s2.indexOf(":"), s2.indexOf("::"))),
				Double.valueOf(s1.substring(s2.indexOf("::"), s2.length() - 1)));
	}
}
