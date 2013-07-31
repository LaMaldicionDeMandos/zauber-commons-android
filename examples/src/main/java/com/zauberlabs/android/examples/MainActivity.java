package com.zauberlabs.android.examples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by marcelo on 7/30/13.
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.examples, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.paginator_menu:
                intent = new Intent(this, PaginatorActivity.class);
                break;
            case R.id.vertical_scroll_menu:
                intent = new Intent(this, VerticalScrollActivity.class);
                break;
            case R.id.old_vertical_scroll_menu:
                intent = new Intent(this, OldVerticalScrollActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        this.startActivity(intent);
        return true;
    }
}
