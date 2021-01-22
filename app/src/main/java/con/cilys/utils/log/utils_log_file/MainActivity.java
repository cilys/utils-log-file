package con.cilys.utils.log.utils_log_file;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import con.cilys.utils.log.file.LogFileUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    LogFileUtils.getInstance().startWriteLog(Environment.getExternalStorageDirectory().getAbsolutePath(), "123.txt", "第" + i + "次追加内容...");

                    try {
                        Thread.sleep(50 * i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }
}
