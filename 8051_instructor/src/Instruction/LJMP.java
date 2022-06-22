package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import Memory.CodeMemory;
import Register.register;

import java.util.ArrayList;

public class LJMP extends OperatorSuper{
    public LJMP(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "LJMP", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(StepOrRun == ASSEMBLY){
            CodeMemory.updateCodeMemoryBeginPos(2);
            return;
        }

        if(Label.checkIsLabelExist(operand.get(0))){
            if(StepOrRun == STEP)
                Instruction.updateEncoding(2, CarrySystem.BinarytoHex("00000010" + CarrySystem.HextoBinary(Label.getPosition(operand.get(0)).substring(2), 16)));
            else{
                register.setPC(Label.getPosition(operand.get(0)));
            }
        }
        else
            throw new WrongException(operand.get(0) + "doesn't exist", false);
    }
}
