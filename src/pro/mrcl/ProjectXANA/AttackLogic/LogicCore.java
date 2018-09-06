package pro.mrcl.ProjectXANA.AttackLogic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.
import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AttackModule;
import mrcl.pro.GoodOldJack12.ProjectCarthage.Main;


public class ProjectXANAModule extends AttackModule {
    public ProjectXANAModule(Main pl) {
        super("ProjectXANACore","Sky");
        pl.getNetwork().getXana().addAttackModule(new ProjectXANAModule());
        addAttack(new ExampleAttack());
    }
}