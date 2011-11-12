package com.precipicegames.glyphgate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;




public class GlyphGate extends JavaPlugin {

	public void onDisable() {
		registeredGlyphs.clear();
	}
	private static int[][] basematrix = {
		{4,0,4},
		{0,35,0},
		{4,0,4}
	};
	
	public List<Glyph> registeredGlyphs;
	public void onEnable() {
		// TODO Auto-generated method stub
		
		registeredGlyphs = new ArrayList<Glyph>();
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvent(Event.Type.PLAYER_INTERACT, new PlayerGlyphListener(this), Event.Priority.Normal, this);
	}
	public void registerGlyph(Glyph g)
	{
		registeredGlyphs.add(g);
	}
	public class PlayerGlyphListener extends PlayerListener {
		private GlyphGate plugin;
		public PlayerGlyphListener(GlyphGate glyphGate) {
			plugin = glyphGate;
		}

		public void onPlayerInteract(PlayerInteractEvent event)
		{
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				int center = (GlyphGate.basematrix.length-1)/2;
				boolean failed = false;
				for(int iz = 0; iz < GlyphGate.basematrix.length && !failed; ++iz)
				{
					for(int ix = 0; ix < GlyphGate.basematrix[iz].length && !failed; ++ix)
					{
						Location l = event.getClickedBlock().getLocation();
						if(GlyphGate.basematrix[iz][ix] != 
							l.getWorld().getBlockTypeIdAt(l.getBlockX()+(ix-center), l.getBlockY(), l.getBlockZ()+(iz - center)))
						{
							failed = true;
						}
					}
				}
				if(!failed) //We passed initial introspection
				{
					for(Glyph g: plugin.registeredGlyphs)
					{
						int[][] matrix = g.getRuneMatrix();
						int gcenter = (matrix.length-1)/2;
						boolean gfailed = false;
						for(int iz = 0; iz < matrix.length && !gfailed; iz++)
						{
							for(int ix = 0; ix < matrix[iz].length && !gfailed; ix++)
							{
								if(matrix[iz][ix] != -1)
								{
									Location l = event.getClickedBlock().getLocation();
									int type = l.getWorld().getBlockTypeIdAt(l.getBlockX()+(ix-gcenter), l.getBlockY(), l.getBlockZ()+(iz-gcenter));
									if(matrix[iz][ix] != type)
									{
										System.out.println("error");
										gfailed = true;
									}
								}
							}
						}
						if(!gfailed)
						{
							System.out.println("Activated");
							g.activate(event.getPlayer(), event.getClickedBlock().getLocation());
						}
					}
				}
				
			}
		}
	}

}
