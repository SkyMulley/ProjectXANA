package pro.mrcl.ProjectXANA.AttackLogic.Depreciated;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.LyokoWarriorEvents.DevirtualizationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.LyokoWarriorEvents.VirtualizationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.VirtualStructures.Tower;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pro.mrcl.ProjectXANA.MiscLogic.DeathEvent;
import pro.mrcl.ProjectXANA.MiscLogic.EligibleWarriorSelect;
import pro.mrcl.ProjectXANA.XANAMain;

import java.util.ArrayList;
import java.util.List;

public class Satellite extends AbstractAttack {
    private Tower tower = null;
    private SimpleActivationAttack towerAttack;
    private EligibleWarriorSelect ews;
    private Main pl;
    private DeathEvent DE;
    private XANAMain plugin;
    List<LyokoWarrior> eligebleWarriors = new ArrayList<>();

    public Satellite() {
        super(ATTACKDIFFICULTY.EASY);
    }

    @Override
    public boolean startAttack() {
        try {
            super.startAttack();
            towerAttack = new SimpleActivationAttack();
            towerAttack.startAttack();
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onAttackEnd(AttackEndEvent AEE) {
                    if (AEE.getAttack().equals(towerAttack)) {
                        stopAttack();
                    }
                }
            });
            eligebleWarriors = ews.EligibleWarrior();
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onPlayerJoin(PlayerJoinEvent PJE) {
                    LyokoWarrior warrior = pl.getLyokoWarriors().get(PJE.getPlayer());
                    if (!warrior.isRttpIgnored()) {
                        eligebleWarriors.add(warrior);
                    }
                }
            });
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onVirtualization(VirtualizationEvent VE) {
                    if(eligebleWarriors.contains(VE.getLyokoWarrior())) {
                        eligebleWarriors.remove(VE.getLyokoWarrior());
                    }
                }
            });
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onDevirtualization(DevirtualizationEvent DE) {
                    if(!DE.getLyokoWarrior().isRttpIgnored() && !DE.getLyokoWarrior().isXanafied()) {
                        eligebleWarriors.add(DE.getLyokoWarrior());
                    }
                }
            });
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onPlayerMove(PlayerMoveEvent PME) {
                    LyokoWarrior warrior = pl.getLyokoWarriors().get(PME.getPlayer());
                    if(eligebleWarriors.contains(warrior)) {
                        Location loc = PME.getPlayer().getEyeLocation().add(0,1,0);
                        while(loc.getY() < 256) {
                            if(loc.getBlock().getType() == Material.AIR) {
                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                    @Override
                                    public void run() {
                                        if(loc.getBlock().getType() == Material.AIR) {
                                            World world = PME.getPlayer().getWorld();
                                            Location location = PME.getPlayer().getLocation();
                                            world.strikeLightning(location);
                                        }
                                    }
                                }, 600L);
                            }
                        }
                    }
                }
            });
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onPlayerDeath(PlayerDeathEvent PDE) {
                    LyokoWarrior warrior = pl.getLyokoWarriors().get(PDE.getEntity());
                    if(!warrior.isVirtualized()) {
                        warrior.getPlayer().setHealth(20);
                        //Send player to death point here
                    }
                }
            });
            return true;
        } catch(Exception e) {
            Bukkit.getLogger().info("[PRX] Something went wrong while running a Satellite attack: " +e);
            stopAttack();
            return true;
        }
    }
    @Override
    public boolean stopAttack() {
        towerAttack.stopAttack();
        unregisterListeners();
        super.stopAttack();
        return true;
    }
}