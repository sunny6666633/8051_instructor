package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.InstructionPosition;
import Memory.CodeMemory;
import Register.register;

import java.util.ArrayList;

public class JMP extends OperatorSuper{
    public JMP(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "JMP", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("@A+DPTR")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(57);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(57, CarrySystem.BinarytoHex("01110011"));
            else
                register.setPC("0x" + CarrySystem.HexADD(register.getRegister("ACC"), register.getRegister("DPTR"), false, 4));
        }
        else{
            ArrayList<String> op = new ArrayList(operand);
            SJMP sjmp = new SJMP(op, StepOrRun);
        }
    }
}
