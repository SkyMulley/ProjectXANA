package pro.mrcl.ProjectXANA.Depreciated;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class NuclearExplo {
    private Main plugin;
    private boolean isTowerDeactivated;

    public void setTowerDeactivated(Boolean bool) {this.isTowerDeactivated = bool;}

    public NuclearExplo(Main plugin){
        this.plugin = plugin;
    }

    public void startNuclearExplosion() {
        Bukkit.broadcastMessage("[Superscan]" +ChatColor.RED +" High voltage detected in Electric Pylon 2HA, 15 minutes to Voltage Limit cross");
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                if(isTowerDeactivated==false) {
                    Bukkit.broadcastMessage("[Superscan]" +ChatColor.RED +" High voltage detected in Electric Pylon 2HA, 10 minutes to Voltage Limit cross");
                }
            }
        }, 6000L);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                if(isTowerDeactivated==false) {
                    Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " High voltage detected in Electric Pylon 2HA, 5 minutes to Voltage Limit cross");
                }
            }
        }, 6000L);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                if(isTowerDeactivated==false) {
                    Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " High voltage detected in Electric Pylon 2HA, 5 minutes to Voltage Limit cross");
                }
            }
        }, 6000L);
    }
}