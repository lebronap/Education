package example.com.education.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yalantis.ucrop.UCrop;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import example.com.education.R;


public class MyInfoActivity extends AppCompatActivity {
    private static final String TAG = "MyInfoActivity";

    private static final int CODE_SELECT_IMAGE = 2;
    private ImageView iv_head;
    private Uri resultUri;
    private Long id;
    private int roleType;
    private  BroadcastReceiver mBroadcastReceiver;

    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage.jpg";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //registerBoradcastReceiver();
        setContentView(R.layout.activity_my_info);
        SharedPreferences preferences = getSharedPreferences("userlogin", Context.MODE_PRIVATE);
        id = preferences.getLong("id",0);
        roleType = preferences.getInt("roleType",0);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(albumIntent, CODE_SELECT_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME + ".jpg";
            Uri outUri = Uri.fromFile(new File(getCacheDir(), destinationFileName));
            UCrop uCrop = UCrop.of(data.getData(), outUri);
            uCrop.start(MyInfoActivity.this);
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            resultUri = UCrop.getOutput(data);
            iv_head.setImageURI(resultUri);
            putHeadView(resultUri.toString(),id);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Throwable cropError = UCrop.getError(data);
        }
    }


    private void putHeadView(String headUri, Long idUri) {
            RequestParams params = new RequestParams("http://10.1.35.9:8080/Education/ChangeHeadServlet");
            params.addQueryStringParameter("uri",headUri);
            params.addParameter("id",idUri);
            params.addParameter("roleType",roleType);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    JSONObject jsonObject = JSON.parseObject(result);
                    int code = jsonObject.getInteger("code");
                    if (code == 200){
                        Toast.makeText(MyInfoActivity.this, "更换成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MyInfoActivity.this, "更换失败", Toast.LENGTH_SHORT).show();
                    }
                }



                //请求异常后的回调方法
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }
                //主动调用取消请求的回调方法
                @Override
                public void onCancelled(CancelledException cex) {
                }
                @Override
                public void onFinished() {
                }
            });
        }

    /*@Override
    public void onBackPressed() {
      //  super.onBackPressed();
        putHeadView(resultUri.toString(),id);
            *//*Intent intent = new Intent(MyInfoActivity.this, MainActivity.class);
            intent.putExtra("imageUri", resultUri.toString());
            setResult(RESULT_OK, intent);
            startActivity(intent);*//*
         finish();
    }*/

   /* public void onBackPressed(Uri resulturi) {
        putHeadView(resultUri.toString(),id);
    }*/



   /* public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("changeSuccess");
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }*/

}



