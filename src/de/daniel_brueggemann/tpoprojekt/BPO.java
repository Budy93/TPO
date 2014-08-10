/**
 * 
 */
package de.daniel_brueggemann.tpoprojekt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.wimbli.WorldBorder.WorldBorder;

import java.lang.Math;

/**
 * @author Daniel Brüggemann
 *
 */
public class BPO extends JavaPlugin
{
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
	public int xsperrnegativ;
	public int zsperr;
	public int zsperrnegativ;
	public int radius = 0;
	public int xradiusbasis;
	public int zradiusbasis;
	public Boolean rundglobal = false;
	public Boolean weltrund = false;
	public WorldBorder wb;
	
	public void onEnable()
	{
		getConfig().addDefault("Active", true);
		getConfig().addDefault("Prefix", "[BPO]");
		/*
		 * getConfig().addDefault("x:", "12500"); getConfig().addDefault("z:",
		 * "12500"); getConfig().addDefault("Alternativ", "0 65 0");
		 */
		saveConfig();
		active = getConfig().getBoolean("Active");
		prefix = getConfig().getString("Prefix");
		/*
		 * x = getConfig().getInt("x:"); z = getConfig().getInt("z:");
		 * alternativ = getConfig().getString("Alternativ");
		 */
		getLogger().info("BPO aktiv!");
		getLogger().info("BPO plugin by Daniel Brueggemann, Budy93 Version: 1.01");
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
				WorldBorder configwb = (WorldBorder) getServer()
				        .getPluginManager().getPlugin("WorldBorder");
				FileConfiguration conwb = configwb.getConfig();
				String welt = player.getLocation().getWorld().getName();
				rundglobal=conwb.getBoolean("round-border");
				if(conwb.contains("worlds." + welt
				        + ".shape-round"))
				{
					weltrund=conwb.getBoolean("worlds." + welt
				        + ".shape-round");
				}
				if(configwb == null || !configwb.isEnabled())
				{
					if(player.hasPermission("bpo.information.error"))
					{
						sender.sendMessage(ChatColor.RED + "Worldboarder fehlt");
					}
					String xa = args[0];
					String ya = args[1];
					getLogger().info("BPO Befehl genutzt von:" + target);
					getLogger().info("No WorldBorder detected");
					String za = args[2];
					// +12500 und -12500
					Boolean ok = true;
					int x = 0;
					try
					{
						x = Integer.parseInt(xa);
					}
					catch (NumberFormatException e)
					{
						ok = false;
					}
					// ebene 65 Nullebene
					int y = 80;
					try
					{
						y = Integer.parseInt(ya);
					}
					catch (NumberFormatException e)
					{
						ok = false;
					}
					// 12500 und -12500
					int z = 0;
					try
					{
						z = Integer.parseInt(za);
					}
					catch (NumberFormatException e)
					{
						ok = false;
					}
					if(y <= 0)
					{
						ok = false;
					}
					if(ok == true)
					{
						getLogger().info(
						        "BPO hat " + target + " nach " + x + " " + y
						                + " " + z + " Teleportiert");
						String Befehl = "tp " + target + " " + x + " " + y
						        + " " + z;
						Bukkit.getServer().dispatchCommand(
						        Bukkit.getServer().getConsoleSender(), Befehl);
						return true;
					}
					else
					{
						player.sendMessage(ChatColor.RED+"Falsche angabe in den Koordinaten bitte nutze den Befehl wie folgt:\n/bpo x y z\n Beispiel\n/bpo 20 80 30");
						return false;
					}
				}
				else
				{
					if(!rundglobal && !weltrund)
					{
						//sender.sendMessage("Eckig");
						return portingEckig(sender, args, player, target, conwb,
                            welt);
					}
					else if(!rundglobal && weltrund)
					{
						
						//Hier
						//sender.sendMessage("Rund");
						return portingRund(sender, args, player, target, conwb,
                                welt);
					    //bis hier
					}
					else if(rundglobal && !weltrund)
					{
						//sender.sendMessage("Eckig");
						return portingEckig(sender, args, player, target, conwb,
                            welt);
					}
					else if(rundglobal && weltrund)
					{
						//sender.sendMessage("Rund");
						return portingRund(sender, args, player, target, conwb,
                                welt);
					}
					else
					{
						if(player.hasPermission("bpo.information.error"))
						{
							sender.sendMessage(ChatColor.RED+"Fehler: Die Welt ist sowohl rund als Eckig widerspruch\nVerhalte mich wie Eckigewelt");
						}
						return portingEckig(sender, args, player, target, conwb,
	                            welt);
					}
				}
				
				
			}
		}
		else
		{
			player.sendMessage("Du Darst diesen Befehl nicht ausführen");
			getLogger().info(target + " Fehlt die Rechte für BPO");
			return true;
		}
		
		return false;
	}

	/**
	 * @param sender
	 * @param args
	 * @param player
	 * @param target
	 * @param conwb
	 * @param welt
	 * @return
	 * @throws CommandException
	 */
    private boolean portingRund(CommandSender sender, String[] args,
            Player player, String target, FileConfiguration conwb, String welt)
            throws CommandException
    {
    	getLogger().info("Vorgehen Runde World");
	    xradiusbasis = conwb.getInt("worlds." + welt + ".x");
	    zradiusbasis = conwb.getInt("worlds." + welt + ".z");
	    radius = conwb.getInt("worlds." + welt + ".radiusX");
	    // Integer xInteger = new Integer(xsperr);
	    // String xb = xInteger.toString();
	    // Integer zInteger = new Integer(zsperr);
	    // String zb = zInteger.toString();
	    String xa = args[0];
	    String ya = args[1];
	    getLogger().info("BPO Befehl genutzt von:" + target+" es handelt sich um eine runde WorldBorder");
	    String za = args[2];
	    // +12500 und -12500
	    Boolean ok = true;
	    int x = 0;
	    try
	    {
	    	x = Integer.parseInt(xa);
	    }
	    catch (NumberFormatException e)
	    {
	    	ok = false;
	    }
	    // ebene 65 Nullebene
	    int y = 80;
	    try
	    {
	    	y = Integer.parseInt(ya);
	    }
	    catch (NumberFormatException e)
	    {
	    	ok = false;
	    }
	    // 12500 und -12500
	    int z = 0;
	    try
	    {
	    	z = Integer.parseInt(za);
	    }
	    catch (NumberFormatException e)
	    {
	    	ok = false;
	    }
	    if(ok == true)
	    {
	    	int xbetrag=Math.abs(x)+Math.abs(xradiusbasis);
	    	int zbetrag=Math.abs(z)+Math.abs(zradiusbasis);
	    	double zwischenspeicher=Math.pow(xbetrag,2)+Math.pow(zbetrag,2);
	    	int userradius=(int)Math.sqrt(zwischenspeicher);
	    	if(player.hasPermission("bpo.teleporting.bypass"))
	    	{
	    		if(userradius>=radius)
	    		{
	    			getLogger().info(target+" Nutzte die bypass Permission");
	    			if(player.hasPermission("bpo.information.notice"))
	    			{
	    				getLogger().info("Nutze Bypass Permission von"+target);
	    				sender.sendMessage(ChatColor.BLUE
	    			        + "Anmerkung: Zum nutzen des Bypass musst du auf die Bypass liste von WorldBoarder\nNutze dazu: /wb bypass "
	    			        + target + " on bzw off zum Entfernen");
	    			}
	    		}
	    		String Befehl = "tp " + target + " " + x + " " + y
	    		        + " " + z;
	    		Bukkit.getServer().dispatchCommand(
	    		        Bukkit.getServer().getConsoleSender(),
	    		        Befehl);
	    		return true;
	    	}
	    	else
	    	{
	    		if(userradius>=radius || y <= 0)
	    		{
	    			//Grenzport folgt irgendwann.
	    			sender.sendMessage(ChatColor.RED
	    			        + "Zu hohe Koordinaten, porten ist nur innerhalb der Worldboarder zulässig");
	    			/*
	    			String falsch = "tp " + target + " " + x + " "
	    			        + y + " " + z;
	    			getLogger().info(falsch);
	    			Bukkit.getServer().dispatchCommand(
	    			        Bukkit.getServer().getConsoleSender(),
	    			        falsch);
	    			        */
	    			return true;
	    		}
	    		else
	    		{
	    			String Befehl = "tp " + target + " " + x + " "
	    			        + y + " " + z;
	    			// sender.sendMessage(xb);
	    			// sender.sendMessage(zb);
	    			// sender.sendMessage(welt);
	    			getLogger().info(Befehl);
	    			Bukkit.getServer().dispatchCommand(
	    			        Bukkit.getServer().getConsoleSender(),
	    			        Befehl);
	    			return true;
	    		}
	    	}
	    }
	    else
	    {
	    	player.sendMessage("Falsche angabe in den Koordinaten bitte nutze den Befehl wie folgt:\n/bpo x y z\n Beispiel\n/bpo 20 80 30");
	    	getLogger().info(target + " Nutzt BPO falsch");
	    	return true;
	    }
    }

	/**
	 * @param sender
	 * @param args
	 * @param player
	 * @param target
	 * @param conwb
	 * @param welt
	 * @return
	 * @throws CommandException
	 */
    private boolean portingEckig(CommandSender sender, String[] args,
            Player player, String target, FileConfiguration conwb, String welt)
            throws CommandException
    {
    	getLogger().info("Eckige Welt vorhanden");
	    xgrenz = conwb.getInt("worlds." + welt + ".x");
	    zgrenz = conwb.getInt("worlds." + welt + ".z");
	    xrad = conwb.getInt("worlds." + welt + ".radiusX");
	    zrad = conwb.getInt("worlds." + welt + ".radiusZ");
	    xsperr = xgrenz + xrad;
	    xsperrnegativ=xgrenz-xrad;
	    zsperr = zgrenz + zrad;
	    zsperrnegativ=zgrenz-zrad;
	    // Integer xInteger = new Integer(xsperr);
	    // String xb = xInteger.toString();
	    // Integer zInteger = new Integer(zsperr);
	    // String zb = zInteger.toString();
	    String xa = args[0];
	    String ya = args[1];
	    getLogger().info("BPO Befehl genutzt von:" + target);
	    String za = args[2];
	    // +12500 und -12500
	    Boolean ok = true;
	    int x = 0;
	    try
	    {
	    	x = Integer.parseInt(xa);
	    }
	    catch (NumberFormatException e)
	    {
	    	ok = false;
	    }
	    // ebene 65 Nullebene
	    int y = 80;
	    try
	    {
	    	y = Integer.parseInt(ya);
	    }
	    catch (NumberFormatException e)
	    {
	    	ok = false;
	    }
	    // 12500 und -12500
	    int z = 0;
	    try
	    {
	    	z = Integer.parseInt(za);
	    }
	    catch (NumberFormatException e)
	    {
	    	ok = false;
	    }
	    if(ok == true)
	    {
	    	if(player.hasPermission("bpo.teleporting.bypass"))
	    	{
	    		getLogger().info("Nutzung der Bypass Permission von"+target);
	    		if(x <= xsperrnegativ || x >= xsperr
	    		        || z <= zsperrnegativ || z >= zsperr
	    		        || y <= 0)
	    		{
	    			getLogger().info(target+" Nutzte die bypass Permission");
	    			if(player.hasPermission("bpo.information.notice"))
	    			{
	    				sender.sendMessage(ChatColor.BLUE
	    			        + "Anmerkung: Zum nutzen des Bypass musst du auf die Bypass liste von WorldBoarder\nNutze dazu: /wb bypass "
	    			        + target + " on bzw off zum Entfernen");
	    			}
	    		}
	    		String Befehl = "tp " + target + " " + x + " " + y
	    		        + " " + z;
	    		Bukkit.getServer().dispatchCommand(
	    		        Bukkit.getServer().getConsoleSender(),
	    		        Befehl);
	    		return true;
	    	}
	    	else
	    	{
	    		if(x <= xsperrnegativ || x >= xsperr
	    		        || z <= zsperrnegativ || z >= zsperr
	    		        || y <= 0)
	    		{
	    			sender.sendMessage(ChatColor.RED
	    			        + "Zu hohe Koordinaten du wurdest zu einen Alternativ Punkt geportet");
	    			if(x <= xsperrnegativ)
	    			{
	    				x = xsperrnegativ;
	    			}
	    			if(x >= xsperr)
	    			{
	    				x = xsperr;
	    			}
	    			if(z <= zsperrnegativ)
	    			{
	    				z = zsperrnegativ;
	    			}
	    			if(z >= zsperr)
	    			{
	    				z = zsperr;
	    			}
	    			String falsch = "tp " + target + " " + x + " "
	    			        + y + " " + z;
	    			getLogger().info(falsch);
	    			Bukkit.getServer().dispatchCommand(
	    			        Bukkit.getServer().getConsoleSender(),
	    			        falsch);
	    			return true;
	    		}
	    		else
	    		{
	    			String Befehl = "tp " + target + " " + x + " "
	    			        + y + " " + z;
	    			// sender.sendMessage(xb);
	    			// sender.sendMessage(zb);
	    			// sender.sendMessage(welt);
	    			getLogger().info(Befehl);
	    			Bukkit.getServer().dispatchCommand(
	    			        Bukkit.getServer().getConsoleSender(),
	    			        Befehl);
	    			return true;
	    		}
	    	}
	    }
	    else
	    {
	    	player.sendMessage("Falsche angabe in den Koordinaten bitte nutze den Befehl wie folgt:\n/bpo x y z\n Beispiel\n/bpo 20 80 30");
	    	getLogger().info(target + " Nutzt BPO falsch");
	    	return true;
	    }
    }
}
