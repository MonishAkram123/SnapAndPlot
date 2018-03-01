package com.example.monishakram.snapandplot;

/*
 * This Class Will Compute the Value of the Expression.
 * It Holds functions to Transform Infix to Reverse Polish Notation and also
 * Computes the value of a proper PostFix Expression.
 */

import java.util.Stack;
class Conversion{
    private static Stack< Double > operands = new Stack<>();
    private static Stack< String > operators = new Stack<>();
    private static String[] tokens;
    private static boolean isOperator(String token){
        boolean isOperator = false;
        switch(token){
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
            case "^":
            case "(":
            case ")":
            case "sin":
            case "cos":
            case "tan":
            case "log":
                isOperator = true;
        }
        return isOperator;
    }

    private static short precedence(String token){
        short value = 0;
        switch(token){
            case "(":
            case ")":
                value = 1;break;
            case "+":
            case "-":
                value = 2;break;
            case "*":
            case "/":
            case "%":
                value = 3;break;
            case "^":
                value = 4;break;
            case "sin":
            case "cos":
            case "tan":
            case "log":
                value = 5;
        }
        return value;
    }

    static String infixToPostFix(String expression){
        tokens = expression.split(" ");
        StringBuilder result = new StringBuilder();
        for(String token: tokens){
            if(isOperator(token)){
                if(!token.equals(")")){
                    if(!token.equals("("))
                        while(!operators.isEmpty() && precedence(operators.peek()) >= precedence(token))
                            result.append(operators.pop()).append(" ");
                    operators.push(token);
                }
                else{
                    while(!operators.empty()){
                        if(operators.peek().equals("(")){
                            operators.pop();
                            break;
                        }
                        else
                            result.append(operators.pop()).append(" ");
                    }
                }
            }
            else
                result.append(token).append(" ");
        }
        while(!operators.empty()){
            result.append(operators.pop()).append(" ");
        }
        return result.toString();
    }

    private static double performOperation(double a, double b, String operator){
        double result = 0;
        try{
            switch(operator){
                case "+":
                    result = b +a;break;
                case "-":
                    result = b -a;
                    break;
                case "*":
                    result = b *a;break;
                case "/":
                    result = b /a;break;
                case "^":
                    result = Math.pow(b, a);break;
                case "%":
                    result =  b % (long)a;break;
                case "sin":

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private static double performOperation(double x, String token){
        double result = 0;
        try{
            switch(token){
                case "sin":
                    result = Math.sin(x); break;
                case "cos":
                    result = Math.cos(x); break;
                case "tan":
                    result = Math.tan(x); break;
                case "log":
                    result = Math.log(x); break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private static boolean isBinaryOperator(String operator){
        boolean isBinary = true;
        switch(operator){
            case "sin":
            case "cos":
            case "tan":
            case "log":
                isBinary = false;
        }
        return isBinary;
    }

    static double computePostFixExpression(String expression, double x) throws Exception{
        tokens = expression.split(" ");
        for(String token: tokens)
            if(!isOperator(token)) {
                switch (token) {
                    case "":
                        continue;
                    case "x":
                        operands.push(x);
                        break;
                    case "e":
                        operands.push(Math.E);
                        break;
                    case "pi":
                        operands.push(Math.PI);
                        break;
                    default:
                        operands.push(Double.parseDouble(token));
                        break;
                }
            }
            else if(isBinaryOperator(token))
                operands.push(performOperation(operands.pop(), operands.pop(), token));
            else
                operands.push(performOperation(operands.pop(), token));
        return operands.pop();
    }
}
