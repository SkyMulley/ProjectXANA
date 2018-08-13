package pro.mrcl.ProjectXANA;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
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
