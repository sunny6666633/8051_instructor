package FakeInstruction;

import InstructionProcess.CarrySystem;
import Memory.CodeMemory;
import Exception.WrongException;
import java.util.ArrayList;

public class ORG {

    public ORG(ArrayList<String> op, int StepOrRun) throws WrongException {
        if(StepOrRun == 0)
            CodeMemory.setCodeMemoryBeginPos("0x" + CarrySystem.BinarytoHex(CarrySystem.directNumbertoBinary(op.get(0), 16), 4));
    }
}
