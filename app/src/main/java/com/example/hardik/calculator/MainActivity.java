package com.example.hardik.calculator;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity{
    Button num[]=new Button[19];
    EditText e;
    int ans,count;
    double ans1;
    boolean second=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num[0]=(Button)findViewById(R.id.num0);
        num[1]=(Button)findViewById(R.id.num1);
        num[2]=(Button)findViewById(R.id.num2);
        num[3]=(Button)findViewById(R.id.num3);
        num[4]=(Button)findViewById(R.id.num4);
        num[5]=(Button)findViewById(R.id.num5);
        num[6]=(Button)findViewById(R.id.num6);
        num[7]=(Button)findViewById(R.id.num7);
        num[8]=(Button)findViewById(R.id.num8);
        num[9]=(Button)findViewById(R.id.num9);
        num[10]=(Button)findViewById(R.id.numDot);
        num[11]=(Button)findViewById(R.id.Dvd);
        num[12]=(Button)findViewById(R.id.pluse);
        num[13]=(Button)findViewById(R.id.minuse);
        num[14]=(Button)findViewById(R.id.Multi);
        num[15]=(Button)findViewById(R.id.numEqu);
        num[16]=(Button)findViewById(R.id.delete);
        num[17]=(Button)findViewById(R.id.clear);
        num[18]=(Button)findViewById(R.id.signtific);

        e=(EditText)findViewById(R.id.editText);
        e.setText(null);


        Handler handle = new Handler();
        for(int i=0; i<10; i++ )
            num[i].setOnClickListener(handle);

        HandlerSign SighHandle=new HandlerSign();
            for (int i=10;i<15;i++)
                num[i].setOnClickListener(SighHandle);


        num[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String main=e.getText().toString();
                if(e.getText()==null) e.setText("");
                 ans1=cal(e.getText() + "");
                 ans=(int)ans1;

                    if (checkDigit(ans1))
                        e.setText(main + "\n" + ans + "");
                    else e.setText(main + "\n" + ans1 + "");
                    second = true;
                    count = 0;


            }
        });

            num[16].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if((e.getText()+"").length()>0)
                        e.setText( (e.getText()+"").substring(0, (e.getText()+"").length()-1) );
                }
            });

            num[17].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    e.setText("");
                    second=false;
                }
            });

        num[18].setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "Feature will be available soon...", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }


    private boolean checkDigit(Double s) {

        double num=s,num1;
        int num2=(int)num;
        num1=num2;
        if(num1==num) return true;

        return false;
    }

    public double cal(String s){

        StringTokenizer numTocken = new StringTokenizer(s, "÷×+-");
        StringTokenizer signTocken = new StringTokenizer(s, "1234567890.");
        ArrayList<Double> num = new ArrayList<Double>();
        ArrayList<Character> sign = new ArrayList<Character>();



        while(numTocken.hasMoreTokens()) {
            num.add(Double.parseDouble(numTocken.nextToken()));
        }

        while(signTocken.hasMoreTokens())
            sign.add( signTocken.nextToken().charAt(0) );


        if(num.size()==sign.size()) {
            if(sign.get(0)!='-')
            sign.remove(sign.size()-1);
            else {
                if(sign.get(0)=='-'&&s.charAt(0)=='-'){
                    double m=num.get(0);  num.remove(0);
                    m=-m;
                    num.add(0,m);
                    sign.remove(0);
                }
                else sign.remove(sign.size()-1);

            }
        }
        else if(sign.size()>num.size()) {
                int i=sign.size()-1;
                if(sign.get(0)=='÷'||sign.get(0)=='+'||sign.get(0)=='\u00D7')
                    sign.remove(0);
                else if(sign.get(0)=='-') {
                    double m = num.get(0);
                    num.remove(0);
                    num.add(0, -m);
                    sign.remove(0);
                    sign.remove(sign.size()-1);
                }
                else {try{
                    while (sign.size() != num.size() - 1) {
                        sign.remove(i);
                        i--;
                    }}
                    catch (Exception e){};
                }
        }
        else {
            try {
                for (int i = 0; i < sign.size(); i++) {

                    if (sign.get(i) == '-' && sign.get(i + 1) == '+') {
                        double m = num.get(i + 1);
                        m = -m;
                        sign.remove(i);
                        sign.add(i, '+');
                        num.remove(i + 1);
                        num.add(i + 1, m);
                    }

                }
            }
            catch (Exception e){};
        }

        for(char c : new char[]{'÷', '\u00D7', '+', '-'}){
            for(int i=0; i< sign.size(); i++){
                if(sign.get(i)==c){

                    double num1 = num.get(i);
                    double num2 = num.get(i+1);
                    double ans=0;

                    switch (c){
                        case '÷' : ans=num1/num2; break;
                        case '\u00D7' : ans=num1*num2; break;
                        case '+' :ans=num1+num2; break;

                        case '-' : ans=num1-num2; break;
                    }

                    num.remove(i);
                    num.remove(i);
                    num.add(i, ans);
                    sign.remove(i);
                    i--;
                }

            }
        }

        return num.size()>0 ? num.get(0) : 0;
    }

    private class Handler implements View.OnClickListener{
        public void onClick(View v){

            e.setText( e.getText() + (((Button)v).getText()+"") );

        }
    }

    private class HandlerSign implements View.OnClickListener{
        public void onClick(View v){

             if(second&&checkDigit(ans1)&&count==0) e.setText(Integer.toString(ans));
            else if(second&&count==0) e.setText(Double.toString(ans1));
            else e.setText(e.getText());
            String q=e.getText().toString();
            int len=q.length();
            try {
                if (q.equals("")&&((Button) v).getText().toString().charAt(0)=='-') e.setText("-");
                else if(q.equals("")&&((Button) v).getText().toString()!="-") e.setText(null);
                else if (q.charAt(len-1) == '-' ||q.charAt(len-1) == '+' || q.charAt(len-1) == '÷' || q.charAt(len-1) == '×'||q.charAt(len-1)=='.')
                    e.setText(e.getText());
                else
                    e.setText(e.getText() + (((Button) v).getText() + ""));
            }
            catch (Exception e){};
            count++;
        }
    }
}