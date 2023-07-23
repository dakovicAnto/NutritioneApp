package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.food.DB_Blueprint;
import com.example.myapplication.food.DB_helper;
import com.example.myapplication.food.FoodLibrary;
import com.example.myapplication.meal.DB_Meal;
import com.example.myapplication.meal.DB_helper_Meal;
import com.example.myapplication.meal.Meal_Library;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //----------  Layouts & Other   ------------
    Resources res;

    ConstraintLayout constraintCaca;
    Drawable constraintCacaBackground;

    GradientDrawable gradientDrawable;
    Spinner_Adapter spinner_adapter;
    Spinner mySpinner;

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    //----------  Global Variables   ------------
    float globalCalorie;
    float globalFat;
    float globalProtein;
    float globalCarb;
    String selectedFood;
    float selectedValue;
    ArrayList<String> imenaHrane;

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx





   //----------   Food DB clases   ------------
    SQLiteDatabase sqLiteDatabase;
    DB_helper db_helper;

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx







   //----------   TextViews & EditTexts  & Buttons ------------
   Button btnFoodFridge;
   View foodFridgeLayout;
   Button btnAddItemToMeal;
   View btnAddMealTo_DB;
   View mealLayout;
   TextView textAmount;
   TextView calVal;
   TextView fatVal;
   TextView proVal;
   TextView carbVal;
   TextInputEditText textInputEditText;

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx




    //----------   Variables for meal creation ------------

    String mealName;
    ArrayList<String> foodNamesArray;
    String foodNames;
    String mass;
    ArrayList<Integer> massArray;
    LocalTime timeOfMealCreation;
    int sumCalorie;
    float sumFat;
    float sumProtein;
    float sumCarb;
    String[][] matrix; int matrixCounter;

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx


    //----------   Meal DB clases   ------------
    SQLiteDatabase databaseMeal;
    DB_helper_Meal db_helper_meal;

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx



    //----------   RV for meal_insert_dialog   ------------
    RecyclerView recyclerViewCreateMeal;
    CreateMeal_Adapter createMeal_adapter;
    LinearLayoutManager linearLayoutManager;

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx



    //----------   COLORS   ------------

    int colorTamnoPlava;
    int colorPlava;
    int colorZelena;
    int colorCrvena;

    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx





    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFirstStuff();
        initMEALInputStuff();
        int counta=30;
        initColors(res);




        db_helper=new DB_helper(MainActivity.this);
        sqLiteDatabase=db_helper.getReadableDatabase();


        imenaHrane=new ArrayList<String>();
        imenaHrane=getAllItemNames();
        spinner_adapter=new Spinner_Adapter(getApplicationContext(),imenaHrane);
        mySpinner.setAdapter(spinner_adapter);



        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s="";
                s= (String) adapterView.getItemAtPosition(i);
                selectedFood=s;
                setTextValues(0,0,0,0,0);


                //  Toast.makeText(MainActivity.this,pomoc,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textAmount.setText(charSequence+" grams");
                if(charSequence.length()>0) {
                    float f = Float.parseFloat(charSequence.toString());
                    selectedValue = f; //koji god brojcani text unsemo on postaje masa s kojim ce se racunati nutricionisticke vrijednosti
                }
                else{
                    selectedValue=0;  //ako je unos za masu hrane ispraznjen onda se smatra da je masa grama  (textInputEditText)
                }
                float cl=globalCalorie;float ft=globalFat;float pr=globalProtein;float cb=globalCarb;


                cl=cl*selectedValue/100;
                ft=ft*selectedValue/100;
                pr=pr*selectedValue/100;
                cb=cb*selectedValue/100;

                setTextValues(1,cl,ft,pr ,cb );




            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btnAddItemToMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (foodNamesArray.size() < counta) {

                    Float[] values = getTextValues();
                    int intCal=Math.round(values[0]);

                    foodNamesArray.add(selectedFood);
                    massArray.add((int) selectedValue);
                    int pomInt= (int) selectedValue;
                    String pom = String.valueOf(pomInt);
                    matrix[matrixCounter][0]=selectedFood;
                    matrix[matrixCounter][1]=pom;
                    matrix[matrixCounter][2]=String.valueOf(intCal);
                    matrix[matrixCounter][3]=String.valueOf(values[1]);
                    matrix[matrixCounter][4]=String.valueOf(values[2]);
                    matrix[matrixCounter][5]=String.valueOf(values[3]);
                    matrixCounter=matrixCounter+1;




                }




                ValueAnimator colorAnimationFront = ValueAnimator.ofObject(new ArgbEvaluator(),colorPlava,colorZelena);
                colorAnimationFront.setDuration(500); // milliseconds
                colorAnimationFront.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                        int animatedValue= (int) valueAnimator.getAnimatedValue();
                       gradientDrawable.setStroke(converter_dpToint(5,res),animatedValue);
                    }
                });

                colorAnimationFront.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {
                        ValueAnimator colorAnimationBack = ValueAnimator.ofObject(new ArgbEvaluator(),colorZelena,colorPlava);
                        colorAnimationBack.setDuration(500);
                        colorAnimationBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                                int animatedValue= (int) valueAnimator.getAnimatedValue();
                                gradientDrawable.setStroke(converter_dpToint(5,res),animatedValue);
                            }
                        });
                        colorAnimationBack.start();
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {

                    }
                });
                colorAnimationFront.start();



            }
        });

        btnAddMealTo_DB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogInsert();
            }
        });

        foodFridgeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, FoodLibrary.class);
                startActivity(i);

            }
        });

        mealLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, Meal_Library.class);
                startActivity(i);
            }
        });


    }


    private ArrayList<String> getSpecificItems(String name){
         /*  Kada na spinneru sa imenima nutritienata odaberemo neku vrijednost
             Ona se posalje ovoj funkciji kao (String name) i onda se pozove fija
             getSpecificData(name) koja odradi query u bazu sa ovim nazivom.
             Taj query vraca zapis iz baze s tim imenom - vraca ga u formatu din.liste
               */


        ArrayList<String>result=new ArrayList<>();
        float[] my;
        my=db_helper.getSpecificData(name);

        globalCalorie=my[0];
        globalFat=my[1];
        globalProtein=my[2];
        globalCarb=my[3];
                                        /*vrijednosti dobivene iz baze se mnoze s zadanom
                                        masom u edittext mass
                                         */
        my[0]=my[0]*selectedValue/100;   //dijele se sa 100 jer je default masa 100g.
        my[1]=my[1]*selectedValue/100;
        my[2]=my[2]*selectedValue/100;
        my[3]=my[3]*selectedValue/100;

        my[0]= (float) (Math.round(my[0]*10.0)/10.0); //ovdje se zaokruzuju na jednu decimalu
        my[1]= (float) (Math.round(my[1]*10.0)/10.0);
        my[2]= (float) (Math.round(my[2]*10.0)/10.0);
        my[3]= (float) (Math.round(my[3]*10.0)/10.0);




        String calString=String.valueOf(my[0]);
        String fatString=String.valueOf(my[1]);
        String proString=String.valueOf(my[2]);
        String carbString=String.valueOf(my[3]);



        result.add(calString);result.add(fatString);result.add(proString);result.add(carbString);
        return result;
    }

    private ArrayList<String> getAllItemNames(){
         /*  Ova funkcija ukoliko je promjenjen sadrzaj podataka iz baze nutritienata
             ponovno ucitava nazive tih nutritienta kako bi osigurala izvor podataka
             za spinner koji sadrzava sve te nutritiente(hrane)

               */

        ArrayList<String> my;
        my=new ArrayList<>();
        String[] column={DB_Blueprint.FoodTable.COLUMN_NAME};
        Cursor c=sqLiteDatabase.query(DB_Blueprint.FoodTable.TABLE_NAME,column,null,null,null,null,DB_Blueprint.FoodTable._ID);
        while (c.moveToNext()) {
            String ime = c.getString(c.getColumnIndexOrThrow(DB_Blueprint.FoodTable.COLUMN_NAME));
            my.add(ime);
        }
        return my;
    }
    void setTextValues(int isFoodOrMassChanged,float c,float f,float p,float cb){
         /*  Ova fija mijenja ili postavlja nutrit.vrijednosti koje su prikazane(cal,fat,pro,carb)
             Ukoliko je  if(isFoodOrMassChanged==0)
             onda znaci da je applikacija tek otvorena i da se postave
             i prikazu vrijednosti koje su pocetne (vrijednosti uz 100grama).
              Ukoliko je  if(isFoodOrMassChanged==1)
              onda znaci da smo rucno unosili masu i da tada racuna s tom masom
               */

        if(isFoodOrMassChanged==0){}
        calVal.setText(getSpecificItems(selectedFood).get(0));
        fatVal.setText(getSpecificItems(selectedFood).get(1));
        proVal.setText(getSpecificItems(selectedFood).get(2));
        carbVal.setText(getSpecificItems(selectedFood).get(3));
        if(isFoodOrMassChanged==1){
            calVal.setText( String.valueOf(Math.round(c*10.0)/10.0));
            fatVal.setText(String.valueOf(Math.round(f*10.0)/10.0));
            proVal.setText(String.valueOf(Math.round(p*10.0)/10.0));
            carbVal.setText(String.valueOf(Math.round(cb*10.0)/10.0));

            //Math.round(my[0]*10.0)/10.0
        }
    }

    Float[] getTextValues(){
     /*  Ova fija uzima textualne vrijdnosti kalorija,masti i proteina iz textviewa koji
         prikazivaju istoimene vrijednosti .
         Također ova fija te vrijednosti pretvara u float vrijednost kako bi se
         moglo ite vrijednosti koristiti za racunanje .
                   */
        Float[] my=new Float[4];
        Float cal=Float.parseFloat(calVal.getText().toString());
        Float fat=Float.parseFloat(fatVal.getText().toString());
        Float pro=Float.parseFloat(proVal.getText().toString());
        Float carb=Float.parseFloat(carbVal.getText().toString());

        my[0]=cal;
        my[1]=fat;
        my[2]=pro;
        my[3]=carb;

        return  my;

    }




    @Override
    protected void onResume() {
        super.onResume();
         /*  Ova funkcija ukoliko je promjenjen sadrzaj podataka iz baze nutritienata
             ponovno ucitava nazive tih nutritienta kako bi osigurala izvor podataka
             za spinner koji sadrzava sve te nutritiente(hrane), ponovno ucitavanje podataka iz
            baze omogucuje fija  getAllItemNames().
               */

                imenaHrane=getAllItemNames();
               spinner_adapter.clear();
               spinner_adapter.addAll(imenaHrane);
               mySpinner.setAdapter(spinner_adapter);

    }

    public  void createDialogInsert()
    {              /*  Ova funkcija kreira dialog koji se otvara nakon klika na button za zakljucivanje obroka,
                    u ovoj funkciji se korisniku izlista(kroz recyclerView)koje je nutritiente sve unio
                     i u kojoj masi. Korisnik moze sve unjeti kliokom na INSERT button ili moze sve obrisati.
                     Također korisnik moze obrisati bilo koji nutritien ako zeli tako sto napravi slide u lijevo
               */

        AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater =MainActivity.this.getLayoutInflater();
        View v= layoutInflater.inflate(R.layout.dialog_meal_manager,null);
        TextView itemCount=v.findViewById(R.id.tvItemNumber);
        TextView calorie=v.findViewById(R.id.tvItemNumber);
        recyclerViewCreateMeal=v.findViewById(R.id.recyclerCreateMeal);

        createMeal_adapter=new CreateMeal_Adapter(MainActivity.this,foodNamesArray,massArray);   // foodNamesArray,massArray sadrze imena i mase odabranih nutritienta za unos
        linearLayoutManager=new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(recyclerViewCreateMeal.VERTICAL);
        recyclerViewCreateMeal.setLayoutManager(linearLayoutManager);
        recyclerViewCreateMeal.setAdapter(createMeal_adapter);

        final String[] counter = {String.valueOf(foodNamesArray.size())}; // counter se koristi kako bi korisnik mogao pratiti broj trenutno odabranih nutritienta
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //ovdje se događa brisanje

               int r= (int) viewHolder.itemView.getTag(); //dobijemo indeks nutritienta kojeg brisemo

               foodNamesArray.remove(r);
               massArray.remove(r);
               String[][] pomMatrix=new String[30][6];
                int brojac=0;
                for(int i=0;i<matrixCounter;i++){    //ovdje brisemo red iz matrice s tim indeksom

                    for(int j=0;j<6;j++){
                        if(i!=r){
                            pomMatrix[brojac][j]=matrix[brojac][j];
                                if(j==5){brojac+=1;}
                        }
                        else {continue;}


                    }

                }
                 matrix=pomMatrix;
                 matrixCounter=matrixCounter-1; //matrixCounter manji za jedan jer smo obrisali jedan red

                createMeal_adapter.notifyDataSetChanged();
                counter[0] =String.valueOf(foodNamesArray.size());
                itemCount.setText(counter[0]);
            }
        }).attachToRecyclerView(recyclerViewCreateMeal);

        itemCount.setText(counter[0]);
        builder.setView(v);


        String insertString=res.getString(R.string.insert_meal);
       builder.setPositiveButton(insertString,null);

        /*klikom na "Close" button zatvara se dialog za unos obroka ali sve odabrane hrane za obrok
          ostaju  spremljene i opet ce se pojaviti ponovnim otvaranjem ovog dialoga
        */
        String closeString=res.getString(R.string.close_meal);
        builder.setPositiveButton(insertString,null);
        builder.setNegativeButton(closeString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        /*klikom na "DISCARD MEAL" button zatvara se dialog za unos obroka i prazne se sve varijable
          bivaju ispraznjene kako bi sve moglo krenuti ispocetka
        */
        String discardtString=res.getString(R.string.discard_meal);

        builder.setNeutralButton(discardtString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mealName="";
                foodNames="";
                mass="";
                foodNamesArray.clear();
                massArray.clear();
                sumCalorie=0;
                sumFat=0;
                sumProtein=0;
                sumCarb=0;


                matrixCounter=0;
                matrix=new String[6][30];



            }
        });

        AlertDialog alert=builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiv=alert.getButton(DialogInterface.BUTTON_POSITIVE);
                positiv.setTextColor(colorTamnoPlava);
                positiv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (foodNamesArray.isEmpty()) {   //ovdje se provjerava dali je unesena ijedan nutritient,ako nije obavijesti korisnika

                            Toast.makeText(MainActivity.this, "No Items Added to Meal", Toast.LENGTH_SHORT).show();

                        }
                        else {  createCheckInsertDialog();
                                alert.dismiss();
                        }
                    }
                });


            }
        });

        alert.show();
        alert.getWindow().setLayout(1000, 1340);
        Button positive=alert.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negativ=alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button discard=alert.getButton(DialogInterface.BUTTON_NEUTRAL);

        //  String s=positive.getText().toString();
       // Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();

      //  Drawable drawable = res.getDrawable(R.drawable.alert_create_meal_buttons);
       // positive.setBackground(drawable);
      //  negativ.setBackground(drawable);



        negativ.setTextColor(colorTamnoPlava);
        discard.setTextColor(colorCrvena);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) positive.getLayoutParams();
        params.topMargin = 0;//Enter your desired margin value here.
        params.bottomMargin =0 ;
        params.leftMargin = 0;
        params.rightMargin = 0;

        positive.setLayoutParams(params);

    }


    public void createCheckInsertDialog(){
        /*  Ova funkcija kreira dialog koji se otvara nakon dialoga za unos obroka,
            u ovoj funkciji se od korisnika trazi unos imena za obrok ,taj unos je obvezan.
            Ukoliko je naziv obroka pravilno unesen pritiskom na INSERT button poziva se fija
            zaduzena za unos podataka u bazu
   */

        AlertDialog.Builder bilderInsert=new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater =MainActivity.this.getLayoutInflater();
        View v1= layoutInflater.inflate(R.layout.insert_noinsert,null);
        EditText editTextMealName=v1.findViewById(R.id.edMealName);
        InputFilter[] FilterArray = new InputFilter[1];

        int maxLength=20;   //definira maksimalan broj znakova za unos naziva obroka
        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
        editTextMealName.setFilters(FilterArray);   /* ogranicava se max broj znakova za ovaj edittext
                                                       to se dogada jer default naziv obroka(sadrzan u tom editTextMealName)
                                                       je MEAL_1 i pri clicku na taj edittext  zelimo da se ukloni sav text kako bi mogli
                                                       unjeti naziv po nasoj zelji */

        editTextMealName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                editTextMealName.setText("");
            }
        });
        bilderInsert.setView(v1);
        bilderInsert.setTitle("Add new meal");
        bilderInsert.setMessage("Are you sure?");



            //klikom na "Cancel button zatvara se dialog za unos obroka ali sve odabrane hrane za obrok
                //                                                  ostaju spremljene i spremne za unos

        bilderInsert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });



        bilderInsert.setPositiveButton("INSERT",null);
        AlertDialog insertDialog=bilderInsert.create();



        //  positive.setTextColor(colorTamnoPlava);
        insertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiv=insertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiv.setTextColor(colorTamnoPlava);
                positiv.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {


                        db_helper_meal=new DB_helper_Meal(MainActivity.this);
                        databaseMeal=db_helper_meal.getWritableDatabase();


                        //provjerava se jel naziv unesen
                        if(editTextMealName.getText().toString().trim().length()==0){
                            Toast.makeText(MainActivity.this, "Meal name not inserted", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            //dobijamo spremne podatke spremne za unos u bazu podataka

                            mealName=editTextMealName.getText().toString();    //dobijamo naziv obroka
                            for(int i=0;i<matrixCounter;i++){

                                String foodName=matrix[i][0];       /*svi podaci osim naziva obroka i vremena stvaranja
                                                                      nalaze se u matrici(matrix) iz koje se for petljim
                                                                      izvace iz iste  */

                                foodNames=foodNames+foodName+",";

                                String foodMass=matrix[i][1];
                                mass=mass+foodMass+",";

                                String cal,fat,pro,carb;

                                cal=matrix[i][2];
                                fat=matrix[i][3];
                                pro=matrix[i][4];
                                carb=matrix[i][5];

                                sumCalorie=sumCalorie+Integer.parseInt(cal);

                                sumFat=sumFat+Float.parseFloat(fat);

                                sumProtein=sumProtein+Float.parseFloat(pro);
                                sumCarb=sumCarb+Float.parseFloat(carb);


                            }

                            insert(mealName,foodNames,mass, sumCalorie,  sumFat,  sumProtein, sumCarb);

                                    //sve varijable koristene za privremeno cuvanje podataka se nakon unosa u bazu
                                    // isprazne kako bi se proces mogao iznova dogoditi

                            mealName="";
                            foodNames="";
                            mass="";
                            foodNamesArray.clear();
                            massArray.clear();
                            sumCalorie=0;
                            sumFat=0;
                            sumProtein=0;
                            sumCarb=0;

                            matrixCounter=0;
                            matrix=new String[6][30];

                            insertDialog.dismiss();

                        }


                    }
                });

            }
        });
        insertDialog.show();
        Button negativ=insertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativ.setTextColor(colorCrvena);
    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insert(String mealName, String foodNames, String mass,int cal, float fat, float pro, float caarb){
                /*  Ova funkcija radi unos podataka u bazu Meals .
                Prima agrumente potrebne za stvaranje novog obroka ,
                te u trenutku pozivanja ove funkcije kreira se vrijeme nastanka tog obroka

         */

        ContentValues contentValues=new ContentValues();
        String time=getTimeAndFormattTime();

        contentValues.put(DB_Meal.MealTable.COLUMN_NAME,mealName);
        contentValues.put(DB_Meal.MealTable.COLUMN_FOOD_NAMES,foodNames);
        contentValues.put(DB_Meal.MealTable.COLUMN_MASSES,mass);
        contentValues.put(DB_Meal.MealTable.COLUMN_TIME,time);
        contentValues.put(DB_Meal.MealTable.COLUMN_SUM_CALORIES,cal);
        contentValues.put(DB_Meal.MealTable.COLUMN_SUM_FAT,fat);
        contentValues.put(DB_Meal.MealTable.COLUMN_SUM_PROTEIN,pro);
        contentValues.put(DB_Meal.MealTable.COLUMN_SUM_CARB,caarb);


        databaseMeal.insert(DB_Meal.MealTable.TABLE_NAME,null,contentValues);

    }



         @RequiresApi(api = Build.VERSION_CODES.O)
         String getTimeAndFormattTime() {
            /*  Ova funkcija omogucava dobivanje/stvaranje trenutnog datuma ,te ga
                formatira u zeljeni format i vraca u String formatu.
              */

            LocalDateTime localDateTime=LocalDateTime.now();
             DateTimeFormatter formatter = null;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    }
                     else {return null;}
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            return formatter.format(localDateTime);
                        }
                        else {return null;}

    }


    public int converter_dpToint(int dp,Resources r){
         /*  Ova funkcija omogucava unos velicine u dp vrijednosti.
         Tako sto prima argument u integer vrijednosti ,te ga pretvara u dp vrijednost

                        1.neki float broj koji zelimo zaokruziti na odredeni broj decimala
                        2.broj decimala koji zelimo   */

        r = getResources();
        int dpValue = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,r.getDisplayMetrics()));

        return dpValue;
    }


    public float zaokruzivanjeFloata(float f,int brojDecimala){

            /*  Ova funkcija prima  dva argumenta:
                        1.neki float broj koji zelimo zaokruziti na odredeni broj decimala
                        2.broj decimala koji zelimo   */




        String s="1";
        for(int i=0;i<brojDecimala;i++){
            s=s+"0";
        }

        int djeljitelj= Integer.parseInt(s);
        float input=f; input=input*djeljitelj;
        int i= (int) input;


        float output=i/djeljitelj;
        return  output;
    }


    private void initMEALInputStuff(){
        matrixCounter=0;
        btnAddItemToMeal=findViewById(R.id.btnAddItemToMeal);
        btnAddMealTo_DB=findViewById(R.id.btnAddMealTo_DB);

        constraintCaca=findViewById(R.id.constraintCaca);
        constraintCacaBackground=constraintCaca.getBackground();
        gradientDrawable= (GradientDrawable) constraintCacaBackground;

        mealName="";
        foodNames="";
        mass="";
        foodNamesArray=new ArrayList<>();
        massArray=new ArrayList<>();
        timeOfMealCreation=null;
        sumCalorie=0;
        sumFat=0;
        sumProtein=0;
        sumCarb=0;
        matrix=new String[30][6];
    }


    private void initFirstStuff(){
        foodFridgeLayout=findViewById(R.id.viewButton);
        mealLayout=findViewById(R.id.viewMyMeals);
        mySpinner=findViewById(R.id.spinnerMeal);
        textAmount=findViewById(R.id.tvGramAmount);
        textInputEditText=findViewById(R.id.textInputEt);
        calVal=findViewById(R.id.textViewCalVal);
        fatVal=findViewById(R.id.textViewFatVal);
        proVal=findViewById(R.id.textViewProVal);
        carbVal=findViewById(R.id.textViewCarbVal);
        selectedValue=100;
        res = getResources();
    }

     private void initColors(Resources res){
         colorPlava = res.getColor(R.color.MojaPlava);
         colorZelena = res.getColor(R.color.MojaZelana);
         colorTamnoPlava=res.getColor(R.color.MojaTamnoPlava);
         colorCrvena=res.getColor(R.color.MojaCrvena);
     }



}