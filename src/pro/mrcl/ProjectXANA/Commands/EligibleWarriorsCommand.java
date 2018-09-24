package pro.mrcl.ProjectXANA.Commands;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Util.Checker;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import mrcl.pro.GoodOldJack12.ProjectCarthage.commands.Abstract.CarthageCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pro.mrcl.ProjectXANA.MiscLogic.EligibleWarriorSelect;
import pro.mrcl.ProjectXANA.XANAMain;

import java.util.ArrayList;
import java.util.List;

public class EligibleWarriorsCommand extends CarthageCommand {
    private EligibleWarriorSelect ews;
    List<LyokoWarrior> eligebleWarriors = new ArrayList<>();
    public EligibleWarriorsCommand(Main plugin) {
        super(plugin,"eligiblewarriors");
        this.setPermission("ProjectXANA.eligiblewarriors");
        this.setHelpMessage(ChatColor.YELLOW + "Usage: /virtualize <player> (vworld) <sector> \n /virtualize <player> local \n /virtualize <player> <vworld>\n " +
                "Virtualizes the target in the vworld and sector selected.");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command,String s, String[] strings) {
        registerSender(commandSender,command,s,strings);
        try {
            try {
                if(commandSender instanceof Player) {
                    Checker.checkPerm((Player)commandSender,getPermission());
                }
                Checker.checkarglength(strings, 2,3);
                String infocommand = strings[0];
                String commandx2 = strings[1];
                if(strings[0] == "list") {
                    if(strings[1].equals("full")) {
                        eligebleWarriors = ews.EligibleWarrior();
                        commandSender.sendMessage("Full list of eligible players: " +eligebleWarriors);
                        return true;
                    }
                    if(strings[1].equals("earth")) {
                        eligebleWarriors = ews.EligibleWarrior();
                        commandSender.sendMessage("List of eligible players on earth: " +eligebleWarriors);
                        return true;
                    }
                    if(!strings[1].equals("earth") || !strings[1].equals("full")) {
                        return false;
                    }
                }
                if(strings[0] == "remove") {
                    Player player = plugin.getServer().getPlayer(strings[1]);
                    LyokoWarrior lyokoWarrior = plugin.getLyokoWarriors().get(player);
                    if ( !commandSender.hasPermission("ProjectCarthage.rttp.permanentignore")){
                        lyokoWarrior.setRttpIgnored(true);
                        commandSender.sendMessage(ChatColor.GREEN+"Player Removed (Note this removes them from being affected by returns to the past)");
                        return true;
                    }else {
                        commandSender.sendMessage(ChatColor.RED+"Player isn't in the eligible players list!");
                        return true;
                    }
                }
                if(strings[0] == "add") {
                    Player player = plugin.getServer().getPlayer(strings[1]);
                    LyokoWarrior lyokoWarrior = plugin.getLyokoWarriors().get(player);
                    if ( commandSender.hasPermission("ProjectCarthage.rttp.permanentignore")){
                        lyokoWarrior.setRttpIgnored(false);
                        commandSender.sendMessage(ChatColor.GREEN+"Player Added (Note they can now be affected by returns to the past)");
                        return true;
                    }else {
                        commandSender.sendMessage(ChatColor.RED+"Player is in the eligible players list!");
                        return true;
                    }
                }
                return true;
            } catch (Exception e) {
                Bukkit.getLogger().info("[PRX] Something went wrong while running the EligibleWarriors command: " + e);
                return true;
            }
        } catch (Exception e) {
            Bukkit.getLogger().info("[PRX] Something went wrong while running the EligibleWarriors command: " + e);
            return true;
        }
    }
}
