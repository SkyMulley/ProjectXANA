package pro.mrcl.ProjectXANA.Attacks.Pathetic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LockedBoilerRoom extends AbstractAttack {
    private SimpleActivationAttack towerAttack;
    private Block doorBlock = Bukkit.getWorld("NewKadic").getBlockAt(-125,103,153);
    public LockedBoilerRoom() { super(ATTACKDIFFICULTY.FROMEASY);}

    @Override
    public boolean startAttack() {
        boolean wasActivated = isActivated();
        super.startAttack();
        if (!wasActivated) {
            towerAttack = new SimpleActivationAttack();
            towerAttack.startAttack();
            Door door = (Door) doorBlock.getBlockData();
            door.setOpen(false);
            doorBlock.setBlockData(door);
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
                public void onInteract(PlayerInteractEvent event) {
                    if(event.getClickedBlock()!=null) {
                        if(event.getClickedBlock().getLocation().equals(doorBlock.getLocation().clone()) || event.getClickedBlock().getLocation().equals(doorBlock.getLocation().clone().add(0,1,0))) {
                            if(event.getAction()== Action.RIGHT_CLICK_BLOCK) {
                                event.getPlayer().sendMessage(ChatColor.RED+"The door is locked! You'll have to find another way around...");
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            });
        }
        return false;
    }

    @Override
    public boolean stopAttack() {
        super.stopAttack();
        towerAttack.safeStopAttack();
        return true;
    }
}
