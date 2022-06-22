package Instruction;

import java.util.ArrayList;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

public class XCH extends OperatorSuper{
    public XCH(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 2, "XCH", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(operand.get(0).equals("A")){
            String temp = register.getRegister("ACC");
            String b = temp, a;

            if(judgeIsValid.isIndirectR(operand.get(1))){
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(97);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(97, CarrySystem.BinarytoHex("1100011" + operand.get(1).substring(2)));
                else{
                    a = DataMemory.getDataMemory(register.getRegister(operand.get(1)));

                    register.setRegister(register.getRegisterPosition("ACC"), a);
                    DataMemory.setDataMemory(register.getRegister(operand.get(1)), b);
                }
            }
            else if(judgeIsValid.isdirectNumber(operand.get(1))){
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(98);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(98, CarrySystem.BinarytoHex("11000101"+CarrySystem.directNumbertoBinary(operand.get(1))));
                else{
                    a = DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(1))));

                    register.setRegister(register.getRegisterPosition("ACC"), a);
                    DataMemory.setDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(1))), b);
                }
            }
            else if(judgeIsValid.isdirectR(operand.get(1))){
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(99);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(99, CarrySystem.BinarytoHex("11001" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(1).substring(1)))))));
                else{
                    a = register.getRegister(operand.get(1));

                    register.setRegister(register.getRegisterPosition("ACC"), a);
                    register.setRegister(register.getRegisterPosition(operand.get(1)), b);
                }
            }
            else
                throw new WrongException("XCH", true);
        }
        else
            throw new WrongException("The first operand of XCH instruction should be A", false);
    }
}
