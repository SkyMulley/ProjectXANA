package pro.mrcl.ProjectXANA.XANAAttacks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class XanaficationPly {
    private Player player;
    private boolean isPlyXanafied;

    public boolean getPlyXanafied() { return isPlyXanafied;}
    public void setPlyXanafied(boolean bool) { this.isPlyXanafied = bool; }


    String perm = "ProjectCarthage.autoxana.ignore";
    public void plyXanafication() {
        List<Player> players = getPlayers();
        int pc = players.size();
        if (pc >= 3) {
            Random rand = new Random();
            int value = rand.nextInt(pc + 1 - 1) + 1;
            Player player = players.get(value);
            player.addScoreboardTag("xana");
            player.sendMessage(ChatColor.RED + "You have been possessed by XANA! Use everything you can to stop the Lyoko Warriors from deactivating the tower!");
            setPlyXanafied(true);
            Bukkit.getLogger().info("[PRX] XANA has randomly Xanafied " + player);
        } else {
            Bukkit.getLogger().info("[PRX] Not enough players online to start an attack");
        }
    }

    public void unXanafication() {
        if(getPlyXanafied()){
            player.removeScoreboardTag("xana");
            player.sendMessage(ChatColor.GREEN + "You have been released from XANA's control");
            setPlyXanafied(false);
            Bukkit.getLogger().info(player + " has been unXanafied");
        }
        Bukkit.getLogger().info("[PRX] Tower deactivation was called but nobody was Xanafied!");
    }

    private List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (player.hasPermission(perm)) {
                players.remove(i);
            }
        }
        return players;
    }
}
//