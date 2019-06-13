package pro.mrcl.ProjectXANA.Attacks.Pathetic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import org.bukkit.event.EventHandler;

public class BuggedScanner extends AbstractAttack {
    private SimpleActivationAttack towerAttack;
    public BuggedScanner() {
        super(ATTACKDIFFICULTY.PATHEHTIC);
    }

    @Override
    public boolean startAttack() {
        boolean wasActivated = isActivated();
        super.startAttack();
        if(!wasActivated) {
            towerAttack = new SimpleActivationAttack();
            towerAttack.startAttack();
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onAttackEnd(AttackEndEvent e) {
                    if(e.getAttack().equals(towerAttack)) {
                        safeStopAttack();
                    }
                }
            });
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onMidVirt() {

                }
            });
        }
        return false;
    }

    @Override
    public boolean stopAttack() {
        super.stopAttack();
        return true;
    }
}
