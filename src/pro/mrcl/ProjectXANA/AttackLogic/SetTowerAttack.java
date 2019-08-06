package pro.mrcl.ProjectXANA.AttackLogic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.VirtualStructures.Tower;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SetTowerAttack extends AbstractAttack {
    private List<SimpleActivationAttack> attacks;
    private String perm = "ProjectCarthage.autoxana.ignore";
    private int towers = 2;
    public SetTowerAttack() {
        super(ATTACKDIFFICULTY.ANY);
        attacks = new ArrayList<>();
    }

    @Override
    public boolean startAttack() {
        super.startAttack();
        for (int i = 0; i < towers; i++) {
            attacks.add(new SimpleActivationAttack());
        }
        for (SimpleActivationAttack simpleActivationAttack:attacks) {
            simpleActivationAttack.startAttack();
        }

        List<Tower> listenerTowers = new ArrayList<>();
        attacks.forEach(attack-> listenerTowers.add(attack.getTower()));

        registerListener(new Listener() {
            private int attackends = 0;
            @EventHandler
            public void onAttackEnd(AttackEndEvent e){
                if (attacks.contains(e.getAttack())){
                    attackends++;
                }
                if (attackends >= towers){
                    safeStopAttack();
                }
            }
        });

        return true;
    }

    public void setTowerAmount(int amount) {towers=amount;}
}
