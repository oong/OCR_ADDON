package sun.bob.mcalendar.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import sun.bob.mcalendar.R;

/**
 * Created by oong on 2015-11-22.
 */
abstract public class BaseCompatActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings :
                startActivity(new Intent(getApplicationContext(), GlobalSettingsActivity.class));
                break;
            case R.id.action_housekeeping :
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.action_nutrient :
                startActivity(new Intent(getApplicationContext(), NutrientMainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    abstract public void initUI();

}
