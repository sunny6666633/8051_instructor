package Instruction;

import java.util.ArrayList;
import Exception.WrongException;

public class OperatorSuper implements Operator{
    protected final int STEP = 1;
    protected final int ASSEMBLY = 0;

    public OperatorSuper(ArrayList<String> op, int size, String OperatorName, int StepOrRun) throws WrongException {
        operand.clear();
        if(op.size() > size){
            throw new WrongException(OperatorName + "instruction can not be over" + size + "operand!", false);
        }
        else{
            if(size > 0){
                for(String ele : op)
                    operand.add(ele.toUpperCase());
            }
            judgeOperandIsValid(StepOrRun);
        }
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
    }

    public static ArrayList<String> getOperand() {
        return operand;
    }
}
