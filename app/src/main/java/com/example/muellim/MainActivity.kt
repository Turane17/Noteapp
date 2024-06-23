package com.example.muellim
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muellim.Details
import com.example.muellim.databinding.ActivityMainBinding
import com.example.muellim.Note
import com.example.muellim.NoteAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myDatabase: SQLiteDatabase
    private lateinit var noteList: ArrayList<Note>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.fab.setOnClickListener {
            val intent = Intent(this, Details::class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }
        noteList= ArrayList<Note>()

        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        val noteAdapter= NoteAdapter(noteList)
        binding.recyclerView.adapter=noteAdapter


        try {

            myDatabase = this.openOrCreateDatabase("Notes", Context.MODE_PRIVATE, null)


            val cursor = myDatabase.rawQuery("SELECT * FROM notes", null)

            val idIx = cursor.getColumnIndex("id")
            val titleIx = cursor.getColumnIndex("title")
            val detailIx = cursor.getColumnIndex("details")

            while (cursor.moveToNext()) {
                val idInt = cursor.getInt(idIx)
                val titleStr = cursor.getString(titleIx)
                val detailStr = cursor.getString(detailIx)
                val note = Note(idInt.toString(), titleStr, detailStr)
                println(idInt.toString() + " " + titleStr + " " + detailStr)
                noteList.add(note)

            }

            cursor.close()


        } catch (ex: Exception) {
            ex.printStackTrace()
        }


    }
}