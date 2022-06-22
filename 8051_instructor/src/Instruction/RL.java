package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import Memory.CodeMemory;
import Register.register;

import java.util.ArrayList;

public class RL extends OperatorSuper{
    public RL(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "RL", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(19);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(19, CarrySystem.BinarytoHex("00100011"));
            else{
                String ACCbinary = CarrySystem.HextoBinary(register.getRegister("ACC"), 8);
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(ACCbinary.substring(1) + ACCbinary.substring(0, 1)));
            }
        }
        else
            throw new WrongException("The operand of RL should be A", false);
    }
}
