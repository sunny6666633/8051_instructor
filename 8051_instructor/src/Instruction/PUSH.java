package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class PUSH extends OperatorSuper{
    public PUSH(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "PUSH", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(judgeIsValid.isdirectNumber(operand.get(0))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(92);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(92, CarrySystem.BinarytoHex("11000000" + CarrySystem.directNumbertoBinary(operand.get(0))));
            else{
                register.setRegister(register.getRegisterPosition("SP"), CarrySystem.HexADD(register.getRegister("SP"), "1", false, 2));
                DataMemory.setDataMemory(register.getRegister("SP"), DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0)))));
            }
        }
        else
            throw new WrongException("The operand of PUSH should be a direct number", false);
    }
}
