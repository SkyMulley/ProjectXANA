package pro.mrcl.ProjectXANA;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.XANA;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import pro.mrcl.ProjectXANA.Commands.possess;

public class XANAMain extends JavaPlugin {
    private Listener listener = new Listener(this);
    private Team team;
    @Override
    public void onEnable(){
        registerEvents();
        registerCommands();
        teamCheck();
        getLogger().info("ProjectXANA has booted up!");
    }

    @Override
    public void onDisable(){
        getLogger().info("ProjectXANA has shut down!");
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(listener,this);
    }

    public Main getPRC() {
        Main pl = (Main) Bukkit.getPluginManager().getPlugin("ProjectCarthage");
        return pl;
    }

    private void registerCommands(){
        Main pl = (Main) Bukkit.getPluginManager().getPlugin("ProjectCarthage");
        this.getCommand("possess").setExecutor(new possess(pl));
    }

    private void teamCheck() {
        if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam("XANA")==null) {
            Bukkit.getLogger().info("[PRX] You do not have a XANA team on the server, creating one, you can modify to team to how powerful you want players who are Xanafied");
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getMainScoreboard();
            team = board.registerNewTeam("XANA");
        } else{
            Bukkit.getLogger().info("[PRX] You have a XANA team, you can modify this to how powerful you want players who are Xanafied");
            team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("XANA");
        }
    }
}
