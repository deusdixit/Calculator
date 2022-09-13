package id.gasper;

import java.util.Optional;

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

    public Optional<Result> equals(Optional<Result> r) {
        if ( r.isPresent() ) {
            return Optional.of( new Result( r.get().equals( this )));
        }
        return Optional.empty();
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
    public String toString() {
        return text;
    }
}
