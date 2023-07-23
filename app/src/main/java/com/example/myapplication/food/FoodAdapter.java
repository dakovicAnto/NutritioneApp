package com.example.myapplication.food;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class FoodAdapter extends RecyclerView.Adapter<VH_Food> {

    /*Klasa FoodAdapter koja populizira RecyclerView u koji izlistava sve nutritiente (klasa FoodLibrary)
    * preko konstruktora prima podatke u cursoru(query iz baze)te ih spaja sa ciljanim Viewima */


    Context context;
    Cursor cursor;

    public FoodAdapter(Context context, Cursor cursor) {
        this.context=context;
        this.cursor=cursor;
    }

    @NonNull
    @Override
    public VH_Food onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View row=layoutInflater.inflate(R.layout.rv_food_singlerow,parent,false);
        VH_Food viewHolderFood=new VH_Food(row);
        return viewHolderFood;
    }

    @Override
    public void onBindViewHolder(@NonNull VH_Food holder, int position) {
        if(!cursor.moveToPosition(position)){
            return;
        }
        String name=cursor.getString(cursor.getColumnIndexOrThrow(DB_Blueprint.FoodTable.COLUMN_NAME));
        int  cal=cursor.getInt(cursor.getColumnIndexOrThrow(DB_Blueprint.FoodTable.COLUMN_CALORIES));
        float  fat=cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Blueprint.FoodTable.COLUMN_FAT));
        float  pro=cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Blueprint.FoodTable.COLUMN_PROTEIN));
        float  carb=cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Blueprint.FoodTable.COLUMN_CARB));
        long id=cursor.getLong(cursor.getColumnIndexOrThrow(DB_Blueprint.FoodTable._ID));

            //provjerava se velicna/duzina podatak jer bi mogli preci granice TextViewa u kojem se trebaju pojaviti
        //    zbog toga po potrebi smanjujemo znakove i razdvajamo recenice u dva reda ukoliko se uvjeti posloze
        holder.name.setText(name);

        if(name.contains(" ")&& name.length()>=8){
            holder.name.setTextSize(20);

            int praznina=name.indexOf(" ");
            String a,b="";
            a=name.substring(0,praznina);
            b=name.substring(praznina,name.length());
            holder.name.setLines(2);
            String ab=a+"\n"+b;
           // holder.name.setTextAlignment();

            holder.name.setText(ab);
        }


        if(!name.contains(" ")){
            if(name.length()>=7){
                holder.name.setTextSize(26);
            }
            if(name.length()>=11){
                holder.name.setTextSize(17);
            }
            if(name.length()>=17){
                holder.name.setTextSize(14);
            }
        }

        holder.name.setAllCaps(true);

        fat= (float) (Math.round(fat*10.0)/10.0);
        pro= (float) (Math.round(pro*10.0)/10.0);
        carb= (float) (Math.round(carb*10.0)/10.0);


        holder.cal.setText(String.valueOf(cal));
        holder.fat.setText(String.valueOf(fat));
        holder.pro.setText(String.valueOf(pro));
        holder.carb.setText(String.valueOf(carb));
        holder.itemView.setTag(id);


    }



    @Override
    public int getItemCount() {
       return cursor.getCount();
    }

    // ukoliko se podaci promjene zamjeni stari sa novim cursorom
    public  void swapCursor(Cursor newCursor){
        if(cursor!=null){
            cursor.close();
        }
        cursor=newCursor;

        if(newCursor!=null){
            notifyDataSetChanged();
        }
    }
}
