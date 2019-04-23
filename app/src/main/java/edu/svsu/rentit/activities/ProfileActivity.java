package edu.svsu.rentit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.svsu.rentit.models.User;
import edu.svsu.rentit.workers.GetListingBackgroundWorker;
import edu.svsu.rentit.workers.ProfileBackgroundWorker;
import edu.svsu.rentit.R;
import edu.svsu.rentit.workers.SubmitReviewBackgroundWorker;

public class ProfileActivity extends AppCompatActivity {

    User currentUser;

    TextView txt_Name;
    EditText txt_Bio;
    TextView review_text;
    Button btn_ViewListing;
    Button btn_CreateListing;
    Button btn_Submit;
    String sReviewCount;
    String sReview;
    String id;
    TextView setReview_text;
    TextView textView7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("RentIT - Profile");
        setSupportActionBar(toolbar);

        txt_Name = findViewById(R.id.tv_Profilename);
        txt_Bio = findViewById(R.id.eT_bio);
        btn_ViewListing = findViewById(R.id.btn_ViewListings);
        btn_CreateListing = findViewById(R.id.btn_CreateListing);
        review_text = findViewById(R.id.review_text);
        btn_Submit = findViewById(R.id.btn_Submit);
        setReview_text = findViewById(R.id.setReview_text);
        textView7 = findViewById(R.id.textView7);

        // Grab User object from Intent
        if (getIntent().hasExtra("CURRENT_USER")) {
            currentUser = (User)getIntent().getSerializableExtra("CURRENT_USER");
            btn_Submit.setVisibility(View.INVISIBLE);
            setReview_text.setVisibility(View.INVISIBLE);
            textView7.setVisibility(View.INVISIBLE);
        } else {
            // Hide controls if this is not current logged in user

            btn_ViewListing.setVisibility(View.INVISIBLE);
            btn_CreateListing.setVisibility(View.INVISIBLE);
        }

        if (getIntent().hasExtra("USER_ID")) {

            ProfileBackgroundWorker profileWorker = new ProfileBackgroundWorker(ProfileActivity.this);
            profileWorker.execute(getIntent().getStringExtra("USER_ID"));
        }

        // Set UI values
        if (currentUser != null) {
            setUser(currentUser);
        }


        Button btnCreate = findViewById(R.id.btn_CreateListing);
        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, CreateListingActivity.class);
                intent.putExtra("USER", currentUser);
                startActivity(intent);
            }
        });


        Button btnView = findViewById(R.id.btn_ViewListings);
        btnView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ViewListingActivity.class));
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(setReview_text.getText().toString()) >= 0 || Integer.parseInt(setReview_text.getText().toString()) <= 5){
                    double newReview = Double.parseDouble(setReview_text.getText().toString());
                    double review = Double.parseDouble(sReview);
                    double reviewCount = Double.parseDouble(sReviewCount);
                    double finalReview = ((review * reviewCount) + newReview) / (reviewCount + 1);
                    String sFinalReview = Double.toString(finalReview);
                    reviewCount = reviewCount + 1;
                    sReviewCount = Double.toString(reviewCount);


                    SubmitReviewBackgroundWorker reviewWorker = new SubmitReviewBackgroundWorker(ProfileActivity.this);
                    reviewWorker.execute(sFinalReview, id, sReviewCount);
                }
                else{

                }

            }
        });

    }

    public void setUser(User outputUser)
    {
        txt_Name.setText(outputUser.getFirstname() + " " + outputUser.getLastname());
        txt_Bio.setText(outputUser.getBio());
        review_text.setText(outputUser.getReview());


    }

    public void setOutput(String name, String bio, String review, String reviewCount, String id)
    {
        this.sReviewCount = reviewCount;
        this.sReview = review;
        this.id = id;
        txt_Name.setText(name);
        txt_Bio.setText(bio);
        review_text.setText(review);
    }

}