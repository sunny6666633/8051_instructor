package Instruction;

import java.util.ArrayList;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.InstructionPosition;
import Memory.CodeMemory;
import Register.register;

public class JZ extends OperatorSuper{
    public JZ(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "JZ", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(StepOrRun == ASSEMBLY){
            CodeMemory.updateCodeMemoryBeginPos(49);
            return;
        }

        if(Label.checkIsLabelExist(operand.get(0))){
            String offset = CarrySystem.HexSUBB(Label.getPosition(operand.get(0)).substring(2), InstructionPosition.getNextPositionByPC(register.getPC()).substring(2),false, 2);

            if(StepOrRun == STEP)
                Instruction.updateEncoding(49, CarrySystem.BinarytoHex("01100000") + offset);
            else{
                if(register.getRegister("ACC").equals("00"))
                    register.setPC("0x" + register.getPC().substring(2, 4) + CarrySystem.HexADD(register.getPC().substring(2), offset, false, 2));
            }
        }
        else
            throw new WrongException(operand.get(0) + "doesn't exist", false);
    }
}
