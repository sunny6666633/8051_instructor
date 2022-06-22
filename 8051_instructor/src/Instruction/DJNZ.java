package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.InstructionPosition;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class DJNZ extends OperatorSuper{
    public DJNZ(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 2, "DJNZ", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(StepOrRun == ASSEMBLY){
            if(judgeIsValid.isdirectNumber(operand.get(0)))
                CodeMemory.updateCodeMemoryBeginPos(105);
            else if(judgeIsValid.isdirectR(operand.get(0)))
                CodeMemory.updateCodeMemoryBeginPos(106);
            return;
        }

        if(Label.checkIsLabelExist(operand.get(1))){
            String offset = CarrySystem.HexSUBB(Label.getPosition(operand.get(1)).substring(2), InstructionPosition.getNextPositionByPC(register.getPC()).substring(2),false, 2);

            if(judgeIsValid.isdirectNumber(operand.get(0))){
                int direct = Integer.parseInt(DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0)))), 16);

                if(StepOrRun == STEP)
                    Instruction.updateEncoding(105, CarrySystem.BinarytoHex("11010101" + CarrySystem.directNumbertoBinary(operand.get(0))) + offset);
                else
                    DJNZInstructionExecute(direct, offset, 1);
            }
            else if(judgeIsValid.isdirectR(operand.get(0))){
                int Rn = Integer.parseInt(register.getRegister(operand.get(0)), 16);

                if(StepOrRun == STEP)
                    Instruction.updateEncoding(106, CarrySystem.BinarytoHex("11011" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(0).substring(1)))))) + offset);
                else
                    DJNZInstructionExecute(Rn, offset, 2);
            }
            else
                throw new WrongException("DJNZ", true);
        }
        else
            throw new WrongException(operand.get(1) + "doesn't exist", false);
    }

    public static void DJNZInstructionExecute(int op1, String offset, int oper) throws WrongException{
        if(oper == 1)
            DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0))), CarrySystem.HexADD(DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(0)))), "-1", false, 2));
        else
            register.setRegister(register.getRegisterPosition(operand.get(0)), CarrySystem.HexADD(register.getRegister(operand.get(0)), "-1", false, 2));
        if(op1 != 0)
            register.setPC("0x" + register.getPC().substring(2, 4) + CarrySystem.HexADD(register.getPC().substring(2), offset, false, 2));
    }
}
