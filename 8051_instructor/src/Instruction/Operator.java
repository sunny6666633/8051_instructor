package Instruction;
import Exception.WrongException;

import java.util.ArrayList;

public interface Operator {
    //as being the operand of instruction
    final ArrayList<String> operand = new ArrayList<String>();

    public abstract void judgeOperandIsValid(int StepOrRun) throws WrongException;
}
