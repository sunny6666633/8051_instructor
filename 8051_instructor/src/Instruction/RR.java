package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import Memory.CodeMemory;
import Register.register;

import java.util.ArrayList;

public class RR extends OperatorSuper{
    public RR(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "RR", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(3);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(3, CarrySystem.BinarytoHex("00000011"));
            else{
                String ACCbinary = CarrySystem.HextoBinary(register.getRegister("ACC"), 8);
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(ACCbinary.substring(7) + ACCbinary.substring(0, 7)));
            }
        }
        else
            throw new WrongException("The operand of RR should be A", false);
    }
}
