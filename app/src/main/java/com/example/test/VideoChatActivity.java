package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingError;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.ZoomApiError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomAuthenticationError;
import us.zoom.sdk.ZoomSDKAuthenticationListener;
import us.zoom.sdk.ZoomSDKInitParams;
import us.zoom.sdk.ZoomSDKInitializeListener;
import us.zoom.sdk.StartMeetingOptions;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static java.util.Calendar.getInstance;

public class VideoChatActivity extends AppCompatActivity {

    /*
    private JoinMeetingDialog joinMeetingDialog;
    private LoginDialog loginDialog;
    private Button join;
    private Button login;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat);

        /*
        //setting
        join = (Button)findViewById(R.id.join_button);
        login = (Button)findViewById(R.id.login_button);

        // create a dialog to join meeting
        join.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                 joinMeetingDialog = new JoinMeetingDialog(getApplicationContext(), joinMeetingListener, joinCancelListener);
                 joinMeetingDialog.show();
            }
        });

        // create a dialog to login
        login.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                loginDialog = new LoginDialog(getApplicationContext(), loginListener, loginCancelListener);
                loginDialog.show();
            }
        });

        */
    }

    /*
    private View.OnClickListener joinMeetingListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "화상 채팅을 시작합니다.", Toast.LENGTH_SHORT).show();
            joinMeetingDialog.dismiss();
        }
    };

    private View.OnClickListener joinCancelListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "화상 채팅 참여를 취소합니다.", Toast.LENGTH_SHORT).show();
            joinMeetingDialog.dismiss();
        }
    };

    private View.OnClickListener loginListener = new View.OnClickListener() {
        public  void onClick(View v) {
            Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
            loginDialog.dismiss();
        }
    };

    private View.OnClickListener loginCancelListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "로그인 참여를 취소합니다.", Toast.LENGTH_SHORT).show();
            loginDialog.dismiss();
        }
    };

    */

    private void initSdk(Context context) {

        ZoomSDK sdk = ZoomSDK.getInstance();

        ZoomSDKInitParams params = new ZoomSDKInitParams();
        params.appKey = Credentials.SDK_KEY;
        params.appSecret = Credentials.SDK_SECRET;
        params.domain = Credentials.SDK_DOMAIN;
        params.enableLog = true;

        ZoomSDKInitializeListener listener = new ZoomSDKInitializeListener() {
            @Override
            public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) { }

            @Override
            public void onZoomAuthIdentityExpired() { }
        };

        sdk.initialize(context, listener, params);
    }

    // 1. the join Meeting function
    private void joinMeeting(Context context, String meetingNumber, String password) {
        MeetingService meetingService = ZoomSDK.getInstance().getMeetingService();
        JoinMeetingOptions options = new JoinMeetingOptions();
        JoinMeetingParams params = new JoinMeetingParams();
        params.displayName = "";
        params.meetingNo = meetingNumber;
        params.password = password;
        meetingService.joinMeetingWithParams(context, params, options);
    }
    // 2-1. the login function
    private void login(String username, String password) {
        int result = ZoomSDK.getInstance().loginWithZoom(username, password);
        if (result == ZoomApiError.ZOOM_API_ERROR_SUCCESS) {

            // 2-2. After request is executed, listen for the authentication result prior to starting a meeting
            ZoomSDK.getInstance().addAuthenticationListener(authLsitener);
        }
    }

    // 2-3. the start Meeting function
    private void startMeeting(Context context) {
        ZoomSDK sdk = ZoomSDK.getInstance();
        if(sdk.isLoggedIn()) {
            MeetingService meetingService = sdk.getMeetingService();
            StartMeetingOptions options = new StartMeetingOptions();
            meetingService.startInstantMeeting(context, options);
        }
    }

    // create a dialog to join meeting
    private void createJoinMeetingDialog() {
        new
                AlertDialog.Builder(this).setView(R.layout.activity_join_meeting_dialog).setPositiveButton("JOIN")
    }

}