package Instruction;

import java.util.ArrayList;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import Memory.CodeMemory;
import Register.register;

public class DIV extends OperatorSuper{
    public DIV(ArrayList<String>op, int StepOrRun) throws WrongException {
        super(op, 1, "DIV", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("AB")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(80);
            if(StepOrRun == STEP)
                Instruction.updateEncoding(80, CarrySystem.BinarytoHex("10000100"));
            else{
                String Div = CarrySystem.HexDIV(register.getRegister("ACC"), register.getRegister("B"));
                register.setRegister(register.getRegisterPosition("ACC"), Div.substring(0, 2));
                register.setRegister(register.getRegisterPosition("B"), Div.substring(2));
            }
        }
        else
            throw new WrongException("The operand of DIV should be AB", false);
    }
}
