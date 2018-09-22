package pro.mrcl.ProjectXANA.AttackLogic.Easy;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Listener;
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
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class LaughingGas extends AbstractAttack {
    private Tower tower = null;
    private SimpleActivationAttack towerAttack;
    private Main pl;
    public LaughingGas() {
        super(ATTACKDIFFICULTY.EASY);
    }

    @Override
    public boolean startAttack() {
        try {
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
            List<LyokoWarrior> lyokoWarriors = (List<LyokoWarrior>) Main.getMainInstance().getLyokoWarriors().values(); //get a list of all lyokowarriors
            List<LyokoWarrior> eligebleWarriors = new ArrayList<>();
            lyokoWarriors.forEach(lyokoWarrior -> {
                if (!lyokoWarrior.isVirtualized() && !lyokoWarrior.isRttpIgnored() && !lyokoWarrior.isXanafied()) {
                    eligebleWarriors.add(lyokoWarrior); //if the warrior isnt virtualized, ignored or already xanafied
                }
            });
            eligebleWarriors.forEach(eligebleWarrior -> {
                eligebleWarrior.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 1, false));
                eligebleWarrior.getPlayer().sendMessage(ChatColor.RED + "A smoke wafts around you and you start laughing uncontrollably..");
            });
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onVirtualization(VirtualizationEvent VE) {
                    if(eligebleWarriors.contains(VE.getLyokoWarrior().getPlayer())) {
                        VE.getLyokoWarrior().getPlayer().sendMessage(ChatColor.RED + "ERROR: Virtual Envelope Damaged, Deadly Devirt");
                        VE.getLyokoWarrior().setDeadlydevirt(true);
                    }
                }
            });
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onDevirtualization(DevirtualizationEvent DE) {
                    if(DE.getLyokoWarrior().isDeadlydevirt()) {
                        DE.getLyokoWarrior().frontier();
                    }
                }
            });
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onPlayerJoin(PlayerJoinEvent PJE) {
                    LyokoWarrior warrior = pl.getLyokoWarriors().get(PJE.getPlayer());
                    if (!warrior.isRttpIgnored()) {
                        PJE.getPlayer().sendMessage(ChatColor.RED + "A smoke wafts around you and you start laughing uncontrollably..");
                        PJE.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 1, false));
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
            return true;
        } catch (Exception e) {
            Bukkit.getLogger().info("[PRX] Something went wrong while running a LaughingGas attack: " +e);
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
