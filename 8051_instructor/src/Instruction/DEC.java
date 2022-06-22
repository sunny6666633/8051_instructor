package Instruction;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class DEC extends OperatorSuper{
    public DEC(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "DEC", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(judgeIsValid.isIndirectR(operand.get(0))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(13);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(13, CarrySystem.BinarytoHex("0001011" + operand.get(0).substring(2)));
            else
                DataMemory.setDataMemory(register.getRegister(operand.get(0)), CarrySystem.HexADD(DataMemory.getDataMemory(register.getRegister(operand.get(0))), "-1", false, 2));
        }
        else if(operand.get(0).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(14);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(14, CarrySystem.BinarytoHex("00010100"));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexADD(register.getRegister("ACC"), "-1", false, 2));
        }
        else if(judgeIsValid.isdirectNumber(operand.get(0))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(15);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(15, CarrySystem.BinarytoHex("00010101"+CarrySystem.directNumbertoBinary(operand.get(0))));
            else
                DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0))), CarrySystem.HexADD(DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0)))),  "-1", false, 2));
        }
        else if(judgeIsValid.isdirectR(operand.get(0))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(16);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(16, CarrySystem.BinarytoHex("00011"+String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(0).substring(1)))))));
            else
                register.setRegister(register.getRegisterPosition(operand.get(0)), CarrySystem.HexADD(register.getRegister(operand.get(0)), "-1", false, 2));
        }
        else
            throw new WrongException("INC", true);
    }
}
