package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.InstructionPosition;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class LCALL extends OperatorSuper{
    public LCALL(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "LCALL", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(StepOrRun == ASSEMBLY){
            CodeMemory.updateCodeMemoryBeginPos(11);
            return;
        }

        if(Label.checkIsLabelExist(operand.get(0))){
            if(StepOrRun == STEP)
                Instruction.updateEncoding(11, CarrySystem.BinarytoHex("00010010" + CarrySystem.HextoBinary(Label.getPosition(operand.get(0)).substring(2), 16)));
            else{
                String PC = CarrySystem.HextoBinary(InstructionPosition.getNextPositionByPC(register.getPC()).substring(2), 16);

                register.setRegister(register.getRegisterPosition("SP"), CarrySystem.HexADD(register.getRegister("SP"), "1", false, 2));
                DataMemory.setDataMemory(register.getRegister("SP"), CarrySystem.BinarytoHex(PC.substring(8)));
                register.setRegister(register.getRegisterPosition("SP"), CarrySystem.HexADD(register.getRegister("SP"), "1", false, 2));
                DataMemory.setDataMemory(register.getRegister("SP"), CarrySystem.BinarytoHex(PC.substring(0, 8)));
                register.setPC(Label.getPosition(operand.get(0)));
            }
        }
        else
            throw new WrongException(operand.get(0) + "doesn't exist", false);
    }
}
