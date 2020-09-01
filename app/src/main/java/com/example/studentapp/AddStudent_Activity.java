package com.example.studentapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddStudent_Activity extends AppCompatActivity implements View.OnClickListener {
   public  EditText Edt_Name,Edt_School,Edt_Blood,Edt_Father,
            Edt_Mother,Edt_Parentcon,Edt_Add1,Edt_Add2,Edt_City,Edt_State,Edt_zip,Edt_Emergency;
   TextView Edt_Location;
   public RadioGroup rd_Group;
   public RadioButton Rd_male,Rd_female;
   public Spinner sp_class,Sp_section;
   public Button Bt_submit;
   public ImageView Img_Profile;
    private DatabaseHelper databaseHelper;
    private Student student;
    String gender="";
    String item;
    private static final String TAG = "MainActivity";

    List<Student> students=new ArrayList<>();

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    int ID;
    File destination;
    Bitmap bitmap;
    String imgPath;

    ImageView back;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLARY = 2;


    Uri outPutfileUri;
    String currentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_);
        Img_Profile=(ImageView)findViewById(R.id.img_profile);
        Edt_Name=(EditText)findViewById(R.id.edt_name);
        Edt_School=(EditText)findViewById(R.id.edt_school);
        Edt_Blood=(EditText)findViewById(R.id.edt_blod);
        Edt_Father=(EditText)findViewById(R.id.edt_fater);
        Edt_Mother=(EditText)findViewById(R.id.edt_mother);
        mDisplayDate=(TextView)findViewById(R.id.txt_dob);
        Edt_Parentcon=(EditText)findViewById(R.id.edt_parentph);
        Edt_Add1=(EditText)findViewById(R.id.edt_add1);
        Edt_Add2=(EditText)findViewById(R.id.edt_add2);
        Edt_City=(EditText)findViewById(R.id.edt_city);
        Edt_State=(EditText)findViewById(R.id.edt_state);
        Edt_zip=(EditText)findViewById(R.id.edt_zip);
        Edt_Emergency=(EditText)findViewById(R.id.edt_emergency);
        Edt_Location=findViewById(R.id.edt_loc);
        rd_Group=(RadioGroup)findViewById(R.id.rd_gender);
        Rd_male=(RadioButton)findViewById(R.id.rd_male);
        Rd_female=(RadioButton)findViewById(R.id.rd_female);
        sp_class=(Spinner)findViewById(R.id.spinner_class);
        Sp_section=(Spinner)findViewById(R.id.sp_gr);
        Bt_submit=(Button)findViewById(R.id.btnsubmit);

        Bt_submit.setOnClickListener(this);
        List<String> classname = new ArrayList<String>();
        classname.add("1");
        classname.add("2 ");
        classname.add("3");
        classname.add("4");
        classname.add("5");
        classname.add("6");
        classname.add("7");
        classname.add("8");
        classname.add("9");
        classname.add("10");
        classname.add("11");
        classname.add("12");

        databaseHelper=new DatabaseHelper(this);
        back=(ImageView)findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classname);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ID=getIntent().getIntExtra("ID",0);
        students= databaseHelper.getAllStudent(String.valueOf(ID));
        Edt_Location.setText(""+AppUtils.latitude+" , " + AppUtils.longitude);
        if(students.size()>0) {
            for (int i = 0; i < students.size(); i++)
            {
                if(students.get(0).getImagePath()!=null)
                {
                File imgFile = new  File(students.get(0).getImagePath());
                if(imgFile.exists()) {

                    try {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        Img_Profile.setImageBitmap(myBitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                }

                Edt_Name.setText(students.get(0).getUsername());
                Edt_School.setText(students.get(0).getSchool());
                Edt_Blood.setText(students.get(0).getBlood());
                Edt_Father.setText(students.get(0).getFather());
                Edt_Mother.setText(students.get(0).getMother());
                mDisplayDate.setText(students.get(0).getDob());
                Edt_Parentcon.setText(students.get(0).getParentno());
                Edt_Add1.setText(students.get(0).getAdd1());
                Edt_Add2.setText(students.get(0).getAdd2());
                Edt_City.setText(students.get(0).getCity());
                Edt_State.setText(students.get(0).getState());
                Edt_zip.setText(""+students.get(0).getZip());
                Edt_Emergency.setText(students.get(0).getEmergencyno());
                Edt_Location.setText(""+students.get(0).getLocation());
            }
        }

        // attaching data adapter to spinner
        sp_class.setAdapter(dataAdapter);
        List<String> section = new ArrayList<String>();
        section.add("A");
        section.add("B");
        section.add("C");
        section.add("D");
        section.add("E");
        section.add("F");
        section.add("G");
        section.add("H");
        section.add("I");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, section);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        Sp_section.setAdapter(dataAdapter1);
        sp_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Sp_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                 item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
               // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Edt_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddStudent_Activity.this,MapsMarkers.class);
                startActivity(i);

            }
        });
        Img_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  selectImage();
               askCameraPermissions();
            }
        });

        rd_Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rd_male){
                    gender="Male";
                }
                else if(i==R.id.rd_female){
                    gender="Female";
                }
            }
        });
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddStudent_Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        Edt_Location.setText(""+AppUtils.latitude+" , " + AppUtils.longitude);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnsubmit:
                postDataToSQLite();
                break;

        }
    }


    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
             AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, 2);
                       } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         //inputStreamImg = null;
        if (requestCode == 1) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                Log.e("Activity", "Pick from Camera::>>> ");

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File(Environment.getExternalStorageDirectory() + "/" , "IMG_" + timeStamp + ".jpg");

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgPath = destination.getAbsolutePath();
                Img_Profile.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 2) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");

                imgPath = getRealPathFromURI(selectedImage);
                destination = new File(imgPath.toString());
                Img_Profile.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void askCameraPermissions() {
       /* if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
          //dispatchTakePictureIntent();

        }*/

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now

                            selectImage();
                            //
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();


    }


    private void postDataToSQLite() {

        if (!databaseHelper.checkStudent(Edt_Name.getText().toString().trim())) {
            student=new Student();
            student.setImagePath(imgPath);
            student.setUsername(Edt_Name.getText().toString().trim());
            student.setClassname(sp_class.getSelectedItem().toString());
            student.setSection(Sp_section.getSelectedItem().toString());
            student.setGender(gender);
            student.setDob(mDisplayDate.getText().toString().trim());
            student.setBlood(Edt_Blood.getText().toString().trim());
            student.setFather(Edt_Father.getText().toString().trim());
            student.setMother(Edt_Mother.getText().toString().trim());
            student.setParentno(Edt_Parentcon.getText().toString().trim());
            student.setAdd1(Edt_Add1.getText().toString().trim());
            student.setAdd2(Edt_Add2.getText().toString().trim());
            student.setCity(Edt_City.getText().toString().trim());
            student.setState(Edt_State.getText().toString().trim());
            student.setZip(Edt_zip.getText().toString().trim());
            student.setEmergencyno(Edt_Emergency.getText().toString().trim());
            student.setLocation(Edt_Location.getText().toString().trim());

            databaseHelper.addStudent(student);

            // Snack Bar to show success message that record saved successfully
           // Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),"Successfully inserted",Toast.LENGTH_LONG).show();
            Intent accountsIntent = new Intent(this, HomePage_Activity.class);
          //  emptyInputEditText();
            startActivity(accountsIntent);



       } else {

            student=new Student();
            student.setImagePath(imgPath);
            student.setUsername(Edt_Name.getText().toString().trim());
            student.setClassname(sp_class.getSelectedItem().toString());
            student.setSection(Sp_section.getSelectedItem().toString());
            student.setGender(gender);
            student.setDob(mDisplayDate.getText().toString().trim());
            student.setBlood(Edt_Blood.getText().toString().trim());
            student.setFather(Edt_Father.getText().toString().trim());
            student.setMother(Edt_Mother.getText().toString().trim());
            student.setParentno(Edt_Parentcon.getText().toString().trim());
            student.setAdd1(Edt_Add1.getText().toString().trim());
            student.setAdd2(Edt_Add2.getText().toString().trim());
            student.setCity(Edt_City.getText().toString().trim());
            student.setState(Edt_State.getText().toString().trim());
            student.setZip(Edt_zip.getText().toString().trim());
            student.setEmergencyno(Edt_Emergency.getText().toString().trim());
            student.setLocation(Edt_Location.getText().toString().trim());

            databaseHelper.updateStudent(student);

            // Snack Bar to show success message that record saved successfully
            // Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
            Intent accountsIntent = new Intent(this, HomePage_Activity.class);
            //  emptyInputEditText();
            startActivity(accountsIntent);
            // Snack Bar to show error message that record already exists
          //  Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
       }


    }


}
