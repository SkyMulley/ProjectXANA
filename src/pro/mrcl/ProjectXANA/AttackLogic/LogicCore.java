package pro.mrcl.ProjectXANA.AttackLogic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AttackModule;
import pro.mrcl.ProjectXANA.AttackLogic.Easy.LaughingGas;


public class ProjectXANAModule extends AttackModule {
    public ProjectXANAModule() {
        super("ProjectXANACore","Sky");
        addAttack(new LaughingGas());
    }
}