package Exception;
public class WrongException extends Exception{
    private String wrongInstruction;
    private boolean notHandledInstruction;
    public WrongException(String wrongInstruction, boolean notHandledInstruction){
        this.wrongInstruction = wrongInstruction;
        this.notHandledInstruction = notHandledInstruction;
    }

    @Override
    public String getMessage() {
        return notHandledInstruction ? String.format(wrongInstruction + " instruction has wrong!\n") : String.format(wrongInstruction + "\n");
    }
}
