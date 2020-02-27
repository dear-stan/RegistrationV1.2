package com.example.registration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.xmp.options.Options;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.example.registration.R.id.content;
import static com.example.registration.R.id.image;
import static com.example.registration.R.id.logo;
import static com.example.registration.R.id.parent_matrix;
import static com.example.registration.R.id.photo;
import static com.example.registration.R.id.save_scale_type;
import static com.example.registration.R.id.tag_accessibility_heading;
import static com.example.registration.R.id.text;

public class disclaimer extends AppCompatActivity {


    Button button;

    EditText textname, num1,textid, textdt, textmedic, textpolicy, emergency_nam, emergency_numb;

    private String currentPhotoPath;

    Bitmap bmp, scaledBmp;

    private static final int IMAGE_WIDTH = 33;
    private static final int IMAGE_HEIGHT = 45;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);
        button = findViewById(R.id.submit);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c_logo);
        scaledBmp = Bitmap.createScaledBitmap(bmp,IMAGE_WIDTH,IMAGE_HEIGHT,false);



        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        createPDF();


        findViewById(R.id.take_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pic_name = "photo";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                try {
                    File imageFile = File.createTempFile(pic_name,".jpg", storageDirectory);

                    currentPhotoPath = imageFile.getAbsolutePath();

                    Uri imageuri = FileProvider.getUriForFile(disclaimer.this, "com.example.registration.fileprovider", imageFile);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);

                    startActivityForResult(intent, 1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



     }


    private void createPDF() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                PdfDocument myPdfDocument = new PdfDocument();
                Paint myPaint = new Paint();

                textname = findViewById(R.id.name);
                String guestname =textname.getEditableText().toString();

                num1 =findViewById(R.id.mob_num);
                String number =num1.getEditableText().toString();

                textid=findViewById(R.id.eid);
                String id =textid.getEditableText().toString();

                textdt=findViewById(R.id.date_time);
                String dt =textdt.getEditableText().toString();

                textmedic=findViewById(R.id.insurance);
                String medi =textmedic.getEditableText().toString();

                textpolicy=findViewById(R.id.policy);
                String pol =textpolicy.getEditableText().toString();

                emergency_nam=findViewById(R.id.emergency_name);
                String ename =emergency_nam.getEditableText().toString();

                emergency_numb=findViewById(R.id.emergency_num);
                String enumb =emergency_numb.getEditableText().toString();




                PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(210,297,1).create();
                PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                Canvas canvas = myPage1.getCanvas();


                canvas.drawBitmap(scaledBmp, 42, 2, new Paint(Paint.FILTER_BITMAP_FLAG));


                //Title
                myPaint.setTextAlign(Paint.Align.CENTER);
                myPaint.setTextSize(6.0f);
                canvas.drawText("Al Jiyad Stables", myPageInfo1.getPageWidth()/2, 74,myPaint);

                //Address
                myPaint.setTextSize(4.0f);
                //myPaint.setColor(Color.rgb(122,119,119));
                myPaint.setTextScaleX(2f);
                canvas.drawText("Al Qudra Road, Seih Al Salam St.(South)", myPageInfo1.getPageWidth()/2, 82,myPaint);
                canvas.drawText("P.O. Box 9148, Dubai, U.A.E.", myPageInfo1.getPageWidth()/2, 88,myPaint);
                canvas.drawText("+971 4 348 6936", myPageInfo1.getPageWidth()/2, 94,myPaint);
                canvas.drawText("+971 50 599 5866,  +971 50 775 9477", myPageInfo1.getPageWidth()/2, 100,myPaint);

                //Disclaimer
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(3.0f);
                myPaint.setTextScaleX(2f);
                canvas.drawText("Welcome to Al Jiyad Stable (Henceforth  \"Horseback Riding\").", 5, 123, myPaint);


                //1st Paragraph
                canvas.drawText("To  ensure  that  you  obtain  the  maximum  enjoyment from your Leisure Activities, in a safe ", 5, 132, myPaint);
                canvas.drawText("and responsible manner, we kindly request you to read this disclaimer carefully...", 5, 137, myPaint);
                canvas.drawText("By signing below I, \"The Guest\" acknowledge that the Al Jiyad Stables, it\'s employees,", 5, 142, myPaint);
                canvas.drawText("agents, and associates will not be held responsible for any damages, injuries,", 5, 147, myPaint);
                canvas.drawText("illness, or irregularities which may occur whilst performing the selected", 5, 152, myPaint);
                canvas.drawText("activity below (Camel or Horse Riding).", 5, 157, myPaint);


                //2nd Paragraph
                canvas.drawText("I am fully aware that with the selected activity comes with associated risks. I fully", 5, 166, myPaint);
                canvas.drawText("understand the nature of the activity I have chosen to engage in and I confirm that I am", 5, 171, myPaint);
                canvas.drawText("physically and mentally able to perform the required actions in order to safely", 5, 176, myPaint);
                canvas.drawText("accomplish these activities. I am also fully aware and understand that the activities", 5, 181, myPaint);
                canvas.drawText("in which I am participating in under the arrangement of the Al Jiyad Stables, its", 5, 186, myPaint);
                canvas.drawText("employees, agents and associates are potentially dangerous and that there are risks ", 5, 191, myPaint);
                canvas.drawText("inherent in all outdoor activities. These risks include but are not limited to ", 5, 196, myPaint);
                canvas.drawText("hazards in walking, riding, hiking, driving, shooting and general outdoor activity. ", 5, 201, myPaint);
                canvas.drawText("Risks associated with such activities include but are not limited to serious bodily", 5, 206, myPaint);
                canvas.drawText("injuries, such as permanent disability, paralysis and death. There are many risks that", 5, 211, myPaint);
                canvas.drawText("may come to me as a result of participating in these activities.", 5, 216, myPaint);


                //3rd Paragraph
                canvas.drawText("I possess / have the necessary Private Medical Insurance coverage with the following", 5, 225, myPaint);
                canvas.drawText("carrier and policy number below to cover all my medical expenses in the", 5, 230, myPaint);
                canvas.drawText("event of an accident. I assume / undertake full liability for any medical &;", 5, 235, myPaint);
                canvas.drawText("on-medical expenses that occur in the event of an accident. I am also aware that", 5, 240, myPaint);
                canvas.drawText("the information I provided in accurate and correct and that participating in", 5, 245, myPaint);
                canvas.drawText("the below selected activity is not permitted if the necessary ", 5, 250, myPaint);
                canvas.drawText("Private Medical Insurance Coverage is not available or covered.", 5, 255, myPaint);


                myPdfDocument.finishPage(myPage1);



                //Personal information, Emergency Contact and Signature
                PdfDocument.PageInfo myPageInfo2 = new PdfDocument.PageInfo.Builder(210,297,2).create();
                PdfDocument.Page myPage2 = myPdfDocument.startPage(myPageInfo2);
                Canvas canvas2 = myPage2.getCanvas();


                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(10.0f);
                canvas2.drawText("Personal Details of the Customer", 10, 20, myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(3.0f);
                canvas2.drawText("Full Name",20,50, myPaint);
                myPaint.setTextSize(8.0f);
                canvas2.drawText(guestname, 20, 60, myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(3.0f);
                canvas2.drawText("Mobile Number",20,70, myPaint);
                myPaint.setTextSize(8.0f);
                canvas2.drawText(number, 20, 80, myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(3.0f);
                canvas2.drawText("Emirates ID or Passport Number",20,90, myPaint);
                myPaint.setTextSize(8.0f);
                canvas2.drawText(id, 20, 100, myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(3.0f);
                canvas2.drawText("Date & Time:-",20,110, myPaint);
                myPaint.setTextSize(8.0f);
                canvas2.drawText(dt, 20, 120, myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(3.0f);
                canvas2.drawText("Medical Insurance Provider",20,130, myPaint);
                myPaint.setTextSize(8.0f);
                canvas2.drawText(medi, 20, 140, myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(3.0f);
                canvas2.drawText("Policy Number",20,150, myPaint);
                myPaint.setTextSize(8.0f);
                canvas2.drawText(pol, 20, 160, myPaint);


                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(10.0f);
                canvas2.drawText("Person To Contact In Case of Emergency", 20, 180, myPaint);


                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(3.0f);
                canvas2.drawText("Full Name",20,210, myPaint);
                myPaint.setTextSize(8.0f);
                canvas2.drawText(ename, 20, 220, myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(3.0f);
                canvas2.drawText("Mobile Number",20,230, myPaint);
                myPaint.setTextSize(8.0f);
                canvas2.drawText(enumb, 20, 240, myPaint);



                myPdfDocument.finishPage(myPage2);



                PdfDocument.PageInfo myPageInfo3 = new PdfDocument.PageInfo.Builder(210,297,3).create();
                PdfDocument.Page myPage3 = myPdfDocument.startPage(myPageInfo3);
                Canvas canvas3 = myPage3.getCanvas();
                canvas3.drawText("",20,50, myPaint);

                //Bitmap pic = BitmapFactory.decodeFile(String.valueOf(findViewById(photo)));
                canvas3 = myPage3.getCanvas();



                //Bitmap bitmap3 = Bitmap.createScaledBitmap((BitmapFactory.decodeFile(currentPhotoPath)), 210, 148, false);
                Bitmap bitmap3 = Bitmap.createBitmap(BitmapFactory.decodeFile(currentPhotoPath));

                Bitmap ID = Bitmap.createScaledBitmap(bitmap3, 210, 148, true);


                //Bitmap bitmap3 = Bitmap.createBitmap(BitmapFactory.decodeFile(currentPhotoPath));

                //Bitmap bitmap3 = BitmapFactory.decodeFile(currentPhotoPath);
                canvas3.rotate(90);
                canvas3.drawBitmap(ID, 10, 5, myPaint);


                myPdfDocument.finishPage(myPage3);





                File file = new File(Environment.getExternalStorageDirectory(),guestname+".pdf");

                try {
                    myPdfDocument.writeTo(new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myPdfDocument.close();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            Bitmap bitmap   = BitmapFactory.decodeFile(currentPhotoPath);

            ImageView imageView = findViewById(R.id.photo);
            imageView.setImageBitmap(bitmap);




        }
    }
}
