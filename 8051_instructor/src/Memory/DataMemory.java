package Memory;
import GUI.TheEditor;

import java.util.Map;
import java.util.HashMap;
public class DataMemory extends Memory{
    private static Map<String, String> dataMemory = new HashMap<>();
    public DataMemory(){
        super();
        for(char i : getMemoryPosition()){
            for(char j : getMemoryPosition())
                dataMemory.put("0x"+Character.toString(i).concat(Character.toString(j)), "00");
        }
        dataMemory.replace("0x81", "07"); //SP initial value is 0x07
    }

    public static String getDataMemory(String position){
        if(!position.startsWith("0x"))
            position = "0x".concat(position);

        return dataMemory.get(position.substring(0, 2)+position.substring(2).toUpperCase());
    }

    public static void setDataMemory(String position, String value){
        if(!position.startsWith("0x"))
            position = "0x".concat(position);

        dataMemory.replace(position.substring(0, 2)+position.substring(2).toUpperCase(), value);
        System.out.println("datamemory: " + position.substring(0, 2)+position.substring(2).toUpperCase() + " " + getDataMemory(position));
    }
}
