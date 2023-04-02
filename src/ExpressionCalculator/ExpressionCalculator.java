package ExpressionCalculator;
import java.util.Stack;

/**
 * Класс описывающий калькулятор выражений
 * */
public class ExpressionCalculator {
    /** Функция производящая парсинг и вычисление выражения
     * @param expression строка содержащая выражение
     * @return результат вычисления
     */
    public static double calculateExpression(String expression) throws InvalidExpressionException {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch)) {
                StringBuilder num = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    num.append(expression.charAt(i++));
                }
                i--;
                values.push(Double.parseDouble(num.toString()));
            } else if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            } else if (isOperator(ch)) {
                while (!operators.empty() && hasPrecedence(ch, operators.peek())) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(ch);
            } else {
                throw new InvalidExpressionException("Invalid expression");
            }
        }
        while (!operators.empty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }

    /** Функция проверяющая является ли символ одним из действий(+, -, *, /)
     * @param ch символ
     * @return является символ действием или нет
     */
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    /** Функция определяющая приотитет выполнения действий(+, -, *, /)
     * @param op1 первое действие
     * @param op2 второе действие
     */
    private static boolean hasPrecedence(char op1, char op2) {
        boolean result = true;
        if (op2 == '(' || op2 == ')') {
            result = false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            result = false;
        }
        return result;
    }

    /** Функция вычисляющая результат действия с числами а и b
     * @param operator первое действие
     * @param b число
     * @param a число
     * @return результат действия
     */
    private static double applyOperator(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}

