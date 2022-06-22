package Instruction;
import Exception.WrongException;
import InstructionProcess.*;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class ORL extends OperatorSuper{
    public ORL(ArrayList<String> op, int StepOrRun)throws WrongException{
        super(op, 2, "ORL", StepOrRun);
    }
    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("A") && judgeIsValid.isImmediate(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(32);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(32, CarrySystem.BinarytoHex("01000100" + CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.OR(CarrySystem.directNumbertoBinary("ACC"), CarrySystem.ImmediatetoBinary(operand.get(1), 8), 8)));
        }
        else if(operand.get(0).equals("A") && judgeIsValid.isIndirectR(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(33);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(33, CarrySystem.BinarytoHex("0100011"+operand.get(1).substring(2)));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.OR(CarrySystem.directNumbertoBinary("ACC"), CarrySystem.directNumbertoBinary("0x"+DataMemory.getDataMemory(register.getRegister(operand.get(1)))), 8)));
        }
        else if(operand.get(0).equals("A") && judgeIsValid.isdirectNumber(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(34);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(34, CarrySystem.BinarytoHex("01000101" + CarrySystem.directNumbertoBinary(operand.get(1))));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.OR(CarrySystem.directNumbertoBinary("ACC"), CarrySystem.directNumbertoBinary("0x"+DataMemory.getDataMemory(Instruction.getNewEncoding(34).substring(2))), 8)));
        }
        else if(operand.get(0).equals("A") && judgeIsValid.isdirectR(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(35);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(35, CarrySystem.BinarytoHex("01001" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(1).substring(1)))))));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.OR(CarrySystem.directNumbertoBinary("ACC"), CarrySystem.directNumbertoBinary("0x"+register.getRegister(operand.get(1))), 8)));
        }
        else if(operand.get(0).equals("C") && operand.get(1).startsWith("/") && judgeIsValid.isdirectNumber(operand.get(1).substring(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(36);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(36, CarrySystem.BinarytoHex("10100000"+CarrySystem.directNumbertoBinary(operand.get(1).substring(1))));
            else{
                int Clocation = Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1).substring(1)), 2) / 8 + 32;
                int Cbit = 7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1).substring(1)), 2) % 8;

                StringBuilder C = new StringBuilder(CarrySystem.directNumbertoBinary("0x"+DataMemory.getDataMemory(register.getRegisterPosition("PSW"))));
                C.setCharAt(0, CarrySystem.OR(CarrySystem.complement(CarrySystem.directNumbertoBinary("0x"+DataMemory.getDataMemory(Integer.toHexString(Clocation))).substring(Cbit, Cbit+1)), register.getRegister("C"), 1).charAt(0));
                register.setRegister(register.getRegisterPosition("PSW"), CarrySystem.BinarytoHex(C.toString()));
            }
        }
        else if(operand.get(0).equals("C") && judgeIsValid.isdirectNumber(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(37);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(37, CarrySystem.BinarytoHex("01110010"+CarrySystem.directNumbertoBinary(operand.get(1))));
            else{
                int Clocation = Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1)), 2) / 8 + 32;
                int Cbit = 7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1)), 2) % 8;

                StringBuilder C = new StringBuilder(CarrySystem.directNumbertoBinary("0x"+DataMemory.getDataMemory(register.getRegisterPosition("PSW"))));
                C.setCharAt(0, CarrySystem.OR(CarrySystem.directNumbertoBinary("0x"+DataMemory.getDataMemory(Integer.toHexString(Clocation))).substring(Cbit, Cbit+1), register.getRegister("C"), 1).charAt(0));
                register.setRegister(register.getRegisterPosition("PSW"), CarrySystem.BinarytoHex(C.toString()));
            }

        }
        else if(judgeIsValid.isdirectNumber(operand.get(0)) && judgeIsValid.isImmediate(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(38);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(38, CarrySystem.BinarytoHex("01000011"+CarrySystem.directNumbertoBinary(operand.get(0))+CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
            else
                DataMemory.setDataMemory(Instruction.getNewEncoding(38).substring(2, 4), CarrySystem.BinarytoHex(CarrySystem.OR(CarrySystem.directNumbertoBinary("0X"+DataMemory.getDataMemory(Instruction.getNewEncoding(38).substring(2, 4))), CarrySystem.ImmediatetoBinary(operand.get(1), 8), 8)));
        }
        else if(judgeIsValid.isdirectNumber(operand.get(0)) && operand.get(1).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(39);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(39, CarrySystem.BinarytoHex("01000010"+CarrySystem.directNumbertoBinary(operand.get(0))));
            else
                DataMemory.setDataMemory(Instruction.getNewEncoding(39).substring(2), CarrySystem.BinarytoHex(CarrySystem.OR(CarrySystem.directNumbertoBinary("0X"+DataMemory.getDataMemory(Instruction.getNewEncoding(39).substring(2))), CarrySystem.directNumbertoBinary("ACC"), 8)));
        }
        else
            throw new WrongException("ORL", true);
    }
}
