package com.example.boundservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.boundservice.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding ;
    private MusicBoundService musicBoundService;
    private boolean isServiceConnected ;
    private ServiceConnection serviceConnection = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
               MusicBoundService.MyBinder myBinder = (MusicBoundService.MyBinder) service;
               musicBoundService = myBinder.getMusicBoundService();
               musicBoundService.startMusic();
               isServiceConnected = true;
        }
        // Gọi hàm onServiceDisconneted khi Service bị chết đột ngột ví dụ như thiếu tài nguyên.
        // Khi nó tháo bỏ ràng buộc nó hk nhảy vào thằng này.
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceConnected = false;

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main);
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 clickStartService();
            }
        });
        binding.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStopService();
            }
        });
    }

    private void clickStartService() {
        Intent intent = new Intent(this , MusicBoundService.class);
        bindService(intent, serviceConnection , Context.BIND_AUTO_CREATE);
        // Chú chúng ta không thể nào để start mucsic sau thằng bindSerivce này bởi vì trong hàm bindService chúng ta có truyền vào
        // một thằng serviceConnection nếu chúng ta để dưới thì sẽ bị trường hợp connnect service chưa được dã chạy vào start music
        // vậy nên khi chúng ta start mucsic trong hàm ServiceConnected
    }

    private void clickStopService() {
        if(isServiceConnected)
        {
           unbindService(serviceConnection);
           isServiceConnected = false;
        }
        else
        {

        }
    }

    
}