package pro.mrcl.ProjectXANA.Attacks.Pathetic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Abstract.SECTORTYPE;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.LyokoWarriorEvents.MidVirtEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Exceptions.SectorDoesNotExistException;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AttackModule;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.event.EventHandler;

public class LostMaze extends AbstractAttack {
    private SimpleActivationAttack towerAttack;
    public LostMaze() {
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
                    try {
                        event.getLyokoWarrior().getPlayer().playSound(event.getLyokoWarrior().getPlayer().getLocation(),"alarm",1000,1);
                        event.getScannerGroup().setDestinationSector(event.getScannerGroup().getVirtualWorld().findSector(SECTORTYPE.CARTHAGE));
                        event.getLyokoWarrior().getPlayer().sendMessage(ChatColor.RED+"There seems to be an issue with the scanner, who knows what could of messed up!");
                    }catch (SectorDoesNotExistException e) {
                        fail("LostMaze attack attempted to send a player to a Carthage sector, but none exists! Ending attack");
                    }
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
