package pro.mrcl.ProjectXANA.AttackLogic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EligibilityChecker {
    public static List<LyokoWarrior> getNonVirtedEligiblePlayers() {
        List<LyokoWarrior> lyokoWarriors = new ArrayList<>();
        lyokoWarriors.addAll(Main.getMainInstance().getLyokoWarriors().values()); //get a list of all lyokowarriors
        List<LyokoWarrior> eligebleWarriors = new ArrayList<>();
        lyokoWarriors.forEach(lyokoWarrior -> {
            if (!lyokoWarrior.isVirtualized() && !lyokoWarrior.isXanaIgnored() && !lyokoWarrior.isXanafied() && !lyokoWarrior.isFrontiered()) {
                eligebleWarriors.add(lyokoWarrior); //if the warrior isnt virtualized, ignored or already xanafied
            }
        });
        return eligebleWarriors;
    }

    public static List<LyokoWarrior> getAllEligiblePlayers() {
        List<LyokoWarrior> lyokoWarriors = new ArrayList<>();
        lyokoWarriors.addAll(Main.getMainInstance().getLyokoWarriors().values()); //get a list of all lyokowarriors
        List<LyokoWarrior> eligebleWarriors = new ArrayList<>();
        lyokoWarriors.forEach(lyokoWarrior -> {
            if (!lyokoWarrior.isXanaIgnored() && !lyokoWarrior.isXanafied() && !lyokoWarrior.isFrontiered()) {
                eligebleWarriors.add(lyokoWarrior); //if the warrior isnt virtualized, ignored or already xanafied
            }
        });
        return eligebleWarriors;
    }
}
