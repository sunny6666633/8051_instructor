package Instruction;
import java.util.ArrayList;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.judgeIsValid;
import Memory.*;
import Register.register;

public class ADDC extends OperatorSuper{
    public ADDC(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 2, "ADDC", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException{
        if(operand.get(0).equals("A")){
            if (judgeIsValid.isImmediate(operand.get(1))) {
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(27);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(27, CarrySystem.BinarytoHex("00110100" + CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexADD(register.getRegister("ACC"), CarrySystem.BinarytoHex(CarrySystem.ImmediatetoBinary(operand.get(1), 8)), true, 2));
            }
            else if (judgeIsValid.isIndirectR(operand.get(1))) {
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(28);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(28, CarrySystem.BinarytoHex("0011011" + operand.get(1).charAt(2)));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexADD(register.getRegister("ACC"), DataMemory.getDataMemory(register.getRegister(operand.get(1))),true, 2));
            }
            else if(judgeIsValid.isdirectNumber(operand.get(1))){
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(29);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(29, CarrySystem.BinarytoHex("00110101" + CarrySystem.directNumbertoBinary(operand.get(1))));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexADD(register.getRegister("ACC"), DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(1)))),true, 2));
            }
            else if(judgeIsValid.isdirectR(operand.get(1))){
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(30);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(30, CarrySystem.BinarytoHex("00111" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(1).substring(1)))))));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexADD(register.getRegister("ACC"), register.getRegister(operand.get(1)), true, 2));
            }
            else{
                throw new WrongException("ADDC", true);
            }
        }
        else{
            throw new WrongException("The first operand of ADDC instruction must be A", false);
        }
    }
}
