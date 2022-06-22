package Instruction;

import java.util.ArrayList;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import Memory.CodeMemory;
import Register.register;

public class MUL extends OperatorSuper{
    public MUL(ArrayList<String> op, int StepOrRun) throws WrongException {
        super(op, 1, "MUL", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("AB")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(76);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(76, CarrySystem.BinarytoHex("10100100"));
            else{
                String Mul = CarrySystem.HexMUL(register.getRegister("ACC"), register.getRegister("B"));
                register.setRegister(register.getRegisterPosition("ACC"), Mul.substring(2));
                register.setRegister(register.getRegisterPosition("B"), Mul.substring(0, 2));
            }
        }
        else
            throw new WrongException("The operand of MUL should be AB", false);
    }
}
