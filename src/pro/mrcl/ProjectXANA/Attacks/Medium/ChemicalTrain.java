package pro.mrcl.ProjectXANA.Attacks.Medium;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pro.mrcl.ProjectXANA.AttackLogic.EligibilityChecker;

public class ChemicalTrain extends AbstractAttack {
    private SimpleActivationAttack towerAttack;
    int counter;
    int attackID1;
    int attackID2;
    public ChemicalTrain() {
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
                        Bukkit.getScheduler().cancelTask(attackID1);
                        if(attackID2!=0) {Bukkit.getScheduler().cancelTask(attackID2);}
                        Bukkit.broadcastMessage("[Superscan] "+ChatColor.GREEN+"Trains 13 and 51 now coming to a complete stop.");
                        safeStopAttack();
                    }
                }
            });
            Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED +" Region internet outage, detecting traces of malware on local ISPs");
            counter = -1;
            attackID2 = 0;
            attackID1 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getMainInstance(), new Runnable() {
                @Override
                public void run() {
                    counter = counter + 1;
                    if (counter == 1) {
                        Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " Trains 13 and 51 detected on collision course on Boulogne-Billancourt Northern Line. 10 minutes to collision");
                    }
                    if (counter == 2) {
                        Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " Trains 13 and 51 detected on collision course on Boulogne-Billancourt Northern Line. (Train 51 cargo - Misc Chemicals) 5 minutes to collision");
                    }
                    if (counter == 3) {
                        Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " Train collision imminent");
                        attackID2 = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMainInstance(), new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.broadcastMessage("[Superscan]" + ChatColor.RED + " Train collision, detecting chemicals spreading into lower atmosphere");
                                for(Player player : Bukkit.getOnlinePlayers()) {
                                    if(!Main.getMainInstance().getLyokoWarriors().get(player).isXanaIgnored() || !Main.getMainInstance().getLyokoWarriors().get(player).isVirtualized()) {
                                        player.sendMessage(ChatColor.RED+"You have been poisoned by the chemicals in the air released by the colliding trains");
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,15,10));
                                    }
                                }
                                Bukkit.getScheduler().runTaskLater(Main.getMainInstance(), new Runnable() {
                                            @Override
                                            public void run() {
                                                for(Player player : Bukkit.getOnlinePlayers()) {
                                                    if(!Main.getMainInstance().getLyokoWarriors().get(player).isXanaIgnored() || !Main.getMainInstance().getLyokoWarriors().get(player).isVirtualized()) {
                                                        player.sendMessage(ChatColor.RED+"You have failed to beat XANA and died...");
                                                        Main.getMainInstance().getLyokoWarriors().get(player).removetags();
                                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"warp FrontierDeath "+player.getName());
                                                    }
                                                }
                                            }
                                            },300L);
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
