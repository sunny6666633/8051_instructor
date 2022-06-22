package Instruction;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class INC extends OperatorSuper{
    public INC(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "INC", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(judgeIsValid.isIndirectR(operand.get(0))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(4);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(4, CarrySystem.BinarytoHex("0000011" + operand.get(0).substring(2)));
            else
                DataMemory.setDataMemory(register.getRegister(operand.get(0)), CarrySystem.HexADD(DataMemory.getDataMemory(register.getRegister(operand.get(0))), "1", false, 2));
        }
        else if(operand.get(0).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(5);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(5, CarrySystem.BinarytoHex("00000100"));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexADD(register.getRegister("ACC"), "1", false, 2));
        }
        else if(judgeIsValid.isdirectNumber(operand.get(0))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(6);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(6, CarrySystem.BinarytoHex("00000101"+CarrySystem.directNumbertoBinary(operand.get(0))));
            else
                DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0))), CarrySystem.HexADD(DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0)))), "1", false, 2));
        }
        else if(operand.get(0).equals("DPTR")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(7);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(7, CarrySystem.BinarytoHex("10100011"));
            else
                register.setRegister("DPTR", CarrySystem.HexADD(register.getRegister("DPTR"), "1", false, 4));
        }
        else if(judgeIsValid.isdirectR(operand.get(0))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(8);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(8, CarrySystem.BinarytoHex("00001"+String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(0).substring(1)))))));
            else
                register.setRegister(register.getRegisterPosition(operand.get(0)), CarrySystem.HexADD(register.getRegister(operand.get(0)), "1", false, 2));
        }
        else
            throw new WrongException("INC", true);
    }
}
