package com.cst2335.password_validation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn=findViewById(R.id.Button);
        EditText et=findViewById(R.id.editText);

        btn.setOnClickListener(clk-> {
                    CheckPassWordComplexity(et.getText().toString());


        }



        );



    }


    //!@?


boolean CheckPassWordComplexity(String pw){

    boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
    foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;


   for (char c:pw.toCharArray()) {

       if (Character.isUpperCase(c)) {
           foundUpperCase = true;
           break;

       }

   }
   
   for (char c:pw.toCharArray()){

           if (Character.isLowerCase(c)){
               foundLowerCase=true;
               break;

           }

       }

   for (char c:pw.toCharArray()){

        if (Character.isDigit(c)){
            foundNumber=true;
            break;

        }

    }

   for (char c:pw.toCharArray()){


if (isSpecialCharacter(c)){

    foundSpecial=true;
    break;
}


    }




    if(!foundUpperCase)
    {
        Toast.makeText(this, "Password is missing an upper case letter", Toast.LENGTH_SHORT).show();
        return false;
    }
    else if( ! foundLowerCase)
    {
        Toast.makeText(this, "Password is missing an lower case letter", Toast.LENGTH_SHORT).show();
        return false;
    } else if( ! foundNumber)
    {
        Toast.makeText(this, "Password is missing Numbers", Toast.LENGTH_SHORT).show();
        return false;
    } else if( ! foundSpecial)
    {
        Toast.makeText(this, "Password is missing at least one of #$%^&*!@?", Toast.LENGTH_LONG).show();
        return false;
    }else {

        Toast.makeText(this, "Your password meets the requirements", Toast.LENGTH_SHORT).show();
return true;

    }



}

boolean isSpecialCharacter(char c){


switch (c){


    case '#':
    case '$':
    case '%':
    case '^':
    case '&':
    case '*':
    case '!':
    case '@':
    case '?':{

        return true;


    }

    default: return false;

}



}


}