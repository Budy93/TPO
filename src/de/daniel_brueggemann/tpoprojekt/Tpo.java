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
		String target=player.getName();
		
		if (!(sender instanceof Player))
		{
	           player.sendMessage(ChatColor.RED + "Du musst ein Spieler sein!");
	           return false;
	    }
		if (args.length > 3) 
		{
	           sender.sendMessage(ChatColor.RED + "Zu viele Argumente!");
	           return true;
	    } 
		else if(args.length<3)
		{
			sender.sendMessage(ChatColor.RED + "Zu wenig Argumente!");
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("bpo"))
		{
				String xa=args[0];
				String ya=args[1];
				getLogger().info("BPO Befehl genutzt von:"+target);
				String za=args[2];
				//+12500 und -12500
				int x = Integer.parseInt(xa);
				//ebene 65 Nullebene
				int y = Integer.parseInt(ya);
				//12500 und -12500
				int z = Integer.parseInt(za);
				if(x<=-12500 || x>=12500 || z<=-12500 || z>=12500 || y<=0)
				{
					sender.sendMessage(ChatColor.RED + "Zu hohe Koordinaten");
					String falsch="tp "+target+" 0 65 0";
					getLogger().info(falsch);
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), falsch);
					return true;
				}
				else
				{
					String Befehl="tp "+target+" "+x+" "+y+" "+z;
					getLogger().info(Befehl);
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), Befehl);
				return true;
			}
		}
		return false;
	}
}
