package com.example.addressbook;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Contact> contacts;
    EditText firstNameField, lastNameField, phoneNumberField, addressField;
    File outputFile = null;
    int permissionRequestCode = 0;

    public class Contact {
        String firstName, lastName, contactNumber, address;

        public Contact(String firstName, String lastName, String contactNumber, String address) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.contactNumber = contactNumber;
            this.address = address;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts = new ArrayList<>();

        firstNameField = findViewById(R.id.etx_first_name);
        lastNameField = findViewById(R.id.etx_last_name);
        phoneNumberField = findViewById(R.id.etx_phone_number);
        addressField = findViewById(R.id.etx_address);

        String outputFileName = "contacts.csv";
        outputFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + outputFileName);

        // Request permissions from user if they are not already set
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, permissionRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            new AlertDialog.Builder(this).setMessage("Permissions were not successfully set. App will now close.").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    finish();
                                }
                            }).show();
                        }
                    }
                }
                return;
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            addNewContact();

        } else if (item.getItemId() == R.id.save) {
            saveAllContacts();

        } else {
            return false;



        /* } else if (item.getItemId() == R.id.load) {
            try {
                textPage.setText("");

                BufferedReader reader = new BufferedReader( new FileReader(outputFile.getAbsolutePath()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    textPage.append(line + "\n");
                }
                new AlertDialog.Builder(this).setMessage("Load successful.").setPositiveButton("Ok", null).show();
                return true;
            } catch (IOException e) {
                new AlertDialog.Builder(this).setMessage("IOException: " + e.toString()).setPositiveButton("Ok", null).show();
            } catch (Exception e) {
                new AlertDialog.Builder(this).setMessage("Exception: " + e.toString()).setPositiveButton("Ok", null).show();
            }
        } else if (item.getItemId() == R.id.preferences) {
            try {
                startActivityForResult(new Intent(this, EditorPreferencesActivity.class), 0);
            } catch (Exception e) {
                new AlertDialog.Builder(this).setMessage("Exception: " + e.toString()).setPositiveButton("Ok", null).show();
            } */

        }
        return true;
    }

    void addNewContact() {
        String firstName = firstNameField.getText().toString(),
                lastName = lastNameField.getText().toString(),
                contactNumber = phoneNumberField.getText().toString(),
                address = addressField.getText().toString();

        if (firstName.equals("")) {
            new AlertDialog.Builder(this).setMessage("Please enter a valid first name.").setPositiveButton("Ok", null).show();
        } else if (lastName.equals("")) {
            new AlertDialog.Builder(this).setMessage("Please enter a valid last name.").setPositiveButton("Ok", null).show();
        } else if (contactNumber.equals("")) {
            new AlertDialog.Builder(this).setMessage("Please enter a valid phone number.").setPositiveButton("Ok", null).show();
        } else if (address.equals("")) {
            new AlertDialog.Builder(this).setMessage("Please enter a valid address.").setPositiveButton("Ok", null).show();
        } else {
            for (Contact existingContact : contacts) {
                if (firstName.equals(existingContact.getFirstName()) && lastName.equals(existingContact.getLastName())) {
                    new AlertDialog.Builder(this).setMessage("This person already exists.").setPositiveButton("Ok", null).show();
                    return;
                } else if (contactNumber.equals(existingContact.getContactNumber())) {
                    new AlertDialog.Builder(this).setMessage("This phone number already exists.").setPositiveButton("Ok", null).show();
                    return;
                }
            }
            Contact newContact = new Contact(firstNameField.getText().toString(), lastNameField.getText().toString(), phoneNumberField.getText().toString(), addressField.getText().toString());
            contacts.add(newContact);
            new AlertDialog.Builder(this).setMessage(newContact.getFirstName() + " " + newContact.getLastName() + " added to contacts. There are now " + contacts.size() + " contacts saved.").setPositiveButton("Ok", null).show();

        }
    }

    void saveAllContacts() {
        try {
            if (contacts.size() > 0) {
                PrintWriter pw = new PrintWriter(outputFile.getAbsolutePath());

                for (Contact con : contacts) {
                    pw.println(con.getFirstName() + "," + con.getLastName() + "," + con.getContactNumber() + "," + con.getAddress());
                }
                pw.flush();
                pw.close();
                new AlertDialog.Builder(this).setMessage("Save successful.").setPositiveButton("Ok", null).show();

            } else {
                new AlertDialog.Builder(this).setMessage("No contacts to save.").setPositiveButton("Ok", null).show();
            }
        } catch (IOException e) {
            new AlertDialog.Builder(this).setMessage("IOException: " + e.toString()).setPositiveButton("Ok", null).show();
        } catch (Exception e) {
            new AlertDialog.Builder(this).setMessage("Exception: " + e.toString()).setPositiveButton("Ok", null).show();
        }
    }
}