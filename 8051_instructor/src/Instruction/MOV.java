package Instruction;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;
public class MOV extends OperatorSuper{
    public MOV(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 2, "MOV", StepOrRun);
    }

    public void judgeOperandIsValid(int StepOrRun) throws WrongException{
        if(judgeIsValid.isIndirectR(operand.get(0)) && judgeIsValid.isImmediate(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(58);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(58, CarrySystem.BinarytoHex("0111011" + operand.get(0).charAt(2) + CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
            else
                DataMemory.setDataMemory(register.getRegister(operand.get(0)), CarrySystem.BinarytoHex(CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
        }
        else if(judgeIsValid.isIndirectR(operand.get(0)) && operand.get(1).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(59);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(59, CarrySystem.BinarytoHex("1111011" + operand.get(0).charAt(2)));
            else
                DataMemory.setDataMemory(register.getRegister(operand.get(0)), register.getRegister("ACC"));
        }
        else if(judgeIsValid.isIndirectR(operand.get(0)) && judgeIsValid.isdirectNumber(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(60);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(60, CarrySystem.BinarytoHex("1010011" + operand.get(0).charAt(2) + CarrySystem.directNumbertoBinary(operand.get(1))));
            else
                DataMemory.setDataMemory(register.getRegister(operand.get(0)), DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(1)))));
        }
        else if(operand.get(0).equals("A") && judgeIsValid.isImmediate(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(61);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(61, CarrySystem.BinarytoHex("01110100" + CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
        }
        else if(operand.get(0).equals("A") && judgeIsValid.isIndirectR(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(62);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(62, CarrySystem.BinarytoHex("1110011" + operand.get(1).charAt(2)));
            else
                register.setRegister(register.getRegisterPosition("ACC"), DataMemory.getDataMemory(register.getRegister(operand.get(1))));
        }
        else if(operand.get(0).equals("A") && judgeIsValid.isdirectNumber(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(63);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(63, CarrySystem.BinarytoHex("11100101" + CarrySystem.directNumbertoBinary(operand.get(1))));
            else
                register.setRegister(register.getRegisterPosition("ACC"), DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(1)))));
        }
        else if(operand.get(0).equals("A") && judgeIsValid.isdirectR(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(64);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(64, CarrySystem.BinarytoHex("11101" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(1).substring(1)))))));
            else
                register.setRegister(register.getRegisterPosition("ACC"), register.getRegister(operand.get(1)));
        }
        else if(judgeIsValid.isdirectNumber(operand.get(0)) && operand.get(1).equals("C")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(65);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(65, CarrySystem.BinarytoHex("10010010" + CarrySystem.directNumbertoBinary(operand.get(0))));
            else{
                int Clocation = Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) / 8 + 32;
                int Cbit = 7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) % 8;

                StringBuilder C = new StringBuilder(CarrySystem.HextoBinary(DataMemory.getDataMemory(Integer.toHexString(Clocation)), 8));
                C.setCharAt(Cbit, register.getRegister("C").charAt(0));
                DataMemory.setDataMemory(Integer.toHexString(Clocation), CarrySystem.BinarytoHex(C.toString()));
            }

        }
        else if(operand.get(0).equals("C") && judgeIsValid.isdirectNumber(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(66);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(66, CarrySystem.BinarytoHex("10100010" + CarrySystem.directNumbertoBinary(operand.get(1))));
            else{
                int Clocation = Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1)), 2) / 8 + 32;
                int Cbit = 7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1)), 2) % 8;

                StringBuilder C = new StringBuilder(CarrySystem.HextoBinary(DataMemory.getDataMemory(register.getRegisterPosition("PSW")), 8));
                C.setCharAt(0, CarrySystem.HextoBinary(DataMemory.getDataMemory(Integer.toHexString(Clocation)), 8).charAt(Cbit));
                register.setRegister(register.getRegisterPosition("PSW"), CarrySystem.BinarytoHex(C.toString()));
            }

        }
        else if(judgeIsValid.isdirectNumber(operand.get(0)) && judgeIsValid.isdirectNumber(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(67);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(67, CarrySystem.BinarytoHex("10000101" + CarrySystem.directNumbertoBinary(operand.get(1)) + CarrySystem.directNumbertoBinary(operand.get(0))));
            else
                DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0))), DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(1)))));
        }
        else if(judgeIsValid.isdirectNumber(operand.get(0)) && judgeIsValid.isImmediate(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(68);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(68, CarrySystem.BinarytoHex("01110101" + CarrySystem.directNumbertoBinary(operand.get(0)) + CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
            else
                DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0))), CarrySystem.BinarytoHex(CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
        }
        else if(judgeIsValid.isdirectNumber(operand.get(0)) && judgeIsValid.isIndirectR(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(69);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(69, CarrySystem.BinarytoHex("1000011" + operand.get(1).charAt(2) + CarrySystem.directNumbertoBinary(operand.get(0))));
            else
                DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0))), DataMemory.getDataMemory(register.getRegister(operand.get(1))));
        }
        else if(judgeIsValid.isdirectNumber(operand.get(0)) && operand.get(1).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(70);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(70, CarrySystem.BinarytoHex("11110101" + CarrySystem.directNumbertoBinary(operand.get(0))));
            else
                DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0))), register.getRegister("ACC"));
        }
        else if(judgeIsValid.isdirectNumber(operand.get(0)) && judgeIsValid.isdirectR(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(71);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(71, CarrySystem.BinarytoHex("10001" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(1).substring(1))))) + CarrySystem.directNumbertoBinary(operand.get(0))));
            else
                DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0))), register.getRegister(operand.get(1)));
        }
        else if(operand.get(0).equals("DPTR") && (judgeIsValid.isImmediate(operand.get(1)))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(72);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(72, CarrySystem.BinarytoHex("10010000"+CarrySystem.ImmediatetoBinary(operand.get(1), 16)));
            else
                register.setRegister("DPTR", CarrySystem.BinarytoHex(CarrySystem.ImmediatetoBinary(operand.get(1), 16)));
        }
        else if(operand.get(0).equals("DPTR") && Label.checkIsLabelExist(operand.get(1).substring(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(72);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(72, CarrySystem.BinarytoHex("10010000") + Label.getPosition(operand.get(1).substring(1)).substring(2));
            else
                register.setRegister("DPTR", Label.getPosition(operand.get(1).substring(1)).substring(2));
        }
        else if(judgeIsValid.isdirectR(operand.get(0)) && judgeIsValid.isImmediate(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(73);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(73, CarrySystem.BinarytoHex("01111" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(0).substring(1))))) + CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
            else
                register.setRegister(register.getRegisterPosition(operand.get(0)), CarrySystem.BinarytoHex(CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
        }
        else if(judgeIsValid.isdirectR(operand.get(0)) && operand.get(1).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(74);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(74, CarrySystem.BinarytoHex("11111"+String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(0).substring(1)))))));
            else
                register.setRegister(register.getRegisterPosition(operand.get(0)), register.getRegister("ACC"));
        }
        else if(judgeIsValid.isdirectR(operand.get(0)) && judgeIsValid.isdirectNumber(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(75);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(75, CarrySystem.BinarytoHex("10101" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(0).substring(1))))) + CarrySystem.directNumbertoBinary(operand.get(1))));
            else
                register.setRegister(register.getRegisterPosition(operand.get(0)), DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(1)))));
        }
        else if(judgeIsValid.isdirectR(operand.get(0)) && operand.get(1).matches("AR[0-7]")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(75);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(75, CarrySystem.BinarytoHex("10101" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(0).substring(1))))) + CarrySystem.directNumbertoBinary(operand.get(1).substring(2))));
            else
                register.setRegister(register.getRegisterPosition(operand.get(0)), register.getRegister(operand.get(1).substring(1)));
        }
        else if(judgeIsValid.isdirectR(operand.get(0)) && judgeIsValid.isdirectR(operand.get(1))){
            throw new WrongException(operand.get(1) + " cannot be used here!\nHint:You can add 'A' in front of " + operand.get(1), false);
        }
        else{
            throw new WrongException("MOV", true);
        }
    }
}
