package pro.mrcl.ProjectXANA.Commands;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Util.Checker;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.VirtualStructures.Network;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import mrcl.pro.GoodOldJack12.ProjectCarthage.commands.Abstract.CarthageCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import pro.mrcl.ProjectXANA.XANAAttacks.XanaficationPly;

public class possess extends CarthageCommand {
    private XanaficationPly XANAPLY;
    private boolean possessable;
    private Player player;
    private boolean plyPossessed;

    public void setPlyPossessed(boolean bool) { this.plyPossessed = bool; }

    public boolean isPlyPossessed() { return plyPossessed; }

    public possess(Main plugin) {
        super(plugin, "possess");
        this.setHelpMessage(ChatColor.YELLOW + "Usage: /possess <player>\nSets the target under the control of XANA");
        this.setPermission("ProjectXANA.possess");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        registerSender(commandSender, command, s, strings);

        try {
            Checker.checkPerm(commandSender, getPermission());
            Checker.checkarglength(strings, 1, 1);
            if (isPlyPossessed() == false ) {
                player = plugin.getServer().getPlayer(strings[0]);
                Main pl = (Main) Bukkit.getPluginManager().getPlugin("ProjectCarthage");
                Network network = pl.getNetwork();
                commandSender.sendMessage("Towers active: " +network.getMultiscan().getTotalTowers());
                if (network.getMultiscan().getTotalTowers() != 0) {
                    Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("XANA");
                    team.addPlayer(player);
                    player.sendMessage(ChatColor.RED + "You have been possessed by XANA! Use everything you can to stop the Lyoko Warriors from deactivating the tower!");
                    commandSender.sendMessage(ChatColor.GREEN + "Possession of " + player.getName() + " completed");
                    Bukkit.getLogger().info("[PRX]" + player.getName() + " has been manually possessed by " + commandSender.getName());
                    setPlyPossessed(true);
                    return true;
                } else {
                    commandSender.sendMessage(ChatColor.RED + "Possession cannot occur due to no towers being activated");
                    Bukkit.getLogger().info("[PRX]" + player.getName() + "" + " has had a possession attempted on him by " + commandSender.getName() + " but there are no towers activated!");
                    return true;
                }
            } else {
                commandSender.sendMessage("A player is already manually possessed! Calm down fella!");
                Bukkit.getLogger().info("[PRX]" + player.getName() + " has had a possession attempted on him by " + commandSender.getName() + " but there is already a possessed player!");
                return true;
            }
        } catch (Exception e) {
            Bukkit.getLogger().info("[PRX] In a manual possession of " + player.getName() + " by " + commandSender.getName() + " the process failed: " +e);
            return true;
        }
    }

    public void unXanafication() {
        try {
            if(isPlyPossessed()){
                Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("XANA");
                team.removePlayer(player);
                player.sendMessage(ChatColor.GREEN + "You have been released from XANA's control");
                setPlyPossessed(false);
                Bukkit.getLogger().info(player.getName() + " has been unXanafied");
            }
        }catch (Exception e){
            Bukkit.getLogger().info("[PRX] Something went wrong while UnXanafiying a manually Xanafied player: " +e);
        }
    }
}