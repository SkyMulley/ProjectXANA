package pro.mrcl.ProjectXANA.Attacks;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AttackModule;
import pro.mrcl.ProjectXANA.Attacks.Easy.BadWeatherAttack;
import pro.mrcl.ProjectXANA.Attacks.Hard.GroupXanafication;
import pro.mrcl.ProjectXANA.Attacks.Hard.Meteorite;
import pro.mrcl.ProjectXANA.Attacks.Medium.ChemicalTrain;
import pro.mrcl.ProjectXANA.Attacks.Medium.NuclearPlant;
import pro.mrcl.ProjectXANA.Attacks.Medium.RTTPAttack;
import pro.mrcl.ProjectXANA.Attacks.Pathetic.*;

public class LogicCore extends AttackModule {
    public LogicCore() {
        super("PRXCore","Sky");
        addAttack(new RTTPAttack());
        addAttack(new NuclearPlant());
        addAttack(new GroupXanafication());
        addAttack(new LostMaze());
        addAttack(new BuggedScanner());
        addAttack(new BadWeatherAttack());
        addAttack(new LockedBoilerRoom());
        addAttack(new ElevatorLockAttack());
        addAttack(new Meteorite());
        addAttack(new ChemicalTrain());
    }
}