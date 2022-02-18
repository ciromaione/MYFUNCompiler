package ast;

public enum ParDeclMode {
    IN("in"),
    OUT("out");

    private String name;

    ParDeclMode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
