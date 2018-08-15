package pro.mrcl.ProjectXANA.Commands;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Util.Checker;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import mrcl.pro.GoodOldJack12.ProjectCarthage.commands.Abstract.CarthageCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class release extends CarthageCommand {
    private possess possession;
    private Player player;
    public release(Main plugin) {
        super(plugin, "release");
        this.setHelpMessage(ChatColor.YELLOW + "Usage: /release <player>\nReleases target from the control of XANA");
        this.setPermission("ProjectXANA.possess");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        registerSender(commandSender, command, s, strings);

        try {
            Checker.checkPerm(commandSender, getPermission());
            Checker.checkarglength(strings, 1, 1);
            Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("XANA");
            player = plugin.getServer().getPlayer(strings[0]);
            if(player == null) {
                if(team.hasPlayer(player)){
                    team.removePlayer(player);
                    if(player.equals(possession.whoAmI())) {
                        possession.setPlyPossessed(false);
                    }
                    Bukkit.getLogger().info("[PRX]" +player.getName() +" has been released from XANA's control by " +commandSender.getName());
                    commandSender.sendMessage("Target has been released from XANA's control");
                    player.sendMessage("A mysterious force has released you from XANA's control...");
                    return true;
                }else{
                    Bukkit.getLogger().info("[PRX] " +commandSender.getName() +" tried to release " +player.getName() +" from XANA's control when he wasn't in the first place");
                    commandSender.sendMessage("Target is not under XANA's control");
                    return true;
                }
            } else {
                Bukkit.getLogger().info("[PRX] " +commandSender.getName() +" tried to release an invalid player");
                commandSender.sendMessage("Target does not exist on the server!");
                return true;
            }
        } catch (Exception e) {
            Bukkit.getLogger().info("[PRX] In a manual release of " +player.getName() + " by " +commandSender.getName() +" the process failed: " +e);
            commandSender.sendMessage("Something went wrong! Check the logs");
            return true;
        }
    }
}
