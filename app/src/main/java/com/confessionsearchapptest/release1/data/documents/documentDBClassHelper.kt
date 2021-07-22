package com.confessionsearchapptest.release1.data.documents

import android.content.Context
import android.database.Cursor

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import com.confessionsearchapptest.release1.databasehelpers.DatabaseContext
import android.widget.Toast
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteDatabase
import kotlin.jvm.Synchronized
import android.database.sqlite.SQLiteQueryBuilder
import com.confessionsearchapptest.release1.data.bible.BibleTranslation
import com.confessionsearchapptest.release1.data.bible.BibleContentsList
import android.os.Environment
import android.util.Log
import com.confessionsearchapptest.release1.data.bible.BibleBooks
import com.confessionsearchapptest.release1.data.bible.BibleContents
import java.io.File
import java.lang.Exception

import kotlin.collections.ArrayList

class documentDBClassHelper : SQLiteAssetHelper {
    var context: Context? = null

    constructor(context: Context?) : super(context, DATABASE_NAME, null, DATABASE_VERSION) {
        this.context = context
    }

    internal constructor(context: Context?, databaseName: String?) : super(
        DatabaseContext(context),
        databaseName,
        null,
        DATABASE_VERSION
    ) {
        try {
            val myPath = DATABASE_PATH
            val dbFile = File(myPath)
            if (dbFile.exists()) {
                Toast.makeText(context, "DBExists", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Db doesn't exist", Toast.LENGTH_LONG).show()
            }
        } catch (e: SQLiteException) {
            Log.d("Database doesn't exist", e.message!!)
        }
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        return super.getWritableDatabase()
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
    }

    @Synchronized
    override fun getReadableDatabase(): SQLiteDatabase {
        return super.getReadableDatabase()
    }

    // Cursors
    val documentTypes: Cursor
        get() {
            val db = readableDatabase
            Log.d("CHECKDB", readableDatabase.path)
            val docTypes = SQLiteQueryBuilder()
            val docTypeSQLQuery = arrayOf(KEY_DOCUMENT_TYPE_ID, KEY_DOCUMENT_TYPE_NAME)
            val tables = TABLE_DOCUMENTTYPE
            docTypes.tables = tables
            val c = docTypes.query(db, docTypeSQLQuery, null, null, null, null, null)
            c.moveToFirst()
            return c
        }
    val bibleTranslations: Cursor
        get() {
            val db = readableDatabase
            Log.d("BIBLECHK", readableDatabase.path)
            val bibleTranslations = SQLiteQueryBuilder()
            val bibleTranslationSQLQuery = arrayOf(
                KEY_BIBLE_TRANSLATION_ID,
                KEY_BIBLE_TRANSLATION_TITLE,
                KEY_BIBLE_TRANSLATION_ABBREV
            )
            val tables = TABLE_BIBLETRANSLATION
            bibleTranslations.tables = tables
            val c =
                bibleTranslations.query(db, bibleTranslationSQLQuery, null, null, null, null, null)
            c.moveToFirst()
            return c
        }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENTTITLE)
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENT)
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENTTYPE)
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIBLECONTENTS)
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIBLE_BOOKS)
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIBLETRANSLATION)
           db.execSQL("CREATE TABLE " + TABLE_DOCUMENTTITLE)
            db.execSQL("CREATE TABLE " + TABLE_DOCUMENT)
            db.execSQL("CREATE TABLE " + TABLE_DOCUMENTTYPE)
             db.execSQL("CREATE TABLE " + TABLE_BIBLECONTENTS)
            db.execSQL("CREATE TABLE " + TABLE_BIBLETRANSLATION)
            db.execSQL("CREATE TABLE " + TABLE_BIBLE_BOOKS)
            onCreate(db)
        }
    }

    //Get Bible translations
    fun getAllBibleTranslations(dbBibles: SQLiteDatabase?): ArrayList<BibleTranslation> {
        val translations = ArrayList<BibleTranslation>()
        val commandText = "SELECT * FROM BibleTranslations"
        val cursor = bibleTranslations
        try {
            if (cursor.moveToFirst()) {
                var i = 0
                while (i < cursor.count) {
                    val newTranslation = BibleTranslation()
                    newTranslation.bibleTranslationID = cursor.getInt(
                        cursor.getColumnIndex(
                            KEY_BIBLE_TRANSLATION_ID
                        )
                    )
                    newTranslation.bibleTranslationName = cursor.getString(
                        cursor.getColumnIndex(
                            KEY_BIBLE_TRANSLATION_TITLE
                        )
                    )
                    newTranslation.bibleTranslationAbbrev = cursor.getString(
                        cursor.getColumnIndex(
                            KEY_BIBLE_TRANSLATION_ABBREV
                        )
                    )
                    translations.add(newTranslation)
                    i++
                    cursor.moveToNext()
                }
            }
        } catch (e: Exception) {
            Log.e("Error", "Error Retrieving Entries from DB")
        } finally {
            if (cursor != null && !cursor.isClosed) cursor.close()
        }
        return translations
    }

    //Get Bible Contents
    //QUERY SQL STATEMENTS FOR SEARCH
    fun getAllDocTypes(dbTypes: SQLiteDatabase?): ArrayList<DocumentType> {
        val types = ArrayList<DocumentType>()
        val commandText = "SELECT * FROM DocumentType"
        val cursor = documentTypes
        try {
            if (cursor.moveToFirst()) {
                var i = 0
                while (i < cursor.count) {
                    val newType = DocumentType()
                    newType.documentTypeID = cursor.getInt(
                        cursor.getColumnIndex(
                            KEY_DOCUMENT_TYPE_ID
                        )
                    )
                    newType.documentTypeName = cursor.getString(
                        cursor.getColumnIndex(
                            KEY_DOCUMENT_TYPE_NAME
                        )
                    )
                    types.add(newType)
                    i++
                    cursor.moveToNext()
                }
            }
        } catch (e: Exception) {
            Log.d("ERROR", "Error retrieving entries from DB")
        } finally {
            if (cursor != null && !cursor.isClosed) cursor.close()
            run { cursor.close() } //
        }
        return types
    }
//Book Specific Filtering
    fun getAllChapters(
        bibleList: SQLiteDatabase?,
        translationName: String?,
        bookNum: Int?
    ): BibleContentsList {
    val bookList = BibleContentsList()
    val accessString: String? = BibleTranslationAccess(translationName, bookNum)
    val cursor = bibleList!!.rawQuery(accessString, null)
    try {
        if (cursor.moveToFirst()) {
            var i = 0
            while (i < cursor.count) {
                val addBibleContent = BibleContents()
                addBibleContent.TranslationID = cursor.getInt(
                    cursor.getColumnIndex(
                        KEY_BIBLE_CONTENTS_TRANSLATION_ID_FK
                    )
                )

                addBibleContent.BookNum = cursor.getInt(
                    cursor.getColumnIndex(
                        KEY_BIBLE_CONTENTS_BOOKNUM_FK
                    )
                )
                addBibleContent.ChapterNum = cursor.getInt(
                    cursor.getColumnIndex(
                        KEY_BIBLE_CONTENTS_CHAPTERNUMBER
                    )
                )
                addBibleContent.VerseNumber = cursor.getInt(
                    cursor.getColumnIndex(
                        KEY_BIBLE_CONTENTS_VERSENUMBER
                    )
                )
                addBibleContent.VerseText = cursor.getString(
                    cursor.getColumnIndex(
                        KEY_BIBLE_CONTENTS_VERSETEXT
                    )
                )
                bookList.add(addBibleContent)
                i++
                cursor.moveToNext()
            }
        }
        cursor.close()
        return bookList
    } catch (exception: Exception) {
        exception.printStackTrace()
        cursor.close()
        return BibleContentsList()
    }
}

    //Get Chapters for spinner
    fun getAllChapters(
        bibleList: SQLiteDatabase?,
        translationName: String?,
        bookName: String?
    ): ArrayList<Int?> {
        val bookList = BibleContentsList()
        var chInt = 0
        var accessString: String?
        val ChList = ArrayList<Int?>()

       accessString  = BookChapterNumberAccess(bookName)


        val cursor = bibleList!!.rawQuery(accessString, null)
        try {
            if (cursor.moveToFirst()) {
                var i = 0
                chInt = cursor.getInt(cursor.getColumnIndex(KEY_BIBLE_CONTENTS_CHAPTERNUMBER))
                ChList.add(chInt)
                while (i < cursor.count) {
                    val addBibleContent = BibleContents()

                    addBibleContent.ChapterNum = cursor.getInt(
                        cursor.getColumnIndex(
                            KEY_BIBLE_CONTENTS_CHAPTERNUMBER
                        )
                    )

if(chInt<addBibleContent.ChapterNum!!)
{
    chInt = addBibleContent.ChapterNum!!
    ChList.add(addBibleContent.ChapterNum)}
                    i++
                    cursor.moveToNext()
                }
            }
            cursor.close()
            return ChList
        } catch (exception: Exception) {
            exception.printStackTrace()
            cursor.close()
            return ChList
        }
    }
    fun getAllVerseNumbers(
        bibleList: SQLiteDatabase?,
        translationName: String?,
        bookName: String?,chapNum: Int?
    ): ArrayList<Int?> {
        val bookList = BibleContentsList()
        var verseInt = 0
        var accessString: String?
        val verseList = ArrayList<Int?>()

        accessString  = BookChapterVerseAccess(chapNum,bookName)


        val cursor = bibleList!!.rawQuery(accessString, null)
        try {
            if (cursor.moveToFirst()) {
                var i = 0
                verseInt = cursor.getInt(cursor.getColumnIndex(KEY_BIBLE_CONTENTS_VERSENUMBER))
                verseList.add(verseInt)
                while (i < cursor.count) {
                    val addBibleContent = BibleContents()

                    addBibleContent.VerseNumber = cursor.getInt(
                        cursor.getColumnIndex(
                            KEY_BIBLE_CONTENTS_VERSENUMBER
                        )
                    )

                    if(verseInt<addBibleContent.VerseNumber!!)
                    {
                        verseInt = addBibleContent.VerseNumber!!
                        verseList.add(addBibleContent.VerseNumber)}
                    i++
                    cursor.moveToNext()
                }
            }
            cursor.close()
            return verseList
        } catch (exception: Exception) {
            exception.printStackTrace()
            cursor.close()
            return verseList
        }
    }


//get bible books
    fun getAllBooks(
        bibleList: SQLiteDatabase?
    ): ArrayList<BibleBooks> {
        var bookList =ArrayList<BibleBooks>()
        var cursor: Cursor?
        var accessString: String? = BibleBookAccess()
        cursor=bibleList!!.rawQuery(accessString,null)
        try{

            if(cursor.moveToFirst())
            {
                var i =0
                while(i<cursor.count)
                {
           val bibleBook = BibleBooks()
                    bibleBook.BookID=cursor.getInt(cursor.getColumnIndex(
                        KEY_BIBLE_BOOKS_ID))
                    bibleBook.BookName=cursor.getString(cursor.getColumnIndex(
                        KEY_BIBLE_BOOKS_BOOKNAME))
                    i++
                    cursor.moveToNext()
                    bookList.add(bibleBook)
                }
            }
            cursor.close()
            return bookList
        }
        catch (exception : Exception)
        {
            exception.printStackTrace()
            cursor.close()
            return bookList
        }
    }
//Chapter specific filtering


    fun getAllVerses(
        bibleList: SQLiteDatabase?,
        translationName: String?,
        bookName: String?,
        chapNum: Int?
    ): BibleContentsList {
        //var cursor: Cursor?
       var bibleContentsList = BibleContentsList()
        var accessString: String? = BibleChapterAccess(translationName,bookName,chapNum)
       val cursor=bibleList!!.rawQuery(accessString,null)
        try{
            if (cursor.moveToFirst()){
                var i =0
                while(i<cursor.count)
                {
                    val addBibleContent =BibleContents()
                    addBibleContent.TranslationID=cursor.getInt(cursor.getColumnIndex(
                        KEY_BIBLE_CONTENTS_TRANSLATION_ID_FK))

                    addBibleContent.BookNum=cursor.getInt(cursor.getColumnIndex(
                        KEY_BIBLE_CONTENTS_BOOKNUM_FK))
                    addBibleContent.ChapterNum = cursor.getInt(cursor.getColumnIndex(
                        KEY_BIBLE_CONTENTS_CHAPTERNUMBER))
                    addBibleContent.VerseNumber = cursor.getInt(cursor.getColumnIndex(
                        KEY_BIBLE_CONTENTS_VERSENUMBER))
                    addBibleContent.VerseText = cursor.getString(cursor.getColumnIndex(
                        KEY_BIBLE_CONTENTS_VERSETEXT))

                    bibleContentsList.add(addBibleContent)
                    i++
                    cursor.moveToNext()
                }
            }
            cursor.close()
            return BibleContentsList()
            }

        catch (exception : Exception)
        {
            exception.printStackTrace()
            cursor.close()
            return bibleContentsList
        }

    }

//Verse specific filtering
    fun getVerses(
        bibleList: SQLiteDatabase?,
        translationName: String?,
        bookName: String?,
        chapNum: Int?,
        verseNum: Int?
    ): BibleContentsList {
        var cursor: Cursor?
        var biblebookList = BibleContentsList()
        var cursor2: Cursor?
        var accessString: String? = BibleVerseAccess(translationName, bookName, chapNum, verseNum)
        cursor = bibleList!!.rawQuery(accessString, null)
        try {
            if (cursor.moveToFirst()) {
                var i = 0
                while (i < cursor.count) {
                    val addBibleContent = BibleContents()
                    addBibleContent.TranslationID = cursor.getInt(
                        cursor.getColumnIndex(
                            KEY_BIBLE_CONTENTS_TRANSLATION_ID_FK
                        )
                    )
                    addBibleContent.BookNum= cursor.getInt(
                        cursor.getColumnIndex(
                            KEY_BIBLE_CONTENTS_BOOKNUM_FK
                        )
                    )
                    addBibleContent.ChapterNum = cursor.getInt(
                        cursor.getColumnIndex(
                            KEY_BIBLE_CONTENTS_CHAPTERNUMBER
                        )
                    )
                    addBibleContent.VerseNumber = cursor.getInt(
                        cursor.getColumnIndex(
                            KEY_BIBLE_CONTENTS_VERSENUMBER
                        )
                    )
                    addBibleContent.VerseText = cursor.getString(
                        cursor.getColumnIndex(
                            KEY_BIBLE_CONTENTS_VERSETEXT
                        )
                    )

                    biblebookList.add(addBibleContent)
                    i++
                    cursor.moveToNext()

                }
            }
            cursor.close()

        } catch (exception: Exception) {
            exception.printStackTrace()
            cursor.close()

        }
    return biblebookList
    }
    //Probably Useless code
    fun getBibleChapters(
        bibleList: SQLiteDatabase?,
        translationName: String?,
    bookName: String?):ArrayList<Int?>{
        val bibleCHList = ArrayList<Int?>()
        var accessString:String?=BookChapterNumberAccess(bookName)




        return  bibleCHList
    }







    fun getVersesForProofs(){
        TODO("This is for future implementation with the Transition from static proofs to dynamic proofs allowing the user to select their own translation")}

    fun getAllDocTitles(type: String, dbType: SQLiteDatabase): ArrayList<DocumentTitle> {
        var type = type
        val documentTitles = ArrayList<DocumentTitle>()
        var typeID = 0
        val commandText: String
        if (type === "") type = "ALL"
        commandText = when (type.toUpperCase()) {
            "ALL" -> "SELECT * FROM DocumentTitle"
            else -> LayoutString(type.toUpperCase())
        }
        when (type.toUpperCase()) {
            "CREED" -> typeID = 1
            "CONFESSION" -> typeID = 2
            "CATECHISM" -> typeID = 3
            "ALL" -> typeID = 0
        }

        // SQLiteDatabase db =dbType; //getReadableDatabase();
        val cursor = dbType.rawQuery(commandText, null)
        try {
            if (cursor!!.moveToFirst()) {
                var i = 0
                while (i < cursor.count) {
                    val newTitle = DocumentTitle()
                    newTitle.documentTypeID = cursor.getInt(
                        cursor.getColumnIndex(
                            KEY_DOCUMENT_TITLE_TYPE_ID_FK
                        )
                    )
                    newTitle.documentID = cursor.getInt(cursor.getColumnIndex(KEY_DOCUMENTTITLE_ID))
                    newTitle.documentName = cursor.getString(
                        cursor.getColumnIndex(
                            KEY_DOCUMENTTITLE_NAME
                        )
                    )
                    if (newTitle.documentTypeID === typeID || typeID == 0) documentTitles.add(
                        newTitle
                    ) else {
                        i++
                        cursor.moveToNext()
                        continue
                    }
                    i++
                    cursor.moveToNext()
                }
            }
            cursor.close()
        } catch (e: Exception) {
            Log.d("Error", "Error Retrieving the entries from DocumentTitle Table")
        } finally {
            if (cursor != null && !cursor.isClosed) cursor.close()
            run { cursor!!.close() }
        }
        return documentTitles
    }

    //Fetch documents for processing
    fun getAllDocuments(
        fileString: String,
        fileName: String?,
        docID: Int,
        allDocs: Boolean?,
        dbList: SQLiteDatabase,
        access: String?,
        docList: DocumentList?,
        context: Context?
    ): DocumentList? {
        var docList = docList
        val cursor: Cursor?
        val documentList = DocumentList()
        val commandText: String
        val docCommandText: String
        val accessString: String
        var documentIndex = 0
        accessString = DataTableAccess(access)
        //SQL Query Execution
//Identify what needs selected
        commandText = if (docID != 0) {
            TableAccess(fileString)
        } else fileString
        docCommandText = accessString
        //Add entries to Document List
        val docTitle = ArrayList<DocumentTitle>()
        val docIds = ArrayList<Int?>()
        val docTitleList = ArrayList<String?>()
        cursor = dbList.rawQuery(commandText, null)
        //cursor1 =
        try {
            if (cursor.moveToFirst()) {
                var i = 0
                while (i < cursor.count) {
                    val addDoc = DocumentTitle()
                    addDoc.documentName = cursor.getString(
                        cursor.getColumnIndex(
                            KEY_DOCUMENTTITLE_NAME
                        )
                    )
                    addDoc.documentTypeID = cursor.getInt(
                        cursor.getColumnIndex(
                            KEY_DOCUMENT_TITLE_TYPE_ID_FK
                        )
                    )
                    addDoc.documentID = cursor.getInt(cursor.getColumnIndex(KEY_DOCUMENTTITLE_ID))
                    docTitle.add(addDoc)
                    i++
                    cursor.moveToNext()
                }
            }
            cursor.close()
            for (y in docTitle.indices) {
                docIds.add(docTitle[y].documentID)
                docTitleList.add(docTitle[y].documentName)
            }
            val cursor1: Cursor
            cursor1 = dbList.rawQuery(docCommandText, null)
            Log.d(
                "Size of Query List", cursor1.getColumnIndexOrThrow(KEY_DOCDETAILID_ID)
                    .toString()
            )
            if (cursor1.moveToFirst()) {
                var i = 0
                while (i < cursor1.count) {
                    val doc = Document()
                    doc.chName = cursor1.getString(cursor1.getColumnIndex(KEY_CHAPTER_NAME))
                    doc.chNumber = cursor1.getInt(cursor1.getColumnIndex(KEY_DOC_INDEX_NUM))
                    doc.documentID = cursor1.getInt(cursor1.getColumnIndex(KEY_DOCUMENT_ID_FK))
                    run {
                        documentIndex = docIds.indexOf(doc.documentID)
                        Log.d("DocumentIndex", "Document Index is $documentIndex")
                        if (documentIndex > -1) doc.documentName =
                            docTitle[documentIndex].documentName
                        Log.d("DocumentTitle", "Document Title: " + doc.documentName)
                    }
                    doc.chName = cursor1.getString(cursor1.getColumnIndex(KEY_CHAPTER_NAME))
                    doc.proofs = cursor1.getString(cursor1.getColumnIndex(KEY_CHAPTER_PROOFS))
                    doc.docDetailID = cursor1.getInt(cursor1.getColumnIndex(KEY_DOCDETAILID_ID))
                    doc.documentText = cursor1.getString(cursor1.getColumnIndex(KEY_CHAPTER_TEXT))
                    doc.tags = cursor1.getString(cursor1.getColumnIndex(KEY_DOCUMENT_TAGS))
                    doc.matches = cursor1.getInt(cursor1.getColumnIndex(KEY_MATCHES))
                    documentList.add(doc)
                    i++
                    cursor1.moveToNext()
                }
            }
            cursor1.close()
            Log.d("Size of List", documentList.size.toString())
            docList = documentList
            return docList
        } catch (e: Exception) {
            Log.d("ERROR", e.message!!)
        } finally {
            if (cursor != null && !cursor.isClosed) cursor.close()
            run {
                cursor.close()
                return docList
            }
        }
    }

    fun Formatter(formatString: String): String {
        var formatString = formatString
        val x = 0
        var formatter = ""
        val words: Array<String>
        words = formatString.split("|").toTypedArray()
        for (i in 0..words.size - 1) {
            formatter += words[i]
            formatter += "\r\n\n"
        }
        formatString = formatter
        return formatString
    }

    fun LayoutString(docType: String?): String {
        return String.format(
            "SELECT DocumentType.*, DocumentTitle.* FROM DocumentTitle NATURAL JOIN DocumentType WHERE DocumentTitle.DocumentTypeID = DocumentType.DocumentTypeId AND DocumentType.DocumentTypeName = '%s'",
            docType
        )
    }

    fun DataTableAccess(documentName: String?): String {
        return String.format(
            "SELECT Documenttitle.documentName, " +
                    "document.*, documenttitle.documentid FROM " +
                    "documentTitle NATURAL JOIN document WHERE document.DocumentID = DocumentTitle.DocumentID %s",
            documentName
        )
    }

    /*fun BookChapterVerseAccess(book : Int?):String{
        return "SELECT BibleTranslations.*, BibleBooks.*, BibleContents.* from BibleBooks Natural Join BibleContents Natural Join BibleTranslations Where " +
                "BibleContents.BookNumber = BibleBooks.BookID and BibleContents.TranslationID = BibleTranslations.TranslationID  And BibleBooks.BookID ="+bookID
    }*/

    fun BookChapterNumberAccess(bookName: String?) : String{
        return String.format("SELECT BibleTranslations.*, BibleBooks.*, BibleContents.* from BibleContents NATURAL JOIN BibleBooks Natural Join BibleTranslations " +
                "Where BibleContents.BookNumber = BibleBooks.BookID And BibleContents.TranslationID = BibleTranslations.TranslationID And BibleBooks.BookName = '%s'",bookName)

    }
    fun BookChapterVerseAccess(chapNum: Int?,bookName: String?) : String{
        return BookChapterNumberAccess(bookName)+" And BibleContents.ChapterNum = " + chapNum
    }



    fun BibleContentAccess(translationName: String?): String {
        return String.format(
            "SELECT BibleTranslations.*," +
                    "BibleContents.* FROM BibleTranslations NATURAL JOIN BibleContents WHERE BibleContents.TranslationID = BibleTranslations.TranslationID And BibleTranslations.TranslationTitle = '%s'",
            translationName
        )
    }

    fun BibleTranslationAccess(TranslationName: String?, BookName: String?): String {
        return             BibleContentAccess(TranslationName) + "And BibleBooks.BookName = "+BookName
    }
    fun BibleTranslationAccess(TranslationName: String?, BookNum: Int?): String {
        return             BibleContentAccess(TranslationName) + "And BibleContents.BookNumber = "+BookNum
    }

    fun BibleChapterAccess(
        TranslationName: String?,
      BookName: String?,
        ChapterNumber: Int?
    ): String {
        return String.format(
            BibleTranslationAccess(
                TranslationName,
                BookName
            ) + "BibleContents.ChapterNum = " + ChapterNumber
        )
    }
    fun BibleVerseAccess(TranslationName: String?,BookName: String?,ChapterNumber: Int?,VerseNumber: Int?): String{
        return String.format(BibleChapterAccess(TranslationName,BookName,ChapterNumber)+"BibleContents.VerseNumber = "+VerseNumber)
    }

    fun BibleBookAccess():String{
        return  "SELECT * FROM BibleBooks"
    }


    fun TableAccess(tableName: String): String {
        val table1: String
        table1 = if (tableName !== "") String.format(
            "SELECT documenttitle.* FROM documenttitle WHERE %s",
            tableName
        ) else "Select documenttype.*,documenttitle.* from documenttitle natural join documenttype where documenttitle.documenttypeid = documenttype.documenttypeid"
        return table1
    }

    companion object {
        //DATABASE INFORMATION
        private const val DATABASE_NAME = "confessionSearchDB.sqlite3"
        private const val DATABASE_VERSION = 4
        private val DATABASE_PATH = Environment.DIRECTORY_DOWNLOADS + "/" + DATABASE_NAME

        //TABLE INFO
        private const val TABLE_DOCUMENT = "Document"
        private const val TABLE_DOCUMENTTYPE = "DocumentType"
        private const val TABLE_DOCUMENTTITLE = "DocumentTitle"
        private const val TABLE_BIBLETRANSLATION = "BibleTranslations"
        private const val TABLE_BIBLECONTENTS = "BibleContents"
        private const val TABLE_BIBLE_BOOKS = "BibleBooks"
        var db: SQLiteDatabase? = null

        //DOCUMENT TABLE COLUMNS
        private const val KEY_DOCDETAILID_ID = "DocDetailID"
        private const val KEY_DOCUMENT_ID_FK = "DocumentID"
        private const val KEY_DOC_INDEX_NUM = "DocIndexNum"
        private const val KEY_CHAPTER_NAME = "ChName"
        private const val KEY_CHAPTER_TEXT = "ChText"
        private const val KEY_CHAPTER_PROOFS = "ChProofs"
        private const val KEY_DOCUMENT_TAGS = "ChTags"
        private const val KEY_MATCHES = "ChMatches"

        //DOCUMENT TITLE TABLE
        private const val KEY_DOCUMENTTITLE_ID = "DocumentID"
        private const val KEY_DOCUMENTTITLE_NAME = "DocumentName"
        const val KEY_DOCUMENT_TITLE_TYPE_ID_FK = "DocumentTypeID"

        //DOCUMENT TYPE TABLE
        private const val KEY_DOCUMENT_TYPE_ID = "DocumentTypeID"
        private const val KEY_DOCUMENT_TYPE_NAME = "DocumentTypeName"

        //BIBLE TRANSLATION TABLE
        private const val KEY_BIBLE_TRANSLATION_ID = "TranslationID"
        private const val KEY_BIBLE_TRANSLATION_TITLE = "TranslationTitle"
        private const val KEY_BIBLE_TRANSLATION_ABBREV = "TranslationAbbrev"

        //BibleBooksTable
        private const val KEY_BIBLE_BOOKS_ID = "BookID"
        private const val KEY_BIBLE_BOOKS_BOOKNAME="BookName"

        //BIBLE CONTENTS TABLE
        private const val KEY_BIBLE_CONTENTS_ENTRY_ID = "EntryID"
        private const val KEY_BIBLE_CONTENTS_TRANSLATION_ID_FK = "TranslationID"
        private const val KEY_BIBLE_CONTENTS_BOOKNUM_FK = "BookNum"
        private const val KEY_BIBLE_CONTENTS_CHAPTERNUMBER = "ChapterNum"
        private const val KEY_BIBLE_CONTENTS_VERSENUMBER = "VerseNumber"
        private const val KEY_BIBLE_CONTENTS_VERSETEXT = "VerseText"
        private var sInstance: documentDBClassHelper? = null
        @Synchronized
        fun getInstance(context: Context): documentDBClassHelper? {
            if (sInstance == null) {
                sInstance = documentDBClassHelper(context.applicationContext)
            }
            return sInstance
        }
    }
}