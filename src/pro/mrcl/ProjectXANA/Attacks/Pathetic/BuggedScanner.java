package pro.mrcl.ProjectXANA.Attacks.Pathetic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.LyokoWarriorEvents.MidVirtEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

public class BuggedScanner extends AbstractAttack {
    private SimpleActivationAttack towerAttack;
    public BuggedScanner() {
        super(ATTACKDIFFICULTY.FROMEASY);
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
                public void onMidVirt(MidVirtEvent event) {
                    event.getLyokoWarrior().getPlayer().playSound(event.getLyokoWarrior().getPlayer().getLocation(),"alarm",1000,1);
                    event.getScannerGroup().setDestinationSector(event.getScannerGroup().getVirtualWorld().getRandomSector());
                    event.getLyokoWarrior().getPlayer().sendMessage(ChatColor.RED+"There seems to be an issue with the scanner, who knows what could of messed up!");
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
