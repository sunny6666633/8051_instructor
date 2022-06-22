package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import Memory.CodeMemory;
import Register.register;

import java.util.ArrayList;

public class SWAP extends OperatorSuper{
    public SWAP(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "SWAP", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(96);
            if(StepOrRun == STEP)
                Instruction.updateEncoding(96, CarrySystem.BinarytoHex("11000100"));
            else
                register.setRegister(register.getRegisterPosition("ACC"), register.getRegister("ACC").substring(1) + register.getRegister("ACC").substring(0, 1));
        }
        else
            throw new WrongException("The operand of SWAP should be A", false);
    }
}
