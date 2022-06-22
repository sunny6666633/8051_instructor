package Instruction;

import java.util.ArrayList;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.InstructionPosition;
import Memory.CodeMemory;
import Register.register;

public class MOVC extends OperatorSuper{
    public MOVC(ArrayList<String>op, int StepOrRun) throws WrongException{
        super(op, 2, "MOVC", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if (operand.get(0).equals("A")) {
            if (operand.get(1).equals("@A+DPTR")) {
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(78);
                else if (StepOrRun == STEP)
                    Instruction.updateEncoding(78, CarrySystem.BinarytoHex("10010011"));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CodeMemory.getcodeMemory(CarrySystem.HexADD(register.getRegister("ACC"), register.getRegister("DPTR"), false, 4)));
            }
            else if (operand.get(1).equals("@A+PC")) {
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(79);
                else if (StepOrRun == STEP)
                    Instruction.updateEncoding(79, CarrySystem.BinarytoHex("10000011"));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CodeMemory.getcodeMemory(CarrySystem.HexADD(register.getRegister("ACC"), register.getPC().substring(2), false, 4)));
            }
            else
                throw new WrongException("The first operand of ADD instruction must be A", false);
        }
    }
}
