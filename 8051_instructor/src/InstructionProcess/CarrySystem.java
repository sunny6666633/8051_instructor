package InstructionProcess;
import Exception.WrongException;
import Instruction.Label;
import Register.register;

import java.util.Arrays;

public class CarrySystem {

    public static String complement(String opBinary){
        if(opBinary.equals("0"))
            return "1";
        else
            return "0";
    }
    public static String DecimaltoHex(String decimal, int len){
        String string = Integer.toHexString(Integer.parseInt(decimal));
        if(string.length() < len)
            string = "0".repeat(len - string.length()).concat(string);
        else
            string = string.substring(string.length()-len);
        return string.toUpperCase();
    }

    public static String DecimaltoBinary(String decimal, int len){
        String string = Integer.toBinaryString(Integer.parseInt(decimal));
        if(string.length() < len)
            string = "0".repeat(len-string.length()).concat(string);
        else
            string = string.substring(string.length()-len);
        return string;
    }

    public static String BinarytoHex(String binary){
        String string = Integer.toHexString(Integer.parseInt(binary, 2));
        if(string.length() % 2 != 0)
            string = "0" + string;
        return string.toUpperCase();
    }

    public static String BinarytoHex(String binary, int len){
        String string = Integer.toHexString(Integer.parseInt(binary, 2));
        if(string.length() < len)
            string = "0".repeat(len-string.length()).concat(string);
        else
            string = string.substring(string.length()-len);
        return string.toUpperCase();
    }

    public static String HextoBinary(String hex, int len) throws WrongException{
        try {
            String string = Integer.toBinaryString(Integer.parseInt(hex, 16));
            if(string.length() < len)
                string = "0".repeat(len-string.length()).concat(string);
            else
                string = string.substring(string.length() - len);
            return string;
        }catch (Exception e){
            throw new WrongException("The number is too large", false);
        }
    }

    public static String HexADD(String op1, String op2, Boolean isADDC, int length){
        int addend = Integer.parseInt(op1, 16);
        int augend = Integer.parseInt(op2, 16);
        if(isADDC)
            addend += Integer.parseInt(register.getRegister("C"), 16);

        return DecimaltoHex(Integer.toString(addend+augend), length);
    }

    public static String HexSUBB(String op1, String op2, Boolean isSUBB, int length){
        int minuend = Integer.parseInt(op1, 16);
        int subtrahend  = Integer.parseInt(op2, 16);
        if(isSUBB)
            minuend -= Integer.parseInt(register.getRegister("C"), 16);
        return DecimaltoHex(Integer.toString(minuend-subtrahend), length);
    }
    public static String HexMUL(String op1, String op2){
        int multiplicand = Integer.parseInt(op1, 16);
        int multiplier = Integer.parseInt(op2, 16);
        return DecimaltoHex(Integer.toString(multiplicand * multiplier), 4);
    }

    public static String HexDIV(String op1, String op2){
        int dividend = Integer.parseInt(op1, 16);
        int divisor = Integer.parseInt(op2, 16);
        return DecimaltoHex(Integer.toString(dividend / divisor), 2) + DecimaltoHex(Integer.toString(dividend % divisor), 2);
    }

    public static String ImmediatetoBinary(String operand, int len)throws WrongException{
        if(operand.matches("(#([0-9a-fA-F]+)H)")){
            return HextoBinary(operand.substring(1, operand.length()-1), len);
        }
        else if(operand.matches("(#0[xX]([0-9A-F]+))")){
            return HextoBinary(operand.substring(3), len);
        }
        else if(operand.matches("#([0-1]+)[B]")){
            if(operand.substring(1, operand.length()-1).length() < len)
                operand = String.format("%0" + len + "d", Integer.parseInt(operand.substring(1, operand.length()-1)));
            else
                operand = operand.substring(operand.length()-len-1, operand.length()-1);
            System.out.println(operand);
            return operand;
        }
        else if(operand.matches("#([-]{0,1}[0-9]+)")){
            return DecimaltoBinary(operand.substring(1), len);
        }
        else if(Label.checkIsLabelExist(operand.substring(1))){
            return HextoBinary(Label.getPosition(operand.substring(1)).substring(2), len);
        }
        return "Wrong";
    }

    public static String directNumbertoBinary(String operand, int len)throws WrongException{
        if(operand.matches("(([0-9a-fA-F]+)H)")){
            return HextoBinary(operand.substring(0, operand.length()-1), len);
        }
        else if(operand.matches("(0[xX]([0-9A-F]+))")){
            return HextoBinary(operand.substring(2), len);
        }
        else if(operand.matches("([0-1]+)[B]")){
            if(operand.substring(0, operand.length()-1).length() < len)
                operand = String.format("%0" + len + "d", Integer.parseInt(operand.substring(0, operand.length()-1)));
            else
                operand = operand.substring(operand.length()-len-1, operand.length()-1);
            return operand;
        }
        else if(operand.matches("([-]{0,1}[0-9]+)")){
            return DecimaltoBinary(operand, len);
        }
        else if(Label.checkIsLabelExist(operand)){
            return HextoBinary(Label.getPosition(operand).substring(2), len);
        }
        else if(!Label.checkIsLabelExist(operand))
            return HextoBinary(register.getRegisterPosition(operand).substring(2), len);
        return "";
    }

    public static String directNumbertoBinary(String operand)throws WrongException{
        return directNumbertoBinary(operand, 8);
    }

    public static String AND(String op1Binary, String op2Binary, int length){
        System.out.println(op1Binary+" "+op2Binary);
        char[] andOutPut = new char[length];
        for(int i = 0; i < length; i++)
            andOutPut[i] = (char)((int)(op1Binary.charAt(i)) & (int)(op2Binary.charAt(i)));

        return String.valueOf(andOutPut);
    }

    public static String OR(String op1Binary, String op2Binary, int length){
        char[] orOutPut = new char[length];
        for(int i = 0; i < length; i++)
            orOutPut[i] = (char) ((int) (op1Binary.charAt(i)) | (int) (op2Binary.charAt(i)));
        return String.valueOf(orOutPut);
    }

    public static String XOR(String op1Binary, String op2Binary, int length){
        char[] xorOutPut = new char[length];
        for(int i = 0; i < length; i++)
            xorOutPut[i] = (char) ((int) (op1Binary.charAt(i)) | (int) (op2Binary.charAt(i)));

        return String.valueOf(xorOutPut);
    }
}
