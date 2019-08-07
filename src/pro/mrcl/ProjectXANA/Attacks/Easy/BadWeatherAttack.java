package pro.mrcl.ProjectXANA.Attacks.Easy;

import com.mysql.jdbc.Buffer;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Events.AttackEvents.AttackEndEvent;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AbstractAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.Core.Pathethic.SimpleActivationAttack;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Difficulty.ATTACKDIFFICULTY;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BadWeatherAttack extends AbstractAttack {
    private SimpleActivationAttack towerAttack;
    public BadWeatherAttack() { super(ATTACKDIFFICULTY.FROMEASY);}

    @Override
    public boolean startAttack() {
        boolean wasActivated = isActivated();
        super.startAttack();
        if (!wasActivated) {
            towerAttack = new SimpleActivationAttack();
            towerAttack.startAttack();
            Bukkit.broadcastMessage(ChatColor.RED+"This sudden change in weather seems... un-natural. Maybe we should check it out");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"xweather set Windy 99999 NewKadic");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"xweather set Thunderstorm 99999 NewKadic");
            registerListener(new org.bukkit.event.Listener() {
                @EventHandler
                public void onAttackEnd(AttackEndEvent e) {
                    if (e.getAttack().equals(towerAttack)) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"xweather stop");
                        safeStopAttack();
                    }
                }
            });
        }
        return false;
    }

    @Override
    public boolean stopAttack() {
        super.stopAttack();
        return true;
    }
}
