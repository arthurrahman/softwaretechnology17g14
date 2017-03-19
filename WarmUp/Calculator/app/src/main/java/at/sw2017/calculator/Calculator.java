package at.sw2017.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Calculator extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        // Link Buttons with view-elements
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonDivide = (Button) findViewById(R.id.buttonDivide);
        buttonEquals = (Button) findViewById(R.id.buttonEquals);
        buttonSub = (Button) findViewById(R.id.buttonSub);
        buttonTimes = (Button) findViewById(R.id.buttonTimes);
        buttonZero = (Button) findViewById(R.id.buttonZero);
        buttonOne = (Button) findViewById(R.id.buttonOne);
        buttonTwo = (Button) findViewById(R.id.buttonTwo);
        buttonThree = (Button) findViewById(R.id.buttonThree);
        buttonFour = (Button) findViewById(R.id.buttonFour);
        buttonFive = (Button) findViewById(R.id.buttonFive);
        buttonSix = (Button) findViewById(R.id.buttonSix);
        buttonSeven = (Button) findViewById(R.id.buttonSeven);
        buttonEight = (Button) findViewById(R.id.buttonEight);
        buttonNine = (Button) findViewById(R.id.buttonNine);

        // Register Button Presses
        buttonAdd.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonTimes.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);
        buttonZero.setOnClickListener(this);
        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);
        buttonFive.setOnClickListener(this);
        buttonSix.setOnClickListener(this);
        buttonSeven.setOnClickListener(this);
        buttonEight.setOnClickListener(this);
        buttonNine.setOnClickListener(this);

        // Link TextView
        numberView = (TextView) findViewById(R.id.textView);
        state = State.INIT;
    }

    @Override
    public void onClick(View v) {
        Button clickedButton = (Button)v;

        switch(clickedButton.getId())
        {
            case R.id.buttonAdd:
                clearNumberView();
                state = State.ADD;
                break;
            case R.id.buttonSub:
                clearNumberView();
                state = State.SUB;
                break;
            case R.id.buttonTimes:
                clearNumberView();
                state = State.MUL;
                break;
            case R.id.buttonDivide:
                clearNumberView();
                state = State.DIV;
                break;
            case R.id.buttonEquals:
                calculateResult();
                state = State.INIT;
                break;
            case R.id.buttonClear:
                clearTextView();
                break;
            default:
                String recentNumber = numberView.getText().toString();
                if(recentNumber.equals("0"))
                {
                    recentNumber = "";
                }
                recentNumber += clickedButton.getText().toString();
                numberView.setText(recentNumber);
        }
    }

    private void calculateResult () {
        int secondNumber = 0;
        String tempString = numberView . getText (). toString ();
        if (! tempString . equals ( "" )){
            secondNumber = Integer . valueOf ( tempString );
        }
        int result;
        switch ( state ){
            case ADD:
                result = Calculations.doAddition ( firstNumber , secondNumber );
                break;
            case SUB:
                result = Calculations.doSubtraction ( firstNumber , secondNumber );
                break;
            case MUL:
                result = Calculations.doMultiplication ( firstNumber , secondNumber );
                break;
            case DIV:
                result = Calculations.doDivision ( firstNumber , secondNumber );
                break;
            default:
                result = secondNumber;
        }
        numberView . setText ( Integer.toString ( result ));
    }

    private void clearTextView () {
        numberView . setText ( "0" );
        firstNumber = 0;
        state = State.INIT;
    }

    private void clearNumberView () {
        String tempString = numberView . getText (). toString ();
        if (! tempString . equals ( "" )){
            firstNumber = Integer . valueOf ( tempString );
        }
        numberView . setText ( "" );
    }

    public enum State {
        ADD , SUB , MUL , DIV , INIT
    }
    // Member variables
    private Button buttonZero;
    private Button buttonOne;
    private Button buttonTwo;
    private Button buttonThree;
    private Button buttonFour;
    private Button buttonFive;
    private Button buttonSix;
    private Button buttonSeven;
    private Button buttonEight;
    private Button buttonNine;
    private Button buttonClear;
    private Button buttonEquals;
    private Button buttonDivide;
    private Button buttonTimes;
    private Button buttonSub;
    private Button buttonAdd;
    private TextView numberView;
    int firstNumber;
    State state;
}
