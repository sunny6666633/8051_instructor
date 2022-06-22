package Instruction;

import Exception.WrongException;
import InstructionProcess.CarrySystem;
import InstructionProcess.InstructionPosition;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;

import java.util.ArrayList;

public class CJNE extends OperatorSuper{
    public CJNE(ArrayList<String> op, int StepOrRun) throws WrongException{
        super(op, 3, "CJNE", StepOrRun);
    }

    @Override
    public void judgeOperandIsValid(int StepOrRun) throws WrongException {
        if(StepOrRun == ASSEMBLY){
            CodeMemory.updateCodeMemoryBeginPos(88);
            return;
        }

        if(Label.checkIsLabelExist(operand.get(2))){
            String offset = CarrySystem.HexSUBB(Label.getPosition(operand.get(2)).substring(2), InstructionPosition.getNextPositionByPC(register.getPC()).substring(2),false, 2);

            if(judgeIsValid.isIndirectR(operand.get(0)) && judgeIsValid.isImmediate(operand.get(1))){
                int Rn = Integer.parseInt(DataMemory.getDataMemory(register.getRegister(operand.get(0))), 16);
                int immediate = Integer.parseInt(CarrySystem.ImmediatetoBinary(operand.get(1), 8), 2);

                if(StepOrRun == STEP)
                    Instruction.updateEncoding(88, CarrySystem.BinarytoHex("1101011" + operand.get(0).substring(2) + CarrySystem.ImmediatetoBinary(operand.get(1), 8)) + offset);
                else
                    CJNEInstructionExecute(Rn, immediate, offset);
            }
            else if(operand.get(0).equals("A") && judgeIsValid.isImmediate(operand.get(1))){
                int immediate = Integer.parseInt(CarrySystem.ImmediatetoBinary(operand.get(1), 8), 2);
                int A = Integer.parseInt(register.getRegister("ACC"), 16);

                if(StepOrRun == STEP)
                    Instruction.updateEncoding(89, CarrySystem.BinarytoHex("10110100" + CarrySystem.ImmediatetoBinary(operand.get(1), 8)) + offset);
                else
                    CJNEInstructionExecute(A, immediate, offset);
            }
            else if(operand.get(0).equals("A") && judgeIsValid.isdirectNumber(operand.get(1))){
                int direct = Integer.parseInt(DataMemory.getDataMemory(CarrySystem.directNumbertoBinary(operand.get(1))), 16);
                int A = Integer.parseInt(register.getRegister("ACC"), 16);

                if(StepOrRun == STEP)
                    Instruction.updateEncoding(90, CarrySystem.BinarytoHex("10110101" + CarrySystem.directNumbertoBinary(operand.get(1))) + offset);
                else
                    CJNEInstructionExecute(A, direct, offset);
            }
            else if(judgeIsValid.isdirectR(operand.get(0)) && judgeIsValid.isImmediate(operand.get(1))){
                int immediate = Integer.parseInt(CarrySystem.ImmediatetoBinary(operand.get(1), 8), 2);
                int Rn = Integer.parseInt(register.getRegister(operand.get(0)), 16);

                if(StepOrRun == STEP)
                    Instruction.updateEncoding(91, CarrySystem.BinarytoHex("10111" + String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(operand.get(0).substring(1))))) + CarrySystem.ImmediatetoBinary(operand.get(1), 8)) + offset);
                else
                    CJNEInstructionExecute(Rn, immediate, offset);
            }
            else
                throw new WrongException("CJNE", true);
        }
        else
            throw new WrongException(operand.get(2) + "doesn't exist", false);
    }

    public static void CJNEInstructionExecute(int op1, int op2, String offset) throws WrongException{
        if(op1 != op2)
            register.setPC("0x" + register.getPC().substring(2, 4) + CarrySystem.HexADD(register.getPC().substring(2), offset, false, 2));
        if(op1 < op2)
            register.setRegister(register.getRegisterPosition("PSW"), CarrySystem.BinarytoHex("1" + CarrySystem.HextoBinary(register.getRegister("PSW"), 8).substring(1)));
        else
            register.setRegister(register.getRegisterPosition("PSW"), CarrySystem.BinarytoHex("0" + CarrySystem.HextoBinary(register.getRegister("PSW"), 8).substring(1)));
    }
}
