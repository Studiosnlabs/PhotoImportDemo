package com.example.photoimportdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public void getPhoto(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==1){
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getPhoto();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }

        getPhoto();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 &resultCode==RESULT_OK && data !=null ){

            Uri selectedImage= data.getData();


           try {
               Bitmap bitmap=(Bitmap) MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
               ImageView imageView =(ImageView) findViewById(R.id.imageView);
               imageView.setImageBitmap(bitmap);

           }catch(IOException e){
               e.printStackTrace();
            }


        }

    }
}
