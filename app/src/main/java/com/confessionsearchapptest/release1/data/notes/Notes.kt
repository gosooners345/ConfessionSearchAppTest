package com.confessionsearchapptest.release1.data.notes


import android.icu.text.DateFormat
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
class Notes() : Parcelable, Cloneable,Comparable<Notes> {

    @ColumnInfo(name = "title")
    var name: String? = null

    @JvmField
    @PrimaryKey(autoGenerate = true)
    var noteID = 0

    @ColumnInfo(name = "content")
    var content: String? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        noteID = parcel.readInt()
        content = parcel.readString()
        timeModified=parcel.readLong()
        time=parcel.readString()
    }

    @ColumnInfo(name = "time_modified")
   var timeModified : Long?=System.currentTimeMillis()

@RequiresApi(Build.VERSION_CODES.N)
@ColumnInfo(name ="time_displayed")
var time : String?=DateFormat.getInstance().format(timeModified)


//migrating from old to new
    @RequiresApi(Build.VERSION_CODES.N)
    constructor(newName: String?, newContent: String?, newNoteID: Int) : this() {
        name = newName
        content = newContent
        this.noteID = newNoteID
         this.timeModified =System.currentTimeMillis()
       this.time = DateFormat.getInstance().format(timeModified)


    }
   /* @RequiresApi(Build.VERSION_CODES.N)
    constructor(newName: String?, newContent: String?, newNoteID: Int, timeModified : Long? ) : this() {
        name = newName
        content = newContent
        this.noteID = newNoteID
        this.timeModified=timeModified
        time=DateFormat.getInstance().format(this.timeModified)

    }*/

    override fun toString(): String {
        return "Notes{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                   ",time updated '=" + time +'\''+
                ",time modified '='" + timeModified + '\'' +
                '}'
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeString(name)
        dest.writeInt(noteID)
        dest.writeString(content)
        dest.writeLong(timeModified!!)
        dest.writeString(time!!)

    }

    @Throws(CloneNotSupportedException::class)
    override fun clone(): Any {
        return super.clone()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Notes) return false
        return name == other.name && content == other.content && time == other.time && timeModified == other.timeModified
    }



    companion object CREATOR : Parcelable.Creator<Notes> {
        override fun createFromParcel(parcel: Parcel): Notes {
            return Notes(parcel)
        }

        override fun newArray(size: Int): Array<Notes?> {
            return arrayOfNulls(size)
        }
var compareDateTime = java.util.Comparator<Notes>{
    notes1,notes2 ->
    if(notes1.timeModified!! < notes2.timeModified!!) 1
        else -1
}


    }

    override fun compareTo(other: Notes): Int {
        return noteID.compareTo(other.noteID)
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + noteID
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + (timeModified?.hashCode() ?: 0)
        result = 31 * result + (time?.hashCode() ?: 0)
        return result
    }

}