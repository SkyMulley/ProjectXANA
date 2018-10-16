package pro.mrcl.ProjectXANA.AttackLogic.Easy;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.LyokoWarriorEvents.DevirtualizationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.LyokoWarriorEvents.VirtualizationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.TowerEvents.TowerDeactivationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Exceptions.BrokenInteractorException;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Exceptions.InteractorDoesNotExistException;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Exceptions.NoAnnexInteractorException;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.VirtualStructures.Tower;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;
import pro.mrcl.ProjectXANA.XANAMain;

import java.util.ArrayList;
import java.util.List;

public class Satellite extends AbstractAttack {
    private Tower tower;
    List<LyokoWarrior> eligebleWarriors = new ArrayList<>();
    private XANAMain plugin;
    private Main pl;

    public Satellite() {
        super(ATTACKDIFFICULTY.EASY);
    }

    @Override
    public boolean startAttack() {
        try {
            this.plugin = plugin;
            tower = Main.getMainInstance().getNetwork().getAnnex().activateRandom();
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onAttackEnd(TowerDeactivationEvent AEE) {
                    if (AEE.getTower().equals(tower)) {
                        Bukkit.getScheduler().runTaskLater(Main.getMainInstance(), () -> stopAttack(), 20L);
                    }
                }
            });
            List<LyokoWarrior> lyokoWarriors = new ArrayList<>();
            lyokoWarriors.addAll(Main.getMainInstance().getLyokoWarriors().values()); //get a list of all lyokowarriors
            List<LyokoWarrior> eligebleWarriors = new ArrayList<>();
            lyokoWarriors.forEach(lyokoWarrior -> {
                if (!lyokoWarrior.isVirtualized() && !lyokoWarrior.isRttpIgnored() && !lyokoWarrior.isXanafied()) {
                    eligebleWarriors.add(lyokoWarrior); //if the warrior isnt virtualized, ignored or already xanafied
                }
            });
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
                public void onPlayerLeave(PlayerQuitEvent PQE) {
                    LyokoWarrior warrior = pl.getLyokoWarriors().get(PQE.getPlayer());
                    if(eligebleWarriors.contains(warrior)) {
                        eligebleWarriors.remove(warrior);
                    }
                }
            });
            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {

                }
            }, 0L, 20*60L);
            return true;
        } catch(Exception e) {
            Bukkit.getLogger().info("[PRX] Something went wrong while running a Satellite attack: " +e);
            stopAttack();
            return true;
        }
    }

    @Override
    public boolean stopAttack() {
        Bukkit.getLogger().info("[PRX] stopAttack has been called");
        try {
            unregisterListeners();
            tower.deactivate();
            return true;
        } catch (Tower.AlreadyDeactivatedException e) {
            return true;
        } catch (BrokenInteractorException | NoAnnexInteractorException | InteractorDoesNotExistException e) {
            e.printStackTrace();
            return false;
        }
    }
}
