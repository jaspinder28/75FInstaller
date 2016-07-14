package com.x75f.installer.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.onegravity.rteditor.RTEditText;
import com.onegravity.rteditor.RTManager;
import com.onegravity.rteditor.api.RTApi;
import com.onegravity.rteditor.api.RTMediaFactoryImpl;
import com.onegravity.rteditor.api.RTProxyImpl;
import com.onegravity.rteditor.api.format.RTFormat;
import com.x75f.installer.R;
import com.x75f.installer.Utils.Generic_Methods;

import java.io.InputStream;

/**
 * Created by JASPINDER on 7/11/2016.
 */
public class AddEditNote extends AppCompatActivity implements View.OnClickListener {
    private RTEditText NotesEditor;
    private Toolbar tool_bar;
    private RTManager rtManager;
    private Button bCam;
    private Button bDelete;
    private int REQUEST_CAMERA;
    private int SELECT_FILE;
    private boolean Cam = false;
    private String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_note);
        NotesEditor = (RTEditText) findViewById(R.id.NotesEditor);
        tool_bar = (Toolbar) findViewById(R.id.tool_bar);
        bCam = (Button) findViewById(R.id.bCam);
        bDelete = (Button) findViewById(R.id.bDelete);
        setSupportActionBar(tool_bar);
        getSupportActionBar().setTitle("Back");
        tool_bar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        // create RTManager
        RTApi rtApi = new RTApi(this, new RTProxyImpl(this), new RTMediaFactoryImpl(this, true));
        rtManager = new RTManager(rtApi, savedInstanceState);


        // register editor & set text
        rtManager.registerEditor(NotesEditor, true);
        bCam.setOnClickListener(this);
        bDelete.setOnClickListener(this);
        try {
            InputStream is = getAssets().open("index.html");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String str = new String(buffer);
            str = str.replace("\n", "<br/>");
            ImageGetter imageGetter = new ImageGetter();
            NotesEditor.setText(Html.fromHtml(str, imageGetter, null));
//            NotesEditor.setRichTextEditing(true,str);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        rtManager.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rtManager.onDestroy(isFinishing());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.bCam):
                UploadNewImage();
                message = NotesEditor.getText(RTFormat.HTML);
                break;
            case (R.id.bDelete):
//                Html.toHtml(NotesEditor.getText());
                Generic_Methods.getToast(AddEditNote.this, Html.toHtml(NotesEditor.getText()).toString());
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("mainresult1",requestCode+" "+ resultCode+" "+data);
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("mainresult",requestCode+" "+ resultCode+" "+data);
    }

    public void UploadNewImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEditNote.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Cam = true;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Cam = false;
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(galleryIntent, SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }





    private class ImageGetter implements Html.ImageGetter {

        public Drawable getDrawable(String source) {
            int id;

            if (source.equals("stack.jpg")) {
                id = R.drawable.camera_crop_width;
            } else if (source.equals("overflow.jpg")) {
                id = R.drawable.camera_crop_width;
            } else {
                id = R.mipmap.login_bg;
            }

            Drawable d = getResources().getDrawable(id);
            d.setBounds(0, 0, 310,206);
            return d;
        }
    }

    ;
}
