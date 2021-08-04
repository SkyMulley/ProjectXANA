package pro.mrcl.ProjectXANA.Attacks.Hard;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import pro.mrcl.ProjectXANA.AttackLogic.EligibilityChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupXanafication extends AbstractAttack {
    private SimpleActivationAttack towerAttack;
    private List<LyokoWarrior> xanafiedWarriors = new ArrayList<>();

    public GroupXanafication() {super(ATTACKDIFFICULTY.FROMHARD);}

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
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onnXanafiedLeave(PlayerQuitEvent e) {
                    LyokoWarrior xanafiedWarrior = Main.getMainInstance().getLyokoWarriors().get(e);
                    if (xanafiedWarriors.contains(xanafiedWarrior)) {
                        xanafiedWarrior.dexanafy();
                        xanafiedWarriors.remove(xanafiedWarrior);
                        if(!EligibilityChecker.getNonVirtedEligiblePlayers().isEmpty()) {
                            Random random = new Random();
                            xanafiedWarrior = EligibilityChecker.getNonVirtedEligiblePlayers().get(random.nextInt(EligibilityChecker.getNonVirtedEligiblePlayers().size()));
                            xanafiedWarriors.add(xanafiedWarrior);
                            xanafiedWarrior.xanafy();
                        }
                    }
                }
            });
            List<LyokoWarrior> eligebleWarriors = EligibilityChecker.getNonVirtedEligiblePlayers();
            if(!eligebleWarriors.isEmpty()) {
                double intwarriors = Math.ceil(eligebleWarriors.size() / 3);
                if(intwarriors <= 1) {
                    fail("One or less players were found eligible for group xanafication, so attack was cancelled");
                    return false;
                }
                for(int i = 0; i < intwarriors; i++) {
                    Random random = new Random();
                    LyokoWarrior xanafiedWarrior = eligebleWarriors.get(random.nextInt(eligebleWarriors.size()));
                    xanafiedWarriors.add(xanafiedWarrior);
                    xanafiedWarrior.xanafy();
                }
            } else {
                fail("No eligible players were found for group xanafication");
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean stopAttack() {
        super.stopAttack();
        for(LyokoWarrior warrior : xanafiedWarriors) {
            warrior.dexanafy();
        }
        xanafiedWarriors.clear();
        towerAttack.safeStopAttack();
        return true;
    }
}
