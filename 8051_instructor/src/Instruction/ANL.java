package Instruction;
import Exception.WrongException;
import InstructionProcess.*;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class ANL extends OperatorSuper{
    public ANL(ArrayList<String> op, int StepOrRun)throws WrongException{
        super(op, 2, "ANL", StepOrRun);
    }
    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("A") && judgeIsValid.isImmediate(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(41);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(41, CarrySystem.BinarytoHex("01010100" + CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.AND(CarrySystem.HextoBinary(register.getRegister("ACC"), 8), CarrySystem.ImmediatetoBinary(operand.get(1), 8), 8)));
        }
        else if(operand.get(0).equals("A") && judgeIsValid.isIndirectR(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(42);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(42, CarrySystem.BinarytoHex("0101011"+operand.get(1).substring(2)));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.AND(CarrySystem.HextoBinary(register.getRegister("ACC"), 8), CarrySystem.HextoBinary(DataMemory.getDataMemory(register.getRegister(operand.get(1))), 8), 8)));
        }
        else if(operand.get(0).equals("A") && judgeIsValid.isdirectNumber(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(43);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(43, CarrySystem.BinarytoHex("01010101" + CarrySystem.directNumbertoBinary(operand.get(1))));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.AND(CarrySystem.HextoBinary(register.getRegister("ACC"), 8), CarrySystem.HextoBinary(DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(1)))), 8), 8)));
        }
        else if(operand.get(0).equals("A") && judgeIsValid.isdirectR(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(44);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(44, CarrySystem.BinarytoHex("01011" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(1).substring(1)))))));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.AND(CarrySystem.HextoBinary(register.getRegister("ACC"), 8), CarrySystem.HextoBinary(register.getRegister(operand.get(1)), 8), 8)));
        }
        else if(operand.get(0).equals("C") && operand.get(1).startsWith("/") && judgeIsValid.isdirectNumber(operand.get(1).substring(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(45);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(45, CarrySystem.BinarytoHex("10110000"+CarrySystem.directNumbertoBinary(operand.get(1).substring(1))));
            else{
                int Clocation = Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1).substring(1)), 2) / 8 + 32;
                int Cbit = 7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1).substring(1)), 2) % 8;

                StringBuilder C = new StringBuilder(CarrySystem.HextoBinary(DataMemory.getDataMemory(register.getRegisterPosition("PSW")), 8));
                C.setCharAt(0, CarrySystem.AND(CarrySystem.complement(CarrySystem.HextoBinary(DataMemory.getDataMemory(Integer.toHexString(Clocation)), 8).substring(Cbit, Cbit+1)), register.getRegister("C"), 1).charAt(0));
                register.setRegister(register.getRegisterPosition("PSW"), CarrySystem.BinarytoHex(C.toString()));
            }

        }
        else if(operand.get(0).equals("C") && judgeIsValid.isdirectNumber(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(46);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(46, CarrySystem.BinarytoHex("10000010"+CarrySystem.directNumbertoBinary(operand.get(1))));
            else{
                int Clocation = Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1)), 2) / 8 + 32;
                int Cbit = 7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1)), 2) % 8;

                StringBuilder C = new StringBuilder(CarrySystem.HextoBinary(DataMemory.getDataMemory(register.getRegisterPosition("PSW")), 8));
                C.setCharAt(0, CarrySystem.AND(CarrySystem.HextoBinary(DataMemory.getDataMemory(Integer.toHexString(Clocation)), 8).substring(Cbit, Cbit+1), register.getRegister("C"), 1).charAt(0));
                register.setRegister(register.getRegisterPosition("PSW"), CarrySystem.BinarytoHex(C.toString()));
            }

        }
        else if(judgeIsValid.isdirectNumber(operand.get(0)) && judgeIsValid.isImmediate(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(47);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(47, CarrySystem.BinarytoHex("01010011"+CarrySystem.directNumbertoBinary(operand.get(0))+CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
            else
                DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0))), CarrySystem.BinarytoHex(CarrySystem.AND(CarrySystem.HextoBinary(DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.ImmediatetoBinary(operand.get(1), 8))), 8), CarrySystem.ImmediatetoBinary(operand.get(1), 8), 8)));
        }
        else if(judgeIsValid.isdirectNumber(operand.get(0)) && operand.get(1).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(48);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(48, CarrySystem.BinarytoHex("01010010"+CarrySystem.directNumbertoBinary(operand.get(0))));
            else
                DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0))), CarrySystem.BinarytoHex(CarrySystem.AND(CarrySystem.HextoBinary(DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(1)))), 8), CarrySystem.HextoBinary(register.getRegister("ACC"), 8), 8)));
        }
        else
            throw new WrongException("ANL", true);
    }
}
