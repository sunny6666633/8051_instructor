package Instruction;
import java.util.ArrayList;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;
public class ADD extends OperatorSuper{
    public ADD(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 2, "ADD", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException{
        if(operand.get(0).equals("A")){
            if (judgeIsValid.isImmediate(operand.get(1))) {
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(20);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(20, CarrySystem.BinarytoHex("00100100" + CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexADD(register.getRegister("ACC"), CarrySystem.BinarytoHex(CarrySystem.ImmediatetoBinary(operand.get(1), 8)), false, 2));
            }
            else if (judgeIsValid.isIndirectR(operand.get(1))) {
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(21);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(21, CarrySystem.BinarytoHex("0010011" + operand.get(1).charAt(2)));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexADD(register.getRegister("ACC"), DataMemory.getDataMemory(register.getRegister(operand.get(1))), false, 2));
            }
            else if(judgeIsValid.isdirectNumber(operand.get(1))) {
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(22);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(22, CarrySystem.BinarytoHex("00100101" + CarrySystem.directNumbertoBinary(operand.get(1))));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexADD(register.getRegister("ACC"), DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(1)))), false, 2));
            }
            else if(judgeIsValid.isdirectR(operand.get(1))){
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(23);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(23, CarrySystem.BinarytoHex("00101" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(1).substring(1)))))));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexADD(register.getRegister("ACC"), register.getRegister(operand.get(1)), false, 2));
            }
            else{
                throw new WrongException("ADD", true);
            }
        }
        else{
            throw new WrongException("The first operand of ADD instruction must be A", false);
        }
    }
}
