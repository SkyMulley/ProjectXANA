package pro.mrcl.ProjectXANA.Attacks.Pathetic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

public class ElevatorLockAttack extends AbstractAttack {
    private SimpleActivationAttack towerAttack;
    public ElevatorLockAttack() { super(ATTACKDIFFICULTY.PATHEHTIC);}

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
            if(Bukkit.getWorld("newkadic").getBlockAt(9,40,-346).isBlockPowered()) {
                safeStopAttack();
            } else {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "elevator edit factory disabled yes");
            }
        }
        return false;
    }

    @Override
    public boolean stopAttack() {
        super.stopAttack();
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"elevator edit factory disabled no");
        return true;
    }
}