package Instruction;

import InstructionProcess.CarrySystem;
import Exception.WrongException;
import InstructionProcess.OperationDescription;
import Memory.CodeMemory;
import Register.register;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class Instruction{
    private static long[] ID, bytes, cycle;
    private static String[] bytes2, Encoding, operation, newEncoding, Mnemonic;
    private static final String Url = "https://sunny6666633.github.io/hi/instruction.json";

    public Instruction() throws Exception{
        ID = new long[111];
        bytes = new long[111];
        cycle = new long[111];
        bytes2 = new String[111];
        newEncoding = new String[111];
        Encoding = new String[111];
        operation = new String[111];
        Mnemonic = new String[111];
        setInstruction();
    }

    public void setAllFunction(JSONArray arr){
        for(int i = 1; i <= 110; i++){
            JSONObject js = (JSONObject)arr.get(i-1);
            ID[i] = (long)js.get("ID");
            bytes[i] = (long) js.get("bytes");
            cycle[i] = (long) js.get("cycle");
            bytes2[i] = (String) js.get("bytes2");
            Encoding[i] = (String) js.get("Encoding");
            newEncoding[i] = (String) js.get("Encoding");
            operation[i] = (String) js.get("operation");
            Mnemonic[i] = (String)js.get("Mnemonic");
        }
    }

    public void setInstruction() throws Exception{
        InputStream is = new URL(Instruction.Url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is,"utf-8")); //避免中文亂碼問題
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            Object ob = new JSONParser().parse(sb.toString());
            JSONArray arr = (JSONArray) ob;
            setAllFunction(arr);
        }
        finally {
            is.close();
        }
    }

    public static void updateEncoding(int id, String newThing){
        int i = 0;
        newEncoding[id] = newThing;
        System.out.println(newEncoding[id]);

        String Pointer = register.getPC();

        while(i < getBytes(id)*2){
            CodeMemory.setcodeMemory(newEncoding[id].substring(i, i+2), Pointer);
            i+=2;
            Pointer = "0x" + CarrySystem.HexADD(Pointer.substring(2), "1", false, 4);
        }

        try{
            OperationDescription.setOperation(id);
        }catch (WrongException e){}
    }

    public static long getID(int i){
        return ID[i];
    }

    public static int getBytes(int i){
        return (int)bytes[i];
    }

    public static String getBytes2(int i) {
        return bytes2[i];
    }

    public static long getCycle(int i){
        return cycle[i];
    }

    public static String getEncoding(int i){
        return Encoding[i];
    }

    public static String getOperation(int i){
        return operation[i];
    }

    public static String getNewEncoding(int i){
        return newEncoding[i];
    }

    public static Set<String> getMnemonic(){
        Set<String> words = new HashSet<>();
        for(String token : Mnemonic) {
            words.add(token);
        }
        return words;
    }
}

