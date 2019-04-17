package pro.mrcl.ProjectXANA.Attacks.Medium;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

public class NuclearPlant extends AbstractAttack {
    private SimpleActivationAttack towerAttack;
    int counter;
    int attackID1;
    int attackID2;
    public NuclearPlant() {
        super(ATTACKDIFFICULTY.MEDIUM);
    }

    @Override
    public boolean startAttack() {
        boolean wasActivated = isActivated();
        super.startAttack();
        if (!wasActivated) {
            towerAttack = new SimpleActivationAttack();
            towerAttack.startAttack();
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onAttackEnd(AttackEndEvent e) {
                    if (e.getAttack().equals(towerAttack)) {
                        Bukkit.getScheduler().cancelTask(attackID1);
                        if(attackID2!=0) {Bukkit.getScheduler().cancelTask(attackID2);}
                        safeStopAttack();
                    }
                }
            });
            Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED +" High voltage detected in Electric Pylon 2HA, 15 minutes to Voltage Limit cross");
            counter = 0;
            attackID2 = 0;
            attackID1 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getMainInstance(), new Runnable() {
                @Override
                public void run() {
                    counter = counter + 1;
                    if (counter == 1) {
                        Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " High voltage detected in Electric Pylon 2HA, 10 minutes to Voltage Limit cross");
                    }
                    if (counter == 2) {
                        Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " High voltage detected in Electric Pylon 2HA, 5 minutes to Voltage Limit cross");
                    }
                    if (counter == 3) {
                        Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " Voltage in Electric Pylon 2HA at maximum capacity");
                        attackID2 = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMainInstance(), new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " Detonation detected at Boulogne-Billancourt Nuclear Power Plant");
                                //Death goes here big boom
                                stopAttack();
                            }
                        }, 1200L);
                    }
                }
            }, 0L, 6000L);
            return true;
        }
        return false;
    }
}
