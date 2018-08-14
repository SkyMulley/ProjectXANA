package pro.mrcl.ProjectXANA;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.TowerActivationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.TowerDeactivationEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;
import pro.mrcl.ProjectXANA.Commands.possess;
import pro.mrcl.ProjectXANA.XANAAttacks.XanaficationPly;

public class Listener implements org.bukkit.event.Listener{
    private XANAMain plugin;
    private XanaficationPly XANAPLY;
    private possess possession;

    public Listener(XANAMain plugin){
        this.plugin = plugin;
        XANAPLY = new XanaficationPly(plugin.getPRC());
    }

    @EventHandler
    public void onTowerActivation(TowerActivationEvent event){
        XANAPLY.plyXanafication();
        possession.setPossessable(true);
    }

    @EventHandler
    public void onTowerDeactivation(TowerDeactivationEvent event){
        XANAPLY.unXanafication();
        possession.unXanafication();
        possession.setPossessable(false);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player leaveplayer = event.getPlayer();
        XANAPLY.teamCheck();
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("XANA");
        if(team.hasPlayer(leaveplayer)){
            team.removePlayer(leaveplayer);
            Bukkit.getLogger().info("[PRX]" +leaveplayer.getName() +" left the server while under XANA possession");
        }
    }
}
