package Instruction;
import Exception.WrongException;
import InstructionProcess.*;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class XRL extends OperatorSuper{
    public XRL(ArrayList<String> op, int StepOrRun)throws WrongException{
        super(op, 2, "XRL", StepOrRun);
    }
    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("A") && judgeIsValid.isImmediate(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(50);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(50, CarrySystem.BinarytoHex("01100100" + CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.XOR(CarrySystem.HextoBinary(register.getRegister("ACC"), 8), CarrySystem.ImmediatetoBinary(operand.get(1), 8), 8)));
        }
        else if(operand.get(0).equals("A") && judgeIsValid.isIndirectR(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(51);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(51, CarrySystem.BinarytoHex("0110011"+operand.get(1).substring(2)));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.XOR(CarrySystem.HextoBinary(register.getRegister("ACC"), 8), CarrySystem.HextoBinary(DataMemory.getDataMemory(register.getRegister(operand.get(1))), 8), 8)));
        }
        else if(operand.get(0).equals("A") && judgeIsValid.isdirectNumber(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(52);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(52, CarrySystem.BinarytoHex("01100101" + CarrySystem.directNumbertoBinary(operand.get(1))));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.XOR(CarrySystem.HextoBinary(register.getRegister("ACC"), 8), CarrySystem.HextoBinary(DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(1)))), 8), 8)));
        }
        else if(operand.get(0).equals("A") && judgeIsValid.isdirectR(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(53);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(53, CarrySystem.BinarytoHex("01101" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(1).substring(1)))))));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.XOR(CarrySystem.HextoBinary(register.getRegister("ACC"), 8), CarrySystem.HextoBinary(register.getRegister(operand.get(1)), 8), 8)));
        }
        else if(judgeIsValid.isdirectNumber(operand.get(0)) && judgeIsValid.isImmediate(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(54);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(54, CarrySystem.BinarytoHex("01100011"+CarrySystem.directNumbertoBinary(operand.get(0))+CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
            else
                DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0))), CarrySystem.BinarytoHex(CarrySystem.XOR(CarrySystem.HextoBinary(DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0)))), 8), CarrySystem.ImmediatetoBinary(operand.get(1), 8), 8)));
        }
        else if(judgeIsValid.isdirectNumber(operand.get(0)) && operand.get(1).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(55);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(55, CarrySystem.BinarytoHex("01100010"+CarrySystem.directNumbertoBinary(operand.get(0))));
            else
                DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0))), CarrySystem.BinarytoHex(CarrySystem.XOR(CarrySystem.HextoBinary(DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0)))), 8), CarrySystem.directNumbertoBinary("ACC"), 8)));
        }
        else
            throw new WrongException("XRL", true);
    }
}
