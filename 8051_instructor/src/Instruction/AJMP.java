package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.InstructionPosition;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class AJMP extends OperatorSuper{
    public AJMP(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "AJMP", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(StepOrRun == ASSEMBLY){
            CodeMemory.updateCodeMemoryBeginPos(1);
            return;
        }

        if(Label.checkIsLabelExist(operand.get(0))){
            String poistion = CarrySystem.HextoBinary(Label.getPosition(operand.get(0)).substring(2), 11);
            if(StepOrRun == STEP)
                Instruction.updateEncoding(1, CarrySystem.BinarytoHex(poistion.substring(0, 3) + "00001" + poistion.substring(3)));
            else{
                poistion = CarrySystem.HextoBinary(register.getPC().substring(2), 16).substring(0, 5) + poistion;
                register.setPC("0x".concat(CarrySystem.BinarytoHex(poistion, 4)));
            }
        }
        else
            throw new WrongException(operand.get(0) + "doesn't exist", false);
    }
}
