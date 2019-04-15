package pro.mrcl.ProjectXANA.MiscLogic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.LyokoWarrior.LyokoWarrior;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;

import java.util.ArrayList;
import java.util.List;

public class EligibleWarriorSelect{
    public List<LyokoWarrior> EligibleWarrior() {
        List<LyokoWarrior> lyokoWarriors = (List<LyokoWarrior>) Main.getMainInstance().getLyokoWarriors().values(); //get a list of all lyokowarriors
        List<LyokoWarrior> eligebleWarriors = new ArrayList<>();
        lyokoWarriors.forEach(lyokoWarrior -> {
            if (!lyokoWarrior.isRttpIgnored() && !lyokoWarrior.isXanafied()) {
                eligebleWarriors.add(lyokoWarrior); //if the warrior isnt virtualized, ignored or already xanafied
            }
        });
        return eligebleWarriors;
    }

    public List<LyokoWarrior> EligibleWarriorVirt() {
        List<LyokoWarrior> lyokoWarriors = (List<LyokoWarrior>) Main.getMainInstance().getLyokoWarriors().values(); //get a list of all lyokowarriors
        List<LyokoWarrior> eligebleWarriors = new ArrayList<>();
        lyokoWarriors.forEach(lyokoWarrior -> {
            if (!lyokoWarrior.isVirtualized() && !lyokoWarrior.isRttpIgnored() && !lyokoWarrior.isXanafied()) {
                eligebleWarriors.add(lyokoWarrior); //if the warrior isnt virtualized, ignored or already xanafied
            }
        });
        return eligebleWarriors;
    }
}
