package com.example.addressbook;

import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Contact> contacts;
    EditText firstNameField, lastNameField, phoneNumberField, addressField;

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
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {

            addNewContact();
            return true;

            /*
            try {
                PrintWriter pw = new PrintWriter(outputFile.getAbsolutePath());
                pw.println(textPage.getText().toString());
                pw.flush();
                pw.close(); // close the file to ensure data is flushed to file
                new AlertDialog.Builder(this).setMessage("Save successful.").setPositiveButton("Ok", null).show();
                return true;
            } catch (IOException e) {
                new AlertDialog.Builder(this).setMessage("IOException: " + e.toString()).setPositiveButton("Ok", null).show();
            } catch (Exception e) {
                new AlertDialog.Builder(this).setMessage("Exception: " + e.toString()).setPositiveButton("Ok", null).show();
            } */

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
        return false;
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
        }

    }
}