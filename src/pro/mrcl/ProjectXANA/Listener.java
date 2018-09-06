package pro.mrcl.ProjectXANA;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.LyokoWarriorEvents.DevirtualizationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.LyokoWarriorEvents.VirtualizationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.TowerEvents.TowerActivationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.TowerEvents.TowerDeactivationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listener implements org.bukkit.event.Listener{
    private XANAMain plugin;
    private Main pl;

    public Listener(XANAMain plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onTowerActivation(TowerActivationEvent event){
        Bukkit.getLogger().info("[PRX] Listener detects Tower Activation Event");
    }

    @EventHandler
    public void onTowerDeactivation(TowerDeactivationEvent event){
        Bukkit.getLogger().info("[PRX] Listener detects Tower Deactivation Event");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Bukkit.getLogger().info("[PRX] Listener detects Player Quit Event");
    }

    @EventHandler
    public void onVirtualisation(VirtualizationEvent event){
        Bukkit.getLogger().info("[PRX] Listener detects Virtualization Event");
    }

    @EventHandler
    public void onDevirtualisation(DevirtualizationEvent event){
        Bukkit.getLogger().info("[PRX] Listener detects Devirtualization Event");
    }
}
