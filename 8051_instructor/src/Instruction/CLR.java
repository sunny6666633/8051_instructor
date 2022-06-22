package Instruction;

import InstructionProcess.CarrySystem;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Exception.WrongException;
import Register.register;

import java.util.ArrayList;

public class CLR extends OperatorSuper{
    public CLR(ArrayList<String> op, int StepOrRun) throws WrongException {
        super(op, 1, "CLR", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(93);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(93, CarrySystem.BinarytoHex("11100100"));
            else
                DataMemory.setDataMemory(register.getRegisterPosition("ACC"), "00");
        }
        else if(judgeIsValid.isdirectNumber(operand.get(0))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(94);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(94, CarrySystem.BinarytoHex("11000010" + CarrySystem.directNumbertoBinary(operand.get(0))));
            else{
                int Clocation = Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) / 8 + 32;
                int Cbit = 7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) % 8;

                StringBuilder C = new StringBuilder(CarrySystem.HextoBinary(DataMemory.getDataMemory(Integer.toHexString(Clocation)), 8));
                C.setCharAt(Cbit, '0');
                DataMemory.setDataMemory(Integer.toHexString(Clocation), CarrySystem.BinarytoHex(C.toString()));
            }
        }
        else if(operand.get(0).equals("C")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(95);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(95, CarrySystem.BinarytoHex("11000011"));
            else{
                StringBuilder C = new StringBuilder(CarrySystem.HextoBinary(DataMemory.getDataMemory(register.getRegisterPosition("PSW")), 8));
                C.setCharAt(0, '0');
                DataMemory.setDataMemory(register.getRegisterPosition("PSW"), CarrySystem.BinarytoHex(C.toString()));
            }

        }
        else
            throw new WrongException("CLR", true);
    }
}
