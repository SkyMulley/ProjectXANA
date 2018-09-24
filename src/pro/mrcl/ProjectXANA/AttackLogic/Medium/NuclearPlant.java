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
    private int counter;
    private boolean rip;
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
                        isTowerDeactivated = true;
                    }
                }
            });
            Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED +" High voltage detected in Electric Pylon 2HA, 15 minutes to Voltage Limit cross");
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    if(isTowerDeactivated==false) {
                        counter = counter + 1;
                        if(counter==1) {
                            Bukkit.broadcastMessage("[Superscan]" +ChatColor.RED +" High voltage detected in Electric Pylon 2HA, 10 minutes to Voltage Limit cross");
                        }
                        if(counter==2) {
                            Bukkit.broadcastMessage("[Superscan]" +ChatColor.RED +" High voltage detected in Electric Pylon 2HA, 5 minutes to Voltage Limit cross");
                        }
                        if(counter==3) {
                            Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " Voltage in Electric Pylon 2HA at maximum capacity");
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    List<LyokoWarrior> lyokoWarriors = (List<LyokoWarrior>) Main.getMainInstance().getLyokoWarriors().values(); //get a list of all lyokowarriors
                                    List<LyokoWarrior> eligebleWarriors = new ArrayList<>();
                                    lyokoWarriors.forEach(lyokoWarrior -> {
                                        if (!lyokoWarrior.isRttpIgnored() && !lyokoWarrior.isXanafied()) {
                                            eligebleWarriors.add(lyokoWarrior); //if the warrior isnt virtualized, ignored or already xanafied
                                        }
                                    });
                                    //Make some sort of "kill everyone" logic (idk the way jack wants it)
                                    stopAttack();
                                }
                            }, 1200L);
                        }
                    }
                }
            }, 0L, 6000L);
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
