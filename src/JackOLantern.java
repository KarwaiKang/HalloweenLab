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

    public void fillColumn(String str, int column) {
        for (int i = 0; i < faceFeatures.length; i++)
            edit(str, i, column);
    }

    public void graphFunction(String str, String fn) {
        System.out.println("Input: " + fn);

        // Remove whitespace

        String[] terms = fn.split(" ");
        fn = String.join("", terms);

        System.out.println("w/o spaces: " + fn);

        // Determines the domain and removes the dependent variable and equal sign

        int domain;
        if (fn.substring(0, 1).equals("y"))
            domain = faceFeatures[0].length;
        else
            domain = faceFeatures.length;
        fn = fn.substring(2);

        System.out.println("Expression only: " + fn);

        for (int i = 0; i < domain; i++) {
            String instance = fn;

            // Substitute variables with i and add * signs between number and parenthesis
            if (instance.substring(0, 1).matches("x"))
                instance = "(" + i + ")" + instance.substring(1);
            if (instance.substring(instance.length() - 1).matches("x"))
                instance = instance.substring(0, instance.length() - 1) + "(" + i + ")";
            instance = String.join("(" + i + ")", instance.split("x"));

            System.out.println("Substituted: " + instance);

            // Evaluates the expression by first dealing with parenthesis.

            while (!instance.matches("-?\\d*\\.?\\d+")) {
                while (instance.contains("(")) {
                    int begIdx = instance.lastIndexOf("(");
                    int endIdx = begIdx + instance.substring(begIdx).indexOf(")");
                    String innerFn = instance.substring(begIdx + 1, endIdx);
                    String evaluated = interpretExpression(innerFn);
                    instance = instance.substring(0, begIdx) + evaluated + instance.substring(endIdx + 1);

                }
                instance = interpretExpression(instance);
            }

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

        while (!expr.matches("-?\\d*\\.?\\d+")) {
            while (expr.contains("^"))
                expr = doOperation(expr, expr.indexOf("^"));

            System.out.println("| ^: " + expr);

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

            System.out.println("| */: " + expr);

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

            System.out.println("| +-: " + expr + " ✔");
        }
        return expr;
    }

    private String doOperation(String fn, int i) {
        int begIdx, endIdx;
        for (begIdx = i; begIdx != 0 && (fn.substring(begIdx - 1, begIdx).matches("[\\d.]") ||
                (begIdx > 1 && fn.substring(begIdx - 1, begIdx).equals("-") && fn.substring(begIdx - 2, begIdx - 1).equals("-"))); begIdx--) {}
        for (endIdx = i; endIdx != fn.length() - 1 && (fn.substring(endIdx + 1, endIdx + 2).matches("[\\d.]") ||
                (fn.substring(endIdx + 1, endIdx + 2).equals("-") && endIdx == i)); endIdx++) {}
        String str1 = fn.substring(begIdx, i);
        System.out.println("from " + begIdx + " to " + i);
        double num1;
        try {
            num1 = Double.parseDouble(str1);
        } catch (NumberFormatException e) {
           num1 = 0;
        }

        String str2 = fn.substring(i + 1, endIdx + 1);
        System.out.println("from " + (i + 1) + " to " + (endIdx + 1));
        double num2;
        try {
            num2 = Double.parseDouble(str2);
        } catch (NumberFormatException e) {
            num2 = 0;
        }

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
        return fn.substring(0, begIdx) + result + fn.substring(endIdx + 1);
    }

    public String toString() {
        String out = "";
        for (int i = faceFeatures.length-1; i >= 0; i--) {
            for (int j = 0; j < faceFeatures[i].length; j++)
                out += faceFeatures[i][j];
            out += "\n";
        }
        return out;
    }
}
