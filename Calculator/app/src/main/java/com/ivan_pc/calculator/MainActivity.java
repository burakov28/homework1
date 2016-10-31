package com.ivan_pc.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int ACCURACY = 100;
    private static final int ANSWER_LENGTH = 18;

    static char[] binOps = new char[]{ '+', '*', '/', '^', '+'};
    static char[] mathConst = new char[]{'e', '\u03c0'};
    static char[] numbers = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    int pointer;

    static boolean findInArr(char[] arr, char c) {
        for (char chr : arr) {
            if (chr == c) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView line = (TextView) findViewById(R.id.TextLine);
        TextView upLine = (TextView) findViewById(R.id.TextUpLine);
        if (savedInstanceState != null) {
            line.setText(savedInstanceState.getCharSequence("line"));
            upLine.setText(savedInstanceState.getCharSequence("upLine"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TextView line = (TextView) findViewById(R.id.TextLine);
        TextView upLine = (TextView) findViewById(R.id.TextUpLine);
        outState.putCharSequence("line", line.getText());
        outState.putCharSequence("upLine", upLine.getText());
        super.onSaveInstanceState(outState);
    }

    public void deleteAllPressed(View view) {
        TextView line = (TextView) findViewById(R.id.TextLine);
        line.setText("");
    }

    public void deleteOnePressed(View view) {
        TextView line = (TextView) findViewById(R.id.TextLine);
        if (line.getText().toString().equals(getString(R.string.Error))) {
            line.setText("");
            return;
        }

        StringBuilder str = new StringBuilder(line.getText());
        if (str.length() != 0) {
            str.deleteCharAt(str.length() - 1);
            line.setText(str);
        }
    }

    public void numberPressed(View view) {
        Button num = (Button) view;
        TextView line = (TextView) findViewById(R.id.TextLine);
        if (line.getText().toString().equals(getString(R.string.Error))) {
            line.setText("");
        }
        StringBuilder str = new StringBuilder(line.getText());
        if (str.length() == 0) {
            line.setText(num.getText());
            return;
        }
        char last = str.charAt(str.length() - 1);
        if (findInArr(mathConst, last) || last == getString(R.string.ParenthesisRight).charAt(0)) {
            line.append(getString(R.string.Multiplication));
        }
        line.append(num.getText());
    }

    public void pointPressed(View view) {
        TextView line = (TextView) findViewById(R.id.TextLine);

        if (line.getText().toString().equals(getString(R.string.Error))) {
            line.setText("");
        }

        if (line.length() == 0) {
            line.append(getString(R.string.Number0));
            line.append(getString(R.string.Point));
            return;
        }
        StringBuilder str = new StringBuilder(line.getText());
        int cnt = 0;
        for (int i = str.length() - 1; i >= 0; --i) {
            if (str.charAt(i) == getString(R.string.Point).charAt(0)) {
                ++cnt;
                continue;
            }
            if (!findInArr(numbers, str.charAt(i))) {
                break;
            }
        }

        if (cnt > 0) return;

        char last = str.charAt(str.length() - 1);
        if (findInArr(numbers, last)) {
            line.append(getString(R.string.Point));
            return;
        }
        if (findInArr(mathConst, last) || last == getString(R.string.ParenthesisRight).charAt(0)) {
            line.append(getString(R.string.Multiplication));
        }
        line.append(getString(R.string.Number0));
        line.append(getString(R.string.Point));
    }


    public void binaryOperationPressed(View view) {
        TextView line = (TextView) findViewById(R.id.TextLine);

        if (line.getText().toString().equals(getString(R.string.Error))) {
            line.setText("");
        }

        if (line.length() == 0) {
            return;
        }

        StringBuilder str = new StringBuilder(line.getText());
        char last = str.charAt(str.length() - 1);
        Button button = (Button) view;

        if (findInArr(binOps, last)) {
            str.deleteCharAt(str.length() - 1);
            str.append(button.getText());
            line.setText(str);
            return;
        }
        if (findInArr(numbers, last) || findInArr(mathConst, last) ||
                last == getString(R.string.Point).charAt(0) ||
                last == getString(R.string.ParenthesisRight).charAt(0)) {
            line.append(button.getText());
        }
    }


    public void mathConstPressed(View view) {
        Button button = (Button) view;
        TextView line = (TextView) findViewById(R.id.TextLine);

        if (line.getText().toString().equals(getString(R.string.Error))) {
            line.setText("");
        }

        if (line.length() == 0) {
            line.setText(button.getText());
            return;
        }

        StringBuilder str = new StringBuilder(line.getText());
        char last = str.charAt(str.length() - 1);
        if (findInArr(mathConst, last) || findInArr(numbers, last) ||
                last == getString(R.string.Point).charAt(0) ||
                last == getString(R.string.ParenthesisRight).charAt(0)) {
            line.append(getString(R.string.Multiplication));
        }
        line.append(button.getText());
    }

    public void unaryOperationPressed(View view) {
        TextView line = (TextView) findViewById(R.id.TextLine);
        Button button = (Button) view;

        if (line.getText().toString().equals(getString(R.string.Error))) {
            line.setText("");
        }
        if (line.length() == 0) {
            line.append(button.getText());
            return;
        }

        StringBuilder str = new StringBuilder(line.getText());
        char last = str.charAt(str.length() - 1);

        if (findInArr(mathConst, last) || findInArr(numbers, last) ||
                last == getString(R.string.Point).charAt(0) ||
                last == getString(R.string.ParenthesisRight).charAt(0)) {
            line.append(getString(R.string.Multiplication));
        }
        line.append(button.getText());
    }

    public void minusPressed(View view) {
        TextView line = (TextView) findViewById(R.id.TextLine);

        if (line.getText().toString().equals(getString(R.string.Error))) {
            line.setText("");
        }
        if (line.length() == 0) {
            line.append(getString(R.string.Minus));
            return;
        }

        StringBuilder str = new StringBuilder(line.getText());
        char last = str.charAt(str.length() - 1);

        if (findInArr(binOps, last)) {
            str.deleteCharAt(str.length() - 1);
        }
        str.append(getString(R.string.Minus));
        line.setText(str);
    }

    public void powerOperationPressed(View view) {
        TextView line = (TextView) findViewById(R.id.TextLine);

        if (line.getText().toString().equals(getString(R.string.Error))) {
            line.setText("");
        }
        if (line.length() == 0) {
            return;
        }

        StringBuilder str = new StringBuilder(line.getText());
        char last = str.charAt(str.length() - 1);

        if (findInArr(binOps, last)) {
            str.deleteCharAt(str.length() - 1);
            str.append(getString(R.string.signPower));
            line.setText(str);
            return;
        }
        if (findInArr(numbers, last) || findInArr(mathConst, last) ||
                last == getString(R.string.Point).charAt(0) ||
                last == getString(R.string.ParenthesisRight).charAt(0)) {
            line.append(getString(R.string.signPower));
        }
    }

    public void sqrPressed(View view) {
        TextView line = (TextView) findViewById(R.id.TextLine);

        if (line.getText().toString().equals(getString(R.string.Error))) {
            line.setText("");
        }

        if (line.length() == 0) {
            return;
        }

        StringBuilder str = new StringBuilder(line.getText());
        char last = str.charAt(str.length() - 1);

        StringBuilder app = new StringBuilder(getString(R.string.signPower));
        app.append(getString(R.string.ParenthesisLeft));
        app.append(getString(R.string.Number2));
        app.append(getString(R.string.ParenthesisRight));

        if (findInArr(binOps, last)) {
            str.deleteCharAt(str.length() - 1);
            str.append(app);
            line.setText(str);
            return;
        }
        if (findInArr(numbers, last) || findInArr(mathConst, last) ||
                last == getString(R.string.Point).charAt(0) ||
                last == getString(R.string.ParenthesisRight).charAt(0)) {
            line.append(app);
        }
    }

    public void parenthesisLeftPressed(View view) {
        TextView line = (TextView) findViewById(R.id.TextLine);

        if (line.getText().toString().equals(getString(R.string.Error))) {
            line.setText("");
        }

        String par = getString(R.string.ParenthesisLeft);
        if (line.length() == 0) {
            line.append(par);
            return;
        }

        StringBuilder str = new StringBuilder(line.getText());
        char last = str.charAt(str.length() - 1);
        if (findInArr(numbers, last) || findInArr(mathConst, last) ||
                last == getString(R.string.Point).charAt(0) ||
                last == getString(R.string.ParenthesisRight).charAt(0)) {
            line.append(getString(R.string.Multiplication));
        }
        line.append(par);
    }

    public void parenthesisRightPressed(View view) {
        TextView line = (TextView) findViewById(R.id.TextLine);

        if (line.getText().toString().equals(getString(R.string.Error))) {
            line.setText("");
        }

        StringBuilder str = new StringBuilder(line.getText());
        int bal = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == getString(R.string.ParenthesisRight).charAt(0)) bal -= 1;
            if (str.charAt(i) == getString(R.string.ParenthesisLeft).charAt(0)) bal += 1;
        }

        if (bal > 0) {
            char last = str.charAt(str.length() - 1);
            if (findInArr(numbers, last) || findInArr(mathConst, last) ||
                    last == getString(R.string.Point).charAt(0) ||
                    last == getString(R.string.ParenthesisRight).charAt(0)) {
                line.append(getString(R.string.ParenthesisRight));
            }
        }
    }

    ArrayList<StringBuilder> tokenizer(StringBuilder str) {
        ArrayList<StringBuilder> ret = new ArrayList<>();

        for (int i = 0; i < str.length(); ) {
            StringBuilder token = new StringBuilder();
            while (i < str.length() && (findInArr(numbers, str.charAt(i)) || str.charAt(i) == '.')) {
                token.append(str.charAt(i));
                ++i;
            }
            if (token.length() > 0) {
                ret.add(token);
                continue;
            }
            char cur = str.charAt(i);
            if (findInArr(mathConst, cur) || findInArr(binOps, cur) ||
                    cur == getString(R.string.ParenthesisRight).charAt(0) ||
                    cur == getString(R.string.ParenthesisLeft).charAt(0) ||
                    cur == getString(R.string.sqrt).charAt(0) ||
                    cur == getString(R.string.Minus).charAt(0)) {
                token.append(cur);
                ret.add(token);
                ++i;
                continue;
            }

            if (cur == getString(R.string.MathSIN).charAt(0) ||
                    cur == getString(R.string.MathTAN).charAt(0) ||
                    cur == getString(R.string.MathCOS).charAt(0)) {
                for (int j = 0; j < 3; ++j) {
                    token.append(str.charAt(i + j));
                }
                ret.add(token);
                i += 3;
                continue;
            }

            if (cur == getString(R.string.MathLG).charAt(0) ||
                    cur == getString(R.string.MathLN).charAt(0)) {
                for (int j = 0; j < 2; ++j) {
                    token.append(str.charAt(i + j));
                }
                ret.add(token);
                i += 2;
            }
        }
        return ret;
    }

    BigDecimal toNumber(StringBuilder str) throws Exception {
        Log.d(LOG_TAG, Integer.valueOf(pointer).toString());
        if (str.length() == 0 ||
                (str.charAt(0) != getString(R.string.Point).charAt(0) &&
                        (str.charAt(0) < numbers[0] || str.charAt(0) > numbers[9]))) {
            return null;
        }

        int cnt = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == getString(R.string.Point).charAt(0)) {
                ++cnt;
            }
        }
        if (cnt > 1) return null;
        Log.d(LOG_TAG, str.toString());
        return new BigDecimal(str.toString());
    }

    BigDecimal getUnary(ArrayList<StringBuilder> tokens) throws Exception {
        if (pointer == tokens.size()) return null;
        String op = tokens.get(pointer).toString();
        if (op.equals(getString(R.string.ParenthesisLeft))) {
            ++pointer;
            BigDecimal ret = getSum(tokens);
            if (pointer == tokens.size()) return null;
            op = tokens.get(pointer).toString();
            if (!op.equals(getString(R.string.ParenthesisRight))) {
                return null;
            }
            ++pointer;
            return ret;
        }

        if (op.equals(getString(R.string.MathE))) {
            ++pointer;
            return new BigDecimal(Math.E);
        }
        if (op.equals((getString(R.string.MathPI)))) {
            ++pointer;
            return new BigDecimal((Math.PI));
        }
        if (op.equals(getString(R.string.Minus))) {
            ++pointer;
            BigDecimal ans = getUnary(tokens);
            if (ans == null) return null;
            return ans.negate();
        }

        if (op.equals(getString(R.string.MathCOS)) || op.equals(getString(R.string.MathSIN)) ||
                op.equals(getString(R.string.MathTAN)) || op.equals(getString(R.string.MathLN)) ||
                op.equals(getString(R.string.MathLG))) {
            ++pointer;
            BigDecimal arg = getUnary(tokens);
            if (arg == null) return null;
            if (op.equals(getString(R.string.MathCOS))) {
                Double cans = Math.cos(arg.doubleValue());
                if (cans.isNaN()) return null;
                return new BigDecimal(cans);
            }
            if (op.equals(getString(R.string.MathSIN))) {
                Double cans = Math.sin(arg.doubleValue());
                if (cans.isNaN()) return null;
                return new BigDecimal(cans);
            }
            if (op.equals(getString(R.string.MathTAN))) {
                Double cans = Math.tan(arg.doubleValue());
                if (cans.isNaN()) return null;
                return new BigDecimal(cans);
            }
            if (op.equals(getString(R.string.MathLN))) {
                Double cans = Math.log(arg.doubleValue());
                if (cans.isNaN()) return null;
                return new BigDecimal(cans);
            }
            if (op.equals(getString(R.string.MathLG))) {
                Double cans = Math.log10(arg.doubleValue());
                if (cans.isNaN()) return null;
                return new BigDecimal(cans);
            }
        }

        if (op.equals(getString(R.string.sqrt))) {
            ++pointer;
            BigDecimal arg = getUnary(tokens);
            if (arg == null) return null;
            Double cans = Math.sqrt(arg.doubleValue());
            if (cans.isNaN()) return null;
            return new BigDecimal(cans);
        }

        BigDecimal ret = toNumber(tokens.get(pointer));
        if (ret == null) return null;
        ++pointer;
        return ret;
    }

    BigDecimal getPower(ArrayList<StringBuilder> tokens) throws Exception {
        BigDecimal left = getUnary(tokens);
        if (left == null) return null;
        if (pointer == tokens.size()) return left;
        String op = tokens.get(pointer).toString();
        if (!op.equals(getString(R.string.signPower))) {
            return left;
        }
        pointer++;
        BigDecimal right = getPower(tokens);
        if (right == null) return null;
        Double cans = Math.pow(left.doubleValue(), right.doubleValue());
        Log.d(LOG_TAG, cans.toString());
        if (cans.isNaN()) return null;
        return new BigDecimal(cans);
    }

    BigDecimal getMult(ArrayList<StringBuilder> tokens) throws Exception {
        BigDecimal left = getPower(tokens);
        if (left == null) return null;
        if (pointer == tokens.size()) return left;
        String op = tokens.get(pointer).toString();
        while (pointer < tokens.size() && (op.equals(getString(R.string.division)) ||
                op.equals(getString(R.string.Multiplication)))) {
            ++pointer;
            Log.d(LOG_TAG, left.toPlainString());
            BigDecimal right = getPower(tokens);
            if (right == null) return null;
            if (op.equals(getString(R.string.Multiplication))) {
                left = left.multiply(right);
            } else {
                left = left.divide(right, ACCURACY, BigDecimal.ROUND_HALF_EVEN);
            }

            Log.d(LOG_TAG, left.toPlainString());
            if (pointer < tokens.size()) {
                op = tokens.get(pointer).toString();
            }
        }
        return left;
    }

    BigDecimal getSum(ArrayList<StringBuilder> tokens) throws Exception {
        BigDecimal left = getMult(tokens);

        //Log.d(LOG_TAG, left.toPlainString());
        if (left == null) return null;
        if (pointer == tokens.size()) return left;
        String op = tokens.get(pointer).toString();

        if (!op.equals(getString(R.string.Minus)) && !op.equals(getString(R.string.Plus))) {
            //Log.d(LOG_TAG, "porter");
            return left;
        }
        ++pointer;
        BigDecimal right = getSum(tokens);
        if (right == null) return null;
        if (op.equals(getString(R.string.Plus))) return left.add(right);
        return left.subtract(right);
    }

    BigDecimal calculate(StringBuilder str) throws Exception {
        ArrayList<StringBuilder> tokens = tokenizer(str);
        for (StringBuilder s : tokens) {
            Log.d(LOG_TAG, s.toString());
        }
        pointer = 0;
        BigDecimal ans;
        //ans = getSum(tokens);
        //return ans;

        try {
            ans = getSum(tokens);
            return ans;
        } catch (Exception e) {
            Log.d(LOG_TAG, "porno");
            return null;
        }

    }


    public void equationPressed(View view) throws Exception {
        TextView line = (TextView) findViewById(R.id.TextLine);
        StringBuilder str = new StringBuilder(line.getText());
        int bal = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == getString(R.string.ParenthesisRight).charAt(0)) bal -= 1;
            if (str.charAt(i) == getString(R.string.ParenthesisLeft).charAt(0)) bal += 1;
        }

        while (bal > 0) {
            str.append(getString(R.string.ParenthesisRight));
            bal -= 1;
        }

        TextView upLine = (TextView) findViewById(R.id.TextUpLine);
        upLine.setText(str.toString());
        Log.d(LOG_TAG, str.toString());
        BigDecimal ans = calculate(str);
        if (ans == null) {
            line.setText(getString(R.string.Error));
        } else {
            ans = ans.round(new MathContext(ANSWER_LENGTH));
            line.setText(ans.toPlainString());
        }
    }
}
