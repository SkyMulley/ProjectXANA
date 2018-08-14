package pro.mrcl.ProjectXANA;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.TowerActivationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.TowerDeactivationEvent;
import org.bukkit.event.EventHandler;
import pro.mrcl.ProjectXANA.XANAAttacks.XanaficationPly;

public class Listener implements org.bukkit.event.Listener{
    private XANAMain plugin;
    private XanaficationPly XANAPLY;

    public Listener(XANAMain plugin){
        this.plugin = plugin;
        XANAPLY = new XanaficationPly(plugin.getPRC());
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
