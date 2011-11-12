package com.precipicegames.glyphgate;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LandClaimGlyph implements Glyph{
	private static int[][] matrix = {
			{35, 0, 85, 0,35},
			{0,-1,-1,-1,0},
			{85,-1,-1,-1,85},
			{0,-1,-1,-1,0},
			{35, 0, 85, 0,35}
	};

	public void activate(Player caster, Location loc) {
		// TODO Auto-generated method stub
		
	}

	public int[][] getRuneMatrix() {
		return matrix;
	}

}
