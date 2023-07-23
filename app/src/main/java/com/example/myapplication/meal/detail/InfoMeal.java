package com.example.myapplication.meal.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.meal.DB_Meal;
import com.example.myapplication.meal.DB_helper_Meal;
import com.example.myapplication.meal.Meal_Blueprint;
import com.example.myapplication.meal.Meal_Library;

import java.util.ArrayList;
import java.util.Arrays;

public class InfoMeal extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    DB_helper_Meal db_helper_meal;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    AdapterInfo adapterInfo;

    Meal_Blueprint majFood;
    ArrayList<String> stringArrayList;
    ArrayList<Integer> integerArrayList;

    //-----------------------
    TextView tvCal;
    TextView tvFat;
    TextView tvPro;
    TextView tvCarb;
    TextView tvMealName;
    TextView tvTime;
    TextView tvDate;

    TextView tvinfoItemNumber;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_meal);

        db_helper_meal=new DB_helper_Meal(InfoMeal.this);
        sqLiteDatabase=db_helper_meal.getReadableDatabase();
        Intent intent=getIntent();
        String sentTimeFromMealLib = intent.getStringExtra("vrijemeStvaranjaMeala");
        init();
        initAdapter_RV();

        //maj food je objet klase Meal_Blueprint dakle ona reprezentira jedan obrok
        majFood=getAllFromDB(sentTimeFromMealLib);

        //ovdje vadimo vrijednosti  i saljemo ih metodi setValues koja te vrijednosti postavlja kao text ciljanih TextViewa-a
        setValues(majFood.getM_name(),vrijemeDatum(majFood.getTime()), (int) majFood.getSum_calories(),majFood.getSum_fat(),majFood.getSum_protein(),majFood.getSum_carb());


        //uzimaju se liste potrbne za RV
        stringArrayList.addAll(majFood.getFoodNames()) ;
        integerArrayList.addAll(majFood.getMass()) ;
        String itemNumber= String.valueOf(0);
        itemNumber= String.valueOf(stringArrayList.size());
        tvinfoItemNumber.setText("Meal"+"\n"+"items("+itemNumber+")");

    }

    //metoda kojoj saljemo vrijeme nastanka nekog obroka jer po tome svaki obrok autentican saljemo kako bi smo mogli
    // odraditi query u bazu i zatim dobiti zeljene podatke za taj obrok i prikazati ih u activity-u
  public Meal_Blueprint getAllFromDB(String dateTime) {
        Cursor cursor;
       ArrayList<String> foodNamesArray = new ArrayList<>();
       ArrayList<Integer> foodMassArray = new ArrayList<>();
       ArrayList<String> foodMassArrayS = new ArrayList<>();
        Meal_Blueprint mm=null;


       String[] result;
       String[] result2;

       cursor=sqLiteDatabase.query(DB_Meal.MealTable.TABLE_NAME, null, DB_Meal.MealTable.COLUMN_TIME+" = '"+dateTime+"'", null, null, null, DB_Meal.MealTable._ID);

       while (cursor.moveToNext()) {
           String mealName = cursor.getString(cursor.getColumnIndexOrThrow(DB_Meal.MealTable.COLUMN_NAME));
           String foodNames = cursor.getString(cursor.getColumnIndexOrThrow(DB_Meal.MealTable.COLUMN_FOOD_NAMES));
           String foodMasses = cursor.getString(cursor.getColumnIndexOrThrow(DB_Meal.MealTable.COLUMN_MASSES));
        //   String timeOfCreation = cursor.getString(cursor.getColumnIndexOrThrow(DB_Meal.MealTable.COLUMN_TIME));
           int cal = cursor.getInt(cursor.getColumnIndexOrThrow(DB_Meal.MealTable.COLUMN_SUM_CALORIES));
           float fat = cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Meal.MealTable.COLUMN_SUM_FAT));
           float pro = cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Meal.MealTable.COLUMN_SUM_PROTEIN));
           float carb = cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Meal.MealTable.COLUMN_SUM_CARB));
           long id = cursor.getLong(cursor.getColumnIndexOrThrow(DB_Meal.MealTable._ID));

           result = foodNames.split(",");
           foodNamesArray.addAll(Arrays.asList(result));


           result2 = foodMasses.split(",");
            foodMassArrayS.addAll(Arrays.asList(result2));

           foodMassArray.addAll(stringToInteger(foodMassArrayS));
           mm=new Meal_Blueprint(mealName,foodMassArray,foodNamesArray,dateTime,cal,fat,pro,carb);

       }
     return mm;
   }


   ArrayList<Integer> stringToInteger(ArrayList<String> ulaz){
        ArrayList<Integer> pom=new ArrayList<>();

     for(int i=0;i<ulaz.size();i++){
         pom.add(Integer.valueOf(ulaz.get(i)));
       }

       return  pom;
   }


   //medota koja postavi vrijednosti svih TextView-a
   public void setValues(String mealName,String[] time,int cal,float fat,float pro,float carb) {

       String timee = "";
       timee = time[1];
       String datee = "";
       datee = time[0];

       tvMealName.setText(mealName);

       if (mealName.length() > 10) {
           tvMealName.setTextSize(28);
       }

       if(mealName.contains(" ")&& tvMealName.length()>14){
           int praznina=mealName.indexOf(" ");
           String a,b="";
           a=mealName.substring(0,praznina);
           b=mealName.substring(praznina+1,mealName.length());

           String ab=a+"\n"+b;
           tvMealName.setText(ab);

       }

       if (mealName.length() > 14 && !mealName.contains(" ")) {
           tvMealName.setTextSize(24);
           tvMealName.setPadding(0,0,0,6);
       }

       if (mealName.length() > 17 && !mealName.contains(" ")) {
           tvMealName.setTextSize(20);
           tvMealName.setPadding(0,0,0,14);
       }

        tvTime.setText(timee);
        tvDate.setText(datee);
        tvCal.setText(String.valueOf(cal));
        tvFat.setText(String.valueOf(fat));
        tvPro.setText(String.valueOf(pro));
        tvCarb.setText(String.valueOf(carb));
   }

   //medota koja dobije dateTime i dijeli ga na dio za vrijem i dio za datum
   public String[] vrijemeDatum(String dateTime){
        String[] result;
        result=dateTime.split(" ",2);

        return result;
   }

   private void init(){
       tvCal=findViewById(R.id.tvCalMealValue);
       tvFat=findViewById(R.id.tvFatMealValue);
       tvPro=findViewById(R.id.tvProMealValue);
       tvCarb=findViewById(R.id.tvCarbMealValueInfo);
       tvTime=findViewById(R.id.tvTimeInfo);
       tvDate=findViewById(R.id.tvDateInfo);
       tvMealName=findViewById(R.id.tvMealNameInfo);
       tvinfoItemNumber=findViewById(R.id.tvMealContains);

       stringArrayList=new ArrayList<>();
       integerArrayList=new ArrayList<>();
   }


    private void initAdapter_RV(){
        recyclerView=findViewById(R.id.rvMealContains);
        adapterInfo=new AdapterInfo(InfoMeal.this, stringArrayList,integerArrayList);
        linearLayoutManager=new LinearLayoutManager(InfoMeal.this);
        linearLayoutManager.setOrientation(recyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterInfo);
    }

}