package com.example.myapplication.meal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.meal.detail.InfoMeal;
import com.example.myapplication.meal.toolbar.CalendarAdapter;
import com.example.myapplication.meal.toolbar.Spinner_Meal_Adapter;
import com.google.android.material.appbar.AppBarLayout;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Meal_Library extends AppCompatActivity implements CalendarAdapter.OnItemListner {


    //------------  Valriables ALL - By Date Transition  --------------------

    int pomakRegulator;
    int pomakRegulatorSpinner;
    int byDayFirstTime;
    int allFirstTime;


    //----------------------------------------------------------------------------

//----------   Meal DB clases   ------------
    SQLiteDatabase sqLiteDatabase;
    DB_helper_Meal db_helper_meal;
//------------------------------------------------------------

//----------------- RV Adapter --------------
    AdapterAllMeals adapterAllMeals;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

//------------------------------------------------------------



//-----------------  Calendar Stuff -----------------------
    int prviPutMealOtvoren;
    RecyclerView gridRecycler;
    CalendarAdapter calendarAdapter;
    RecyclerView.LayoutManager layoutManagerGrid;
    int firstTime;
    NumberPicker np_Month;
    NumberPicker np_Year;
    String[] months;
//---------------------------------------------------------


    //-----  SELECT_BUTTON  -------  Getting Selected Dates with Meals From DB  --------------------

    ArrayList<String> datesWithMeals;
    String selectedMonth,selectedYear;
    ArrayList<String>selectedDates;


    Cursor cursorAllMeals;
    Cursor cursorSelect;
    int selectBtnCounta;

    SharedPreferences sharedPreferences;



    //------------  Creating Meal_Library_ToolBar  --------------------
    Spinner spinner;
    Toolbar myToolBar;
    Spinner_Meal_Adapter spinner_meal_adapter;
    ArrayList<String> selectBy;
    AppBarLayout appBarLayout;
    ConstraintLayout calendar;



    LocalDate localDate;
    Button btnSelect;
    View dividerSelect;

    AnimatedVectorDrawable animatedVectorDrawable;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_library);
        initVariables();
        initSharedPref();

        selectedDates=getSelectedDatesFromSharedP();


        //------------  Creating Things for RecyclerView Meals  --------------------
        Resources res=getResources();
        int mojaPlava=res.getColor(R.color.MojaTamnoPlava);
        int mojaCrvena=res.getColor(R.color.MojaCrvena);

        init_And_SetSpinner(res);
        initAdapterAndRView();



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,  ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());


            }
        }).attachToRecyclerView(recyclerView);


        //------------  CreatingCalendar  --------------------

        int m=0,y=0;
        initCalendarStuff(m,y,res);



        np_Month.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int monthNew=i1;
                localDate=localDate.withMonth(monthNew);
                selectedMonth= String.valueOf(i1);

                setCalendar(selectedDates);
            }
        });

        np_Year.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int yearNew=i1;
                localDate=localDate.withYear(yearNew);
                selectedYear= String.valueOf(i1);
                setCalendar(selectedDates);
            }
        });


        //------------  Creating Meal_Library_ToolBar  --------------------


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedOne="";
                    selectedOne= (String) adapterView.getItemAtPosition(i);

                if(selectedOne.equals("By day")||selectedOne.equals("Dan")){
                    int pomakOd=0;
                    int pomakDo=250;
                    if(byDayFirstTime==1){
                        if(pomakRegulatorSpinner==0){
                            pomakOd=0;
                            pomakDo=250;
                        }
                        else if (pomakRegulatorSpinner==1) {
                            pomakOd=0;
                            pomakDo=0;

                        }
                    }
                    btnSelect.setVisibility(View.VISIBLE);
                    AnimationStartDropDown(pomakOd,pomakDo);

                    if(firstTime==1){
                        cursorSelect=getDesiredCursor(selectedDates,getAllFromDB());
                        adapterAllMeals.swapCursor(cursorSelect);
                    }
                    byDayFirstTime=1;
                    firstTime=1;
                   AnimationMoveLeftRight(100,0);
                    }

                if(selectedOne.equals("All")||selectedOne.equals("Svi")){
                    int pomakOd=0;
                    int pomakDo=0;



                    if(allFirstTime==0){

                    }
                    else{
                        if(pomakRegulator==0){
                            pomakOd=250;
                            pomakDo=0;
                        }
                        else if (pomakRegulator==1) {
                            pomakOd=0;
                            pomakDo=0;

                        }
                    }


                    adapterAllMeals.swapCursor(getAllFromDB());
                    AnimationStartDropDown(pomakOd,pomakDo);
                    btnSelect.setVisibility(View.INVISIBLE);
                    allFirstTime=1;
                    AnimationMoveLeftRight(0,100);


                }
                }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //-----  SELECT_BUTTON  -------  Getting Selected Dates with Meals From DB  --------------------

            btnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(selectedDates.size()<1){
                        Toast.makeText(Meal_Library.this,"Niste odabrali niti jedan datum", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        cursorSelect=getDesiredCursor(selectedDates,getAllFromDB());
                        if(cursorSelect==null){
                        }
                        else {
                                if(selectBtnCounta==0){
                                    adapterAllMeals.swapCursor(cursorSelect);
                                    int pomakOd=250;
                                    int pomakDo=0;

                                    AnimationStartDropDown(pomakOd,pomakDo);
                                    pomakRegulator=1;
                                    selectBtnCounta=1;
                                    btnSelect.setText("Calendar");
                                    dividerSelect.setBackgroundColor(mojaPlava);
                                    pomakRegulatorSpinner=1;

                                }
                                else if (selectBtnCounta==1)
                                {
                                    int pomakOd=0;
                                    int pomakDo=250;
                                    AnimationStartDropDown(pomakOd,pomakDo);
                                    pomakRegulator=0;
                                    selectBtnCounta=0;
                                    btnSelect.setText("SELECT");
                                    dividerSelect.setBackgroundColor(mojaCrvena);
                                    pomakRegulatorSpinner=0;
                                }


                        }

                    }


                }
            });


    }

    private void setCalendar(ArrayList<String> selectedDatesThis){
       ArrayList<String> daysInMonth=new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            daysInMonth=daysInMonthArray(localDate);
        }
        if(prviPutMealOtvoren==0){
            datesWithMeals=new ArrayList<>();
            /*vraca listu datuma koji sadrzavaju neki obrok ali bez hh::mm
             i sad salje to u calendar adapter gdje pri bindanju kroz for petlju usporedjuju
              ovi poslani datumi s svakim mogucim datumom calendara i  ako   se podudaraju vidjet ce
             se icona koja kaze da taj datum sadrzi obrok
             */
            datesWithMeals=db_helper_meal.getAllDatesWithMeals();
            prviPutMealOtvoren=1;

        }

        calendarAdapter=new CalendarAdapter(getApplicationContext(),daysInMonth,this,datesWithMeals,selectedMonth,selectedYear,selectedDatesThis);
        layoutManagerGrid=new GridLayoutManager(getApplicationContext(),7);
        gridRecycler.setLayoutManager(layoutManagerGrid);
        gridRecycler.setAdapter(calendarAdapter);
    }


    /* metoda daysInMonthArray  prima datum od kojeg dobija informacije danu,godini,mjesecu
     i broju dana u mjesecu.
     Ova metoda zapravo osigurava podatke za calendar adapter,to jest ona salje adapteru listu s rednim
     brojevima dana ili praznih mjesta*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date){
        //date=date.plusMonths(3);
        ArrayList<String> daysInMonthArray=new ArrayList<>();
        YearMonth yearMonth=YearMonth.from(date); // npr:2023/07
        int daysInMonth=yearMonth.lengthOfMonth(); // npr:31
        LocalDate firstInMonth=date.withDayOfMonth(1);//npr:2023/07/01
        int dayOfWeek=firstInMonth.getDayOfWeek().getValue(); //vraca ime dana prvogu mjecesu

        //ova ptelja ispod provjerava ona prazna polja u calndaru tako da prvi redni broj
        //dana u mjesecu ne krene od npr:(ponedeljak)ako je npr:2023/07/01 (cetvrtak)
        for(int i=1;i<=42;i++){
            if(i<dayOfWeek || i>daysInMonth+dayOfWeek-1){
                daysInMonthArray.add("");
            }
            else {
                daysInMonthArray.add(String.valueOf(i-dayOfWeek+1));
            }

        }
        return daysInMonthArray;
    }




/*metoda koja vraca localTime u zeljenom formatu*/
    public static String getTime(LocalTime localTime) {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
        }
        else {return null;}
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return formatter.format(localTime);
        }
        else {return null;}

    }

    /*metoda koja pretvara int u dp vrijednost kako bi mogli postavljat neke dimenzije */
    public int intToDp(int dp){

        Resources r = getResources();
        int result = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,r.getDisplayMetrics()));

        return result;
    }

/*  Ova metoda(Implementirana iz CalendarAdaptera) omogucava da kliknuti datum dodamo u listu datuma koje zelimo
        ispisati iz baze podataka,također kada kliknemo na neki dan calendaru  boja u pozadini toga dana poplavi
     */
    @Override
    public void onItemClick(int position, View view,String dayy) {

        if(dayy==""){
                    //ukoliko je dan bez broja nemoj nista
        }
        else {
            Drawable drawable=view.getBackground();
            int bela=getResources().getColor(R.color.white);
            ColorDrawable colorDrawable= (ColorDrawable) drawable;
            int color=  colorDrawable.getColor();
            int svijetloPlava=getResources().getColor(R.color.MojaSvijetloPlava);
            String generatedDate=calendarAdapter.returnGeneratedDate(dayy);
            if(selectedDates.contains(generatedDate)) {
                view.setBackgroundColor(bela);
                selectedDates.remove(calendarAdapter.returnGeneratedDate(dayy));

                //  Toast.makeText(this, calendarAdapter.returnGeneratedDate(dayy) +" "+String.valueOf(selectedDates.size()) , Toast.LENGTH_SHORT).show();
            }
            else if(!selectedDates.contains(generatedDate) &&  db_helper_meal.getAllDatesWithMeals().contains(generatedDate)){
                view.setBackgroundColor(svijetloPlava);
                selectedDates.add(generatedDate);
                //   Toast.makeText(this, calendarAdapter.returnGeneratedDate(dayyw) +" "+String.valueOf(selectedDates.size()) , Toast.LENGTH_SHORT).show();
            }
        }



    }
    //ova metoda brise odabrani obrok iz nase baze obroka
    private void removeItem(long id){
        AlertDialog.Builder builder=new AlertDialog.Builder(Meal_Library.this);
        builder.setCancelable(false);
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                sqLiteDatabase.delete(DB_Meal.MealTable.TABLE_NAME,DB_Meal.MealTable._ID + "=" + id,null);
                adapterAllMeals.swapCursor(getDesiredCursor(selectedDates,getAllFromDB())); /*mijenjaju se podaci za RecyclerView
                koji izlistava odabrane obroke*/

                calendarAdapter.swapData(db_helper_meal.getAllDatesWithMeals()); /*taakođer se mijenjaju podaci potrebni za calendar
                jer obrok koji smo obrisali vise ne postoji in ne moze se vise pojavljivati na calendaru*/

            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.setTitle("You are about to delete item permanently");
        alertDialog.setMessage("Are you sure?");
        alertDialog.show();


    }
//animacija zaduzena za dropdown kalendara kada god je to potrebno
    void AnimationStartDropDown(int pomakFrom,int pomakTo){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(intToDp(pomakFrom),intToDp(pomakTo));

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                float animatedValue= (float) valueAnimator.getAnimatedValue();
                appBarLayout.setTranslationY(animatedValue);
                calendar.setTranslationY(animatedValue);
                recyclerView.setTranslationY(animatedValue);


            }
        });
        valueAnimator.setDuration(300);
        valueAnimator.start();
    }



    //metoda koja vadi sve obroke iz baze podataka
    Cursor getAllFromDB() {
        Cursor cursor;
        String[] columns={DB_Meal.MealTable.COLUMN_NAME,DB_Meal.MealTable.COLUMN_TIME,DB_Meal.MealTable.COLUMN_SUM_CALORIES,DB_Meal.MealTable._ID};
        cursor=sqLiteDatabase.query(DB_Meal.MealTable.TABLE_NAME, columns, null, null, null, null, DB_Meal.MealTable._ID);

        cursorAllMeals=cursor;
        return cursor;
    }

    // metoda koja dohvati sve datume od obroka iz baye podataka kako bi se ti datumi mogli koristiti za kalendar
    //ova metoda prima cursor jer su u njemu sadrzani svi podaci iz baze
    public ArrayList<String> getFulllDatesAsList(ArrayList<String> selectedDates,Cursor cursor){
        String fullDateTime;
        ArrayList<String> fullDatesFromCursor=new ArrayList<>();
        InfoMeal infoMeal=new InfoMeal(); // inicijaliziramo objekt kalse infomeal jer ta klasa sadrzi metodu koja nam je potrebna
        String[] dateAndTime=new String[2];
        String datePart="";
        String timePart="";
        if(cursor.isClosed()){
            Toast.makeText(Meal_Library.this, "closed", Toast.LENGTH_SHORT).show();
        }

        while (cursor.moveToNext()){

            fullDateTime = cursor.getString(cursor.getColumnIndexOrThrow(DB_Meal.MealTable.COLUMN_TIME));
            dateAndTime=infoMeal.vrijemeDatum(fullDateTime); // to je ta metoda koja raydvaja puni datum na vrijeme i datum
            datePart= dateAndTime[0];
            timePart=dateAndTime[1];

            /*Sad ovdje se događa glavni dio i logika.
            * datumi iz liste->selectedDates<-(to su datumi dodani klikom na dane iz kalendara  kao datumi za koje
            * zelimo da prikazu svoje datume) se uspoređuju sa svakim datumom iz baze kako bi te zeljene datume dobili
            * u punom formatu YY:MM:DD-hh:mm.
            i ako su jednaki puni datum  se onda dodaje u izlaznu listu */

            // ZASTO SE TO RADI ?
                /*  Zato jer prilikom odabira datuma na kalendaru koji je generiran  od odabrane godine,mjeseca i dana ne mozemo
                genrirati i vrijeme(sate minute i sekunde),  a bas to nam je potrebno da bi mogli napravit query u bazu sa
                odabranim datumima jer je sa svakim obrokom unesen i njegov puni datum nastanka po kojim mi radimo upit.
                */

            for(int i=0;i<selectedDates.size();i++){
                if(datePart.equals(selectedDates.get(i))){

                    fullDatesFromCursor.add(fullDateTime);
                }

            }

        }
        if(fullDatesFromCursor.isEmpty()){
            Toast.makeText(Meal_Library.this, "Prazno", Toast.LENGTH_SHORT).show();
        }
        return fullDatesFromCursor;
    }

    /*Metoda koja prima pune datume iz metode getFulllDatesAsList() kako bi iste poslala metodi
    * db_helper_meal.getCursorWithSpecificDated(datesFinal) koja obavlja query i vraca podatke o obrocima s tim datumima*/
        Cursor getDesiredCursor(ArrayList<String> dates,Cursor cursor){

            ArrayList<String> datesFinal=getFulllDatesAsList(dates,cursor);
            Cursor c=null;
             if(datesFinal.isEmpty()){
                 Toast.makeText(this, "Chosen date does not contain any meal", Toast.LENGTH_SHORT).show();
             }
             else {
                  c=db_helper_meal.getCursorWithSpecificDated(datesFinal);

             }
            return  c;
        }

        //svi odabrani datumi pri gasenju activitya ili citave aplikacije se srepmaju u  SharedPreference
        // kako bi opet pri pokretanju bili dostupni za uporabu
    void storeSelectedDatesToSharedPrefs(ArrayList<String> selectedDatesThis){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(selectedDatesThis);
        editor.putStringSet("key", set);

        editor.apply();

    }

    void initSharedPref(){
        sharedPreferences=getSharedPreferences("SHARED_PREFS_FILE",MODE_PRIVATE);
    }

    public ArrayList<String> getSelectedDatesFromSharedP(){
        ArrayList<String> data=new ArrayList<>();
        Set<String> set = sharedPreferences.getStringSet("key", null);
       data.addAll(set);
        return data;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        storeSelectedDatesToSharedPrefs(selectedDates);//događa se spremnaje

    }

    void initAdapterAndRView() {
        recyclerView = findViewById(R.id.recyclerMeal);
        db_helper_meal = new DB_helper_Meal(Meal_Library.this);
        sqLiteDatabase = db_helper_meal.getWritableDatabase();


        if (selectedDates.size() < 1) {
            cursorSelect = getAllFromDB();
            adapterAllMeals = new AdapterAllMeals(Meal_Library.this, cursorSelect);
            linearLayoutManager = new LinearLayoutManager(Meal_Library.this);
            linearLayoutManager.setOrientation(recyclerView.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapterAllMeals);
            spinner.setSelection(1);

        }
        else {

            cursorSelect = getDesiredCursor(selectedDates, getAllFromDB());
            adapterAllMeals = new AdapterAllMeals(Meal_Library.this, cursorSelect);
            linearLayoutManager = new LinearLayoutManager(Meal_Library.this);
            linearLayoutManager.setOrientation(recyclerView.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapterAllMeals);


        }
    }

    void init_And_SetSpinner(Resources res){
        spinner=findViewById(R.id.spinnerMea);
        selectBy=new ArrayList<>();
        String[]selectby;

        selectby=res.getStringArray(R.array.spinner_meal);

        selectBy.addAll(Arrays.asList(selectby));

        spinner_meal_adapter=new Spinner_Meal_Adapter(getApplicationContext(),selectBy);
        spinner.setAdapter(spinner_meal_adapter);
    }

    void AnimationDividerSelectRisize(int widthOd,int widthDo){
        int widthFrom=dividerSelect.getWidth();
/*
      ValueAnimator colorAnimator=ValueAnimator.ofArgb(colorFrom,colorTo);
      colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          @Override
          public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
              Integer animatedValue= (Integer) valueAnimator.getAnimatedValue();
              dividerSelect.setBackgroundColor(animatedValue);
          }
      });

 */


        //this converts dp into pixels(int)----------------
        Resources r = getResources();
        int widthTo= Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, widthDo,r.getDisplayMetrics()));
        //-----------------------------

        ValueAnimator valueAnimator=ValueAnimator.ofInt(widthFrom,widthTo);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                Integer animatedWidthValue= (Integer) valueAnimator.getAnimatedValue();
                dividerSelect.getLayoutParams().width=animatedWidthValue.intValue();
                dividerSelect.requestLayout();

            }

        });


       // AnimatorSet animatorSet=new AnimatorSet();
       // animatorSet.playTogether(valueAnimator,colorAnimator);
       valueAnimator.setDuration(500);
        valueAnimator.start();

    }

    void AnimationMoveLeftRight(int pomakFrom,int pomakTo){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(intToDp(pomakFrom),intToDp(pomakTo));

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                float animatedValue= (float) valueAnimator.getAnimatedValue();
                dividerSelect.setTranslationX(animatedValue);


            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    private void initCalendarStuff(int m,int y,Resources res){
        np_Month=findViewById(R.id.numberPicler);
        np_Month.setMinValue(1);
        np_Month.setMaxValue(12);
        months=res.getStringArray(R.array.months);
        np_Month.setDisplayedValues(months);

        np_Year=findViewById(R.id.numberPicler2);
        np_Year.setMinValue(2021);
        np_Year.setMaxValue(2024);

        gridRecycler=findViewById(R.id.recyclerGrid);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localDate=LocalDate.now();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            m=localDate.getMonthValue();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            y=localDate.getYear();
        }
        np_Month.setValue(m);np_Year.setValue(y);

        selectedMonth= String.valueOf(m);selectedYear= String.valueOf(y);
        setCalendar(selectedDates);

        btnSelect=findViewById(R.id.btnSelect);
        dividerSelect=findViewById(R.id.dividerSelect);
        firstTime=0;
        calendar=findViewById(R.id.constraintCalendar);
        appBarLayout=findViewById(R.id.toolbarLayout);
        myToolBar=findViewById(R.id.toolbar);
        setSupportActionBar(myToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initVariables(){
        firstTime=0;
        byDayFirstTime=0;
        pomakRegulatorSpinner=0;
        pomakRegulator=0;
        allFirstTime=0;
        selectedDates=new ArrayList<>();
        prviPutMealOtvoren=0;
        selectBtnCounta=0;
    }


}