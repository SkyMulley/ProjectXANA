package pro.mrcl.ProjectXANA.Attacks.Pathetic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import net.minecraft.server.v1_13_R2.CommandSay;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitScheduler;

import static org.bukkit.Bukkit.getServer;
import static org.bukkit.Bukkit.savePlayers;

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
            try {
                if (!Main.getMainInstance().getNetwork().getVWorld("Lyoko").getScannerGroup().getEmptyScanner().isPowered()) {
                    safeStopAttack();
                }
            }catch (Exception e) {
                safeStopAttack();
            }
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"elevator edit factory disabled yes");
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