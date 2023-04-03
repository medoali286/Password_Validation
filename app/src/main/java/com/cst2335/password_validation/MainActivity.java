package com.cst2335.password_validation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cst2335.password_validation.Data.MainViewModel;
import com.cst2335.password_validation.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    private  ActivityMainBinding binding;
    MainViewModel viewModel;



    protected String cityName;
    protected RequestQueue queue = null;
    Bitmap image;
    String url = null;
    String imgUrl = "https://openweathermap.org/img/w/";
    private Executor thread;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);

     binding=ActivityMainBinding.inflate(getLayoutInflater());



setContentView(binding.getRoot());




        binding.forecastButton.setOnClickListener(clk->{

            cityName=binding.cityTextField.getText().toString();


            try {
                url = "https://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(cityName, "UTF-8") + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";


                Log.i("url", "onCreate: "+url );


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        response -> {






                            try {
                                JSONObject coord = response.getJSONObject("coord");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }









                            try {



                                JSONArray weatherArray = response.getJSONArray("weather");
                                JSONObject position0 = weatherArray.getJSONObject(0);
                                String description = position0.getString("description");
                                String iconName = position0.getString("icon");
                                JSONObject mainObject = response.getJSONObject("main");
                                double current = mainObject.getDouble("temp");
                                double min = mainObject.getDouble("temp_min");
                                double max = mainObject.getDouble("temp_max");
                                int humidity = mainObject.getInt("humidity");



                                String pathName = getFilesDir() + "/" + iconName + ".png";
                                File file = new File(pathName);
                                if (file.exists()) {
                                    image = BitmapFactory.decodeFile(pathName);
                                } else {
                                    ImageRequest imgReq = new ImageRequest(imgUrl + iconName + ".png", new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            image = bitmap;
                                            FileOutputStream fOut = null;
                                            try {
                                                fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                                image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                                fOut.flush();
                                                fOut.close();
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {

                                    });

                                    queue.add(imgReq);
                                }








                                runOnUiThread(() -> {
                                    binding.temp.setText("The current temperature is " + current);
                                    binding.temp.setVisibility(View.VISIBLE);

                                    binding.minTemp.setText("The min temperature is " + min);
                                    binding.minTemp.setVisibility(View.VISIBLE);

                                    binding.maxTemp.setText("The max temperature is " + max);
                                    binding.maxTemp.setVisibility(View.VISIBLE);

                                    binding.humidity.setText("The humidity is " + humidity + "%");
                                    binding.humidity.setVisibility(View.VISIBLE);

                                   binding.icon.setImageBitmap(image);

                                   binding.icon.setVisibility(View.VISIBLE);







                                    binding.description.setText(description);
                                    binding.description.setVisibility(View.VISIBLE);
                                });








                            } catch (JSONException e) {

                                throw new RuntimeException(e);
                            }

                            } ,error -> {








                });

                queue.add(request);


            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }


        });










    }





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