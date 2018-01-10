package ke.co.toshngure.demo.editor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import ke.co.toshngure.editor.Editor;
import ke.co.toshngure.editor.EditorActivity;

public class MainActivity extends AppCompatActivity {

    private static final int TEXT_REQUEST = 231;
    public static final String TEXT_CACHE_KEY = "text_cache";
    private String mCurrentDescriptionHtmlString;
    private Editor renderEditor;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        renderEditor = findViewById(R.id.renderEditor);

        mCurrentDescriptionHtmlString = sharedPreferences.getString(TEXT_CACHE_KEY, "");

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditorActivity.start(MainActivity.this, TEXT_REQUEST, mCurrentDescriptionHtmlString);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data.getExtras() != null) {
                mCurrentDescriptionHtmlString = data.getExtras().getString(EditorActivity.EXTRA_TEXT);
                if (!TextUtils.isEmpty(mCurrentDescriptionHtmlString)) {
                    renderEditor.render(mCurrentDescriptionHtmlString.trim());
                    sharedPreferences.edit().putString(TEXT_CACHE_KEY, mCurrentDescriptionHtmlString).apply();
                }
            }
        }
    }
}
