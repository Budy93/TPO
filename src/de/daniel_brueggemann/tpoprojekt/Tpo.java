/**
 * 
 */
package de.daniel_brueggemann.tpoprojekt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Daniel Brüggemann
 *
 */
public class Tpo extends JavaPlugin
{
	//private Location location;

	public void onEnable()
	{
		getLogger().info("Tpo aktiv!");
        getLogger().info("Tpo plugin by Daniel Brüggemann, Budy93");
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	public void onDisable()
	{
		getLogger().info("Tpo disabled!");
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = (Player) sender;
		
		if (!(sender instanceof Player))
		{
	           player.sendMessage(ChatColor.RED + "Du musst ein Spieler sein!");
	           return false;
	    }
		if (args.length > 4) 
		{
	           sender.sendMessage(ChatColor.RED + "Zu viele Argumente!");
	           return true;
	    } 
		else if(args.length<4)
		{
			sender.sendMessage(ChatColor.RED + "Zu wenig Argumente!");
			return true;
		}
		else if(args.length==4)
		{
			String xa=args[1];
			String ya=args[2];
			String za=args[3];
			//+12500 und -12500
			int x = Integer.parseInt(xa);
			//ebene 65 Nullebene
			int y = Integer.parseInt(ya);
			//12500 und -12500
			int z = Integer.parseInt(za);
			if(x<-12500 || x>12500 || z<-12500 || z>12500 || y<0)
			{
				sender.sendMessage(ChatColor.RED + "Zu hohe Koordinaten");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tpos 0 65 0");
				return true;
			}
			else
			{
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tpos "+x+" "+y+" "+z);
			return true;
			}
		}
		return false;
	}
}
