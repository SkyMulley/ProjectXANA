package pro.mrcl.ProjectXANA.MiscLogic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class DeathEvent {
    private Player player;
    private EligibleWarriorSelect ews;
    List<LyokoWarrior> eligebleWarriors = new ArrayList<>();
    public DeathEvent(Player player) {
        this.player = player.getPlayer();
    }
    public void DeathExecution() {
        eligebleWarriors = ews.EligibleWarrior();
        eligebleWarriors.forEach(eligebleWarrior -> {
            eligebleWarrior.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1, false));
            eligebleWarrior.getPlayer().sendMessage(ChatColor.RED + "You have died...");
            //Where you get sent in death will go here
        });
    }
}
