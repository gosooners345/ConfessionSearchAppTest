package com.confessionsearchapptest.release1.data.notes


import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.util.*


@Entity(tableName = "notes")
class Notes : Parcelable, Cloneable {
    @ColumnInfo(name = "title")
    var name: String? = null

    @JvmField
    @PrimaryKey(autoGenerate = true)
    var noteID = 0

    @ColumnInfo(name = "content")
    var content: String? = null

   // @ColumnInfo(name = "time_modified")
  //  var timeModified : Long?=null

//@ColumnInfo(name ="time_displayed")
//var time : String?=null

    constructor()
    @RequiresApi(Build.VERSION_CODES.N)
    constructor(newname: String?, newcontent: String?, noteID: Int) {//},time : String? ) {
        name = newname
        content = newcontent
        this.noteID = noteID
    //     this.timeModified =System.currentTimeMillis()
  //     this.time = DateFormat.getInstance().format(timeModified)

    }
    @RequiresApi(Build.VERSION_CODES.N)
    constructor(newname: String?, newcontent: String?, noteID: Int, timeModified : Long?, timeString : String? ) {
        name = newname
        content = newcontent
        this.noteID = noteID
//        this.timeModified=timeModified

        //time=DateFormat.getInstance().format(this.timeModified)


    }

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
        noteID = `in`.readInt()
        content = `in`.readString()
        //timeModified = `in`.readLong()
        //time = `in`.readString()

    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeInt(noteID)
        dest.writeString(content)
        //dest.writeLong(timeModified!!)
        // dest.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Notes{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                 //  ",time modified '=" + time +'\''+
                '}'
    }

    @Throws(CloneNotSupportedException::class)
    override fun clone(): Any {
        return super.clone()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Notes) return false
        val notes = o
        return name == notes.name && content == notes.content// && time == notes.time && timeModified==notes.timeModified
    }


    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Notes?> = object : Parcelable.Creator<Notes?> {
            override fun createFromParcel(`in`: Parcel): Notes? {
                return Notes(`in`)
            }

            override fun newArray(size: Int): Array<Notes?> {
                return arrayOfNulls(size)
            }
        }

    }
}