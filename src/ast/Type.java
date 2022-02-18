package ast;

public enum Type {
    INTEGER("integer"),
    BOOL("bool"),
    REAL("real"),
    STRING("string"),
    VAR("var");

    private String name;

    Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
