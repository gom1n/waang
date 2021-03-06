package com.waang.waang;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;


public class FirstSemester extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper dh;

    ArrayList<FirstSemesterData> firstSemesterDataList;
    FirstAdapter firstAdapter;

    TextView yearAndSemester1;

    private ArrayList<FirstSemesterData> arraylist;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        String year = intent.getBundleExtra("myBundle").getString("year");
        String semester = intent.getBundleExtra("myBundle").getString("semester");
        System.out.println("year and semester : " + year + " , " + semester);

        yearAndSemester1 = (TextView) findViewById(R.id.yearAndSemester1);
        yearAndSemester1.setText(year + "?????? " + semester + "?????? ????????????");

        FirstSemesters();

        this.InitializeClassData();

        arraylist = new ArrayList<FirstSemesterData>();
        arraylist.addAll(firstSemesterDataList);

        ListView listView = (ListView) findViewById(R.id.firstSemesterList);
        firstAdapter = new FirstAdapter(getApplicationContext(), firstSemesterDataList);

        listView.setAdapter(firstAdapter);

        EditText firstSearch = (EditText) findViewById(R.id.firstSearch);

        firstSearch.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){

            }
            @Override
            public void afterTextChanged(Editable editable){
                String text = firstSearch.getText().toString();
                search(text);
            }
        });

    }

    public void search(String charText){

        firstSemesterDataList.clear();

        if(charText.length() == 0){
            firstSemesterDataList.clear();
            firstSemesterDataList.addAll(arraylist);
        }
        else{
            for(int i = 0; i < arraylist.size(); i++){
                if(arraylist.get(i).getClassName().toLowerCase().contains(charText)){
                    firstSemesterDataList.add(arraylist.get(i));
                }
            }
        }
        firstAdapter.notifyDataSetChanged();
    }


    public void InitializeClassData() {
        firstSemesterDataList = new ArrayList<FirstSemesterData>();

        db = dh.getReadableDatabase();
        Cursor c = db.query("firstsemester", null, null, null, null, null, null);


            while (c.moveToNext()) {
                String coursemajor = c.getString(c.getColumnIndex("??????????????????"));
                String subjectnames = c.getString(c.getColumnIndex("????????????"));
                String compare = c.getString(c.getColumnIndex("????????????"));
                String section = c.getString(c.getColumnIndex("??????"));
                Double scores = c.getDouble(c.getColumnIndex("??????"));
                firstSemesterDataList.add(new FirstSemesterData(coursemajor, subjectnames, compare, section, scores));
                if(firstSemesterDataList.size() >= 2278) break;
            }

        // ????????? ?????? ?????? ????????? ???????????? ?????? ??????
    }


    public void FirstSemesters() {
        ContentValues values = new ContentValues();
        Workbook workbook = null;
        Sheet sheet = null;

        //db?????? ??????
        dh = new DatabaseHelper(FirstSemester.this, "firstsemester.db", null, 1);
        db = dh.getWritableDatabase();


//?????? ???????????? db??? ????????????
        try {
            InputStream is = getBaseContext().getResources().getAssets().open("firstsem.xls");
            workbook = Workbook.getWorkbook(is);

            if (workbook != null) {
                sheet = workbook.getSheet(0);

                if (sheet != null) {

                    int colTotal = sheet.getColumns();
                    int rowIndexStart = 1;
                    int rowTotal = sheet.getColumn(colTotal - 1).length;

                    for (int row = rowIndexStart; row < rowTotal; row++) {

                        for (int col = 0; col < colTotal; col++) {
                            String contents = sheet.getCell(col, row).getContents();

                            switch (col) {
                                case 0:
                                    values.put("??????", contents);
                                    break;
                                case 1:
                                    values.put("??????????????????", contents);
                                    break;
                                case 2:
                                    values.put("????????????", contents);
                                    break;
                                case 3:
                                    values.put("????????????", contents);
                                    break;
                                case 4:
                                    values.put("??????", contents);
                                    break;
                                case 5:
                                    values.put("????????????", contents);
                                    break;
                                case 6:
                                    values.put("??????", contents);
                                    break;
                                case 7:
                                    values.put("??????", contents);
                                    break;
                                case 8:
                                    values.put("????????????", contents);
                                    break;
                                case 9:
                                    values.put("?????????", contents);
                                    break;
                                case 10:
                                    values.put("?????????", contents);
                                    break;
                                case 11:
                                    values.put("????????????", contents);
                                    break;
                                case 12:
                                    values.put("?????????", contents);
                                    break;
                                case 13:
                                    values.put("????????????", contents);
                                    break;
                                case 14:
                                    values.put("??????????????????", contents);
                                    break;
                                case 15:
                                    values.put("??????????????????", contents);
                                    break;
                                case 16:
                                    values.put("?????????????????????", contents);
                                    break;
                            }
                        }
                        //db??? firstsemester ???????????? values ??? ?????????
                        db.insert("firstsemester", null, values);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    class FirstSemesterData {
        private String majorCourse;
        private String className;
        private String classClassification;
        private String classSection;
        private Double classCredit;

        public FirstSemesterData() {

        }

        public FirstSemesterData(String majorCourse, String className, String classClassification, String classSection, Double classCredit) {
            this.majorCourse = majorCourse;
            this.className = className;
            this.classClassification = classClassification;
            this.classSection = classSection;
            this.classCredit = classCredit;
        }

        public String getMajorCourse() {
            return this.majorCourse;
        }
        public String getClassName() { return this.className; }
        public String getClassClassification() { return this.classClassification; }
        public String getClassSection() {
            return this.classSection;
        }
        public Double getClassCredit() {
            return this.classCredit;
        }
    }


    class FirstAdapter extends BaseAdapter {
        Context mContext = null;
        LayoutInflater mLayoutInflater = null;
        private ArrayList<FirstSemesterData> data;
        private TextView firstMajorTextView;
        private TextView classSemesterNameTextView;
        private TextView classSemesterClassificationTextView;
        private TextView classSemesterSectionTextView;
        private TextView classSemesterCreditTextView;
        private Button addClass;

        // ?????????????????? ?????????????????? ??????
        private FirebaseDatabase mDatabase;
        private DatabaseReference mReference;
        private FirebaseAuth firebaseAuth;
        String id;

        // ????????????+?????? array
        String classifiList [] = {"????????????????????????????????????", "????????????SW??????", "????????????????????????", "??????????????????", "???????????????2?????????",
                "????????????", "???????????????????????????", "????????????????????????", "??????????????????",
                "???????????????????????????", "???????????????????????????", "???????????????????????????", "???????????????????????????", "???????????????????????????", "???????????????????????????",
        };

        public FirstAdapter(Context context, ArrayList<FirstSemesterData> dataArray) {
            mContext = context;
            data = dataArray;
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        public void addItem(FirstSemesterData item) {
            data.add(item);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public FirstSemesterData getItem(int position) {
            return data.get(position);
        }

        public View getView(int position, View converView, ViewGroup parent) {

            View view = mLayoutInflater.inflate(R.layout.first_listview_custom, null);


            //id ????????????
            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            String numberinfo = user.getEmail();
            int idIndex = numberinfo.indexOf("@");
            id = numberinfo.substring(0, idIndex);
            // ?????????????????? ??????
            mDatabase = FirebaseDatabase.getInstance();
            mReference = mDatabase.getReference("waang/UserAccount/" + id);

            addClass = (Button) view.findViewById(R.id.addFirstClass);
            addClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // ?????? ?????? ?????? ?????? ?????? ??????
                    String majorCourse = data.get(position).getMajorCourse();
                    String className = data.get(position).getClassName();
                    String classClassification = data.get(position).getClassClassification();
                    String classSection = data.get(position).getClassSection();
                    Double classCredit = data.get(position).getClassCredit();
                    // ????????? ?????? _ ??????) id - ??????????????? - ????????? - ????????????1?????? - [????????????] - [????????????/??????/????????????/??????]
                    mReference.child("????????? ??????").child(majorCourse).child(classClassification + classSection).child(className).setValue(data.get(position));
                    mReference = mDatabase.getReference("waang/UserAccount/" + id);
                    String str = classClassification + classSection;
                    for(int i=0; i<classifiList.length; i++) {
                        if(str.equals(classifiList[i])) {
                            mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Double temp = dataSnapshot.child("??? ????????????").child(str).getValue(Double.class);
                                    Double credit = temp + classCredit;
                                    mReference.child("??? ????????????").child(str).setValue(credit);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
            });


            firstMajorTextView = (TextView) view.findViewById(R.id.FirstSemesterMajorName);
            firstMajorTextView.setText(data.get(position).getMajorCourse());
            classSemesterNameTextView = (TextView) view.findViewById(R.id.FirstclassSemesterNameCus);
            classSemesterNameTextView.setText(data.get(position).getClassName());
            classSemesterClassificationTextView = (TextView) view.findViewById(R.id.FirstclassSemesterClassification);
            classSemesterClassificationTextView.setText(data.get(position).getClassClassification());
            classSemesterSectionTextView = (TextView) view.findViewById(R.id.FirstclassSection);
            classSemesterSectionTextView.setText(data.get(position).getClassSection());
            classSemesterCreditTextView = (TextView) view.findViewById(R.id.FirstclassSemesterCreditCus);
            classSemesterCreditTextView.setText(data.get(position).getClassCredit() + "");

            return view;
        }
    }
}

