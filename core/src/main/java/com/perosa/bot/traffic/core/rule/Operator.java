package com.perosa.bot.traffic.core.rule;

public enum Operator {
    EQUAL, NOT_EQUAL, EQUAL_IGNORE_CASE, CONTAIN, START_WITH, END_WITH;

    public boolean apply(String input, String expected) {
        boolean ret = false;

        if (this.equals(Operator.EQUAL)) {
            ret = input.equals(expected);
        } else if (this.equals(Operator.NOT_EQUAL)) {
            ret = !input.equals(expected);
        } else if (this.equals(Operator.EQUAL_IGNORE_CASE)) {
            ret = input.equalsIgnoreCase(expected);
        } else if (this.equals(Operator.CONTAIN)) {
            ret = input.contains(expected);
        } else if (this.equals(Operator.START_WITH)) {
            ret = input.startsWith(expected);
        } else if (this.equals(Operator.END_WITH)) {
            ret = input.endsWith(expected);
        }

        return ret;
    }

}
