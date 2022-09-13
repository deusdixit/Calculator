package id.gasper;

public class Result {

    private final String text;

    private Double dValue;


    public Result(Double value) {
        text = String.valueOf( value );
        dValue = value;
    }

    public Result(boolean value) {
        text = String.valueOf( value );
    }

    public Double getDouble() {
        return dValue;
    }

    public Result equalTerm(Result r) {
        return new Result( r.equals( this ) );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Result r) {
            return r.text.equals( this.text );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return text;
    }
}
