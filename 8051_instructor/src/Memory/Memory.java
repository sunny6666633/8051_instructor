package Memory;

import GUI.InitialPage;
import GUI.TheEditor;

public class Memory {
    private static char[] memoryPosition = new char[16];
    protected static TheEditor theEditor = InitialPage.editor;
    public Memory(){
        int p = 0;
        for(char i = '0'; i <= '9'; i++)
            memoryPosition[p++] = i;
        for(char i = 'A'; i <= 'F'; i++)
            memoryPosition[p++] = i;
    }

    public static char[] getMemoryPosition(){
        return memoryPosition;
    }
}
