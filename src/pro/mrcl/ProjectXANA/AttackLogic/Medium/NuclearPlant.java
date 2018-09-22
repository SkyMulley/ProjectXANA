package pro.mrcl.ProjectXANA.AttackLogic.Medium;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.XANA;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.VirtualStructures.Tower;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import pro.mrcl.ProjectXANA.XANAMain;

import java.util.ArrayList;
import java.util.List;

public class NuclearPlant extends AbstractAttack {
    private Tower tower = null;
    private SimpleActivationAttack towerAttack;
    private Main pl;
    private XANAMain plugin;
    public NuclearPlant() {
        super(ATTACKDIFFICULTY.MEDIUM);
    }
    private boolean isTowerDeactivated;
    @Override
    public boolean startAttack() {
        try {
            this.plugin = plugin;
            towerAttack = new SimpleActivationAttack();
            towerAttack.startAttack();
            isTowerDeactivated = false;
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onAttackEnd(AttackEndEvent AEE) {
                    if (AEE.getAttack().equals(towerAttack)) {
                        stopAttack();
                        isTowerDeactivated = false;
                    }
                }
            });
            List<LyokoWarrior> lyokoWarriors = (List<LyokoWarrior>) Main.getMainInstance().getLyokoWarriors().values(); //get a list of all lyokowarriors
            List<LyokoWarrior> eligebleWarriors = new ArrayList<>();
            lyokoWarriors.forEach(lyokoWarrior -> {
                if (!lyokoWarrior.isRttpIgnored() && !lyokoWarrior.isXanafied()) {
                    eligebleWarriors.add(lyokoWarrior); //if the warrior isnt virtualized, ignored or already xanafied
                }
            });
            Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED +" High voltage detected in Electric Pylon 2HA, 15 minutes to Voltage Limit cross");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    if(isTowerDeactivated==false) {
                        Bukkit.broadcastMessage("[Superscan]" +ChatColor.RED +" High voltage detected in Electric Pylon 2HA, 10 minutes to Voltage Limit cross");
                    }
                }
            }, 6000L);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    if(isTowerDeactivated==false) {
                        Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " High voltage detected in Electric Pylon 2HA, 5 minutes to Voltage Limit cross");
                    }
                }
            }, 6000L);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    if(isTowerDeactivated==false) {
                        Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " High voltage detected in Electric Pylon 2HA, 5 minutes to Voltage Limit cross");
                    }
                }
            }, 6000L);
            return true;
        } catch (Exception e) {
            Bukkit.getLogger().info("[PRX] Something went wrong while running a NuclearPlant attack: " +e);
            stopAttack();
            return true;
        }
    }

    @Override
    public boolean stopAttack() {
        towerAttack.stopAttack();
        unregisterListeners();
        super.stopAttack();
        return true;
    }
}
