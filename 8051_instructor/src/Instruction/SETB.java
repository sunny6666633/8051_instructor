package Instruction;
import Exception.WrongException;
import InstructionProcess.*;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class SETB extends OperatorSuper{
    public SETB(ArrayList<String> op, int StepOrRun) throws WrongException {
        super(op, 1, "SETB", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(judgeIsValid.isdirectNumber(operand.get(0))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(102);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(102, CarrySystem.BinarytoHex("11010010" + CarrySystem.directNumbertoBinary(operand.get(0))));
            else{
                int Clocation = Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) / 8 + 32;
                int Cbit = 7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) % 8;

                StringBuilder C = new StringBuilder(CarrySystem.HextoBinary(DataMemory.getDataMemory(Integer.toHexString(Clocation)), 8));
                C.setCharAt(Cbit, '1');
                DataMemory.setDataMemory(Integer.toHexString(Clocation), CarrySystem.BinarytoHex(C.toString()));
            }
        }
        else if(operand.get(0).equals("C")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(103);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(103, CarrySystem.BinarytoHex("11010011"));
            else{
                StringBuilder C = new StringBuilder(CarrySystem.HextoBinary(DataMemory.getDataMemory(register.getRegisterPosition("PSW")), 8));;
                C.setCharAt(0, '1');
                DataMemory.setDataMemory(register.getRegisterPosition("PSW"), CarrySystem.BinarytoHex(C.toString()));
            }

        }
        else
            throw new WrongException("SETB", true);
    }
}
