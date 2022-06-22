package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.InstructionPosition;
import Memory.CodeMemory;
import Register.register;

import java.util.ArrayList;

public class SJMP extends OperatorSuper{
    public SJMP(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "SJMP", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(StepOrRun == ASSEMBLY){
            CodeMemory.updateCodeMemoryBeginPos(77);
            return;
        }

        if(Label.checkIsLabelExist(operand.get(0))){
            System.out.println(InstructionPosition.getNextPositionByPC(register.getPC()));
            System.out.println(Label.getPosition(operand.get(0)));
            String offset = CarrySystem.HexSUBB(Label.getPosition(operand.get(0)).substring(2), InstructionPosition.getNextPositionByPC(register.getPC()).substring(2),false, 2);

            if(StepOrRun == STEP)
                Instruction.updateEncoding(77, CarrySystem.BinarytoHex("10000000") + offset);
            else
                register.setPC("0x" + register.getPC().substring(2, 4) + CarrySystem.HexADD(register.getPC().substring(2), offset, false, 2));
        }
        else
            throw new WrongException(operand.get(0) + "doesn't exist", false);
    }
}
