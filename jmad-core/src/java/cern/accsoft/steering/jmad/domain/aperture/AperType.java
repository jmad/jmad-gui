package cern.accsoft.steering.jmad.domain.aperture;

public enum AperType {
    CIRCLE("CIRCLE"), RECTANGLE("RECTANGLE"), RECTELLIPSE("RECTELLIPSE"), RACETRACK("RACETRACK"), NONE("NONE");

    private String name;

    private AperType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}