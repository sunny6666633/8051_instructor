package Memory;
import GUI.TheEditor;
import Instruction.Instruction;
import InstructionProcess.CarrySystem;
import InstructionProcess.InstructionPosition;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class CodeMemory extends Memory{
    private static Map<String, String> codeMemory = new HashMap<>();
    private static String CodeMemoryBeginPos;//point to the beginning of each Instruction
    private static int lastID; //as the ID of the last instruction
    private static ArrayList<Integer> IDlist = new ArrayList<Integer>();
    public CodeMemory() {
        super();
        for (char k : getMemoryPosition()){
            for (char r : getMemoryPosition())
                codeMemory.put("0x" + "00".concat(Character.toString(k)).concat(Character.toString(r)), "00");
        }
        setCodeMemoryBeginPos("0x0000");
    }

    public static String getcodeMemory(String position){
        if(!position.startsWith("0x")) //fix position let it be correct
            position = "0x".concat(position);
        return codeMemory.get(position);
    }

    public static void setcodeMemory(String value, String pointer){
        codeMemory.replace(pointer, value);
        System.out.println("codememory: " + pointer + " " + getcodeMemory(pointer));
    }

    public static void updateCodeMemoryBeginPos(int id){
        lastID = id;
        IDlist.add(id);
        int idBytes = (int)Instruction.getBytes(id);

        InstructionPosition.setPosition(CodeMemory.getCodeMemoryBeginPos());
        setCodeMemoryBeginPos("0x"+CarrySystem.HexADD(getCodeMemoryBeginPos().substring(2), Integer.toString(idBytes), false, 4));
    }
    public static String getCodeMemoryBeginPos() {
        return CodeMemoryBeginPos;
    }

    public static void setCodeMemoryBeginPos(String codeMemoryBeginPos) {
        CodeMemoryBeginPos = codeMemoryBeginPos;
    }

    public static int getLastID() {
        System.out.println(lastID);
        return lastID;
    }

    public static int getIDlist(int i) {
        return IDlist.get(i);
    }
}
