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
        graphFunction(str, fn, new int[]{0, faceFeatures[0].length});
    }

    public void graphFunction(String str, String fn, int[] domain) {
        // Remove whitespace

        String[] terms = fn.split(" ");
        fn = String.join("", terms);

        // Determine  values and remove dependent variable and equal sign

        int values;
        if (fn.substring(0, 1).equals("y"))
            values = faceFeatures[0].length;
        else
            values = faceFeatures.length;
        fn = fn.substring(2);

        for (int i = 0; i < values; i++) {
            if (!(i >= domain[0] && i <= domain[1]))
                continue;

            String instance = fn;

            // Substitute variables with i

            if (instance.substring(0, 1).matches("x"))
                instance = "(" + i + ")" + instance.substring(1);
            if (instance.substring(instance.length() - 1).matches("x"))
                instance = instance.substring(0, instance.length() - 1) + "(" + i + ")";
            instance = String.join("(" + i + ")", instance.split("x"));

            // Evaluate expression first with parenthesis, from innermost to outermost

            while (instance.contains("(")) {
                int begIdx = instance.lastIndexOf("(");
                int endIdx = begIdx + instance.substring(begIdx).indexOf(")");
                String innerFn = instance.substring(begIdx + 1, endIdx);
                String evaluated = interpretExpression(innerFn);
                instance = instance.substring(0, begIdx) + evaluated + instance.substring(endIdx + 1);
                if (evaluated.contains("NaN"))
                    break;
            }
            instance = interpretExpression(instance);

            // Skip NaN

            if (instance.contains("NaN"))
                continue;

            // Convert final String to double

            int result = (int)Math.round(Double.parseDouble(instance));

            // Edit string array value

            try {
                if (terms[0].equals("y"))
                    edit(str, result, i);
                else
                    edit(str, i, result);
            } catch (ArrayIndexOutOfBoundsException e) {}
        }
    }

    private String interpretExpression(String expr) {
        while (expr.contains("^"))
            expr = doOperation(expr, expr.indexOf("^"));

        if (expr.contains("NaN"))
            return "NaN";

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
        return expr;
    }

    private String doOperation(String fn, int i) {
        int begIdx, endIdx;
        for (begIdx = i; begIdx != 0; begIdx--) {
            String nxtChar = fn.substring(begIdx - 1, begIdx);
            if (nxtChar.matches("[\\d.]"))
                continue;
            if (nxtChar.equals("-"))
                begIdx--;
            break;
        }
        for (endIdx = i; endIdx != fn.length() - 1 && (fn.substring(endIdx + 1, endIdx + 2).matches("[\\d.]") ||
                (fn.substring(endIdx + 1, endIdx + 2).equals("-") && endIdx == i)); endIdx++) {}
        String str1 = fn.substring(begIdx, i);
        double num1;
        try {
            num1 = Double.parseDouble(str1);
        } catch (NumberFormatException e) {
           num1 = 0;
        }

        String str2 = fn.substring(i + 1, endIdx + 1);
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
                result = 0;
                break;
        }
        if (begIdx != 0 && fn.substring(begIdx - 1, begIdx).matches("\\d")) {
            fn = fn.substring(0, begIdx) + "+" + result + fn.substring(endIdx + 1);
        } else
            fn = fn.substring(0, begIdx) + result + fn.substring(endIdx + 1);

        return fn;
    }

    public String toString() {
        String out = "";
        for (int i = faceFeatures.length-1; i >= 0; i--) {
            for (int j = 0; j < faceFeatures[i].length; j++)
                out += " " + faceFeatures[i][j] + "";
            out += "\n";
        }
        return out;
    }
}
