package brainbreaker.mechfood;

import com.firebase.client.Firebase;

/**
 * Created by brainbreaker on 18/2/16.
 */
public class ApplicationClass extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        Firebase.setAndroidContext(this);
    }
}
