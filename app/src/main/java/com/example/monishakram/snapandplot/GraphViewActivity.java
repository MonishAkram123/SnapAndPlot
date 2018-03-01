package com.example.monishakram.snapandplot;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GraphViewActivity extends AppCompatActivity {
    FloatingActionButton fab_menu, fab_camera, fab_KeyPad,fab_scroll;
    Animation fabMenuOpen, fabMenuClose, fabRotateClockWise, fabRotateAntiClockWise, keyPadOpen, keyPadClose;
    TextView formula;
    GraphView graph;
    LinearLayout keyPad;
    ArrayList<String> tokens = null;
    String s = "";
    String TAG = "GraphViewActivity";
    boolean fabMenuIsOpen = false;
    boolean keyPadIsVisible = false;
    boolean isScrollLock = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        //Start
        Log.i(TAG, "OnCreate is called");

        //Setting Buttons
        fab_menu = findViewById(R.id.fab_menu);
        fab_camera = findViewById(R.id.fab_camera);
        fab_KeyPad = findViewById(R.id.fab_EditText);
        fab_scroll = findViewById(R.id.fab_ScrollLock);
        keyPad = findViewById(R.id.keyPad);
        formula = findViewById(R.id.formula);

        //Setting Animations
        fabMenuOpen = AnimationUtils.loadAnimation(this, R.anim.fab_menu_open);
        fabMenuClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        fabRotateClockWise = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_clockwise);
        fabRotateAntiClockWise = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_anticlockwise);
        keyPadOpen = AnimationUtils.loadAnimation(this, R.anim.keypad_open);
        keyPadClose = AnimationUtils.loadAnimation(this, R.anim.keypad_close);
        graph = findViewById(R.id.graphView);
        fab_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fabMenuIsOpen){
                    fab_camera.setVisibility(View.INVISIBLE);
                    fab_camera.setClickable(false);
                    fab_KeyPad.setVisibility(View.INVISIBLE);
                    fab_KeyPad.setClickable(false);
                    if(keyPadIsVisible){
                        keyPad.setVisibility(View.INVISIBLE);
                        keyPad.startAnimation(keyPadClose);
                        keyPadIsVisible = false;

                    }
                    fabMenuIsOpen = false;
                    fab_camera.startAnimation(fabMenuClose);
                    fab_KeyPad.startAnimation(fabMenuClose);
                    fab_menu.startAnimation(fabRotateAntiClockWise);
                    fab_scroll.startAnimation(fabMenuClose);
                }
                else{
                    fab_camera.setVisibility(View.VISIBLE);
                    fab_camera.setClickable(true);
                    fab_KeyPad.setVisibility(View.VISIBLE);
                    fab_KeyPad.setClickable(true);
                    fabMenuIsOpen = true;
                    fab_camera.startAnimation(fabMenuOpen);
                    fab_KeyPad.startAnimation(fabMenuOpen);
                    fab_scroll.startAnimation(fabMenuOpen);
                    fab_menu.startAnimation(fabRotateClockWise);
                }
            }
        });
        fab_KeyPad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(keyPadIsVisible){
                    keyPad.setVisibility(View.INVISIBLE);
                    keyPad.startAnimation(keyPadClose);
                    keyPadIsVisible = false;
                }
                else{
                    keyPad.setVisibility(View.VISIBLE);
                    keyPad.startAnimation(keyPadOpen);
                    keyPadIsVisible = true;
                }
            }
        });
        tokens = new ArrayList<>();

        fab_scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isScrollLock){
                    graph.setScrollLock(false);
                    isScrollLock = false;
                    fab_scroll.setImageDrawable(getDrawable(R.drawable.ic_scroll_unlock));
                }
                else {
                    graph.setScrollLock(true);
                    isScrollLock = true;
                    fab_scroll.setImageDrawable(getDrawable(R.drawable.ic_scroll_lock));
                }
            }
        });

        fab_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Will be added in the final release", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View view) {
        s = formula.getText().toString();
        switch (view.getId()){
            case R.id.button1:
                if(s.length() > 0 && !(s.charAt(s.length() -1) >= '0' && s.charAt(s.length() -1) <= '9')  && s.charAt(s.length() -1) != '.')
                    s += ' ';
                s += "1";break;
            case R.id.button2:
                if(s.length() > 0 && !(s.charAt(s.length() -1) >= '0' && s.charAt(s.length() -1) <= '9')  && s.charAt(s.length() -1) != '.')
                    s += ' ';
                s += "2"; break;
            case R.id.button3:
                if(s.length() > 0 && !(s.charAt(s.length() -1) >= '0' && s.charAt(s.length() -1) <= '9')  && s.charAt(s.length() -1) != '.')
                    s += ' ';
                s += "3"; break;
            case R.id.button4:
                if(s.length() > 0 && !(s.charAt(s.length() -1) >= '0' && s.charAt(s.length() -1) <= '9')  && s.charAt(s.length() -1) != '.')
                    s += ' ';
                s += "4"; break;
            case R.id.button5:
                if(s.length() > 0 && !(s.charAt(s.length() -1) >= '0' && s.charAt(s.length() -1) <= '9')  && s.charAt(s.length() -1) != '.')
                    s += ' ';
                s += "5"; break;
            case R.id.button6:
                if(s.length() > 0 && !(s.charAt(s.length() -1) >= '0' && s.charAt(s.length() -1) <= '9')  && s.charAt(s.length() -1) != '.')
                    s += ' ';
                s += "6"; break;
            case R.id.button7:
                if(s.length() > 0 && !(s.charAt(s.length() -1) >= '0' && s.charAt(s.length() -1) <= '9')  && s.charAt(s.length() -1) != '.')
                    s += ' ';
                s += "7"; break;
            case R.id.button8:
                if(s.length() > 0 && !(s.charAt(s.length() -1) >= '0' && s.charAt(s.length() -1) <= '9')  && s.charAt(s.length() -1) != '.')
                    s += ' ';
                s += "8"; break;
            case R.id.button9:
                if(s.length() > 0 && !(s.charAt(s.length() -1) >= '0' && s.charAt(s.length() -1) <= '9')  && s.charAt(s.length() -1) != '.')
                    s += ' ';
                s += "9"; break;
            case R.id.button0:
                if(s.length() > 0 && !(s.charAt(s.length() -1) >= '0' && s.charAt(s.length() -1) <= '9')  && s.charAt(s.length() -1) != '.')
                    s += ' ';
                s += "0"; break;
            case R.id.buttonX:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " x";
                else
                    s += "x";break;

            case R.id.buttonE:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " e";
                else
                    s += "e";break;

            case R.id.buttonPi:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " pi";
                else
                    s += "pi";break;
            case R.id.buttonPlus:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " +";
                else
                    s += "+";break;

            case R.id.buttonMinus:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " -";
                else
                    s += "-";break;
            case R.id.buttonDivide:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " /";
                else
                    s += "/";break;
            case R.id.buttonProduct:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " *";
                else
                    s += "*";break;
            case R.id.buttonRemainder:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " %";
                else
                    s += "%";break;
            case R.id.buttonBracketOpen:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " (";
                else
                    s += "(";break;
            case R.id.buttonBracketClose:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " )";
                else
                    s += ")";break;
            case R.id.buttonDecimal:
                if(s.length() > 0 && s.charAt(s.length() -1) >= '0' && s.charAt(s.length() -1) <= '9')
                    s += ".";break;

            case R.id.buttonSqrt:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " ^ 0.5";
                else
                    s += "^ 0.5";break;
            case R.id.buttonSin:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " sin ";
                else
                    s += "sin ";break;
            case R.id.buttonCos:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " cos ";
                else
                    s += "cos ";break;
            case R.id.buttonTan:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " tan ";
                else
                    s += "tan ";break;
            case R.id.buttonLog:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " log ";
                else
                    s += "log ";break;
            case R.id.buttonPower:
                if(s.length() > 0 && s.charAt(s.length() -1) != ' ')
                    s += " ^ ";
                else
                    s += "^ ";break;
            case R.id.buttonBackSpace:
                if(s.length() > 1)
                    s = s.substring(0, s.length() -1);
                else
                    s = "";break;
            case R.id.buttonClear:
                s = "";

                break;
            case R.id.buttonOK:
                if(s.equals(""))
                    Toast.makeText(this, "Specify the formula first", Toast.LENGTH_SHORT).show();
                else
                    graph.setExpression(s);
                break;
        }
        formula.setText(s);
    }

}
