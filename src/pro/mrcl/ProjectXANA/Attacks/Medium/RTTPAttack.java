package pro.mrcl.ProjectXANA.Attacks.Medium;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitScheduler;

import static org.bukkit.Bukkit.getServer;

public class RTTPAttack extends AbstractAttack {
    private SimpleActivationAttack towerAttack;
    private int attackID;
    public RTTPAttack() { super(ATTACKDIFFICULTY.FROMMEDIUM);}

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
                        safeStopAttack();
                    }
                }
            });
            BukkitScheduler scheduler = getServer().getScheduler();
            attackID = scheduler.scheduleSyncRepeatingTask(Main.getMainInstance(), new Runnable() {
                @Override
                public void run() {
                    try {
                        Main.getMainInstance().getNetwork().getReturnToThePast().activate(true);
                        Bukkit.broadcastMessage("The Return to the Past program seems to be bugging out, what could be the issue?");
                    } catch (Exception e) {
                        safeStopAttack();
                    }
                }
            }, 0L, 12000L);
        }
        return false;
    }

    @Override
    public boolean stopAttack() {
        super.stopAttack();
        Bukkit.getScheduler().cancelTask(attackID);
        return true;
    }
}