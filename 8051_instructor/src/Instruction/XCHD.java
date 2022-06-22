package Instruction;

import java.util.ArrayList;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

public class XCHD extends OperatorSuper {
    public XCHD(ArrayList<String> op, int StepOrRun) throws WrongException {
        super(op, 2, "XCHD", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("A") && judgeIsValid.isIndirectR(operand.get(1))){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(100);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(100, CarrySystem.BinarytoHex("1101011" + operand.get(1).substring(2)));
            else{
                String a = CarrySystem.HextoBinary(register.getRegister("ACC"), 8).substring(4);
                String b = CarrySystem.HextoBinary(DataMemory.getDataMemory(register.getRegister(operand.get(1))), 8).substring(4);
                String temp = a;
                a = b;
                b = temp;
                register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.BinarytoHex(CarrySystem.HextoBinary(register.getRegister("ACC"), 8).substring(0, 4) + a));
                DataMemory.setDataMemory(register.getRegister(operand.get(1)), CarrySystem.BinarytoHex(CarrySystem.HextoBinary(DataMemory.getDataMemory(register.getRegister(operand.get(1))), 8).substring(0, 4) + b));
            }
        }
        else
            throw new WrongException("XCHD", true);
    }
}
