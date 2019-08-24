package pro.mrcl.ProjectXANA.Attacks.Hard;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.WorldEvents.RTTPevent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import pro.mrcl.ProjectXANA.AttackLogic.EligibilityChecker;

public class Meteorite extends AbstractAttack {
    private SimpleActivationAttack towerAttack;
    int counter;
    int attackID1;
    int attackID2;
    public Meteorite() {
        super(ATTACKDIFFICULTY.FROMHARD);
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
                        Bukkit.broadcastMessage(ChatColor.BLUE+"The tower has been deactivated but the meteorite is still on a collision course, what can we do now?");
                    }
                }
            });
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onRTTP(RTTPevent e) {
                    try {
                        if (!towerAttack.getTower().isActivated()) {
                            Bukkit.getScheduler().cancelTask(attackID1);
                            if (attackID2 != 0) {
                                Bukkit.getScheduler().cancelTask(attackID2);
                            }
                            Bukkit.broadcastMessage("[Superscan] "+ChatColor.GREEN+"Meteorite has returned to previous course.");
                            safeStopAttack();
                        }
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED +" Satellite 82HA has altered the course of a nearby meteorite cluster, potential collision course detected.");
            counter = -1;
            attackID2 = 0;
            attackID1 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getMainInstance(), new Runnable() {
                @Override
                public void run() {
                    counter = counter + 1;
                    if (counter == 1) {
                        Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " Collision course confirmed, meteorite designated MZ is now on collision course with Earth. T-Minus 10 minutes");
                    }
                    if (counter == 2) {
                        Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " Meteorite MZ is now T-Minus 5 minutes from collision");
                    }
                    if (counter == 3) {
                        Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " Collision with Meteorite MZ to Earth imminent");
                        attackID2 = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMainInstance(), new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " Collision detected at Boulogne-Billancourt");
                                for(Player player : Bukkit.getOnlinePlayers()) {
                                    if(!Main.getMainInstance().getLyokoWarriors().get(player).isXanaIgnored()) {
                                        player.sendMessage(ChatColor.RED+"You have failed to beat XANA and died...");
                                        Main.getMainInstance().getLyokoWarriors().get(player).removetags();
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"warp FrontierDeath "+player.getName());
                                    }
                                }
                                stopAttack();
                            }
                        }, 300L);
                    }
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
        return true;
    }
}
