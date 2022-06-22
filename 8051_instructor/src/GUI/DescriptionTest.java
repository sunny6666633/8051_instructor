package GUI;

import Instruction.Instruction;
import InstructionProcess.CarrySystem;
import InstructionProcess.InstructionPosition;
import InstructionProcess.OperationDescription;
import Memory.CodeMemory;
import Exception.WrongException;

public class DescriptionTest{
    private static int count = 0;

    public static String getInstructionValue(){
        if(!checkIsCountValid())
            count = 0;
        return InstructionPosition.getInstruction(count);
    }

    public static String getEncodingValue(){
        if(!checkIsCountValid())
            count = 0;
        return Instruction.getBytes2(CodeMemory.getIDlist(count));
    }

    public static String getCodeMemoryValue(){
        if(!checkIsCountValid())
            count = 0;
        String pos = InstructionPosition.getPosition(count);
        String Value = "";
        int offset = Instruction.getBytes(CodeMemory.getIDlist(count));

        try{
            for(int i = 0; i < offset; i++){
                Value += CarrySystem.HextoBinary(CodeMemory.getcodeMemory(pos), 8);
                if(offset >= 1)
                    Value += " ";
                pos = "0x" + CarrySystem.HexADD(pos.substring(2), "1", false, 4);
            }
        }
        catch (WrongException e){

        }

        return Value;
    }

    public static String getCodeMemoryValueHex(){
        if(!checkIsCountValid())
            count = 0;
        String pos = InstructionPosition.getPosition(count);
        String Value = "";
        int offset = Instruction.getBytes(CodeMemory.getIDlist(count));

        for(int i = 0; i < offset; i++){
            Value += CodeMemory.getcodeMemory(pos);
            if(offset >= 1)
                Value += " ";
            pos = "0x" + CarrySystem.HexADD(pos.substring(2), "1", false, 4);
        }

        return Value;
    }

    public static String getOperationValue(){
        return OperationDescription.getOperationList().get(count++);
    }

    public static Boolean checkIsCountValid(){
        if(count >= InstructionPosition.getSize())
            return false;
        return true;
    }

    public static void setCount(int count) {
        DescriptionTest.count = count;
    }
}
