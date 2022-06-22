package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.InstructionPosition;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class ACALL extends OperatorSuper{
    public ACALL(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "ACALL", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(StepOrRun == ASSEMBLY){
            CodeMemory.updateCodeMemoryBeginPos(10);
            return;
        }

        if(Label.checkIsLabelExist(operand.get(0))){
           if(StepOrRun == STEP){
               String poistion = CarrySystem.HextoBinary(Label.getPosition(operand.get(0)).substring(2), 11);
               Instruction.updateEncoding(10, CarrySystem.BinarytoHex(poistion.substring(0, 3) + "10001" + poistion.substring(3)));
           }
           else{
                String PC = CarrySystem.HextoBinary(register.getPC().substring(2), 16);

                register.setRegister(register.getRegisterPosition("SP"), CarrySystem.HexADD(register.getRegister("SP"), "1", false, 2));
                DataMemory.setDataMemory(register.getRegister("SP"), CarrySystem.BinarytoHex(PC.substring(8)));
                register.setRegister(register.getRegisterPosition("SP"), CarrySystem.HexADD(register.getRegister("SP"), "1", false, 2));
                DataMemory.setDataMemory(register.getRegister("SP"), CarrySystem.BinarytoHex(PC.substring(0, 8)));
                register.setPC(Label.getPosition(operand.get(0)));
           }
        }
        else
            throw new WrongException(operand.get(0) + " doesn't exist", false);
    }
}
