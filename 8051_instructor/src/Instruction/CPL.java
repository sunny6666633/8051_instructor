package Instruction;

import java.util.ArrayList;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

public class CPL extends OperatorSuper{
    public CPL(ArrayList<String>op, int StepOrRun) throws WrongException{
        super(op, 1, "CPL", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(85);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(85, CarrySystem.BinarytoHex("11110100"));
            else
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexADD("-".concat(register.getRegister("ACC")), "FF", false, 2));
        }
        else if(judgeIsValid.isdirectNumber(operand.get(0))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(86);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(86, CarrySystem.BinarytoHex("10110010" + CarrySystem.directNumbertoBinary(operand.get(0))));
            else{
                int Clocation = Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) / 8 + 32;
                int Cbit = 7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) % 8;

                StringBuilder C = new StringBuilder(CarrySystem.HextoBinary(DataMemory.getDataMemory(Integer.toHexString(Clocation)), 8));
                C.setCharAt(Cbit, CarrySystem.complement(CarrySystem.HextoBinary(DataMemory.getDataMemory(Integer.toHexString(Clocation)), 8).substring(Cbit, Cbit+1)).charAt(0));
                DataMemory.setDataMemory(Integer.toHexString(Clocation), CarrySystem.BinarytoHex(C.toString()));
            }
        }
        else if(operand.get(0).equals("C")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(87);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(87, CarrySystem.BinarytoHex("10110011"));
            else{
                StringBuilder C = new StringBuilder(CarrySystem.directNumbertoBinary("0x"+DataMemory.getDataMemory(register.getRegisterPosition("PSW"))));
                C.setCharAt(0, CarrySystem.complement(register.getRegister("C")).charAt(0));
                register.setRegister(register.getRegisterPosition("PSW"), CarrySystem.BinarytoHex(C.toString()));
            }
        }
        else
            throw new WrongException("CPL", true);
    }
}
