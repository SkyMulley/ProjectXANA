package pro.mrcl.ProjectXANA.Attacks;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AttackModule;
import pro.mrcl.ProjectXANA.Attacks.Medium.RTTPAttack;
import pro.mrcl.ProjectXANA.Attacks.Pathetic.ElevatorLockAttack;

public class LogicCore extends AttackModule {
    public LogicCore() {
        super("PRXCore","Sky");
        addAttack(new ElevatorLockAttack());
        addAttack(new RTTPAttack());
    }
}