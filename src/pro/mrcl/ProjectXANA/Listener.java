package pro.mrcl.ProjectXANA;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.TowerActivationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.TowerDeactivationEvent;
import org.bukkit.event.EventHandler;
import pro.mrcl.ProjectXANA.XANAAttacks.XanaficationPly;

public class Listener implements org.bukkit.event.Listener{
    private Main plugin;
    private mrcl.pro.GoodOldJack12.ProjectCarthage.Main prcplugin;
    XanaficationPly XANAPLY = new XanaficationPly(prcplugin);


    public Listener(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onTowerActivation(TowerActivationEvent event){
        XANAPLY.plyXanafication();
    }

    @EventHandler
    public void onTowerDeactivation(TowerDeactivationEvent event){
        XANAPLY.unXanafication();
    }
}
