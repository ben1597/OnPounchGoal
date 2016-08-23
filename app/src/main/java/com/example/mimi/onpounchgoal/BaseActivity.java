package com.example.mimi.onpounchgoal;

import android.app.Activity;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseActivity extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.id.BaseActivity,menu);
        menu.add(0, 1, 0, "about");
        menu.add(0, 2, 0, "exit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this, "Add action", Toast.LENGTH_SHORT).show();
                return true;
            case 2:
                Toast.makeText(this, "Buy action", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
