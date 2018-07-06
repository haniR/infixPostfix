package project.pkg2_infixpostfix;
/*
 Name : Mohammad Sadeq Nairat
 ID : 1130361
 */

import java.math.*;
import java.util.regex.Matcher;

public class Expression {

    //class properties declaration
    private final int PLUS = 1, MUL = 2, POW = 3;//precedence level
    private String data;//raw data
    private String result = "";//result
    private Stack<Character> stak = new Stack<Character>();//operator stack
    private Stack<Double> valueStak = new Stack<Double>();//operand stack

    //public constructor
    public Expression(String exp) {
        if (exp.startsWith("-") || exp.startsWith("+")) {
            exp = "0" + exp;
        }
        this.data = "(" + exp + ")"; //case validation
        data = data.replace("+-", "-");
        data = data.replace("++", "+");
        data = data.replace("-+", "-");
        data = data.replace("--", "+");
        data = data.replace("*-(", "*-1*(");
        data = data.replace("/-(", "/-1/(");
        data = data.replace("(-", "(0-");
        this.data = fixIt(this.data);
    }

    //a method to fixe the expression when an operetor collides with a sign
    public String fixIt(String data) {
        boolean ob = false;
        String fin = "";
        for (int i = 0; i < data.length(); i++) {
            if ((data.charAt(i) == '*' || data.charAt(i) == '/') && data.charAt(i + 1) == '-') {

                fin += data.charAt(i) + "(0";
                ob = true;

            } else {
                if (!ob) {
                    fin += "" + data.charAt(i);
                } else {
                    if (data.charAt(i) == '-') {
                        fin += "" + data.charAt(i);
                    } else {
                        if (isOperator(data.charAt(i))) {
                            fin += ")" + data.charAt(i);
                            ob = false;
                        } else {
                            fin += "" + data.charAt(i);
                        }
                    }
                }
            }
        }
        return fin;
    }

    //a method that converts an Equation from infix to postfix
    public void toPostFix() {
        String val = "";
        int len = this.data.length();

        for (int i = 0; i < len; i++) {//loop through the infix equation
            if (isOperator(data.charAt(i))) {//check if it's an operator
                val = "";//reset value
                handler(data.charAt(i));//handle the current operator
            } else {//is a digit
                val += data.charAt(i);//append to value
                if (i < data.length()) {
                    if (isOperator(data.charAt(i + 1))) {//if the next is an operator
                        this.result += val + "#";//to seperate values
                    }
                }
            }
        }
        while (!stak.isEmpty()) {//this will pop everything left in the stack
            this.result += stak.pop().getData() + "";//and append it to result
        }
    }

    //return raw inFix data
    public String getData() {
        //this will reset it to it's original form
        return data.replaceAll("[()]", "");
    }

    //handle operator
    public void handler(char x) {
        int proc = getProc(x);//get procedence of current operator

        if (stak.isEmpty()) {//push operator if stack is empty
            stak.push(x);
        } else {//continue pushing and popping operators based on the algorithim
            if (x == '(') {//open bracket: push it
                stak.push(x);
            } else if (x == ')') {//closed bracket: keep popping to open bracket
                while (stak.peak().getData() != '(') {
                    this.result += stak.pop().getData() + "#";//seperate operators
                }
                stak.pop();//get rid of the bracket
            } else {//operator other than bracket
                while (!stak.isEmpty()) {
                    if (proc > getProc(stak.peak().getData())//check precedence
                            || stak.peak() == null) {//if it's higher
                        stak.push(x);//push it
                        break;
                    } else {//has lower precedence
                        this.result += stak.pop().getData() + "#";//pop it
                    }
                }
                if (stak.peak() == null) {//if stack is empty
                    stak.push(x);//push it
                }
            }

        }
    }

    //this will evaluate the equation from postFix notation
    public Double evaluate() {
        Double len, res = 0.0, num1 = 0.0, num2 = 0.0;
        //split result into correct operands and operators
        String[] postArr = this.result.split("#");
        len = (double) postArr.length;
        for (int i = 0; i < len; i++) {//loop through expression

            if (!isOperatorStr(postArr[i])) {//if it's an operand
                valueStak.push(Double.parseDouble(postArr[i]));//push it
            } else if (isOperatorStr(postArr[i])) {//if its an operator
                if (!this.valueStak.isEmpty()) {
                    switch (postArr[i]) {//cover operator cases
                        case "+"://case plus
                            //pop 2 values and add them
                            num1 = valueStak.pop().getData();
                            num2 = valueStak.pop().getData();
                            res = num2 + num1;
                            valueStak.push(res);//push back the result
                            break;
                        case "-"://case minus
                            //pop 2 values and subtract them
                            num1 = valueStak.pop().getData();
                            num2 = valueStak.pop().getData();
                            res = num2 - num1;
                            valueStak.push(res);//push back the result
                            break;
                        case "*"://case multiply
                            //pop 2 values and multiply them
                            num1 = valueStak.pop().getData();
                            num2 = valueStak.pop().getData();
                            res = num2 * num1;
                            valueStak.push(res);//push back the result
                            break;
                        case "/"://case divide
                            //pop 2 values and divide them
                            num1 = valueStak.pop().getData();
                            num2 = valueStak.pop().getData();
                            res = num2 / num1;
                            valueStak.push(res);//push back the result
                            break;
                        case "^"://case power
                            //pop 2 values and evaluate 
                            num1 = valueStak.pop().getData();
                            num2 = valueStak.pop().getData();
                            //result is value 2 to the power value 1
                            res = Math.pow(num2, num1);
                            valueStak.push(res);//push back the result
                            break;

                    }
                }

            }

        }
        return res;//return the final value
    }

    //method that checks precedence
    public int getProc(Character x) {
        int proc = 0;
        switch (x) {//simple case check for passed character
            //set precedence level
            case '^':
                proc = POW;
                break;
            case '*':
            case '/':
                proc = MUL;
                break;
            case '+':
            case '-':
                proc = PLUS;
                break;

        }
        return proc;//return level of precedence
    }

    //method to check if operator (Character)
    private boolean isOperator(char x) {
        return (x == '^' || x == ')' || x == '(' || x == '/' || x == '*'
                || x == '-' || x == '+');
    }

    //method to check if operator (String)
    private boolean isOperatorStr(String x) {
        return (x.equals("^") || x.equals("(") || x.equals(")")
                || x.equals("-") || x.equals("+") || x.equals("*") || x
                .equals("/"));
    }

    //returns the true form of postFix
    public String getResult() {
        return result.replaceAll("#", "");
    }

}
