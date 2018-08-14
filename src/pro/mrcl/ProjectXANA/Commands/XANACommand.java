package pro.mrcl.ProjectXANA.Commands;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Exceptions.*;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Util.Checker;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.VirtualStructures.Network;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.VirtualStructures.Tower;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pro.mrcl.ProjectXANA.XANAMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class XANACommand implements CommandExecutor {
    //utilities
    protected XANAMain plugin;

    //onCommand arguments
    protected CommandSender commandSender;
    protected Command command;
    protected String s;
    protected String[] strings;
    private String permission;
    private List<String> permissions;
    private List<XANACommand> subcommands = new ArrayList<>();
    private List<String> allowedArgs = new ArrayList<>();
    private String HelpMessage;
    private String name;
    private List<String> aliases = new ArrayList<>();

    //default constructor
    public XANACommand(XANAMain plugin, String name) {
        this.plugin = plugin;
        this.setupCommand(name);
    }

    //getters
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getHelpMessage() {
        return HelpMessage;
    }

    //setters
    public void setHelpMessage(String helpMessage) {
        this.HelpMessage = ChatColor.YELLOW + helpMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAllowedArgs() {
        return allowedArgs;
    }

    public void setAllowedArgs(List<String> allowedArgs) {
        this.allowedArgs = allowedArgs;
    }

    public List<XANACommand> getSubcommands() {
        return subcommands;
    }

    public void setSubcommands(List<XANACommand> subcommands) {
        this.subcommands = subcommands;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        registerSender(commandSender, command, s, strings);
        return false;
    }

    /**
     * Registers all the arguments of onCommand for usage in methods outside of it
     **/
    protected void registerSender(CommandSender commandSender, Command command, String s, String[] strings) {
        this.commandSender = commandSender;
        this.command = command;
        this.s = s;
        this.strings = strings;
    }

    /**
     * Sets up help messages, permissions and so forth according to a default procedure. should be overrided in most cases
     **/
    protected void setupCommand(String name) {
        this.setName(name);
        getAllowedArgs().add("3546547665766876486764135789876"); //random argument as default
    }

    //subcommands
    protected void addSubCommand(XANACommand command) {
        this.subcommands.add(command);
    }

    protected void checkSubCommands(int argindex) throws Checker.InvalidArgumentException, CarthageException {

        if (argindex > strings.length){throw new CarthageException();}

        String commandarg = strings[argindex];
        List<String> stringlist = new ArrayList<>();
        stringlist.addAll(Arrays.asList(strings));
        XANACommand foundcommand = null;
        for (XANACommand subcommand: subcommands) {
            if(subcommand.getName().equalsIgnoreCase(commandarg) || subcommand.getAliases().contains(commandarg) ) {
                foundcommand = subcommand;
                if (!stringlist.isEmpty()) {
                    if (argindex > 0) {
                        stringlist.removeAll(stringlist.subList(0, argindex + 1));
                    } else {
                        stringlist.remove(0);
                    }
                    strings = stringlist.toArray(new String[stringlist.size()]);
                }
            }
        }
        if(foundcommand != null){
            foundcommand.onCommand(commandSender,command,s,strings);
        }else {
            throw new Checker.InvalidArgumentException();
        }


    }

    public List<String> getAliases() {
        return aliases;
    }

    protected void addAlias(String string) {
        this.aliases.add(string);
    }

    /**
     * Catches some common exceptions (actually basically all of them) that most commands throw and calls the appropriate method to send an error message
     **/
    protected void catchException(Exception e) {
        if (e.getClass().equals(Checker.NoPermissionException.class)) {
            catchPermdenied();
        } else if (e.getClass().equals(Checker.InvalidArgumentException.class)) {
            catchInvalidArgument();
        } else if (e.getClass().equals(Checker.TooLittleArgsException.class)) {
            catchTooLittleArguments();
        } else if (e.getClass().equals(Checker.TooMuchArgsException.class)) {
            catchTooMuchArguments();
        } else if (e.getClass().equals(SectorDoesNotExistException.class)) {
            catchSectorDoesntExist();
        } else if (e.getClass().equals(TowerDoesntExistException.class)) {
            catchTowerDoesntExist();
        } else if (e.getClass().equals(Tower.AlreadyActivatedException.class)) {
            catchTowerActivated();
        } else if (e.getClass().equals(Tower.AlreadyDeactivatedException.class)) {
            catchTowerDeactivated();
        } else if (e.getClass().equals(Checker.NotAPlayerException.class)) {
            catchnotAPlayer();
        } else if (e.getClass().equals(InteractorAlreadyExistsException.class)) {
            catchInteractorExists();
        } else if (e.getClass().equals(InteractorDoesNotExistException.class)) {
            catchInteractorDoesntExist();
        } else if (e.getClass().equals(InvalidPlayerException.class)) {
            catchInvalidPlayer(((InvalidPlayerException) e).getPlayer());
        } else if (e instanceof InvalidSectorTypeException) {
            catchInvalidSectorType((InvalidSectorTypeException) e);
        } else if (e instanceof WorldAlreadyHasTypeException) {
            catchWorldHasType((WorldAlreadyHasTypeException) e);
        } else if (e instanceof NoAnnexInteractorException) {
            catchNoAnnexInteractorException((NoAnnexInteractorException) e);
        } else if (e instanceof InvalidAnnexUserException) {
            catchInvalidAnnexUser((InvalidAnnexUserException) e);
        } else if(e instanceof VWorldDoesNotExistException){
            catchworldDoesNotExist();
        }else if(e instanceof VpointDoesntExistException) {
            catchVpointDoesntExist();
        }else if(e instanceof VpointAlreadyExistsException) {
            catchVpointAlreadyExists();
        }
        else{
            commandSender.sendMessage(ChatColor.RED + "Something went wrong! Check the log!");
            Bukkit.getLogger().warning(org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e));
        }
    }


    //default error messages
    private void catchPermdenied() {
        commandSender.sendMessage(String.format("You lack the %s permission", this.permission));
    }

    private void catchWorldHasType(WorldAlreadyHasTypeException e) {
        commandSender.sendMessage(ChatColor.RED + "The sector type: " + e.getType() +" already exists in this world!");
    }

    private void catchInvalidArgument() {
        commandSender.sendMessage(String.format(ChatColor.RED + "Invalid arguments!"));
        commandSender.sendMessage(getHelpMessage());
    }

    private void catchInvalidAnnexUser(InvalidAnnexUserException e){
        commandSender.sendMessage(String.format(ChatColor.RED+"The annex user %s is invalid!",e.getAnnexuser()));
    }

    private void catchTooLittleArguments() {
        commandSender.sendMessage(String.format(ChatColor.RED + "Not enough arguments!"));
        commandSender.sendMessage(getHelpMessage());
    }

    private void catchTooMuchArguments() {
        commandSender.sendMessage(ChatColor.RED + "Too many arguments!");
        commandSender.sendMessage(getHelpMessage());
    }

    private void catchSectorDoesntExist() {
        commandSender.sendMessage(ChatColor.RED + "This sector doesn't exist");
    }

    private void catchTowerDoesntExist() {
        commandSender.sendMessage(ChatColor.RED + "This tower doesn't exist");
    }

    private void catchTowerActivated() {
        commandSender.sendMessage(ChatColor.RED + "This tower is already activated");
    }

    private void catchTowerDeactivated() {
        commandSender.sendMessage(ChatColor.RED + "This tower is already deactivated");
    }

    private void catchnotAPlayer() {
        commandSender.sendMessage(ChatColor.RED + "You must run this as a player");
    }

    private void catchInteractorExists() {
        commandSender.sendMessage(ChatColor.RED + "This block is already an interactor!");
    }

    private void catchInteractorDoesntExist() {
        commandSender.sendMessage(ChatColor.RED + "This block isn't an interactor!");
    }

    private void catchInvalidPlayer(String player) {
        commandSender.sendMessage(ChatColor.RED + String.format("The player \"%s\" is not a valid player", player));
        catchInvalidArgument();
    }

    private void catchInvalidSectorType(InvalidSectorTypeException e) {
        commandSender.sendMessage(ChatColor.RED + "Sector type: " + e.getType() + " does not exist!");
        catchInvalidArgument();
    }

    private void catchVpointDoesntExist() { commandSender.sendMessage(ChatColor.RED + "The vpoint doesn't exist!"); }

    private void catchVpointAlreadyExists() {
        commandSender.sendMessage(ChatColor.RED + "The vpoint already exists or there's one nearby!");
    }

    private void catchNoAnnexInteractorException(NoAnnexInteractorException e) {
        commandSender.sendMessage(ChatColor.RED + "This tower doesnt have an activator for " + e.getAnnexuser().getChatFormat());
    }
    private void catchworldDoesNotExist(){
        commandSender.sendMessage(ChatColor.RED+"That world does not exist!");
    }
}