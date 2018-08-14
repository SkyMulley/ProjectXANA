package pro.mrcl.ProjectXANA;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;

public class XANAMain extends JavaPlugin {
    private Listener listener = new Listener(this);
    @Override
    public void onEnable(){
        registerEvents();
        getLogger().info("ProjectXANA has booted up!");
    }

    @Override
    public void onDisable(){
        getLogger().info("ProjectXANA has shut down!");
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(listener,this);
    }
}
