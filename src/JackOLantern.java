public class JackOLantern {
    private String[][] faceFeatures;

    public JackOLantern(String[][] faceFeatures) {
        this.faceFeatures = faceFeatures;
    }

    public void edit(String replace, int row, int column) {
        faceFeatures[row][column] = replace;
    }

    public void fill(String str) {
        for (int i = 0; i < faceFeatures.length; i++) {
            for (int j = 0; j < faceFeatures[i].length; j++)
                edit(str, i, j);
        }
    }

    public void fillRow(String str, int row) {
        for (int i = 0; i < faceFeatures[0].length; i++)
            edit(str, row, i);
    }

    public void fillColumn(String str, int column) {
        for (int i = 0; i < faceFeatures.length; i++)
            edit(str, i, column);
    }

    public void graphFunction(String str, String fn) {
        // Remove whitespace
        String[] terms = fn.split(" ");
        fn = String.join("", terms);

        // Determine the domain and remove the dependent variable and equal sign
        int domain;
        if (fn.substring(0, 1).equals("y"))
            domain = faceFeatures[0].length;
        else
            domain = faceFeatures.length;
        fn = fn.substring(2);

        for (int i = 0; i < domain; i++) {
            String instance = fn;

            // Substitute variables with i
            if (instance.substring(0, 1).matches("x"))
                instance = "(" + i + ")" + instance.substring(1);
            if (instance.substring(instance.length() - 1).matches("x"))
                instance = instance.substring(0, instance.length() - 1) + "(" + i + ")";
            instance = String.join("(" + i + ")", instance.split("x"));

            // Evaluate the expression by first dealing with parenthesis

            //while (!instance.matches("-?\\d*\\.?\\d+") || !instance.equals("NaN")) {
                while (instance.contains("(")) {
                    int begIdx = instance.lastIndexOf("(");
                    int endIdx = begIdx + instance.substring(begIdx).indexOf(")");
                    String innerFn = instance.substring(begIdx + 1, endIdx);
                    String evaluated = interpretExpression(innerFn);
                    instance = instance.substring(0, begIdx) + evaluated + instance.substring(endIdx + 1);

                }
                instance = interpretExpression(instance);
            //}

            // Converts the final String to a double.

            int result = (int)Math.round(Double.parseDouble(instance));
            System.out.println("//////////// " + i + ", " + result);

            try {
                if (terms[0].equals("y"))
                    edit(str, result, i);
                else
                    edit(str, i, result);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
    }

    private String interpretExpression(String expr) {
        System.out.println("| In: " + expr);

        //while (!(expr.matches("-?\\d*\\.?\\d+]") && expr.equals("NaN"))) {
            while (expr.contains("^"))
                expr = doOperation(expr, expr.indexOf("^"));

            while (expr.contains("*") || expr.contains("/")) {
                int i;
                if (!expr.contains("*")) {
                    i = expr.indexOf("/");
                } else if (!expr.contains("/")) {
                    i = expr.indexOf("*");
                } else
                    i = Math.min(expr.indexOf("*"),expr.indexOf("/"));
                expr = doOperation(expr, i);
            }

            while (expr.contains("+") || expr.matches(".*\\d-.*")) {
                int i;
                if (!expr.contains("+")) {
                    i = expr.indexOf("-");
                } else if (!expr.contains("-")) {
                    i = expr.indexOf("+");
                } else {
                    i = Math.min(expr.indexOf("+"), expr.indexOf("-"));
                    if (expr.indexOf("-") == 0 || !expr.substring(expr.indexOf("-") - 1, expr.indexOf("-")).matches("\\d")) {
                        i = expr.indexOf("+");
                    }
                }
                expr = doOperation(expr, i);
            }

            if (expr.length() > 1 && expr.substring(0, 2).equals("--")) {
                expr = expr.substring(2);
            }

            System.out.println("| " + expr + " ✔");
        //}
        return expr;
    }

    private String doOperation(String fn, int i) {
        int begIdx, endIdx;
        for (begIdx = i; begIdx != 0; begIdx--) {
            String nxtChar = fn.substring(begIdx - 1, begIdx);
            if (nxtChar.matches("[\\d.]"))
                continue;
            if (nxtChar.equals("-")) {
                begIdx--;
                break;
            }
        }
        for (endIdx = i; endIdx != fn.length() - 1 && (fn.substring(endIdx + 1, endIdx + 2).matches("[\\d.]") ||
                (fn.substring(endIdx + 1, endIdx + 2).equals("-") && endIdx == i)); endIdx++) {}
        String str1 = fn.substring(begIdx, i);
        System.out.print(begIdx + ", " + i + ": ");
        double num1;
        try {
            num1 = Double.parseDouble(str1);
        } catch (NumberFormatException e) {
           num1 = 0;
        }
        System.out.println(num1);

        String str2 = fn.substring(i + 1, endIdx + 1);
        System.out.print((i + 1) + ", " + (endIdx + 1) + ": ");
        double num2;
        try {
            num2 = Double.parseDouble(str2);
        } catch (NumberFormatException e) {
            num2 = 0;
        }
        System.out.println(num2);

        double result;
        switch (fn.substring(i, i + 1)) {
            case "^":
                result = Math.pow(num1, num2);
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            default:
                System.out.println("WHAT!?");
                result = 0;
                break;
        }
        if (begIdx != 0 && fn.substring(begIdx - 1, begIdx).matches("\\d")) {
            System.out.println("Yes");
            fn = fn.substring(0, begIdx) + "+" + result + fn.substring(endIdx + 1);
        } else
            fn = fn.substring(0, begIdx) + result + fn.substring(endIdx + 1);
        System.out.println("| " + fn);
        return fn;
    }

    public String toString() {
        String out = "";
        for (int i = faceFeatures.length-1; i >= 0; i--) {
            for (int j = 0; j < faceFeatures[i].length; j++)
                out += " " + faceFeatures[i][j] + " ";
            out += "\n";
        }
        return out;
    }
}
