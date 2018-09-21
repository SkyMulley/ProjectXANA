package pro.mrcl.ProjectXANA.AttackLogic;

import mrcl.pro.GoodOldJack12.ProjectCarthage.Logic.Programs.Xana.Attacks.AttackModule;
import pro.mrcl.ProjectXANA.AttackLogic.Easy.LaughingGas;
import pro.mrcl.ProjectXANA.AttackLogic.Pathetic.XanafiedJim;


public class LogicCore extends AttackModule {
    public LogicCore() {
        super("ProjectXANACore","Sky");
        addAttack(new LaughingGas());
        addAttack(new XanafiedJim());
    }
}