package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class RET extends OperatorSuper{
    public RET(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 0, "RET", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(StepOrRun == ASSEMBLY)
            CodeMemory.updateCodeMemoryBeginPos(18);
        else if(StepOrRun == STEP)
            Instruction.updateEncoding(18, CarrySystem.BinarytoHex("00100010"));
        else{
            String PC = DataMemory.getDataMemory(register.getRegister("SP"));
            register.setRegister(register.getRegisterPosition("SP"), CarrySystem.HexADD(register.getRegister("SP"), "-1", false, 2));
            PC = "0x" + PC + DataMemory.getDataMemory(register.getRegister("SP"));
            register.setRegister(register.getRegisterPosition("SP"), CarrySystem.HexADD(register.getRegister("SP"), "-1", false, 2));
            if(register.getRegister("SP").equals("05"))
                return;
            else
                register.setPC(PC);
        }
    }
}
