package pro.mrcl.ProjectXANA;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AttackModule;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import pro.mrcl.ProjectXANA.AttackLogic.LogicCore;
import pro.mrcl.ProjectXANA.AttackLogic.LogicCore;
import pro.mrcl.ProjectXANA.Commands.possess;

public class XANAMain extends JavaPlugin {
    private Main pl;
    @Override
    public void onEnable(){
        registerCommands();
        getLogger().info("ProjectXANA has booted up!");
        pl.getNetwork().getXana().addAttackModule(new LogicCore());
    }

    @Override
    public void onDisable(){
        getLogger().info("ProjectXANA has shut down!");
    }

    private void registerCommands(){
        Main pl = (Main) Bukkit.getPluginManager().getPlugin("ProjectCarthage");
        this.getCommand("possess").setExecutor(new possess(pl));
    }
}
