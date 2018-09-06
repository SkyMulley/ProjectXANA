package pro.mrcl.ProjectXANA.Depreciated;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class XanaficationPly {
    private Player xanaficationplayer;
    private Main plugin;
    private boolean isPlyXanafied;
    public boolean getPlyXanafied() { return isPlyXanafied;}
    public void setPlyXanafied(boolean bool) { this.isPlyXanafied = bool; }
    private Team team;

    public XanaficationPly(Main plugin){
        this.plugin = plugin;
    }

    public void plyXanafication() {
        try {
            List<Player> players = getPlayers();
            int pc = players.size();
            Bukkit.getLogger().info("Attempting XANA Attack (Xanafication). Players Eligible for event: " + pc);
            if (pc >= 3) {
                Random rand = new Random();
                int value = rand.nextInt(pc);
                xanaficationplayer = players.get(value);
                team.addPlayer(xanaficationplayer);
                xanaficationplayer.sendMessage(ChatColor.RED + "You have been possessed by XANA! Use everything you can to stop the Lyoko Warriors from deactivating the tower!");
                setPlyXanafied(true);
                Bukkit.getLogger().info("[PRX] XANA has randomly Xanafied " + xanaficationplayer.getName());
            } else {
                Bukkit.getLogger().info("[PRX] Not enough players online to start an attack");
            }
        }catch (Exception e){
            Bukkit.getLogger().info("[PRX] Something went wrong while Xanafying a player: " +e);
        }
    }

    public void unXanafication() {
        try {
            if(getPlyXanafied()){
                team.removePlayer(xanaficationplayer);
                xanaficationplayer.sendMessage(ChatColor.GREEN + "You have been released from XANA's control");
                setPlyXanafied(false);
                Bukkit.getLogger().info(xanaficationplayer + " has been unXanafied");
            }
            Bukkit.getLogger().info("[PRX] Tower deactivation was called but nobody was Xanafied!");
        }catch (Exception e){
            Bukkit.getLogger().info("[PRX] Something went wrong while UnXanafiying a player: " +e);
        }
    }

    private List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            LyokoWarrior warrior = plugin.getLyokoWarriors().get(player);
            if (player.hasPermission("ProjectCarthage.autoxana.ignore") || warrior.isVirtualized()) {
                players.remove(i);
            }
        }
        return players;
    }
}
//