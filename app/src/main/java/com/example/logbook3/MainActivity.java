package com.example.logbook3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    EditText addLink_txt;
    Button back_button, next_button, add_link_button;
    ArrayList<String> arrayList;
    int index;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        back_button = findViewById(R.id.back_button);
        next_button = findViewById(R.id.next_button);
        add_link_button = findViewById(R.id.add_link_button);
        addLink_txt = findViewById(R.id.addLink_txt);

        add_link_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = new Database(MainActivity.this);
                db.addLink(addLink_txt.getText().toString().trim());

                Glide.with(getApplicationContext())
                        .load(addLink_txt.getText().toString().trim());
                Toast.makeText(MainActivity.this, "Add Successfully!!!", Toast.LENGTH_SHORT).show();
            }
        });

        Database db = new Database(MainActivity.this);

        db.addLink("https://images.vexels.com/media/users/3/263340/isolated/preview/92d75abef1c7523630339a2793eba5eb-pizza-color-stroke-slice.png");
        db.addLink("https://img.freepik.com/premium-psd/fresh-vegetable-pepperoni-mushroom-pizza-transparent-background_670625-101.jpg?w=2000");
        db.addLink("https://toppng.com/uploads/preview/pizza-11527809195frqp1qz4zd.png");

        Glide.with(getApplicationContext())
                .load(loadLastImg())
                .placeholder(R.drawable.ic_baseline_image_24).into(imageView);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext())
                        .load(back_button())
                        .placeholder(R.drawable.ic_baseline_image_24).into(imageView);
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext())
                        .load(next_button())
                        .placeholder(R.drawable.ic_baseline_image_24).into(imageView);
            }
        });
    }

    String loadLastImg(){
        Database db = new Database(MainActivity.this);
        Cursor cursor = db.getAllLink();

        cursor.moveToLast();
        url = cursor.getString(1);
        index = cursor.getPosition();
        return url;
    }

    String next_button(){
        Database db = new Database(MainActivity.this);
        Cursor cursor = db.getAllLink();
        cursor.moveToLast();
        int last = cursor.getPosition();

        if(index == last){
            cursor.moveToFirst();

        } else {
            index++;
            cursor.moveToPosition(index);

        }
        url = cursor.getString(1);
        return url;
    }

    String back_button(){
        Database db = new Database(MainActivity.this);
        Cursor cursor = db.getAllLink();


        if(index == 0){
            cursor.moveToLast();

        } else {
            index--;
            cursor.moveToPosition(index);
        }
        url = cursor.getString(1);
        return url;
    }

}