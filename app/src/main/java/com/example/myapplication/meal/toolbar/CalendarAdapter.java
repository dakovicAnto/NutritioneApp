package com.example.myapplication.meal.toolbar;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<VH_Calendar>{

    /*Klasa CreateMeal_Adapter koja populizira Kalendar(Grid_RecyclerView)
    * preko konstruktora prima:
    * daysOfMonth - dani za mjesec
    * onItemListener - listener za klikanje na iteme gridRV
    * datesWithMeals - datumi s obrocima kako bi calendar znao za koji dan ce staviti iconu koja oznacava da taj dan sadrzi obroke
    * selectedMonth,selectedYear -kako bi calendar mogao usporedivati datume mora moci generirati puni datum koji se
    generira od godine ,mjeseca i dana a posto kalendar moze sam od sebe samo ponuditi broj dana moramo u konstruktor slati odabranu
    godinu i mjesec
    * selectedDatesThis -to su datumi koji su odabrani od strane korisnika (koji on zeli)  */

    Context context;
    private final ArrayList<String> daysOfMonth;
    private final  OnItemListner onItemListner;
    ArrayList<String> datesWithMeals;
    ArrayList<String> selectedDatesThis;


    String selectedMonth;
    String selectedYear;

    public CalendarAdapter(Context c,ArrayList<String> daysOfMonth, OnItemListner onItemListner,ArrayList<String> datesWithMeals
            ,String selectedMonth,String selectedYear,ArrayList<String> selectedDatesThis) {
        this.context=c;
        this.daysOfMonth = daysOfMonth;
        this.onItemListner = onItemListner;
        this.datesWithMeals=datesWithMeals;
        this.selectedMonth=selectedMonth;
        this.selectedYear=selectedYear;
        this.selectedDatesThis=selectedDatesThis;

    }

    @NonNull
    @Override
    public VH_Calendar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View row=layoutInflater.inflate(R.layout.cell_calendar,parent,false);
        ViewGroup.LayoutParams layoutParams=row.getLayoutParams();
        layoutParams.height= (int) (parent.getHeight()*0.333333);   //zadaje se visina jednog itema RecyclerViewa na 33% parenta
        VH_Calendar vh_info=new VH_Calendar(row, onItemListner);
        return  vh_info;
    }

    @Override
    public void onBindViewHolder(@NonNull VH_Calendar holder, int position) {
        holder.dayNumber.setText(daysOfMonth.get(position));
        String date=generateDate(selectedMonth,selectedYear,daysOfMonth.get(position));//generira se datum od poslanih varijabli
        //kako bi se mogao usporediti s datumima koji sadrze obrok

        for(int i=0; i<datesWithMeals.size();i++){
            if(date.equals(datesWithMeals.get(i))){
                holder.icon.setVisibility(View.VISIBLE);//ako taj datum sadrzi neki obrok

            }

        }
        for(int i=0; i<selectedDatesThis.size();i++) {
            if (date.equals(selectedDatesThis.get(i))) {

                holder.itemView.setBackgroundColor(getColor());//ako je taj datum medju odabranima za ispisivanje
            }
        }

    }

    @Override
    public int getItemCount() {
       return daysOfMonth.size();
    }
    public  interface OnItemListner{
        void onItemClick(int position,View view,String day);
    }

    public String generateDate(String selectedMonth,String selectedYear,String selectedDay){
        int day,month=0;
        String outputDate="2023-06-18";
        if(selectedDay.length()<2 && selectedMonth.length()<2){
            outputDate=selectedYear+"-"+"0"+selectedMonth+"-"+"0"+selectedDay;
        }
        if(selectedDay.length()>1 && selectedMonth.length()<2){
            outputDate=selectedYear+"-"+"0"+selectedMonth+"-"+selectedDay;
        }
        if(selectedDay.length()>1 && selectedMonth.length()>1){
            outputDate=selectedYear+"-"+"0"+selectedMonth+"-"+"0"+selectedDay;
        }
        if(selectedDay.length()<2 && selectedMonth.length()>1){
            outputDate=selectedYear+"-"+selectedMonth+"-"+"0"+selectedDay;
        }


        return outputDate;
    }

    public String returnGeneratedDate(String day){
        String date="";
        if(Integer.parseInt(selectedMonth)<10 && Integer.parseInt(day)>9){
            date=selectedYear+"-"+"0"+selectedMonth+"-"+day;
        }
        if(Integer.parseInt(selectedMonth)<10 && Integer.parseInt(day)<10){
            date=selectedYear+"-"+"0"+selectedMonth+"-"+"0"+day;
        }
        if(Integer.parseInt(selectedMonth)>9 && Integer.parseInt(day)<10){
            date=selectedYear+"-"+selectedMonth+"-"+"0"+day;
        }
        if(Integer.parseInt(selectedMonth)>9 && Integer.parseInt(day)>9){
            date=selectedYear+"-"+selectedMonth+"-"+day;
        }


        return date;
    }

   int getColor(){
        int svijetloPlava=context.getResources().getColor(R.color.MojaSvijetloPlava);
        return  svijetloPlava;
    }

    public void swapData(ArrayList<String> datesWithMealsNew){

       datesWithMeals=datesWithMealsNew;
            notifyDataSetChanged();
    }


}
