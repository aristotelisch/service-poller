package eu.happybit.poller.domain;

public enum ServiceStatus {
    OK("OK"), FAIL("FAIL");

    private final String name;

    ServiceStatus(String name) {
       this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
