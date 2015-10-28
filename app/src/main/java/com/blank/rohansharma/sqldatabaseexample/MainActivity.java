package com.blank.rohansharma.sqldatabaseexample;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    String database="Student";  //database name
    String table="Details"; //table name
    EditText fname,lname,roll;
    Button add,del,mod,viewall,view,delall;
    SQLiteDatabase Database;    //declare SQLite Database

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fname=(EditText)findViewById(R.id.firstname);
        lname=(EditText)findViewById(R.id.lastname);
        roll=(EditText)findViewById(R.id.rollno);
        add=(Button)findViewById(R.id.addbutton);
        del=(Button)findViewById(R.id.delbutton);
        mod=(Button)findViewById(R.id.modbutton);
        viewall=(Button)findViewById(R.id.viewallbutton);
        view=(Button)findViewById(R.id.viewbutton);
        delall=(Button)findViewById(R.id.delallbutton);
        Database=openOrCreateDatabase(database, Context.MODE_PRIVATE,null);
        Database.execSQL("CREATE TABLE IF NOT EXISTS " + table + "(fname TEXT,lname TEXT,roll TEXT);");
    }

    public void onClickAdd(View v)
    {
        if (fname.getText().toString().trim().length() == 0 || lname.getText().toString().trim().length() == 0 || roll.getText().toString().trim().length() == 0) {
            Toast toast = Toast.makeText(this, "Error! Enter all values", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Cursor c=Database.rawQuery("SELECT * FROM " + table + " WHERE roll='" + roll.getText().toString().trim() + "'", null);
        if(c.moveToFirst())
        {
            Toast toast = Toast.makeText(this, "Error! Roll number already exists", Toast.LENGTH_SHORT);
            toast.show();
        }
        Database.execSQL("INSERT INTO "+table+" VALUES('"+fname.getText().toString()+"','"+lname.getText().toString()+"','"+roll.getText().toString()+"');");
        Toast toast = Toast.makeText(this, "Successfully Added!", Toast.LENGTH_SHORT);
        toast.show();
        fname.setText("");
        lname.setText("");
        roll.setText("");
    }

    public void onClickDel(View v)
    {
        if (roll.getText().toString().trim().length() == 0)
        {
            Toast toast = Toast.makeText(this, "Error! Enter Roll no", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Cursor c=Database.rawQuery("SELECT * FROM " + table + " WHERE roll='" + roll.getText().toString().trim() + "'", null);
        if(c.moveToFirst())
        {
            Database.execSQL("DELETE FROM "+table+" WHERE roll='"+roll.getText().toString().trim()+"'");
            Toast toast = Toast.makeText(this, "Successfully Deleted!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Toast toast = Toast.makeText(this, "Error! Invalid Roll no", Toast.LENGTH_SHORT);
            toast.show();
        }
        fname.setText("");
        lname.setText("");
        roll.setText("");
    }

    public void onClickMod(View v)
    {
        if (roll.getText().toString().trim().length() == 0)
        {
            Toast toast = Toast.makeText(this, "Error! Enter Roll no", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Cursor c=Database.rawQuery("SELECT * FROM " + table + " WHERE roll='" + roll.getText().toString().trim() + "'", null);
        if(fname.getText().toString().trim().length()==0||lname.getText().toString().trim().length()==0)
        {
            Toast toast = Toast.makeText(this, "Error! Enter all details", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if(c.moveToFirst())
        {
            Database.execSQL("UPDATE "+table+" SET fname='"+fname.getText().toString().trim()+"',lname='"+lname.getText().toString().trim()+"' WHERE roll='"+roll.getText().toString().trim()+"'");
            Toast toast = Toast.makeText(this, "Successfully Modified!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Toast toast = Toast.makeText(this, "Error! Invalid Roll no", Toast.LENGTH_SHORT);
            toast.show();
        }
        fname.setText("");
        lname.setText("");
        roll.setText("");
    }

    public void onClickViewAll(View v)
    {
        Cursor c=Database.rawQuery("SELECT * FROM " + table, null);
        if(c.getCount()==0)
        {
            Toast toast = Toast.makeText(this, "Database empty", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        StringBuffer buffer=new StringBuffer();
        while(c.moveToNext())
        {
            buffer.append("First Name: "+c.getString(0)+"\n");
            buffer.append("Last Name: "+c.getString(1)+"\n");
            buffer.append("Roll no: "+c.getString(2)+"\n\n");
        }
        showMessage("Student Details",buffer.toString());
        fname.setText("");
        lname.setText("");
        roll.setText("");
    }

    public void onClickView(View v)
    {
        if (roll.getText().toString().trim().length() == 0)
        {
            Toast toast = Toast.makeText(this, "Error! Enter Roll no", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Cursor c=Database.rawQuery("SELECT * FROM " + table + " WHERE roll='"+roll.getText().toString().trim()+"'", null);
        StringBuffer buffer=new StringBuffer();
        if(c.moveToFirst())
        {
            buffer.append("First Name: "+c.getString(0)+"\n");
            buffer.append("Last Name: " + c.getString(1) + "\n");
            buffer.append("Roll no: " + c.getString(2));
            showMessage("Student Details",buffer.toString());
        }
        else
        {
            Toast toast = Toast.makeText(this, "Error! Invalid Roll no", Toast.LENGTH_SHORT);
            toast.show();
        }
        fname.setText("");
        lname.setText("");
        roll.setText("");
    }

    public void onClickDelAll(View v)
    {
        Cursor c=Database.rawQuery("SELECT * FROM " + table, null);
        if(c.getCount()==0)
        {
            Toast toast = Toast.makeText(this, "Error! Underflow", Toast.LENGTH_SHORT);
            toast.show();
        }
        if(c.moveToFirst())
        {
            Database.execSQL("DELETE FROM "+table);
            Toast toast = Toast.makeText(this, "Successfully Deleted All!", Toast.LENGTH_SHORT);
            toast.show();
        }
        fname.setText("");
        lname.setText("");
        roll.setText("");
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
