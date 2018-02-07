package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private static Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView tvDescription = findViewById(R.id.tv_description);
        TextView tvIngredients = findViewById(R.id.tv_ingredients);
        TextView tvAlsoKnownAs = findViewById(R.id.tv_also_known);
        TextView tvPlaceOfOrigin = findViewById(R.id.tv_place_of_origin);


        tvDescription.setText(sandwich.getDescription());
        if(sandwich.getIngredients().size() < 1) {
            tvIngredients.setText("None");
        } else tvIngredients.setText(listToString(sandwich.getIngredients()));

        if(sandwich.getAlsoKnownAs().size() < 1) {
            tvAlsoKnownAs.setText("None");
        } else tvAlsoKnownAs.setText(listToString(sandwich.getAlsoKnownAs()));

        if(sandwich.getPlaceOfOrigin().length() < 1) {
            tvPlaceOfOrigin.setText("None");
        } else tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());

        if(sandwich.getDescription().length() < 1) {
            tvDescription.setText("None");
        } else tvDescription.setText(sandwich.getDescription());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_back) {
            Intent intent1 = new Intent(this,MainActivity.class);
            startActivity(intent1);
        }

        return super.onOptionsItemSelected(item);
    }

    private String listToString(List<String> lst) {
        int lstcount = lst.size();
        int currcount = 0;
        String opstr = "";
        for(String str : lst) {
            currcount++;
            if(currcount != lstcount) {
                opstr = opstr + str + ", ";
            } else {
                opstr = opstr + str;
            }
        }
        return opstr;
    }
}
