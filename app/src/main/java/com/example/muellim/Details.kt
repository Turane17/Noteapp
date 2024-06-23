package com.example.muellim

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.muellim.databinding.ActivityDetailsBinding

class Details : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var myDatabase: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        binding.btn.setOnClickListener{
            insertDatabase()
        }
        binding.btnupdate.setOnClickListener{
            updateDatabase()
        }
        val intent=intent
        val id= intent.getStringExtra("id")


        val info= intent.getStringExtra("new")

        if(info=="new"){
            binding.note.setText("")
            binding.title.setText("")

        }else{
            try {
                myDatabase=this.openOrCreateDatabase("Notes",Context.MODE_PRIVATE,null)

                val cursor=myDatabase.rawQuery("SELECT * FROM notes WHERE id = ?", arrayOf(id))

                val title=cursor.getColumnIndex("title")
                val details=cursor.getColumnIndex("detail")

                while (cursor.moveToNext()){
                    binding.note.setText(cursor.getString(details))
                    binding.title.setText(cursor.getString(title))
                }
                cursor.close()

            }catch (ex:Exception){
                ex.printStackTrace()
            }
        }

    }


    fun insertDatabase(){
        try {

            val myDatabase=this.openOrCreateDatabase("Notes", Context.MODE_PRIVATE,null)

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Notes(id INTEGER PRIMARY KEY,title VARCHAR,details VARCHAR)")

            val sqlSorgu="INSERT INTO notes(title,detail)VALUES(?,?)"
            val statement=myDatabase.compileStatement(sqlSorgu)
            statement.bindString(1,binding.title.text.toString())
            statement.bindString(2,binding.note.text.toString())
            statement.execute()

             Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show()

        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }

    fun updateDatabase(){
        try {

            val myDatabase=this.openOrCreateDatabase("Notes", Context.MODE_PRIVATE,null)

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes(id INTEGER PRIMARY KEY,title VARCHAR,detail VARCHAR)")

            val sqlSorgu="UPDATE notes set title=?,detail=? WHERE id=?"
            val statement=myDatabase.compileStatement(sqlSorgu)
            statement.bindString(1,binding.title.text.toString())
            statement.bindString(2,binding.note.text.toString())
            statement.execute()

             Toast.makeText(this,"update",Toast.LENGTH_LONG).show()

        }catch (ex:Exception){
            ex.printStackTrace()
        }

        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}