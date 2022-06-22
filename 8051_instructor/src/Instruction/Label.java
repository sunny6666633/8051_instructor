package Instruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Exception.WrongException;

public class Label {
    private static Map<String, String> labelMap = new HashMap<>();

    public Label(String label, String position) throws WrongException {
        if(checkIsLabelExist(label))
            throw new WrongException("Label cannot be repeated", false);
        else if(Instruction.getMnemonic().contains(label)){
            throw new WrongException(label + "is a keyword", false);
        }
        labelMap.put(label, position);
        System.out.println(label+" "+position);
    }

    public static boolean checkIsLabelExist(String label){
        boolean isFound = true;
        if(labelMap.get(label) == null)
            isFound = false;
        return isFound;
    }

    public static String getPosition(String label){
        return labelMap.get(label);
    }

    public static void clearLabelList(){
        labelMap.clear();
    }
}
