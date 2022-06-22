package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class POP extends OperatorSuper{
    public POP(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "POP", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(judgeIsValid.isdirectNumber(operand.get(0))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(101);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(101, CarrySystem.BinarytoHex("11010000" + CarrySystem.directNumbertoBinary(operand.get(0))));
            else{
                DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0))), DataMemory.getDataMemory(register.getRegister("SP")));
                register.setRegister(register.getRegisterPosition("SP"), CarrySystem.HexADD(register.getRegister("SP"), "-1", false, 2));
            }
        }
        else
            throw new WrongException("The operand of POP should be a direct number", false);
    }
}
