package pro.mrcl.ProjectXANA;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.XANA;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pro.mrcl.ProjectXANA.Commands.possess;

public class XANAMain extends JavaPlugin {
    private Listener listener = new Listener(this);
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

    public Main getPRC() {
        Main pl = (Main) Bukkit.getPluginManager().getPlugin("ProjectCarthage");
        return pl;
    }

    private void registerCommands(){
        Main pl = (Main) Bukkit.getPluginManager().getPlugin("ProjectCarthage");
        this.getCommand("possess").setExecutor(new possess(pl));
    }
}
