package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import Memory.CodeMemory;
import Register.register;

import java.util.ArrayList;

public class RLC extends OperatorSuper{
    public RLC(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 1, "RLC", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(26);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(26, CarrySystem.BinarytoHex("00110011"));
            else{
                String ACCbinary = CarrySystem.HextoBinary(register.getRegister("ACC"), 8);
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(ACCbinary.substring(1, 8) + register.getRegister("C")));
                register.setRegister(register.getRegisterPosition("PSW"), CarrySystem.BinarytoHex(ACCbinary.substring(0, 1) + CarrySystem.HextoBinary(register.getRegister("PSW"), 7)));
            }
        }
        else
            throw new WrongException("The operand of RLC should be A", false);
    }
}
