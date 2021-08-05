package pro.mrcl.ProjectXANA.Attacks.Medium;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.Cause.Cause;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

public class NuclearPlant extends AbstractAttack {
    private SimpleActivationAttack towerAttack;
    private int counter;
    private int attackID1;
    private int attackID2;
    public NuclearPlant() {
        super(ATTACKDIFFICULTY.FROMMEDIUM);
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
                        if(attackID2!=0) {Bukkit.getScheduler().cancelTask(attackID2);}
                        Bukkit.broadcastMessage("[Superscan] "+ChatColor.GREEN+"High voltage in Electric Pylon 2HA is now subsiding");
                        safeStopAttack();
                    }
                }
            });
            Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED +" High voltage detected in Electric Pylon 2HA, 15 minutes to Voltage Limit cross");
            counter = -1;
            attackID2 = 0;
            attackID1 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getMainInstance(), () -> {
                counter = counter + 1;
                if (counter == 1) {
                    Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " High voltage detected in Electric Pylon 2HA, 10 minutes to Voltage Limit cross");
                }
                if (counter == 2) {
                    Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " High voltage detected in Electric Pylon 2HA, 5 minutes to Voltage Limit cross");
                }
                if (counter == 3) {
                    Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " Voltage in Electric Pylon 2HA at maximum capacity");
                    attackID2 = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMainInstance(), () -> {
                        Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " Detonation detected at Boulogne-Billancourt Nuclear Power Plant");
                        for(LyokoWarrior warrior : Main.getMainInstance().getLyokoWarriors().values()) {
                            if(!warrior.isXanaIgnored()) {
                                warrior.getPlayer().sendMessage(ChatColor.RED+"You have failed to beat XANA and died...");
                                warrior.murder(new Cause("NuclearPlant"));
                            }
                        }
                        stopAttack();
                    }, 300L);
                }
            }, 0L, 6000L);
            return true;
        }
        return false;
    }

    @Override
    public boolean stopAttack() {
        super.stopAttack();
        Bukkit.getScheduler().cancelTask(attackID1);
        towerAttack.safeStopAttack();
        return true;
    }
}
