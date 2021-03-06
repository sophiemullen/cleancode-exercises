package be.swsb.cleancode.ch14.args;

import java.text.ParseException;
import java.util.*;

public class Args {
    private String schema;
    private boolean valid = true;
    private Set<Character> unexpectedArguments = new TreeSet<Character>();
    private Map<Character, ArgumentMarshaler> marshalers = new HashMap<Character, ArgumentMarshaler>();
    private Set<Character> argsFound = new HashSet<Character>();
    private Iterator<String> currentArgument;
    private char errorArgumentId = '\0';
    private String errorParameter = "TILT";
    private ErrorCode errorCode = ErrorCode.OK;
    private List<String> argsList;

    private enum ErrorCode {
        OK, MISSING_STRING, MISSING_INTEGER, INVALID_INTEGER, UNEXPECTED_ARGUMENT
    }

    public Args(String schema, String[] args) throws ParseException {
        this.schema = schema;
        argsList = Arrays.asList(args);
        valid = parse();
    }

    private boolean parse() throws ParseException {
        if (schema.length() == 0 && argsList.size() == 0)
            return true;
        parseSchema();
        try {
            parseArguments();
        } catch (ArgsException e) {
        }
        return valid;
    }

    private boolean parseSchema() throws ParseException {
        for (String element : schema.split(",")) {
            if (element.length() > 0) {
                String trimmedElement = element.trim();
                parseSchemaElement(trimmedElement);
            }
        }
        return true;
    }

    private void parseSchemaElement(String element) throws ParseException {
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElementId(elementId);
        if (isBooleanSchemaElement(elementTail))
            marshalers.put(elementId, new BooleanArgumentMarshaler());
        else if (isStringSchemaElement(elementTail))
            marshalers.put(elementId, new StringArgumentMarshaler());
        else if (isIntegerSchemaElement(elementTail)) {
            marshalers.put(elementId, new IntegerArgumentMarshaler());
        } else {
            throw new ParseException(
                    String.format("Argument: %c has invalid format: %s.",
                            elementId, elementTail), 0);
        }
    }

    private void validateSchemaElementId(char elementId) throws ParseException {
        if (!Character.isLetter(elementId)) {
            throw new ParseException(
                    "Bad character:" + elementId + "in Args format: " + schema, 0);
        }
    }

    private void parseBooleanSchemaElement(char elementId) {
        marshalers.put(elementId, new BooleanArgumentMarshaler());
    }

    private void parseIntegerSchemaElement(char elementId) {
        marshalers.put(elementId, new IntegerArgumentMarshaler());
    }

    private void parseStringSchemaElement(char elementId) {
        marshalers.put(elementId, new StringArgumentMarshaler());
    }

    private boolean isStringSchemaElement(String elementTail) {
        return elementTail.equals("*");
    }

    private boolean isBooleanSchemaElement(String elementTail) {
        return elementTail.length() == 0;
    }

    private boolean isIntegerSchemaElement(String elementTail) {
        return elementTail.equals("#");
    }

    private boolean parseArguments() throws ArgsException {
        for (currentArgument = argsList.iterator(); currentArgument.hasNext(); ) {
            String arg = currentArgument.next();
            parseArgument(arg);
        }
        return true;
    }

    private void parseArgument(String arg) throws ArgsException {
        if (arg.startsWith("-"))
            parseElements(arg);
    }

    private void parseElements(String arg) throws ArgsException {
        for (int i = 1; i < arg.length(); i++)
            parseElement(arg.charAt(i));
    }

    private void parseElement(char argChar) throws ArgsException {
        if (setArgument(argChar))
            argsFound.add(argChar);
        else {
            unexpectedArguments.add(argChar);
            errorCode = ErrorCode.UNEXPECTED_ARGUMENT;
            valid = false;
        }
    }

    private boolean setArgument(char argChar) throws ArgsException {
        ArgumentMarshaler argumentMarshaler = marshalers.get(argChar);
        if (argumentMarshaler == null) {
            return false;
        }
        try {
            if (argumentMarshaler instanceof BooleanArgumentMarshaler)
                argumentMarshaler.set(currentArgument);
            else if (argumentMarshaler instanceof StringArgumentMarshaler)
                setStringArg(argumentMarshaler);
            else if (argumentMarshaler instanceof IntegerArgumentMarshaler)
                setIntArg(argumentMarshaler);
        } catch (ArgsException exception) {
            valid = false;
            errorArgumentId = argChar;
            throw exception;
        }
        return true;
    }

    private boolean isIntArg(ArgumentMarshaler argumentMarshaler) {
        return argumentMarshaler instanceof IntegerArgumentMarshaler;
    }

    private void setIntArg(ArgumentMarshaler argumentMarshaler) throws ArgsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            argumentMarshaler.set(parameter);
        } catch (NoSuchElementException e) {
            errorCode = ErrorCode.MISSING_INTEGER;
            throw new ArgsException();

        } catch (ArgsException exception) {
            valid = false;
            errorParameter = parameter;
            errorCode = ErrorCode.INVALID_INTEGER;
            throw exception;
        }
    }

    private void setStringArg(ArgumentMarshaler argumentMarshaler) throws ArgsException {
        try {
            argumentMarshaler.set(currentArgument.next());
        } catch (NoSuchElementException e) {
            errorCode = ErrorCode.MISSING_STRING;
            throw new ArgsException();
        }
    }

    private boolean isStringArg(ArgumentMarshaler argumentMarshaler) {
        return argumentMarshaler instanceof StringArgumentMarshaler;
    }

    private boolean isBooleanArg(ArgumentMarshaler argumentMarshaler) {
        return argumentMarshaler instanceof BooleanArgumentMarshaler;
    }

    public int cardinality() {
        return argsFound.size();
    }

    public String usage() {
        if (schema.length() > 0)
            return "-[" + schema + "]";
        else
            return "";
    }

    public String errorMessage() throws Exception {
        switch (errorCode) {
            case OK:
                throw new Exception("TILT: Should not get here.");
            case UNEXPECTED_ARGUMENT:
                return unexpectedArgumentMessage();
            case MISSING_STRING:
                return String.format("Could not find string parameter for -%c.",
                        errorArgumentId);

            case INVALID_INTEGER:
                return String.format("Argument -%c expects an integer but was ’%s’.",
                        errorArgumentId, errorParameter);
            case MISSING_INTEGER:
                return String.format("Could not find integer parameter for -%c.",
                        errorArgumentId);
        }
        return "";
    }

    private String unexpectedArgumentMessage() {
        StringBuffer message = new StringBuffer("Argument(s) -");
        for (char c : unexpectedArguments) {
            message.append(c);
        }
        message.append("unexpected.");

        return message.toString();
    }

    private boolean falseIfNull(Boolean b) {
        return b != null && b;
    }

    private int zeroIfNull(Integer i) {
        return i == null ? 0 : i;
    }

    private String blankIfNull(String s) {
        return s == null ? "" : s;
    }

    public String getString(char arg) {
        Args.ArgumentMarshaler argumentMarshaler = marshalers.get(arg);
        try {
            return argumentMarshaler == null ? "" : (String) argumentMarshaler.get();
        } catch (ClassCastException exception) {
            return "";
        }
    }

    public int getInt(char arg) {
        Args.ArgumentMarshaler argumentMarshaler = marshalers.get(arg);
        try {
            return argumentMarshaler == null ? 0 : (Integer) argumentMarshaler.get();
        } catch (Exception exception) {
            return 0;
        }
    }

    public boolean getBoolean(char arg) {
        Args.ArgumentMarshaler argumentMarshaler = marshalers.get(arg);
        boolean b = false;
        try {
            b = argumentMarshaler != null && (Boolean) argumentMarshaler.get();
        } catch (ClassCastException error) {
            b = false;
        }
        return b;
    }

    public boolean has(char arg) {
        return argsFound.contains(arg);
    }

    public boolean isValid() {
        return valid;
    }

    private class ArgsException extends Exception {
    }

    private abstract class ArgumentMarshaler {

        public abstract void set(Iterator<String> currentArgument) throws ArgsException;
        public abstract void set(String string) throws ArgsException;
        public abstract Object get();
    }

    private class BooleanArgumentMarshaler extends ArgumentMarshaler {

        private boolean booleanValue = false;

        @Override
        public void set(Iterator<String> currentArgument) throws ArgsException {
            booleanValue = true;
        }

        @Override
        public void set(String string) {
        }

        @Override
        public Object get() {
            return booleanValue;
        }
    }

    private class StringArgumentMarshaler extends ArgumentMarshaler {

        private String stringValue = "";

        @Override
        public void set(Iterator<String> currentArgument) throws ArgsException {
        }

        @Override
        public void set(String string) {
            stringValue = string;
        }

        @Override
        public Object get() {
            return stringValue;
        }
    }

    private class IntegerArgumentMarshaler extends ArgumentMarshaler {

        private int intValue = 0;

        @Override
        public void set(Iterator<String> currentArgument) throws ArgsException {
        }

        @Override
        public void set(String string) throws ArgsException {
            try {
                intValue = Integer.parseInt(string);
            } catch (NumberFormatException exception) {
                throw new ArgsException();
            }
        }

        @Override
        public Object get() {
            return intValue;
        }
    }
}
