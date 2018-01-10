package ke.co.toshngure.editor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.irshulx.R;

import ke.co.toshngure.editor.models.EditorTextStyle;

public class EditorActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT = "extra_text";

    private Editor textEditor;


    public static void start(Activity activity, int requestCode, @Nullable String html) {
        Intent starter = new Intent(activity, EditorActivity.class);
        starter.putExtra(EXTRA_TEXT, html);
        activity.startActivityForResult(starter, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textEditor = findViewById(R.id.textEditor);
        if (getIntent().getExtras() != null) {
            String text = getIntent().getExtras().getString(EXTRA_TEXT);
            if (text != null) {
                textEditor.render(text);
            } else {
                textEditor.render();
            }
        } else {
            textEditor.render();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_editor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ((item.getItemId() == R.id.action_done) || (item.getItemId() == android.R.id.home)) {
            Intent data = new Intent();
            data.putExtra(EXTRA_TEXT, textEditor.getContentAsHTML());
            setResult(Activity.RESULT_OK, data);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent data = new Intent();
        data.putExtra(EXTRA_TEXT, textEditor.getContentAsHTML());
        setResult(Activity.RESULT_OK, data);
        this.finish();
    }

    public void updateStyle(View view){
        if (view.getId() == R.id.actionBold){
            textEditor.updateTextStyle(EditorTextStyle.BOLD);
        }
        else if (view.getId() == R.id.actionItalic){
            textEditor.updateTextStyle(EditorTextStyle.ITALIC);
        }
        else if (view.getId() == R.id.actionIndent){
            textEditor.updateTextStyle(EditorTextStyle.INDENT);
        }
        else if (view.getId() == R.id.actionOutdent){
            textEditor.updateTextStyle(EditorTextStyle.OUTDENT);
        }
        else if (view.getId() == R.id.actionBulletsList){
            textEditor.insertList(false);
        }
        else if (view.getId() == R.id.actionNumbersList){
            textEditor.insertList(true);
        }
        else if (view.getId() == R.id.actionBreak){
            textEditor.insertDivider();
        }
        else if (view.getId() == R.id.actionInsertLink){
            textEditor.insertLink();
        }
    }
}
