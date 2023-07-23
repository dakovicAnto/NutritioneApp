package com.example.myapplication.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class FoodLibrary extends AppCompatActivity {
    //-------------  Strings loaded at RUNTIME  --------------------
    String InserNewFood;
    String Cancel;
    String Insert;
    String Delete;
    String areYousSure;
    String deleteText;


//-------------  View Button  --------------------
    Button floatingActionButton;
    Button btnaddFood;

//---------------------------------------------------------


//-------------  RV Adapter Database classes  --------------------

    FoodAdapter foodAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    SQLiteDatabase sqLiteDatabase;
    DB_helper db_helper;

//---------------------------------------------------------


    //-------------  Global Variables  --------------------
    String globalName;
    int globalCalorie;
    float globalFat;
    float globalProtein;
    float globalCarb;

//---------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_library2);
        Resources res=getResources();
        init();
        setUpRecyclerView_And_DB();
        getStrings(res);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodLibrary.this.finish();
            }
        });

        btnaddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogInsert();
            }
        });


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


    }

    public  void createDialogInsert()
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(FoodLibrary.this);
        LayoutInflater layoutInflater =FoodLibrary.this.getLayoutInflater();
        View v= layoutInflater.inflate(R.layout.insert_alerdialog,null);

        EditText name=v.findViewById(R.id.edName);
        EditText calorie=v.findViewById(R.id.edCal);
        EditText fat=v.findViewById(R.id.edFat);
        EditText protein=v.findViewById(R.id.edPro);
        EditText carb=v.findViewById(R.id.edCarb);
        builder.setView(v).setTitle(InserNewFood);
        builder.setPositiveButton(Insert, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //ovdje se provjerava jesu li polja za unos vrijednosti novog nutritienta pravilno popunjena

                        if (name.getText().toString().trim().length() == 0 || calorie.getText().toString().trim().length() == 0 ||
                                fat.getText().toString().trim().length() == 0 || protein.getText().toString().trim().length() == 0 ||
                                carb.getText().toString().trim().length() == 0) {


                            Toast.makeText(FoodLibrary.this, "You must enter all attributes ", Toast.LENGTH_LONG).show();

                        } else {
                            String ime = name.getText().toString();
                            int cal = Integer.parseInt(calorie.getText().toString());
                            float faat = Float.parseFloat(fat.getText().toString());
                            float pro = Float.parseFloat(protein.getText().toString());
                            float caarb = Float.parseFloat(carb.getText().toString());
                            globalName=ime;
                            globalCalorie=cal;
                            globalFat=faat;
                            globalProtein=pro;
                            globalCarb=caarb;
                            insert(globalName,globalCalorie,globalFat,globalProtein,globalCarb);

                        }
                    }

                });

        builder.setNegativeButton(Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }



    /*Ova funkcija obavlja dodavanje novog nutritienta u bayu podataka*/
    private void insert(String ime,int cal,float faat,float pro,float caarb){
        ContentValues contentValues=new ContentValues();


        contentValues.put(DB_Blueprint.FoodTable.COLUMN_NAME,ime);
        contentValues.put(DB_Blueprint.FoodTable.COLUMN_CALORIES,cal);
        contentValues.put(DB_Blueprint.FoodTable.COLUMN_FAT,faat);
        contentValues.put(DB_Blueprint.FoodTable.COLUMN_PROTEIN,pro);
        contentValues.put(DB_Blueprint.FoodTable.COLUMN_CARB,caarb);

        sqLiteDatabase.insert(DB_Blueprint.FoodTable.TABLE_NAME,null,contentValues);
        foodAdapter.swapCursor(getAllFromDB()); // kada se doda novi nutritient u bazu mijenja se njezin sadrzaj
                                                // stoga moramo azurirati recyclerView tako što cemo njezinom adapteru poslati nove podatke

    }
    /*Ova funkcija obavlja brisanje odabranog nutritienta iz baze*/
    private void removeItem(long id){
        AlertDialog.Builder builder=new AlertDialog.Builder(FoodLibrary.this);
        builder.setCancelable(false);
        builder.setPositiveButton(Delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                sqLiteDatabase.delete(DB_Blueprint.FoodTable.TABLE_NAME,DB_Blueprint.FoodTable._ID + "=" + id,null);
                foodAdapter.swapCursor(getAllFromDB());

            }
        });

        builder.setNegativeButton(Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent()); //restartira ovaj Activity
                overridePendingTransition(0, 0);

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.setTitle(deleteText);
        alertDialog.setMessage(areYousSure);
        alertDialog.show();


    }

   Cursor getAllFromDB() {
        Cursor cursor;
        cursor=sqLiteDatabase.query(DB_Blueprint.FoodTable.TABLE_NAME, null, null, null, null, null, DB_Blueprint.FoodTable._ID);
         return cursor;
   }


   private  void init(){
       globalName="";
       globalCalorie=0;
       globalFat= 0.0F;
       globalProtein=0.0F;
       globalCarb=0.0F;
       floatingActionButton=findViewById(R.id.buttonClose);
       btnaddFood=findViewById(R.id.btnAddFood);

   }

    private  void setUpRecyclerView_And_DB(){
        db_helper= new DB_helper(FoodLibrary.this);
        sqLiteDatabase=db_helper.getWritableDatabase();
        recyclerView=findViewById(R.id.myRVFood);
        foodAdapter=new FoodAdapter(FoodLibrary.this,getAllFromDB());
        linearLayoutManager=new LinearLayoutManager(FoodLibrary.this);
        linearLayoutManager.setOrientation(recyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(foodAdapter);

    }

    private void getStrings(Resources res){
       InserNewFood=res.getString(R.string.insert_new_food);
       Cancel=res.getString(R.string.close_food);
       Insert=res.getString(R.string.insert_food);
       Delete=res.getString(R.string.delete);
        areYousSure=res.getString(R.string.are_you_sure);
        deleteText=res.getString(R.string.delteText);
    }

}