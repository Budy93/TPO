/**
 * 
 */
package de.daniel_brueggemann.tpoprojekt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.WorldBorder;
import com.wimbli.*;

/**
 * @author Daniel Brüggemann
 *
 */
public class Tpo extends JavaPlugin
{
	// private Location location;
	public boolean active;
	public String prefix;
	public String alternativ;
	public int x;
	public int z;
	public int xgrenz;
	public int zgrenz;
	public int xrad;
	public int zrad;
	public int xsperr;
	public int zsperr;
	public WorldBorder wb;
	
	public void onEnable()
	{
		getConfig().addDefault("Active", true);
		getConfig().addDefault("Prefix", "[BPO]");
		/*getConfig().addDefault("x:", "12500");
		getConfig().addDefault("z:", "12500");
		getConfig().addDefault("Alternativ", "0 65 0");*/
		saveConfig();
		active = getConfig().getBoolean("Active");
		prefix = getConfig().getString("Prefix");
		/*x = getConfig().getInt("x:");
		z = getConfig().getInt("z:");
		alternativ = getConfig().getString("Alternativ");*/
		getLogger().info("BPO aktiv!");
		getLogger().info("BPO plugin by Daniel Brueggemann, Budy93 Version: 1");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	public void onDisable()
	{
		getLogger().info("BPO disabled!");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String label,
	        String[] args)
	{
		Player player = (Player) sender;
		String target = player.getName();
		if(!(sender instanceof Player))
		{
			player.sendMessage(ChatColor.RED + "Du musst ein Spieler sein!");
			return false;
		}
		if(player.hasPermission("bpo.teleporting.normal"))
		{
			if(args.length > 3)
			{
				sender.sendMessage(ChatColor.RED + "Zu viele Argumente!");
				return true;
			}
			else if(args.length < 3)
			{
				sender.sendMessage(ChatColor.RED + "Zu wenig Argumente!");
				return true;
			}
			else if(cmd.getName().equalsIgnoreCase("bpo"))
			{
				WorldBorder configwb=(WorldBorder)getServer().getPluginManager().getPlugin("WorldBorder");
				if(configwb==null||!configwb.isEnabled())
				{
					sender.sendMessage(ChatColor.RED+"Worldboarder fehlt");
					String xa = args[0];
					String ya = args[1];
					getLogger().info("BPO Befehl genutzt von:" + target);
					String za = args[2];
					// +12500 und -12500
					Boolean ok=true;
					int x=0;
	                try
	                {
		                x = Integer.parseInt(xa);
	                }
	                catch (NumberFormatException e)
	                {
		                ok=false;
	                }
					// ebene 65 Nullebene
					int y=80;
	                try
	                {
		                y = Integer.parseInt(ya);
	                }
	                catch (NumberFormatException e)
	                {
	                	ok=false;
	                }
					// 12500 und -12500
					int z=0;
	                try
	                {
		                z = Integer.parseInt(za);
	                }
	                catch (NumberFormatException e)
	                {
	                	ok=false;
	                }
	                if(ok==true)
	                {
						getLogger().info("BPO hat "+target+" nach "+x+" "+y+" "+z+" Teleportiert");
						String Befehl = "tp " + target + " " + x + " " + y + " " + z;
						Bukkit.getServer().dispatchCommand(
						        Bukkit.getServer().getConsoleSender(), Befehl);
						return true;
	                }
	                else
	                {
	                	player.sendMessage("Falsche angabe in den Koordinaten bitte nutze den Befehl wie folgt:\n/bpo x y z\n Beispiel\n/bpo 20 80 30");
	                	return false;
	                }
				}
				else
				{
					String welt = player.getLocation().getWorld().getName();
					//String welt=w.toString();
					FileConfiguration conwb=configwb.getConfig();
					xgrenz=conwb.getInt("worlds."+welt+".x");
					zgrenz=conwb.getInt("worlds."+welt+".z");
					xrad=conwb.getInt("worlds."+welt+".radiusX");
					zrad=conwb.getInt("worlds."+welt+".radiusZ");
					xsperr=xgrenz+xrad;
					zsperr=zgrenz+zrad;
					Integer xInteger = new Integer(xsperr);
			        String xb = xInteger.toString();
			        Integer zInteger = new Integer(zsperr);
			        String zb = zInteger.toString();
					String xa = args[0];
					String ya = args[1];
					getLogger().info("BPO Befehl genutzt von:" + target);
					String za = args[2];
					// +12500 und -12500
					Boolean ok=true;
					int x=0;
	                try
	                {
		                x = Integer.parseInt(xa);
	                }
	                catch (NumberFormatException e)
	                {
		                ok=false;
	                }
					// ebene 65 Nullebene
					int y=80;
	                try
	                {
		                y = Integer.parseInt(ya);
	                }
	                catch (NumberFormatException e)
	                {
		                ok=false;
	                }
					// 12500 und -12500
					int z=0;
	                try
	                {
		                z = Integer.parseInt(za);
	                }
	                catch (NumberFormatException e)
	                {
		                ok=false;
	                }
	                if(ok==true)
	                {
	                	if(player.hasPermission("bpo.teleporting.bypass"))
	                	{
	                		String Befehl = "tp " + target + " " + x + " " + y + " " + z;
	                		Bukkit.getServer().dispatchCommand(
							        Bukkit.getServer().getConsoleSender(), Befehl);
							return true;
	                	}
	                	else
	                	{
							if(x <= xsperr*-1 || x >= xsperr || z <= zsperr*-1 || z >= zsperr || y <= 0)
							{
								sender.sendMessage(ChatColor.RED + "Zu hohe Koordinaten");
								if(x <= xsperr*-1)
								{
									x=xsperr*-1;
								}
								if(x >= xsperr)
								{
									x=xsperr;
								}
								if(z <= zsperr*-1)
								{
									z=zsperr*-1;
								}
								if(z >= zsperr)
								{
									z=zsperr;
								}
								String falsch = "tp " + target + " " + x + " " + y + " " + z;
								getLogger().info(falsch);
								Bukkit.getServer().dispatchCommand(
								        Bukkit.getServer().getConsoleSender(), falsch);
								return true;
							}
							else
							{
								String Befehl = "tp " + target + " " + x + " " + y + " " + z;
								//sender.sendMessage(xb);
								//sender.sendMessage(zb);
								//sender.sendMessage(welt);
								getLogger().info(Befehl);
								Bukkit.getServer().dispatchCommand(
								        Bukkit.getServer().getConsoleSender(), Befehl);
								return true;
							}
	                	}
	                }
	                else
	                {
	                	player.sendMessage("Falsche angabe in den Koordinaten bitte nutze den Befehl wie folgt:\n/bpo x y z\n Beispiel\n/bpo 20 80 30");
	                	getLogger().info(target+" Nutzt BPO falsch");
	                	return false;
	                }
				}
			}
		}
		else
		{
			player.sendMessage("Du Darst diesen Befehl nicht ausführen");
        	getLogger().info(target+" Fehlt die Rechte für BPO");
        	return true;
		}
		
		
		return false;
	}
}
