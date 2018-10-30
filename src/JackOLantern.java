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
        graphFunction(str, fn, 0, faceFeatures[0].length);
    }

    public void graphFunction(String str, String fn, int min, int max) {
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
            // Check if i is in specified domain

            if (!(i >= min && i <= max))
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
                instance = instance.substring(0, begIdx) + evaluateExpression(innerFn) + instance.substring(endIdx + 1);
                if (instance.contains("NaN"))
                    break;
            }
            instance = evaluateExpression(instance);

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

    private String evaluateExpression(String expr) {
        while (expr.contains("^"))
            expr = doOperation(expr, expr.indexOf("^"));

        // Catch NaNs from negatives under the radical

        if (expr.contains("NaN"))
            return "NaN";

        while (expr.contains("*") || expr.contains("/")) {
            int mulIdx = expr.indexOf("*");
            int divIdx = expr.indexOf("/");
            int i;
            if (mulIdx < 0)
                i = divIdx;
            else if (divIdx < 0)
                i = mulIdx;
            else
                i = Math.min(expr.indexOf("*"),expr.indexOf("/"));
            expr = doOperation(expr, i);
        }

        while (expr.matches(".*(\\+|\\d-).*")) {
            int plusIdx = expr.indexOf("+");
            int minusIdx = expr.indexOf("-");
            int i;
            if (plusIdx < 0)
                i = minusIdx;
            else if (minusIdx < 1 || !expr.substring(minusIdx - 1, minusIdx).matches("\\d"))
                i = plusIdx;
            else
                i = Math.min(expr.indexOf("+"), expr.indexOf("-"));
            expr = doOperation(expr, i);
        }

        if (expr.length() > 1 && expr.substring(0, 2).equals("--")) {
            expr = expr.substring(2);
        }
        return expr;
    }

    private String doOperation(String expr, int i) {
        int begIdx, endIdx;

        // Get doubles beside operator

        for (begIdx = i; begIdx != 0; begIdx--) {
            String nxtChar = expr.substring(begIdx - 1, begIdx);
            if (nxtChar.matches("[\\d.]"))
                continue;
            if (nxtChar.equals("-"))
                begIdx--;
            break;
        }
        for (endIdx = i; endIdx != expr.length() - 1 && (expr.substring(endIdx + 1, endIdx + 2).matches("[\\d.]") ||
                (expr.substring(endIdx + 1, endIdx + 2).equals("-") && endIdx == i)); endIdx++) {}
        double a, b;
        try {
            a = Double.parseDouble(expr.substring(begIdx, i));
        } catch (NumberFormatException e) {
           a = 0;
        }

        try {
            b = Double.parseDouble(expr.substring(i + 1, endIdx + 1));
        } catch (NumberFormatException e) {
            b = 0;
        }

        // Perform operation on doubles

        double result;
        switch (expr.substring(i, i + 1)) {
            case "^":
                result = Math.pow(a, b);
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            default:
                result = 0;
                break;
        }

        // Add "+" before evaluated expression if it would otherwise merge with a number before it
        // (Occurs with negative numbers)

        if (begIdx > 0 && expr.substring(begIdx - 1, begIdx).matches("\\d")) {
            expr = expr.substring(0, begIdx) + "+" + result + expr.substring(endIdx + 1);
        } else
            expr = expr.substring(0, begIdx) + result + expr.substring(endIdx + 1);

        return expr;
    }

    public String toString() {
        String out = "";
        for (int i = faceFeatures.length-1; i >= 0; i--) {
            for (int j = 0; j < faceFeatures[i].length; j++)
                out += " " + faceFeatures[i][j];
            out += "\n";
        }
        return out;
    }
}
