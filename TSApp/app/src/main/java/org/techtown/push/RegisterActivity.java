package org.techtown.push;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class RegisterActivity extends AppCompatActivity {

/* Database part

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("text");

*/


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private FirebaseAuth mAuth;

    private EditText editTextEmail, editTextPassword, editTextName, editTextPhone;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("201Garage 회원가입") ;

/* Firebase Database Test
        Button testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("message").push().setValue("2");
            }
        });

 */

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword= findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        registerButton = findViewById(R.id.bt_Register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });
    }

    // When sign up, should conform to forms ( this form -> ******@****.*** )!!!!!!!!!!!
    // Sign up code is only this one ( including email_login_button code in onCreate() )
    private void createUser(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success
                            Toast.makeText(RegisterActivity.this, "Signed up Success", Toast.LENGTH_SHORT).show();

                            String userId = editTextEmail.getText().toString();
                            String name = editTextName.getText().toString();
                            String phoneNumber = editTextPhone.getText().toString();
                            addUserToFirebaseDB(userId, name, phoneNumber);

                            finish();
                        } else {
                            // Sign up failed -> 1. ID is already exist.
                            //                -> 2. Signed up form is disaccord
                            Toast.makeText(RegisterActivity.this,
                                    "Signed up Failed. The registration form is incorrect. Or the Email is already registered",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void addUserToFirebaseDB(final String userId, String name, String phoneNumber) {

        User user = new User(name, phoneNumber);

        databaseReference.child("users").child(userId).setValue(user);

    }

}
