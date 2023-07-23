package com.example.myapplication.meal.detail;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class VH_Info extends RecyclerView.ViewHolder {
    /*ViewHolder za RecyclerView sto se pojavi u detaljima obroka (rv gdje mozemo vidjeti koje smo sve nutritiente unjeli u
    nas obrok) */

    TextView name;
    TextView mass;

    View lijevoPlavo;

    public VH_Info(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.tvSelectBy);
        this.mass = itemView.findViewById(R.id.tvFoodMassInfo2);
        this.lijevoPlavo=itemView.findViewById(R.id.viewLijevoPlavo);
    }
}
