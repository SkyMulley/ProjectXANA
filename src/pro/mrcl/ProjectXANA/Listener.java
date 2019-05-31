package pro.mrcl.ProjectXANA;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Listener implements org.bukkit.event.Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(event.getDeathMessage().equalsIgnoreCase("fell out of the world")) {
            event.setDeathMessage("was killed in a XANA attack");
        }
    }
}
