import java.util.ArrayDeque;
import java.util.Deque;

public class Problem3_QuizScoringFormula {

    // Single left-to-right scan with an operand stack and an operator stack.
    // Before pushing an operator, apply every stacked operator of greater or equal
    // precedence (stopping at '('), which gives * / priority over + - and keeps
    // equal precedence left to right. Each token is pushed and popped once
    // -> O(n) time, O(n) space.
    static int evaluate(String expr) {

        Deque<Integer> operands = new ArrayDeque<>();
        Deque<Character> operators = new ArrayDeque<>();

        int i = 0;
        int n = expr.length();

        while (i < n) {

            char c = expr.charAt(i);

            if (c == ' ') {
                i++;
            } else if (Character.isDigit(c)) {
                // Read a multi-digit number.
                int value = 0;
                while (i < n && Character.isDigit(expr.charAt(i))) {
                    value = value * 10 + (expr.charAt(i) - '0');
                    i++;
                }
                operands.push(value);
            } else if (c == '(') {
                operators.push(c);
                i++;
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    applyTop(operands, operators);
                }
                operators.pop();    // discard the matching '('
                i++;
            } else {
                while (!operators.isEmpty() && operators.peek() != '('
                        && precedence(operators.peek()) >= precedence(c)) {
                    applyTop(operands, operators);
                }
                operators.push(c);
                i++;
            }
        }

        while (!operators.isEmpty()) {
            applyTop(operands, operators);
        }

        return operands.pop();
    }

    static int precedence(char op) {
        return (op == '*' || op == '/') ? 2 : 1;
    }

    static void applyTop(Deque<Integer> operands, Deque<Character> operators) {

        char op = operators.pop();
        int right = operands.pop();    // popped first: order matters for - and /
        int left = operands.pop();

        switch (op) {
            case '+':
                operands.push(left + right);
                break;
            case '-':
                operands.push(left - right);
                break;
            case '*':
                operands.push(left * right);
                break;
            default:
                operands.push(left / right);
        }
    }

    public static void main(String[] args) {

        System.out.println(evaluate("2 * (3 + 4) - 10 / 5"));
        System.out.println(evaluate("(8 - 2) * (1 + 2) / 4"));
    }
}
