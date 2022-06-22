package FakeInstruction;

import InstructionProcess.CarrySystem;
import InstructionProcess.judgeIsValid;
import Memory.CodeMemory;
import Register.register;
import Exception.WrongException;
import java.util.ArrayList;

public class DB {
    public DB(ArrayList<String> op, int StepOrRun) throws WrongException {
        if(StepOrRun == 0){
            for(String ele : op){
                if(!judgeIsValid.isdirectNumber(ele))
                    return;
            }
            String Pointer = CodeMemory.getCodeMemoryBeginPos();

            for(String ele : op){
                CodeMemory.setcodeMemory(CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(ele), 2), Pointer);
                Pointer = "0x" + CarrySystem.HexADD(Pointer.substring(2), "1", false, 4);
            }
            CodeMemory.setCodeMemoryBeginPos(Pointer);
        }
    }
}
