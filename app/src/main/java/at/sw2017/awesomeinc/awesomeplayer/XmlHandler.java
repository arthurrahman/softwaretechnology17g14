package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;

/**
 * Created by julian on 26.05.17.
 */

public abstract class XmlHandler {

    protected String filename;
    protected String name;

    protected XmlSerializer xmlWriter;
    protected OutputStreamWriter xmlOutFile;

    protected XmlPullParser xmlReader;
    protected InputStreamReader xmlInFile;

    protected Context context;

    public XmlHandler(String name, Context context) {
        this.filename = name.concat(".xml");
        this.name = name;
        this.context = context;
    }

    /***
     * Initializes the file and starts the file with its necessary header
     */
    protected void writeStart() throws IOException {

        if(xmlOutFile != null) {
            xmlOutFile.close();
            xmlOutFile = null;
        }

        if(xmlWriter != null)
            xmlWriter = null;


        xmlWriter = Xml.newSerializer();
        xmlOutFile = new OutputStreamWriter(new FileOutputStream(new File(context.getExternalFilesDir(null), this.filename), false));
        xmlWriter.setOutput(xmlOutFile);

        xmlWriter.startDocument("UTF-8", true);
        xmlWriter.startTag(null, name);

    }

    /***
     * Serializes one single object at the current position of the XmlSerializer. If no serializer is initiolized, it calls startFile()
     * @param obj The object to be serialized
     */
    private Object temp;
    private Object temp2;
    protected <T> void writeOneObject(String tagName, T obj) throws IOException {

        if(xmlWriter == null)
            writeStart();

        temp = obj;

        Log.d("asdf", temp.toString());

        xmlWriter.startTag(null, tagName);
        for(Field f : obj.getClass().getDeclaredFields()){
            if(f.isSynthetic())
                continue;

            boolean accessible = f.isAccessible();

            f.setAccessible(true);

            xmlWriter.startTag("", f.getName());

            try {
                temp2 = f;
                String val = "";
                if(f.get(obj) != null)
                    val = f.get(obj).toString();

                xmlWriter.text(val);
            } catch (IllegalAccessException e) {
                Log.d("XmlHandler", e.getMessage());
                e.printStackTrace();
            }
            xmlWriter.endTag("", f.getName());

            f.setAccessible(accessible);
        }
        xmlWriter.endTag(null, tagName);


    }

    /***
     * Ends the file with closing tags of the header and destroys the XmlSerializer
     */
    protected void writeEnd() throws IOException {

        xmlWriter.endTag(null, name);
        xmlWriter.endDocument();

        xmlWriter.flush();
        xmlOutFile.close();
        xmlOutFile = null;
        xmlWriter = null;

    }

    /****
     * Reads the start of the header. initializes the readFile and readXML
     */
    protected void readStart() throws IOException, XmlPullParserException {
        if(xmlOutFile != null) {
            xmlOutFile.close();
            xmlOutFile = null;
        }

        if(xmlReader != null)
            xmlReader = null;

        xmlInFile = new InputStreamReader(new FileInputStream(new File(context.getExternalFilesDir(null), this.filename)));
        xmlReader = Xml.newPullParser();
        xmlReader.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
        xmlReader.setInput(xmlInFile);
        xmlReader.nextTag();
        xmlReader.require(XmlPullParser.START_TAG, null, this.name);
    }

    /****
     * Searches for the given start tag and creates the object. Initializes reader if necessary
     */
    protected void readOneObject(String startTagName, Object returnObject) throws IOException, XmlPullParserException {
        if(xmlReader == null)
            readStart();

        while(! xmlReader.getName().equals(startTagName))
            if(xmlReader.next() == XmlPullParser.END_DOCUMENT)
                return;

        while ( xmlReader.next() != XmlPullParser.END_TAG) {
            if (xmlReader.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = xmlReader.getName();

            try {
                Field f = returnObject.getClass().getDeclaredField(name);
                boolean accessible = f.isAccessible();
                f.setAccessible(true);
                try {
                    temp2 = f;
                    if(f.getType().getName().toLowerCase().equals("boolean"))
                        f.set(returnObject, Boolean.valueOf(readTag()));
                    else
                        f.set(returnObject, f.getType().cast(readTag()));
                } catch (IllegalAccessException e) {
                    Log.d("XmlHandler", "Internal error: ".concat(e.getMessage()));
                }
                f.setAccessible(accessible);

            } catch (NoSuchFieldException e) {
                Log.d("XmlHandler", e.getMessage());
            }
        }
    }

    private String readTag() throws IOException, XmlPullParserException {
        String ret = "";
        xmlReader.require(XmlPullParser.START_TAG, null, xmlReader.getName());
        if (xmlReader.next() == XmlPullParser.TEXT) {
            ret = xmlReader.getText();
            xmlReader.nextTag();
        }
        xmlReader.require(XmlPullParser.END_TAG, null, xmlReader.getName());

        return ret;
    }


    protected void readEnd() throws IOException, XmlPullParserException {

        while (xmlReader.next() != XmlPullParser.END_DOCUMENT);
        xmlReader = null;
        xmlInFile.close();
        xmlInFile = null;
    }


}
