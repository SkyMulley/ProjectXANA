package pro.mrcl.ProjectXANA;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pro.mrcl.ProjectXANA.Attacks.LogicCore;

public class XANAMain extends JavaPlugin {
    private Main pl;
    @Override
    public void onEnable(){
        registerCommands();
        getPRC();
        getServer().getPluginManager().registerEvents(new Listener(),this);
        getLogger().info("ProjectXANA has booted up!");
        Bukkit.getWorld("NewKadic").setStorm(false);
        Bukkit.getWorld("NewKadic").setThundering(false);
    }

    @Override
    public void onDisable(){
        getLogger().info("ProjectXANA has shut down!");
    }

    private void registerCommands(){
        //this.getCommand("possess").setExecutor(new possess(pl));
    }

    private void getPRC(){
        Main pl = (Main) Bukkit.getPluginManager().getPlugin("ProjectCarthage");
        pl.getNetwork().getXana().addAttackModule(new LogicCore());
    }
}
