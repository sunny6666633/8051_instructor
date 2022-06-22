package InstructionProcess;
import java.util.ArrayList;
import java.util.Arrays;

import FakeInstruction.DB;
import FakeInstruction.ORG;
import GUI.GUIInstructionAnnotateTest;
import GUI.InitialPage;
import GUI.TheEditor;
import Instruction.*;
import Exception.WrongException;
import Memory.CodeMemory;
import Register.register;

public class instructionFetch
{
    private String instruction;
    private TheEditor theEditor = InitialPage.editor;
    private String [] splitted;
    private final int ASSEMBLY = 0;
    private final int STEP = 1;
    private final int RUN = 2;
    public instructionFetch(String instruction){
        try {
            Instruction instr = new Instruction();
        }catch (Exception e){
        }

        this.instruction = instruction.toUpperCase();
        splitted = this.instruction.split("\\R+");

        StepInstruction();
    }

    public String getInstruction(){
        return instruction;
    }

    public void StepInstruction(){
        for (int j = 0; j < splitted.length; j++){
            if(splitted[j].equals(""))
                continue;
            System.out.println(splitted[j]);

            String operator[] = splitted[j].trim().split("\\s+", 2);

            try {
                if(operator[0].contains(":")){
                    Label label = new Label(operator[0].substring(0, operator[0].indexOf(":")), CodeMemory.getCodeMemoryBeginPos());

                    if(operator.length == 1)
                        continue;
                    if(operator[0].endsWith(":")){
                        ArrayList<String> oper = new ArrayList<String>(Arrays.asList(operator));
                        oper.remove(0);
                        operator = oper.get(0).split("\\s+", 2);
                    }
                    else
                        operator[0] = operator[0].substring(operator[0].indexOf(":")+1);
                }
                InstructionDecode(operator, ASSEMBLY);
            }catch (WrongException wrongException){
                theEditor.showErrorMessage(wrongException.getMessage());
            } catch (Exception e){
            }
        }
        RunInstruction(STEP);
    }

    public void RunInstruction(int StepOrRun){
        Boolean flag = false;
        if(InstructionPosition.getPosition(0) != null)
            register.setPC(InstructionPosition.getPosition(0));
        else
            return;
        while(register.getPC() != null && register.getPC() != InstructionPosition.getLastInstructionPosition()){
            flag = true;
            String operator[] = InstructionPosition.getInstruction(register.getPC()).trim().split("\\s+", 2);
            InstructionDecode(operator, StepOrRun);
            register.setNextPC();
        }
        if(StepOrRun == STEP && flag){
            GUIInstructionAnnotateTest annotationTest = new GUIInstructionAnnotateTest();
        }
    }
    public void InstructionDecode(String[]operator, int StepOrRun){
        try {
            ArrayList<String> operand2=new ArrayList<String>();

            if(operator.length > 1){
                String operand[] = operator[1].split(",");
                if(operand[operand.length - 1].contains(";")){
                    String annotate[] = operand[operand.length - 1].split(";");
                    operand[operand.length - 1] = annotate[0];
                }
                String oper = "";
                for(int i = 0; i < operand.length; i++){
                    operand2.add(operand[i].trim());
                    if(StepOrRun == ASSEMBLY){
                        oper += operand2.get(i);
                        if(i < operand.length - 1)
                            oper += ", ";
                    }
                }

                if(StepOrRun == ASSEMBLY && !operator[0].equals("ORG") && !operator[0].equals("DB") && !operator[0].equals("END"))
                    InstructionPosition.setInstruction(operator[0] + " " + oper);
            }
            else{
                if(StepOrRun == ASSEMBLY && !operator[0].equals("END"))
                    InstructionPosition.setInstruction(operator[0]);
            }


            switch (operator[0]) {
                case "ACALL":
                    ACALL acall = new ACALL(operand2, StepOrRun);
                    break;
                case "ADD":
                    ADD add = new ADD(operand2, StepOrRun);
                    break;
                case "ADDC":
                    ADDC addc = new ADDC(operand2, StepOrRun);
                    break;
                case "AJMP":
                    AJMP ajmp = new AJMP(operand2, StepOrRun);
                    break;
                case "ANL":
                    ANL anl = new ANL(operand2, StepOrRun);
                    break;
                case "CJNE":
                    CJNE cjne = new CJNE(operand2, StepOrRun);
                    break;
                case "CLR":
                    CLR clr = new CLR(operand2, StepOrRun);
                    break;
                case "CPL":
                    CPL cpl = new CPL(operand2, StepOrRun);
                    break;
                case "DA":
                    DA da = new DA(operand2, StepOrRun);
                    break;
                case "DEC":
                    DEC dec = new DEC(operand2, StepOrRun);
                    break;
                case "DIV":
                    DIV div = new DIV(operand2, StepOrRun);
                    break;
                case "DJNZ":
                    DJNZ djnz = new DJNZ(operand2, StepOrRun);
                    break;
                case "INC":
                    INC inc = new INC(operand2, StepOrRun);
                    break;
                case "JB":
                    JB jb = new JB(operand2, StepOrRun);
                    break;
                case "JBC":
                    JBC jbc = new JBC(operand2, StepOrRun);
                    break;
                case "JC":
                    JC jc = new JC(operand2, StepOrRun);
                    break;
                case "JMP":
                    JMP jmp = new JMP(operand2, StepOrRun);
                    break;
                case "JNB":
                    JNB jnb = new JNB(operand2, StepOrRun);
                    break;
                case "JNC":
                    JNC jnc = new JNC(operand2, StepOrRun);
                    break;
                case "JNZ":
                    JNZ jnz = new JNZ(operand2, StepOrRun);
                    break;
                case "JZ":
                    JZ jz = new JZ(operand2, StepOrRun);
                    break;
                case "LCALL":
                    LCALL lcall = new LCALL(operand2, StepOrRun);
                    break;
                case "LJMP":
                    LJMP ljmp = new LJMP(operand2, StepOrRun);
                    break;
                case "MOV":
                    MOV mov = new MOV(operand2, StepOrRun);
                    break;
                case "MOVC":
                    MOVC movc = new MOVC(operand2, StepOrRun);
                    break;
                case "MOVX":
                    throw new WrongException("The simulater has no external memory\n MOVX instruction cannot be implemented", false);
                case "MUL":
                    MUL mul = new MUL(operand2, StepOrRun);
                    break;
                case "NOP":
                    NOP nop = new NOP(operand2, StepOrRun);
                    break;
                case "ORL":
                    ORL orl = new ORL(operand2, StepOrRun);
                    break;
                case "POP":
                    POP pop = new POP(operand2, StepOrRun);
                    break;
                case "PUSH":
                    PUSH push = new PUSH(operand2, StepOrRun);
                    break;
                case "RET":
                    RET ret = new RET(operand2, StepOrRun);
                    break;
                case "RETI":
                    throw new WrongException("Don't support interrupt mechanism", false);
                case "RL":
                    RL rl = new RL(operand2, StepOrRun);
                    break;
                case "RLC":
                    RLC rlc = new RLC(operand2, StepOrRun);
                    break;
                case "RR":
                    RR rr = new RR(operand2, StepOrRun);
                    break;
                case "RRC":
                    RRC rrc = new RRC(operand2, StepOrRun);
                    break;
                case "SETB":
                    SETB setb = new SETB(operand2, StepOrRun);
                    break;
                case "SJMP":
                    SJMP sjmp = new SJMP(operand2, StepOrRun);
                    break;
                case "SUBB":
                    SUBB subb = new SUBB(operand2, StepOrRun);
                    break;
                case "SWAP":
                    SWAP swap = new SWAP(operand2, StepOrRun);
                    break;
                case "XCH":
                    XCH xch = new XCH(operand2, StepOrRun);
                    break;
                case "XCHD":
                    XCHD xchd = new XCHD(operand2, StepOrRun);
                    break;
                case "XRL":
                    XRL xrl = new XRL(operand2, StepOrRun);
                    break;
                case "ORG":
                    ORG org = new ORG(operand2, StepOrRun);
                    break;
                case "DB":
                    DB db = new DB(operand2, StepOrRun);
                    break;
                case "END":
                    break;
                default:
                    throw new WrongException("Wrong instruction", false);
            }
        }catch (WrongException e){
            theEditor.showErrorMessage(e.getMessage());
        }
    }
}
