package Instruction;

import java.util.ArrayList;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.InstructionPosition;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

public class JBC extends OperatorSuper{
    public JBC(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 2, "JBC", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(StepOrRun == ASSEMBLY){
            CodeMemory.updateCodeMemoryBeginPos(9);
            return;
        }

        if(Label.checkIsLabelExist(operand.get(1)) && judgeIsValid.isdirectNumber(operand.get(0))){
            String offset = CarrySystem.HexSUBB(Label.getPosition(operand.get(1)).substring(2), InstructionPosition.getNextPositionByPC(register.getPC()).substring(2),false, 2);

            if(StepOrRun == STEP)
                Instruction.updateEncoding(9, CarrySystem.BinarytoHex("00010000" + CarrySystem.directNumbertoBinary(operand.get(0))) + offset);
            else{
                int Clocation = Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) / 8 + 32;
                int Cbit = 7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) % 8;
                StringBuilder C = new StringBuilder(CarrySystem.HextoBinary(DataMemory.getDataMemory(Integer.toHexString(Clocation)), 8));

                if(C.toString().substring(Cbit, Cbit+1).equals("1")){
                    C.setCharAt(Cbit, '0');
                    DataMemory.setDataMemory(Integer.toHexString(Clocation), CarrySystem.BinarytoHex(C.toString()));

                    register.setPC("0x" + register.getPC().substring(2, 4) + CarrySystem.HexADD(register.getPC().substring(2), offset, false, 2));
                }
            }
        }
        else
            throw new WrongException(operand.get(1) + "doesn't exist", false);
    }
}
