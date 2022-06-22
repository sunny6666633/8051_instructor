package Instruction;
import Exception.WrongException;
import Memory.CodeMemory;
import Register.register;

import java.util.ArrayList;

public class NOP extends OperatorSuper{
    public NOP(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 0, "NOP", StepOrRun);
    }

    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(StepOrRun == ASSEMBLY)
            CodeMemory.updateCodeMemoryBeginPos(110);
        else if(StepOrRun == STEP)
            Instruction.updateEncoding(110, "00");
    }
}
