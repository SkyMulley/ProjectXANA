package pro.mrcl.ProjectXANA.AttackLogic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AttackModule;
import pro.mrcl.ProjectXANA.AttackLogic.Easy.LaughingGas;
import pro.mrcl.ProjectXANA.AttackLogic.Medium.NuclearPlant;
import pro.mrcl.ProjectXANA.AttackLogic.Medium.ZeroGravity;


public class LogicCore extends AttackModule {
    public LogicCore() {
        super("PRXCore","Sky");
        addAttack(new LaughingGas());
        addAttack(new NuclearPlant());
    }
}