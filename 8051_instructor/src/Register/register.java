package Register;

import GUI.TheEditor;
import InstructionProcess.InstructionPosition;
import Memory.DataMemory;

public class register{
    private static TheEditor theEditor = new TheEditor();
    private static String R0Pos = "0x00";
    private static String R0 = DataMemory.getDataMemory(R0Pos);

    private static String R1Pos = "0x01";
    private static String R1 = DataMemory.getDataMemory(R1Pos);

    private static String R2Pos = "0x02";
    private static String R2 = DataMemory.getDataMemory(R2Pos);

    private static String R3Pos = "0x03";
    private static String R3 = DataMemory.getDataMemory(R3Pos);

    private static String R4Pos = "0x04";
    private static String R4 = DataMemory.getDataMemory(R4Pos);

    private static String R5Pos = "0x05";
    private static String R5 = DataMemory.getDataMemory(R5Pos);

    private static String R6Pos = "0x06";
    private static String R6 = DataMemory.getDataMemory(R6Pos);

    private static String R7Pos = "0x07";
    private static String R7 = DataMemory.getDataMemory(R7Pos);

    private static String P0Pos = "0x80";
    private static String P0 = DataMemory.getDataMemory(P0Pos);

    private static String P1Pos = "0x90";
    private static String P1 = DataMemory.getDataMemory(P1Pos);

    private static String P2Pos = "0xA0";
    private static String P2 = DataMemory.getDataMemory(P2Pos);

    private static String P3Pos = "0xB0";
    private static String P3 = DataMemory.getDataMemory(P3Pos);

    private static String ACCPos = "0xE0";
    private static String ACC = DataMemory.getDataMemory(ACCPos);

    private static String BPos = "0xF0";
    private static String B = DataMemory.getDataMemory(BPos);

    private static String DPTR = "0000";

    private static String SPPos = "0x81";
    private static String SP = DataMemory.getDataMemory(SPPos);

    private static String PSWPos = "0xD0";
    private static String C = Integer.toBinaryString(Integer.parseInt(DataMemory.getDataMemory(PSWPos), 16)).substring(0, 1);
    private static String PSW = DataMemory.getDataMemory(PSWPos);
    private static String PC = "0x0000";

    public static void setRegister(String pos, String value){
        if(pos.equals("DPTR")){
            DPTR = value;
        }
        else{
            DataMemory.setDataMemory(pos, value);
        }
    }
    public static String getRegisterPosition(String reg){
        switch (reg){
            case "R0":
                return R0Pos;
            case "R1":
                return R1Pos;
            case "R2":
                return R2Pos;
            case "R3":
                return R3Pos;
            case "R4":
                return R4Pos;
            case "R5":
                return R5Pos;
            case "R6":
                return R6Pos;
            case "R7":
                return R7Pos;
            case "P0":
                return P0Pos;
            case "P1":
                return P1Pos;
            case "P2":
                return P2Pos;
            case "P3":
                return P3Pos;
            case "ACC":
                return ACCPos;
            case "B":
                return BPos;
            case "SP":
                return SPPos;
            case "PSW":
                return PSWPos;
            case "DPTR":
                return "DPTR";
            default:
                break;
        }
        return "Wrong register position";
    }
    public static String getRegister(String reg){
        switch (reg){
            case "@R0":
                R0 = DataMemory.getDataMemory(R0Pos);
                return R0;
            case "@R1":
                R1 = DataMemory.getDataMemory(R1Pos);
                return R1;
            case "R0":
                R0 = DataMemory.getDataMemory(R0Pos);
                return R0;
            case "R1":
                R1 = DataMemory.getDataMemory(R1Pos);
                return R1;
            case "R2":
                R2 = DataMemory.getDataMemory(R2Pos);
                return R2;
            case "R3":
                R3 = DataMemory.getDataMemory(R3Pos);
                return R3;
            case "R4":
                R4 = DataMemory.getDataMemory(R4Pos);
                return R4;
            case "R5":
                R5 = DataMemory.getDataMemory(R5Pos);
                return R5;
            case "R6":
                R6 = DataMemory.getDataMemory(R6Pos);
                return R6;
            case "R7":
                R7 = DataMemory.getDataMemory(R7Pos);
                return R7;
            case "P0":
                P0 = DataMemory.getDataMemory(P0Pos);
                return P0;
            case "P1":
                P1 = DataMemory.getDataMemory(P1Pos);
                return P1;
            case "P2":
                P2 = DataMemory.getDataMemory(P2Pos);
                return P2;
            case "P3":
                P3 = DataMemory.getDataMemory(P3Pos);
                return P3;
            case "ACC":
                ACC = DataMemory.getDataMemory(ACCPos);
                return ACC;
            case "B":
                B = DataMemory.getDataMemory(BPos);
                return B;
            case "SP":
                SP = DataMemory.getDataMemory(SPPos);
                return SP;
            case "C":
                C = Integer.toBinaryString(Integer.parseInt(DataMemory.getDataMemory(PSWPos), 16)).substring(0, 1);
                return C;
            case "PSW":
                PSW = DataMemory.getDataMemory(PSWPos);
                return PSW;
            case "DPTR":
                return DPTR;
            default:
                break;
        }
        return "Wrong register";
    }

    public static void setNextPC(){
        PC = InstructionPosition.getNextPositionByPC(PC);
    }

    public static void setPC(String PC){
        register.PC = PC;
    }

    public static String getPC() {
        if(PC == null)
            return null;
        return PC;
    }
}
