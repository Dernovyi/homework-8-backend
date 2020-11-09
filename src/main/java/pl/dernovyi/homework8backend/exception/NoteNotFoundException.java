package pl.dernovyi.homework8backend.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(Long message) {
        super("Note with number " + message + " not found");
    }
}
