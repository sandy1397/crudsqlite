package com.example.sqllite

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var dbHelper = DatabaseHandler(this)

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    private fun clearEditTexts() {
        id.setText("")
        name.setText("")
        mobile.setText("")
    }

    //function to hide keyboard on click anywhere other than edittext
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun insertData(){
        dbHelper.insertData(name.text.toString(), mobile.text.toString())
        clearEditTexts()
        showToast("Data Added Successfully xD")
    }

    private fun displayIdData(){
        val res: Cursor = dbHelper.viewIdData(id.text.toString())
        if (res.count==0){
            showToast("No Data")
        }
        else{
            while (res.moveToNext()){
                name.setText(res.getString(1))
                mobile.setText(res.getString(2))
            }
        }
    }

    private fun displayMobileData(){
        val res: Cursor = dbHelper.viewMobileData(mobile.text.toString())
        if (res.count==0){
            showToast("No Data")
        }
        else{
            while (res.moveToNext()){
                id.setText(res.getString(0))
                name.setText(res.getString(1))
            }
        }
    }

    private fun updateData(){
        val isUpdate = dbHelper.updateData(id.text.toString(), name.text.toString(), mobile.text.toString())
        if (isUpdate)
            showToast("Data Updated Successfully")
        else
            showToast("Data Not Updated")
        clearEditTexts()
    }

    private fun deleteData(){
        dbHelper.deleteData(id.text.toString())
        clearEditTexts()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = DatabaseHandler(this)

        add_btn.setOnClickListener {
         insertData()
        }

        view_btn.setOnClickListener{
            val strid = id.text.toString()
            if(strid.trim().isNotEmpty()) {
                displayIdData()
            }
            else{
                displayMobileData()
            }
        }

        view_all_btn.setOnClickListener{
            val res: Cursor = dbHelper.viewAllData
            val buffer= StringBuffer()
            if (res.count==0){
                showToast("No Data")
            }
            else{
                while (res.moveToNext()){
                    buffer.append("ID: " + res.getString(0) + "\n")
                    buffer.append("NAME: " + res.getString(1) + "\n")
                    buffer.append("MOBILE: " + res.getString(2) + "\n")
                    buffer.append("\n")
                    display_data.text = buffer.toString()
//                    showToast("ID:"+res.getString(0))
//                    showToast("NAME:"+res.getString(1))
//                    showToast("MOBILE:"+res.getString(2))
                }
            }
            clearEditTexts()
        }

        update_btn.setOnClickListener{
           updateData()
        }

        delete_btn.setOnClickListener {
           deleteData()
        }
    }

}