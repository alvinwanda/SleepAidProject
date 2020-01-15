package info.papdt.blackblub.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceFragment;
import android.view.MenuItem;

import com.uxcam.UXCam;

import info.papdt.blackblub.R;
import info.papdt.blackblub.util.Settings;



public class MoreSettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Settings settings = Settings.getInstance(this);
        UXCam.startWithKey("k0gs1hzhx0v7in3");

        if (settings.isDarkTheme()) {
            setTheme(android.R.style.Theme_Material);
            setContentView(R.layout.bnwlogo);
        }
        else {setContentView(R.layout.clrlogo);}

        super.onCreate(savedInstanceState);

        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setSubtitle(R.string.app_name);
        }

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getPreferenceManager().setSharedPreferencesName(Settings.PREFERENCES_NAME);
            addPreferencesFromResource(R.xml.pref_more_settings);

            findPreference(Settings.KEY_DARK_THEME).setOnPreferenceChangeListener((pref, newValue) -> {
                final Activity parent = getActivity();
                if (parent != null) {
                    Settings.getInstance(parent).setDarkTheme((boolean) newValue);
                    new Handler(Looper.getMainLooper()).postDelayed(parent::recreate, 200);
                    return true;
                } else {
                    return false;
                }
            });

            findPreference("about").setOnPreferenceClickListener(pref -> {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(R.layout.dialog_about);
                //builder.setPositiveButton(android.R.string.ok, (d, i) -> {});
                builder.show();
                return true;
            });
        }

    }

}