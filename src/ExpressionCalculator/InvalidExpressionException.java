package ExpressionCalculator;
/**
 * Класс обробатывающий исключения
 * */
public class InvalidExpressionException extends Exception {
    public InvalidExpressionException(String message) {
        super(message);
    }
}