package pro.mrcl.ProjectXANA;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import pro.mrcl.ProjectXANA.Commands.possess;

public class XANAMain extends JavaPlugin {
    private Listener listener = new Listener(this);
    private Team team;
    @Override
    public void onEnable(){
        registerEvents();
        registerCommands();
        getLogger().info("ProjectXANA has booted up!");
    }

    @Override
    public void onDisable(){
        getLogger().info("ProjectXANA has shut down!");
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(listener,this);
    }

    private void registerCommands(){
        Main pl = (Main) Bukkit.getPluginManager().getPlugin("ProjectCarthage");
        this.getCommand("possess").setExecutor(new possess(pl));
    }
}
