package com.henry.minecraftgamebase.arena;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.henry.minecraftgamebase.Main;
import com.henry.minecraftgamebase.arena.players.ArenaStatusMeta;
import com.henry.minecraftgamebase.arena.players.ArenaStatusMeta.ArenaStatus;
import com.henry.minecraftgamebase.arena.players.ArenaStatusMeta.Team;
import com.henry.minecraftgamebase.utilities.ArenaInfoAccessor;

public class ArenaManager {

	public static void toggleArenaActive(String arena)
			throws UnsupportedEncodingException {
		Arena a = load(arena);
		a.setActive(!a.isActive());
		save(a);
	}

	public static void toggleArenaActive(String arenaName, boolean b)
			throws UnsupportedEncodingException {
		Arena a = load(arenaName);
		a.setActive(b);
		save(a);
	}

	public static void joinPlayer(String arenaName, Player p)
			throws UnsupportedEncodingException {
		Arena a = load(arenaName);
		a.addPlayer(p);
		save(a);
	}

	public static void leavePlayer(String arenaName, Player p)
			throws UnsupportedEncodingException {
		Arena a = load(arenaName);
		ArrayList<Player> ps = a.getPlayers();
		if (ps.contains(p)) {
			ps.remove(p);
		}
		a.setPlayers(ps);
		save(a);
	}

	public static void votePlayer(String arenaName, Player p)
			throws UnsupportedEncodingException {
		if (!p.hasMetadata("voted")) {
			Arena a = load(arenaName);
			p.setMetadata("voted", new FixedMetadataValue(Main.plugin, true));
			a.setVotes(a.getVotes() + 1);
			save(a);
		}
	}

	public static void unvotePlayer(String arenaName, Player p)
			throws UnsupportedEncodingException {
		if (p.hasMetadata("voted")) {
			Arena a = load(arenaName);
			p.removeMetadata("voted", Main.plugin);
			a.setVotes(a.getVotes() - 1);
			save(a);
		}
	}

	public static void startGame(String arenaName)
			throws UnsupportedEncodingException {
		Arena a = load(arenaName);
		distributePlayers(arenaName);
		save(a);
	}

	public static void distributePlayers(String arenaName) {

	}

	public static void containPlayersInBounds(String arenaName) {

	}

	public static void setPlayersSpawnsForTeams(String arenaName, Team t)
			throws UnsupportedEncodingException {
		Arena a = load(arenaName);
		for (Player p : a.getPlayers()) {
			ArenaStatusMeta.setArenaStatus(p, a, ArenaStatus.INGAME_ALIVE, t);
		}
	}

	public static void endGame(String arenaName)
			throws UnsupportedEncodingException {
		Arena a = load(arenaName);
		a.setPlaying(false);
		resetGame(a);
		save(a);
	}

	private static void resetGame(Arena a) {
		a.setVotes(0);
		for (Player p : a.getPlayers()) {
			p.removeMetadata("voted", Main.plugin);
			p.teleport(a.getLobby().getCenterForSpawning());
		}
	}

	// used over and over

	private static Arena load(String arenaName)
			throws UnsupportedEncodingException {
		return ArenaInfoAccessor.getArena(arenaName);
	}

	private static void save(Arena arena) {
		ArenaInfoAccessor.saveToArenaInfo(arena);
	}

}
