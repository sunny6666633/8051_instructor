package Instruction;
import java.util.ArrayList;
import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;
public class SUBB extends OperatorSuper{
    public SUBB(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 2, "SUBB", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException{
        if(operand.get(0).equals("A")){
            if (judgeIsValid.isImmediate(operand.get(1))) {
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(81);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(81, CarrySystem.BinarytoHex("10010100" + CarrySystem.ImmediatetoBinary(operand.get(1), 8)));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexSUBB(register.getRegister("ACC"), CarrySystem.BinarytoHex(CarrySystem.ImmediatetoBinary(operand.get(1), 8)), true, 2));
            }
            else if (judgeIsValid.isIndirectR(operand.get(1))) {
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(82);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(82, CarrySystem.BinarytoHex("1001011" + operand.get(1).charAt(2)));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexSUBB(register.getRegister("ACC"), DataMemory.getDataMemory(register.getRegister(operand.get(1))), true, 2));
            }
            else if(judgeIsValid.isdirectNumber(operand.get(1))){
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(83);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(83, CarrySystem.BinarytoHex("10010101" + CarrySystem.directNumbertoBinary(operand.get(1))));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexSUBB(register.getRegister("ACC"), DataMemory.getDataMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(operand.get(1)))), true, 2));
            }
            else if(judgeIsValid.isdirectR(operand.get(1))){
                if(StepOrRun == ASSEMBLY)
                    CodeMemory.updateCodeMemoryBeginPos(84);
                else if(StepOrRun == STEP)
                    Instruction.updateEncoding(84, CarrySystem.BinarytoHex("10011" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(1).substring(1)))))));
                else
                    register.setRegister(register.getRegisterPosition("ACC"), CarrySystem.HexSUBB(register.getRegister("ACC"), register.getRegister(operand.get(1)), true, 2));
            }
            else{
                throw new WrongException("SUBB", true);
            }
        }
        else
            throw new WrongException("The first operand of SUBB instruction should be A", false);
    }
}
