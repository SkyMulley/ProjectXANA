package pro.mrcl.ProjectXANA.AttackLogic.Easy;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Listener;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.LyokoWarriorEvents.DevirtualizationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.LyokoWarriorEvents.VirtualizationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.TowerEvents.TowerDeactivationEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Exceptions.BrokenInteractorException;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Exceptions.InteractorDoesNotExistException;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Exceptions.NoAnnexInteractorException;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.VirtualStructures.Tower;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pro.mrcl.ProjectXANA.MiscLogic.EligibleWarriorSelect;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LaughingGas extends AbstractAttack {
    private Tower tower = null;
    private Main pl;
    private Timer timer;
    public LaughingGas() {
        super(ATTACKDIFFICULTY.EASY);
    }

    @Override
    public boolean startAttack() {
        try {
            tower = Main.getMainInstance().getNetwork().getAnnex().activateRandom();
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onAttackEnd(TowerDeactivationEvent AEE) {
                    if (AEE.getTower().equals(tower)) {
                        timer.cancel();
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
            eligebleWarriors.forEach(eligebleWarrior -> {
                eligebleWarrior.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 250, 1, true));
                eligebleWarrior.getPlayer().sendMessage(ChatColor.RED + "A smoke wafts around you and you start laughing uncontrollably..");
            });
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onVirtualization(VirtualizationEvent VE) {
                    if(eligebleWarriors.contains(VE.getLyokoWarrior())) {
                        VE.getLyokoWarrior().getPlayer().sendMessage(ChatColor.RED + "ERROR: Virtual Envelope Damaged, Deadly Devirt");
                        VE.getLyokoWarrior().setDeadlydevirt(true);
                        eligebleWarriors.remove(VE.getLyokoWarrior());
                    }
                }
            });
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onDevirtualization(DevirtualizationEvent DE) {
                    eligebleWarriors.add(DE.getLyokoWarrior());
                }
            });
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onPlayerJoin(PlayerJoinEvent PJE) {
                    LyokoWarrior warrior = pl.getLyokoWarriors().get(PJE.getPlayer());
                    if (!warrior.isRttpIgnored()) {
                        PJE.getPlayer().sendMessage(ChatColor.RED + "A smoke wafts around you and you start laughing uncontrollably..");
                        PJE.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 250, 1, true));
                        eligebleWarriors.add(warrior);
                    }
                }
            });
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onPlayerDrink(PlayerItemConsumeEvent PICE) {
                    if (PICE.getItem().getType() == Material.POTION) {
                        eligebleWarriors.remove(PICE.getPlayer());
                    }
                }
            });
            timer = new Timer();
            TimerTask tt = new TimerTask() {
                public void run() {
                    eligebleWarriors.forEach(eligebleWarrior -> {
                        eligebleWarrior.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 250, 1, true));
                        eligebleWarrior.getPlayer().sendMessage(ChatColor.RED + "A smoke wafts around you and you start laughing uncontrollably..");
                    });
                }
            };
            timer.schedule(tt, 1,30);
            return true;
        } catch (Exception e) {
            Bukkit.getLogger().info("[PRX] Something went wrong while running a LaughingGas attack: " +e);
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
