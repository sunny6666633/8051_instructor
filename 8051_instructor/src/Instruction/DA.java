package Instruction;

import java.util.ArrayList;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import Memory.CodeMemory;
import Register.register;

public class DA extends OperatorSuper{
    public DA(ArrayList<String>op, int StepOrRun) throws WrongException{
        super(op, 1, "DA", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("A")){
            if(StepOrRun == ASSEMBLY)
                CodeMemory.updateCodeMemoryBeginPos(104);
            else if(StepOrRun == STEP)
                Instruction.updateEncoding(104, CarrySystem.BinarytoHex("11010100"));
            else{
                if(Integer.parseInt(CarrySystem.directNumbertoBinary("0x"+register.getRegister("ACC")).substring(4), 2) > 9)
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexADD(register.getRegister("ACC"), "6", false, 2));
                else if(Integer.parseInt(CarrySystem.directNumbertoBinary("0x"+register.getRegister("ACC")).substring(0, 4), 2) > 9 || register.getRegister("C").equals("1"))
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexADD(register.getRegister("ACC"), "60", false, 2));
            }
        }
        else
            throw new WrongException("DA", true);
    }
}
