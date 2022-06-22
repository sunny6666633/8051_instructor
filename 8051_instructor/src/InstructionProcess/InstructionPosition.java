package InstructionProcess;

import Instruction.Instruction;
import Memory.CodeMemory;
import java.util.ArrayList;

public class InstructionPosition {
    private static ArrayList<String> instruction = new ArrayList<String>();
    private static ArrayList<String> position = new ArrayList<String>();
    private static String lastInstructionPosition;

    public static void setInstruction(String instruction){
        InstructionPosition.instruction.add(instruction);
    }

    public static void setPosition(String position) {
        InstructionPosition.position.add(position);
    }

    public static String getInstruction(String position) {
        if(InstructionPosition.position.size() == 0)
            return null;
        for(int i = 0; i < InstructionPosition.position.size(); i++){
            if(InstructionPosition.position.get(i).equals(position))
                return instruction.get(i);
        }
        return null;
    }

    public static String getNextPositionByPC(String PC) {
        if(position.size() == 0)
            return null;
        for(int i = 0; i < position.size(); i++){
            if(position.get(i).equals(PC)) {
                if (i + 1 == position.size()){ //if PC point to the last Instruction return the last position
                    lastInstructionPosition = "0x"+CarrySystem.HexADD(position.get(i).substring(2), Integer.toHexString(Instruction.getBytes(CodeMemory.getLastID())), false, 4);
                    return lastInstructionPosition;
                }
                else
                    return position.get(i+1);
            }
        }
        return null;
    }

    public static void clearInstructionPosition(){
        position.clear();
        instruction.clear();
    }

    public static String getLastInstructionPosition() {
        return lastInstructionPosition;
    }
    public static String getInstruction(int i){
        if(instruction.size() > 0)
            return instruction.get(i);
        return null;
    }

    public static String getPosition(int i) {
        if(position.size() > 0)
            return position.get(i);
        return null;
    }

    public static int getSize(){
        return instruction.size();
    }
}
