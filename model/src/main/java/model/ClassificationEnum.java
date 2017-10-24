package model;

public enum ClassificationEnum {
    AFTERNOON_PERSON("Afternoon Person"),
    BIG_SPENDER("Big Spender"),
    BIG_TICKET_SPENDER("Big Ticket Spender"),
    FAST_SPENDER("Fast Spender"),
    MORNING_PERSON("Morning Person"),
    POTENTIAL_SAVER("Potential Saver"),
    POTENTIAL_LOAN("Potential Loan");

    private String value;

    ClassificationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
