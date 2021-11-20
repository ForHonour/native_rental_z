package com.example.nativerentalz

import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private var dbHelper = SQLHelper(this)

    private lateinit var propertyID: EditText
    private lateinit var propertyName: EditText
    private lateinit var propertyAddress: EditText
    private lateinit var propertyType: EditText
    private lateinit var propertyFurniture: EditText
    private lateinit var propertyBedrooms: EditText
    private lateinit var propertyPrice: EditText
    private lateinit var propertyDate: EditText
    private lateinit var propertyReporter: EditText
    private lateinit var propertyNotes: EditText

    private lateinit var addButton: Button
    private lateinit var viewButton: Button
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        propertyID = findViewById(R.id.property_id_field)
        propertyName = findViewById(R.id.property_name_field)
        propertyAddress = findViewById(R.id.property_address_field)
        propertyType = findViewById(R.id.property_type_field)
        propertyFurniture = findViewById(R.id.property_furniture_field)
        propertyBedrooms = findViewById(R.id.property_bedrooms_field)
        propertyPrice = findViewById(R.id.property_price_field)
        propertyDate = findViewById(R.id.property_date_field)
        propertyReporter = findViewById(R.id.property_reporter_field)
        propertyNotes = findViewById(R.id.property_notes_field)

        addButton = findViewById(R.id.add_button)
        viewButton = findViewById(R.id.view_button)
        updateButton = findViewById(R.id.update_button)
        deleteButton = findViewById(R.id.delete_button)

        handleInserts()
        handleUpdate()
        handleDelete()
        handleView()
    }


    fun showToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    fun showDialog(title: String, Message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    fun clearEditTexts() {
        propertyID.setText("")
        propertyName.setText("")
        propertyAddress.setText("")
        propertyType.setText("")
        propertyFurniture.setText("")
        propertyBedrooms.setText("")
        propertyPrice.setText("")
        propertyDate.setText("")
        propertyReporter.setText("")
        propertyNotes.setText("")
    }

    private fun formIsValidated(): Boolean {
        when {
            propertyName.text.toString().trim().isBlank() -> {
                Toast.makeText(
                    applicationContext,
                    "Please enter a Name of the Property",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return false
            }
            propertyAddress.text.toString().trim().isBlank() -> {
                Toast.makeText(
                    applicationContext,
                    "Please enter a the Address of the Property",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return false
            }
            propertyType.text.toString().trim().isBlank() -> {
                Toast.makeText(
                    applicationContext,
                    "Please enter a Type of the Property",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return false
            }
            propertyBedrooms.text.toString().trim().isBlank() -> {
                Toast.makeText(
                    applicationContext,
                    "Please enter the number of Bedrooms",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return false
            }
            propertyDate.text.toString().trim().isBlank() -> {
                Toast.makeText(
                    applicationContext,
                    "Please enter the Date & Time when the Property is added",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return false
            }
            propertyPrice.text.toString().trim().isBlank() -> {
                Toast.makeText(
                    applicationContext,
                    "Please enter a Price",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return false
            }
            propertyReporter.text.toString().trim().isBlank() -> {
                Toast.makeText(
                    applicationContext,
                    "Please enter the name of the report (your name)",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return false
            }
            else -> return true
        }
    }


    private fun handleInserts() {
        addButton.setOnClickListener {
            if (formIsValidated()) {
                try {
                    dbHelper.insertData(
                        propertyName.text.toString(),
                        propertyAddress.text.toString(),
                        propertyType.text.toString(),
                        propertyFurniture.text.toString(),
                        propertyBedrooms.text.toString(),
                        propertyPrice.text.toString(),
                        propertyDate.text.toString(),
                        propertyReporter.text.toString(),
                        propertyNotes.text.toString(),
                    )
                    clearEditTexts()
                } catch (e: Exception) {
                    e.printStackTrace()
                    showToast(e.message.toString())
                }
            }
        }
    }

    private fun handleUpdate() {
        updateButton.setOnClickListener {
            if (formIsValidated()) {
                try {
                    val isUpdating = dbHelper.updateData(
                        propertyID.text.toString(),
                        propertyName.text.toString(),
                        propertyAddress.text.toString(),
                        propertyType.text.toString(),
                        propertyFurniture.text.toString(),
                        propertyBedrooms.text.toString(),
                        propertyPrice.text.toString(),
                        propertyDate.text.toString(),
                        propertyReporter.text.toString(),
                        propertyNotes.text.toString(),
                    )
                    if (isUpdating)
                        showToast("Data Updated Successfully")
                    else
                        showToast("Data Not Updated")
                } catch (e: Exception) {
                    e.printStackTrace()
                    showToast(e.message.toString())
                }
            }
        }
    }

    private fun handleDelete() {
        deleteButton.setOnClickListener {
            try {
                dbHelper.deleteData(propertyID.text.toString())
                clearEditTexts()
            } catch (e: Exception) {
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    private fun handleView() {
        viewButton.setOnClickListener(
            View.OnClickListener {
                val results = dbHelper.allData
                if (results.count == 0) {
                    showDialog("Error", "No Data Found")
                    return@OnClickListener
                }

                val buffer = StringBuffer()
                while (results.moveToNext()) {
                    buffer.append("ID: " + results.getString(0) + "\n")
                    buffer.append("Name: " + results.getString(1) + "\n")
                    buffer.append("Address: " + results.getString(2) + "\n")
                    buffer.append("Type: " + results.getString(3) + "\n")
                    buffer.append("Furniture: " + results.getString(4) + "\n")
                    buffer.append("Bedrooms: " + results.getString(5) + "\n")
                    buffer.append("Price: " + results.getString(6) + "\n")
                    buffer.append("Date: " + results.getString(7) + "\n")
                    buffer.append("Reporter: " + results.getString(8) + "\n")
                    buffer.append("Notes: " + results.getString(9) + "\n\n")
                }
                showDialog("Property Listing", buffer.toString())
            }
        )
    }
}