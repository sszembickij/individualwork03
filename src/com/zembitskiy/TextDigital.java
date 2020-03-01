package com.zembitskiy;

public class TextDigital {
    private StringBuilder number;
    private StringBuilder numberWithZeros;
    private int mantis;

    TextDigital(String number) {
        StringBuilder str = new StringBuilder(TextDigital.pruningZeroString(number));
        if (!number.contains(".")) {
            this.mantis = 0;
            this.number = new StringBuilder(str);
            while (this.number.charAt(this.number.length() - 1) == '0') {
                this.number.deleteCharAt(this.number.length() - 1);
                this.mantis++;
            }
        } else {
            this.mantis = str.indexOf(".") - str.length() + 1;
            this.number = new StringBuilder(str);
            System.out.println(this.number.indexOf("."));
            this.number = this.number.deleteCharAt(this.number.indexOf("."));
        }
        this.pruningZeroNumber();
    }

    private TextDigital() {
        this.number = new StringBuilder();
        this.numberWithZeros = new StringBuilder();
        this.mantis = 0;
    }

    private void pruningZeroNumber() {
        while (number.charAt(number.length() - 1) == '0') {
            number.deleteCharAt(number.length() - 1);
            mantis++;
        }
        while (number.charAt(0) == '0') {
            number.deleteCharAt(0);
        }
    }

    static String pruningZeroString(String string) {
        StringBuilder str = new StringBuilder(string);
        while (str.charAt(string.length() - 1) == '0') {
            str.deleteCharAt(str.length() - 1);
        }
        while (str.charAt(0) == '0') {
            str.deleteCharAt(0);
        }
        return str.toString();
    }

    private static boolean firstOperandMore(TextDigital value1, TextDigital value2) {
        if (value1.number.length() + value1.mantis > value2.number.length() + value2.mantis) {
            return true;
        } else if (value1.number.length() + value1.mantis < value2.number.length() + value2.mantis) {
            return false;
        } else {
            for (int i = 0; i < Math.min(value1.number.length(), value2.number.length()); i++) {
                if (value1.number.charAt(i) > value2.number.charAt(i)) {
                    return true;
                } else if (value1.number.charAt(i) < value2.number.charAt(i)) {
                    return false;
                }
            }
            return value1.number.length() >= value2.number.length();
        }
    }

    static TextDigital additionTextDidital(TextDigital value1, TextDigital value2) {
        TextDigital result = new TextDigital();
        result.mantis = Math.min(value1.mantis, value2.mantis);
        TextDigital.addZeros(value1, value2);
        int temp = 0;
        result.mantis = Math.min(value1.mantis, value2.mantis);

        for (int i = value1.numberWithZeros.length() - 1; i >= 0; i--) {
            temp += Integer.parseInt(String.valueOf(value1.numberWithZeros.charAt(i)))
                    + Integer.parseInt(String.valueOf(value2.numberWithZeros.charAt(i)));
            result.number.insert(0, temp % 10);
            temp /= 10;
        }
        if (temp != 0) {
            result.number.insert(0, temp);
        }
        result.pruningZeroNumber();
        return result;
    }

    private static void addZeros(TextDigital value1, TextDigital value2) {
        value1.numberWithZeros = new StringBuilder(value1.number);
        value2.numberWithZeros = new StringBuilder(value2.number);
        if (value1.mantis <= value2.mantis) {
            for (int i = 0; i < value2.mantis - value1.mantis; i++) {
                value1.numberWithZeros.insert(value1.numberWithZeros.length() - 1, '0');
            }
        } else {
            for (int i = 0; i < value1.mantis - value2.mantis; i++) {
                value2.numberWithZeros.insert(value2.numberWithZeros.length() - 1, '0');
            }
        }
        while (value1.numberWithZeros.length() != value2.numberWithZeros.length()) {
            if (value1.numberWithZeros.length() < value2.numberWithZeros.length()) {
                value1.numberWithZeros.append("0");
            } else {
                value2.numberWithZeros.append("0");
            }
        }
    }

    public static TextDigital subtractionTextDidital(TextDigital value1, TextDigital value2) {
        TextDigital result = new TextDigital();
        int temp = 0;
        boolean isNegativResult = !firstOperandMore(value1, value2);
        if (isNegativResult) {
            TextDigital tmpStr = value1;
            value1 = value2;
            value2 = tmpStr;
        }
        addZeros(value1, value2);
        for (int i = value1.numberWithZeros.length() - 1; i >= 0; i--) {
            temp += Integer.parseInt(String.valueOf(value1.number.charAt(i)))
                    - Integer.parseInt(String.valueOf(value2.numberWithZeros.charAt(i)));
            if (temp < 0) {
                result.number.insert(0, 10 + temp);
                temp = -1;
            } else {
                result.number.insert(0, temp);
                temp = 0;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        if (mantis >= 0) {
            for (int i = 0; i < mantis; i++) {
                str.append('0');
            }
            return number.toString() + str;
        } else if (mantis + number.length() - 1 < 0) {
            for (int i = mantis + 1; i < 0; i++) {
                str.append('0');
            }
            return "0." + str + number.toString();
        }
        return number.insert(number.length() + mantis, '.').toString();
        //return number.toString() + ' ' + mantis;
    }
}
