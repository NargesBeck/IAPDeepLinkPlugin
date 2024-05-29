package com.deadlypishi.iap;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.unity3d.player.UnityPlayer;
public class MainActivity extends AppCompatActivity {
    private final String UNITY_GAME_OBJECT = "NoSDKStoreHandler";
    private final String QUERY_SUCCESS_STATUS_VALUE_BAZAARPAY = "done";
    private final String QUERY_SUCCESS_STATUS_VALUE_ZARRINPAL = "ok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Returning from payment gateway
        String action = getIntent().getAction();
        Log.d("IAPDeeplinkActivity","Action : "+action);
        if(action!=null && action.equals("android.intent.action.VIEW"))
        {
            Uri data = getIntent().getData();
            if(data==null)
            {
                Log.d("IAPDeeplinkActivity","BazaarPay or ZarrinPal purchase returned null");
            }
            else
            {
                Log.d("IAPDeeplinkActivity","BazaarPay or ZarrinPal purchase returned : "+data.toString());
                String status = data.getQueryParameter("Status");
                if(status!=null && status.equalsIgnoreCase(QUERY_SUCCESS_STATUS_VALUE_BAZAARPAY))
                {
                    Log.d("BazaarPay","Status DONE trying to verify purchase...");
                    String authority = data.getQueryParameter("Authority");
                    UnityPlayer.UnitySendMessage(UNITY_GAME_OBJECT,"PurchaseSuccess",authority);
                    finish();//Finish purchase flow and return o unity
                }
                else if (status!=null && status.equalsIgnoreCase(QUERY_SUCCESS_STATUS_VALUE_ZARRINPAL))
                {
                    Log.d("Zarinpal","Status OK trying to verify purchase...");
                    String authority = data.getQueryParameter("Authority");
                    UnityPlayer.UnitySendMessage(UNITY_GAME_OBJECT,"PurchaseSuccess",authority);
                    finish();//Finish purchase flow and return o unity
                } else
                {
                    Log.d("IAPDeeplinkActivity","purchase failed : "+status);
                    UnityPlayer.UnitySendMessage(UNITY_GAME_OBJECT,"PurchaseFailed","failed");
                    this.finish();
                }
            }
        }
    }
}
