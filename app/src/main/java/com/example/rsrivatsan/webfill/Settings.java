package com.example.rsrivatsan.webfill;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = getSharedPreferences("NETACCESS", MODE_PRIVATE);
        final String username = prefs.getString("username", "");
        final String password = prefs.getString("password", "");
        int radio = prefs.getInt("radio",-1);
        EditText user=(EditText)findViewById(R.id.editText2);
        EditText pass=(EditText)findViewById(R.id.editText);
        user.setText(username);
        pass.setText(password);
        if(radio==1 || radio==2){
            RadioButton b[]={(RadioButton)findViewById(R.id.radioButton2),(RadioButton)findViewById(R.id.radioButton)};
            b[radio-1].setChecked(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }
    public void save(View view){
        int status=0;
        EditText username=(EditText)findViewById(R.id.editText2);
        EditText password=(EditText)findViewById(R.id.editText);
        RadioGroup rg=(RadioGroup)findViewById(R.id.radio_group);
        String user=username.getText().toString(),pass= password.getText().toString();
        if(user.equals("")){
            status=1;
        }
        else if(pass.equals("")){
            status=2;
        }
        int radio=0;
        int selectedId=rg.getCheckedRadioButtonId();

        switch (selectedId){
            case R.id.radioButton2:radio=1;
                break;
            case R.id.radioButton:radio=2;
                break;
            default:status=3;
        }
        if(checkRoll(user)==0){
            status=4;
        }
        switch (status) {
            case 1:
                Toast.makeText(getApplicationContext(),"Enter Username",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getApplicationContext(),"Select a period",Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(getApplicationContext(),"Enter a valid Username",Toast.LENGTH_SHORT).show();
                break;
            default:
                SharedPreferences.Editor editor = getSharedPreferences("NETACCESS", MODE_PRIVATE).edit();
                editor.putString("username", user);
                editor.putString("password", pass);
                editor.putInt("radio",radio);
                editor.commit();
                finish();
                startActivity(new Intent(Settings.this,MainActivity.class));
        }
    }
    public int checkRoll(String r){
        char roll[]=r.toCharArray();
        if(r.length()==8) {
            if (Character.isLetter(roll[0]) && Character.isLetter(roll[1]) && Character.isDigit(roll[2]) && Character.isDigit(roll[3]) && Character.isLetter(roll[4]) && Character.isDigit(roll[5]) && Character.isDigit(roll[6]) && Character.isDigit(roll[7])) {
                return 1;
            } else return 0;
        }
        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
