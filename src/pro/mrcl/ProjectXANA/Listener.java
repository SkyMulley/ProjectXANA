package pro.mrcl.ProjectXANA;

import org.bukkit.event.EventHandler;

public class Listener implements org.bukkit.event.Listener{
    private Main plugin;


    public Listener(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onTowerActivation(TowerActivationEvent event){

    }
}
