package GUI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class getJson{
    private static final String url = "https://sunny6666633.github.io/hi/instructionExplain.json";
    private static final String url2 = "https://sunny6666633.github.io/hi/instruction.json";
    private ArrayList<String> instruction = new ArrayList<String>();
    private ArrayList<String> operand = new ArrayList<String>();
    private ArrayList<String> ch = new ArrayList<String>();
    private ArrayList<String> en = new ArrayList<String>();
    private ArrayList<Long> bytes = new ArrayList<Long>();
    private ArrayList<String> operation = new ArrayList<String>();
    private JSONArray arr, arr2;

    public getJson() throws Exception {
        load();
    }

    public void load() throws Exception{
        InputStream is = new URL(getJson.url).openStream();
        InputStream is2 = new URL(getJson.url2).openStream();
        try{
            StringBuilder out = new StringBuilder();
            StringBuilder out2 = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is2,"utf-8"));

            String str = "", str2 = "";
            while((str = br.readLine()) != null){
                out.append(str);
            }
            while((str2 = br2.readLine()) != null){
                out2.append(str2);
            }

            Object ob = new JSONParser().parse(out.toString());
            Object ob2 = new JSONParser().parse(out2.toString());
            arr = (JSONArray) ob;
            arr2 = (JSONArray) ob2;
        }
        finally{
            is.close();
            is2.close();
        }
    }

    public void set(String ins){
        for(int i = 0; i < 107; i++){
            JSONObject json = (JSONObject)arr.get(i);
            JSONObject json2 = (JSONObject)arr2.get(i);
            //System.out.println(ins);
            //System.out.println("/////" + (String)json.get("instruction"));
            if(ins.equals((String)json.get("instruction"))){
                System.out.println("found.");
                instruction.add((String)json.get("instruction"));
                operand.add((String)json.get("operand"));
                ch.add((String)json.get("chinese"));
                en.add((String)json.get("english"));
                bytes.add((Long)json2.get("bytes"));
                operation.add((String)json2.get("operation"));
            }
        }
    }

    public ArrayList<String> getIns(){
        return instruction;
    }

    public ArrayList<String> getOperand(){
        return operand;
    }

    public ArrayList<String> getChinese(){
        return ch;
    }

    public ArrayList<String> getEnglish(){
        return en;
    }

    public ArrayList<Long> getBytes(){
        return bytes;
    }

    public ArrayList<String> getOperation(){
        return operation;
    }
}