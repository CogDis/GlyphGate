package com.precipicegames.glyphgate;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Glyph{
	abstract void activate(Player caster, Location loc);
	abstract int[][] getRuneMatrix();
}
