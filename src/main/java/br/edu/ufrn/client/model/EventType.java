package br.edu.ufrn.client.model;

public enum EventType {

    POLICE_OFFICER("Departamento Policial", 1),
    FIRE_DEPARTMENT("Corpo de Bombeiros", 2),
    CIVIL_DEFENSE("Defesa Civil", 3);

    private final String name;
    private final int code;

    EventType(String name, int code){
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static EventType getEventTypeByCode(int code) throws IllegalArgumentException{
        return switch (code) {
            case 1 -> POLICE_OFFICER;
            case 2 -> FIRE_DEPARTMENT;
            case 3 -> CIVIL_DEFENSE;
            default -> throw new IllegalArgumentException("Code is not exist");
        };
    }
}
