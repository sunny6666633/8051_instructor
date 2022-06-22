package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import Memory.CodeMemory;
import Register.register;

import java.util.ArrayList;

public class RRC extends OperatorSuper{
    public RRC(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "RRC", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(12);
            if(StepOrRun == STEP)
                Instruction.updateEncoding(12, CarrySystem.BinarytoHex("00010011"));
            else{
                String ACCbinary = CarrySystem.HextoBinary(register.getRegister("ACC"), 8);
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(register.getRegister("C") + ACCbinary.substring(0, 7)));
                register.setRegister(register.getRegisterPosition("PSW"), CarrySystem.BinarytoHex(ACCbinary.substring(7) + CarrySystem.HextoBinary(register.getRegister("PSW"), 7)));
            }
        }
        else
            throw new WrongException("The operand of RRC should be A", false);
    }
}
