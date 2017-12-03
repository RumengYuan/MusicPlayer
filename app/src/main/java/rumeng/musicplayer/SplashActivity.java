package rumeng.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    //private int currentTime = 5;
    public Intent intent;
    private static final String TAG = "SplashActivity";

    /**
     * private Handler myHandler=new Handler(){
     * 消息处理
     *
     * @Override public void handleMessage(Message msg) {
     * super.handleMessage(msg);
     * int a  =(Integer)msg.obj;
     * Log.d(TAG, "handleMessage: "+a);
     * btnSkip.setText("跳过 "+a+" s");
     * if (a==0){
     * startActivity(intent);
     * <p>
     * }
     * }
     * };
     */
    private Button btnSkip;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        intent = new Intent(this, MainActivity.class);
        initView();
        imageView.setOnClickListener(splash2web());
        btnSkip.setOnClickListener(splash2Main());
    }

    private void initView() {
        btnSkip = (Button) findViewById(R.id.btn_skip);

        imageView = (ImageView) findViewById(R.id.iv_splash);

    }

    /**
       new  Thread(new Runnable() {
         @Override
         public void run() {
             while (true){
                 Message message=new Message();
                 message.what=2;
                 message.obj=currentTime;
                 myHandler.sendMessage(message);
                 if (currentTime==0){
                     return;
                 }
                 currentTime--;
                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
         }
     }).start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeMessages(2);
    }*/


    @NonNull
    private View.OnClickListener splash2Main() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
                finish();
            }

        };
    }
    @NonNull
    private View.OnClickListener splash2web() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this,WebActivity.class));
                count.cancel();
            }
        };
    }

    CountDownTimer count= new CountDownTimer(10000, 1000) {
         @Override
         public void onTick(long l) {
             btnSkip.setText("跳过 "+l/1000+" s");

         }
         @Override
         public void onFinish() {
             startActivity(intent);
         }
     }.start();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        count.cancel();
    }
}