package InstructionProcess;

public class judgeIsValid {
    public static boolean isIndirectR(String operand) {
        return operand.matches("@R[0-1]");
    }

    public static boolean isdirectR(String operand) {
        return operand.matches("R[0-7]");
    }
    public static boolean isImmediate(String operand) {
        if(operand.substring(0, 1).equals("#") && (operand.charAt(1) < '0' && operand.charAt(1) > '9') && !operand.endsWith("H"))
            return true;
        return operand.matches("#((0[xX][0-9a-fA-F]+)|([0-1]+[bB])|([0-9]([a-fA-F0-9]*)[Hh])|([-]{0,1}[0-9]+)|(0[a-fA-F]([a-fA-F0-9]*)[Hh]))");
    }
    public static boolean isdirectNumber(String operand) {
        if((operand.charAt(0) < '0' && operand.charAt(0) > '9') && !operand.endsWith("H"))
            return true;
        return operand.matches("(0[xX][0-9a-fA-F]+)|([0-1]+[bB])|(([0-9]+)[Hh])|([pP][0-3])|([-]{0,1}[0-9]+)|(0([a-fA-f]+)[Hh])|(SP)|(B)|(ACC)");
    }
}
