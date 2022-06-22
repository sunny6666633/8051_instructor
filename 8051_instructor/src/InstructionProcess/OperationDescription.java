package InstructionProcess;

import Instruction.Instruction;
import Instruction.Label;
import Instruction.OperatorSuper;
import java.util.ArrayList;
import Exception.WrongException;
public class OperationDescription {
    private static ArrayList<String> operand;
    private static ArrayList<String> operationList = new ArrayList<String>();

    public static void setOperand() {
        OperationDescription.operand = OperatorSuper.getOperand();
    }

    public static void setOperation(int id) throws WrongException {
        String operation = Instruction.getOperation(id);
        setOperand();
        switch (id){
            case 1:
                operation = "PC = PC + 2\nPC(Binary bit 10-0) = " + Label.getPosition(operand.get(0)) + "(Label: " + operand.get(0) + "'s position)(Binary bit 10-0)";
                break;
            case 2:
                operation = "PC = " + Label.getPosition(operand.get(0));
                break;
            case 4:
                operation = "The content of dataMemory at the content of register " + operand.get(0).substring(1) + " = The content of dataMemory at the content of register " + operand.get(0).substring(1) + " + 1";
                break;
            case 6:
                operation = "The content of dataMemory at " + operand.get(0) + " = The content of dataMemory at " + operand.get(0) + " + 1";
                break;
            case 8:
                operation = "The content of register " + operand.get(0) + " = The content of register " + operand.get(0) + " + 1";
                break;
            case 9, 17, 24:
                String Clocation = Integer.toString(Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) / 8 + 32);
                String Cbit = Integer.toString(7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) % 8);

                operation = "offset = current PC - Label: " + operand.get(1) + "'s position\n(bit) = The " + Cbit + " bit of the content of dataMemory at 0x" + Clocation + "\n" + operation;
                break;
            case 10:
                operation = "PC = PC + 2\nSP = SP + 1\nThe content of dataMemory at the content of SP = PC(Binary bit 7-0)\nSP = SP + 1\nThe content of dataMemory at the content of SP = PC(Binary bit 15-8)\nPC(Binary bit 10-0) = "+ Label.getPosition(operand.get(0)) + " (Binary bit 10-0)";
                break;
            case 11:
                operation = "PC = PC + 3\nSP = SP + 1\nThe content of dataMemory at the content of SP = PC(Binary bit 7-0)\nSP = SP + 1\nThe content of dataMemory at the content of SP = PC(Binary bit 15-8)\nPC = " + Label.getPosition(operand.get(0));
                break;
            case 13:
                operation = "The content of dataMemory at the content of register " + operand.get(0).substring(1) + " = The content of dataMemory at the content of register " + operand.get(0).substring(1) + " - 1";
                break;
            case 15:
                operation = "The content of dataMemory at " + operand.get(0) + " = The content of dataMemory at " + operand.get(0) + " - 1";
                break;
            case 16:
                operation = "The content of register " + operand.get(0) + " = The content of register " + operand.get(0) + " - 1";
                break;
            case 18:
                operation = "PC(Binary bit 15-8) = The content of dataMemory at the content of SP\nSP = SP - 1\nPC(Binary bit 7-0) = The content of dataMemory at the content of SP\nSP = SP - 1";
                break;
            case 20:
                operation = "A = A + " + operand.get(1).substring(1);
                break;
            case 21:
                operation = "A = A + The content of dataMemory at the content of register " + operand.get(1).substring(1);
                break;
            case 22:
                operation = "A = A + The content of dataMemory at " + operand.get(1);
                break;
            case 23:
                operation = "A = A + The content of register" + operand.get(1);
                break;
            case 27:
                operation = "A = A + Carry bit(register C) + " + operand.get(1).substring(1);
                break;
            case 28:
                operation = "A = A + Carry bit(register C) + The content of dataMemory at the content of register " + operand.get(1).substring(1);
                break;
            case 29:
                operation = "A = A + Carry bit(register C) + The content of dataMemory at " + operand.get(1);
                break;
            case 30:
                operation = "A = A + Carry bit(register C) + The content of register" + operand.get(1);
                break;
            case 31:
                operation = "offset = current PC - Label: " + operand.get(1) + "'s position\n";
                operation += "PC = PC + 2\nif (the content of Carry bit(register C) == 1\n    PC = PC + offset)";
                break;
            case 32:
                operation = "A = A OR " + operand.get(1).substring(1);
                break;
            case 33:
                operation = "A = A OR The content of dataMemory at the content of register " + operand.get(1).substring(1);
                break;
            case 34:
                operation = "A = A OR The content of dataMemory at " + operand.get(1);
                break;
            case 35:
                operation = "A = A OR The content of register" + operand.get(1);
                break;
            case 36:
                Clocation = Integer.toString(Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1).substring(1)), 2) / 8 + 32);
                Cbit = Integer.toString(7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1).substring(1)), 2) % 8);

                operation = "(bit) = The " + Cbit + " bit of the content of dataMemory at 0x" + Clocation + "\n";
                operation += "Carry bit(register C) = Carry bit(register C) OR the complement of (bit)";
                break;
            case 37:
                Clocation = Integer.toString(Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1)), 2) / 8 + 32);
                Cbit = Integer.toString(7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1)), 2) % 8);

                operation = "(bit) = The " + Cbit + " bit of the content of dataMemory at 0x" + Clocation + "\n";
                operation += "Carry bit(register C) = Carry bit(register C) OR (bit)";
                break;
            case 38:
                operation = "The content of dataMemory at " + operand.get(0) + " = The content of dataMemory at " + operand.get(0) + " OR " + operand.get(1).substring(1);
                break;
            case 39:
                operation = "The content of dataMemory at " + operand.get(0) + " = The content of dataMemory at " + operand.get(0) + " OR A";
                break;
            case 105, 106:
                operation = "offset = current PC - Label: " + operand.get(1) + "'s position\n" + operation;
                break;
            case 40, 49, 56, 77:
                operation = "offset = current PC - Label: " + operand.get(0) + "'s position\n" + operation;
                break;
            case 88-91:
                operation = "offset = current PC - Label: " + operand.get(2) + "'s position\n" + operation;
                break;
            case 41:
                operation = "A = A AND " + operand.get(1).substring(1);
                break;
            case 42:
                operation = "A = A AND The content of dataMemory at the content of register " + operand.get(1).substring(1);
                break;
            case 43:
                operation = "A = A AND The content of dataMemory at " + operand.get(1);
                break;
            case 44:
                operation = "A = A AND The content of register" + operand.get(1);
                break;
            case 45:
                Clocation = Integer.toString(Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1).substring(1)), 2) / 8 + 32);
                Cbit = Integer.toString(7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1).substring(1)), 2) % 8);

                operation = "(bit) = The " + Cbit + " bit of the content of dataMemory at 0x" + Clocation + "\n";
                operation += "Carry bit(register C) = Carry bit(register C) AND the complement of (bit)";
                break;
            case 46:
                Clocation = Integer.toString(Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1)), 2) / 8 + 32);
                Cbit = Integer.toString(7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1)), 2) % 8);

                operation = "(bit) = The " + Cbit + " bit of the content of dataMemory at 0x" + Clocation + "\n";
                operation += "Carry bit(register C) = Carry bit(register C) AND (bit)";
                break;
            case 47:
                operation = "The content of dataMemory at " + operand.get(0) + " = The content of dataMemory at " + operand.get(0) + " AND " + operand.get(1).substring(1);
                break;
            case 48:
                operation = "The content of dataMemory at " + operand.get(0) + " = The content of dataMemory at " + operand.get(0) + " AND A";
                break;
            case 50:
                operation = "A = A XOR " + operand.get(1).substring(1);
                break;
            case 51:
                operation = "A = A XOR The content of dataMemory at the content of register " + operand.get(1).substring(1);
                break;
            case 52:
                operation = "A = A XOR The content of dataMemory at " + operand.get(1);
                break;
            case 53:
                operation = "A = A XOR The content of register" + operand.get(1);
                break;
            case 54:
                operation = "The content of dataMemory at " + operand.get(0) + " = The content of dataMemory at " + operand.get(0) + " XOR " + operand.get(1).substring(1);
                break;
            case 55:
                operation = "The content of dataMemory at " + operand.get(0) + " = The content of dataMemory at " + operand.get(0) + " XOR A";
                break;
            case 58:
                operation = "The content of dataMemory at the content of the register " + operand.get(0).substring(1) + " = " + operand.get(1).substring(1);
                break;
            case 59:
                operation = "The content of dataMemory at the content of the register " + operand.get(0).substring(1) + " = A";
                break;
            case 60:
                operation = "The content of dataMemory at the content of the register " + operand.get(0).substring(1) + " = The content of dataMemory at " + operand.get(1);
                break;
            case 61:
                operation = "A = " + operand.get(1).substring(1);
                break;
            case 62:
                operation = "A = " + "The content of dataMemory at the content of the register " + operand.get(1).substring(1);
                break;
            case 63:
                operation = "A = " + "The content of dataMemory at " + operand.get(1);
                break;
            case 64:
                operation = "A = " + "The content of register" + operand.get(1);
                break;
            case 65:
                Clocation = Integer.toString(Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) / 8 + 32);
                Cbit = Integer.toString(7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) % 8);

                operation = "(bit) = The " + Cbit + " bit of the content of dataMemory at 0x" + Clocation + "\n";
                operation += "(bit) = Carry bit(register C)";
                break;
            case 66:
                Clocation = Integer.toString(Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1)), 2) / 8 + 32);
                Cbit = Integer.toString(7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(1)), 2) % 8);

                operation = "(bit) = The " + Cbit + " bit of the content of dataMemory at 0x" + Clocation + "\n";
                operation += "Carry bit(register C) = (bit)";
                break;
            case 67:
                operation = "The content of dataMemory at " + operand.get(0) + " = The content of dataMemory at " + operand.get(1);
                break;
            case 68:
                operation = "The content of dataMemory at " + operand.get(0) + " = " + operand.get(1).substring(1);
                break;
            case 69:
                operation = "The content of dataMemory at " + operand.get(0) + " = the content of dataMemory at the content of register " + operand.get(1);
                break;
            case 70:
                operation = "The content of dataMemory at " + operand.get(0) + " = A";
                break;
            case 71:
                operation = "The content of dataMemory at " + operand.get(0) + " = the content of register " + operand.get(1);
                break;
            case 72:
                operation = "DPTR = " + operand.get(1).substring(1);
                break;
            case 73:
                operation = "The content of register " + operand.get(0) + " = " + operand.get(1).substring(1);
                break;
            case 74:
                operation = "The content of register " + operand.get(0) + " = A";
                break;
            case 75:
                operation = "The content of register " + operand.get(0) + " = The content of dataMemory at " + operand.get(1);
                break;
            case 78:
                operation = "A = the content of codeMemory at A + DPTR";
                break;
            case 79:
                operation = "A = the content of codeMemory at A + PC";
                break;
            case 81:
                operation = "A = A - Carry bit(register C) - " + operand.get(1).substring(1);
                break;
            case 82:
                operation = "A = A - Carry bit(register C) - The content of dataMemory at the content of register " + operand.get(1).substring(1);
                break;
            case 83:
                operation = "A = A - Carry bit(register C) - The content of dataMemory at " + operand.get(1);
                break;
            case 84:
                operation = "A = A - Carry bit(register C) - The content of register" + operand.get(1);
                break;
            case 85:
                operation = "A = the complement of A";
                break;
            case 92:
                operation =  "SP = SP + 1\nThe content of dataMemory at the content of SP = The content of dataMemory at " + operand.get(0);
                break;
            case 96:
                operation = "A(Binary bit 3-0) swap A(Binary bit 7-4)";
                break;
            case 97:
                operation = "A swap The content of dataMemory at the content of register " + operand.get(1);
                break;
            case 98:
                operation = "A swap The content of dataMemory at "+ operand.get(1);
                break;
            case 99:
                operation = "A swap " + operand.get(1);
                break;
            case 100:
                operation = "A(Binary bit 3-0) swap The content of dataMemory at the content of register" + operand.get(1) + "(Binary bit 3-0)";
                break;
            case 101:
                operation = "The content of dataMemory at " + operand.get(0) + " = The content of dataMemory at SP\nSP = SP - 1";
                break;
            case 86, 94, 102:
                Clocation = Integer.toString(Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) / 8 + 32);
                Cbit = Integer.toString(7 - Integer.parseInt(CarrySystem.directNumbertoBinary(operand.get(0)), 2) % 8);

                operation = "(bit) = The " + Cbit + " bit of the content of dataMemory at 0x" + Clocation + "\n" + operation;
                break;
        }

        operationList.add(operation);
    }

    public static ArrayList<String> getOperationList() {
        return operationList;
    }
    public static void clearOperationList(){
        operationList.clear();
    }
}
