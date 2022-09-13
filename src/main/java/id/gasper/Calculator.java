package id.gasper;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    private final Pattern varPattern = Pattern.compile( "(?<var>[a-zA-Z_][a-zA-Z0-9_]*)($|[^a-zA-Z0-9_\\(])" );
    private final HashMap<String, Expression> varBucket;

    public Calculator() {
        varBucket = new HashMap<>();
    }

    public String execute(String term) {
        term = cleanUp( term );
        Optional<Result> result;
        if (isEquation( term )) {
            try {
                result = evalEquation( term );
            } catch (RecursionException re) {
                return "<" + re.toString() + ">";
            }
        } else {
            result = eval( term );
        }
        return result.isPresent() ? String.valueOf( result.get() ) : term;
    }

    private boolean isAssignment(String term) {
        return term.matches( "(?<var>[a-zA-Z_][a-zA-Z0-9_]*)=([^=])*" );
    }

    private Optional<Result> evalEquation(String term) throws RecursionException, IllegalArgumentException {
        String[] sides = term.split( "=" );
        Set<String> varA = getVariables( sides[0] );
        Set<String> varB = getVariables( sides[1] );
        if (isAssignment( term )) {
            if (varA.size() == 1) {
                String var = varA.iterator().next();
                if (varB.contains( var )) {
                    throw new RecursionException();
                }
                Expression e = new ExpressionBuilder( sides[1] ).variables( varB ).build();

                varBucket.put( var, e );
                return eval( e );
            } else {
                return Optional.empty();
            }
        } else {
            Optional<Result> r1 = eval( sides[0] );
            Optional<Result> r2 = eval( sides[1] );
            if (r1.isPresent()) {
                return r1.get().equals( r2 );
            }
            return Optional.empty();
        }
    }

    private Optional<Result> eval(Expression e) throws IllegalArgumentException {
        if (!e.getVariableNames().isEmpty()) {
            for (String var : e.getVariableNames()) {
                if (varBucket.containsKey( var )) {
                    Optional<Result> result;
                    if ((result = eval( varBucket.get( var ) )).isPresent()) {
                        e.setVariable( var, result.get().getDouble() );
                    } else {
                        return Optional.empty();
                    }
                } else {
                    return Optional.empty();
                }
            }
        }
        return Optional.of( new Result( e.evaluate() ) );
    }

    private Optional<Result> eval(String term) {
        Set<String> vars = getVariables( term );
        if (varBucket.keySet().containsAll( vars )) {
            Expression e = new ExpressionBuilder( term ).variables( vars ).build();
            return eval( e );
        } else {
            return Optional.empty();
        }
    }

    private Set<String> getVariables(String term) {
        Matcher m = varPattern.matcher( term );
        Set<String> vars = new HashSet<>();
        while (m.find()) {
            vars.add( m.group( "var" ) );
        }
        return vars;
    }

    private boolean isEquation(String term) {
        int index = term.indexOf( "=" );
        return index == term.lastIndexOf( "=" ) && index >= 0;
    }


    private String cleanUp(String term) {
        return term.replaceAll( " ", "" );
    }
}
