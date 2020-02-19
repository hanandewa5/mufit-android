//package com.nostratech.mufit.consumer.my_voucher;
//
//import android.content.Context;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RadioButton;
//import android.widget.TextView;
//
//import com.nostratech.mufit.consumer.R;
//import com.nostratech.mufit.consumer.model.MyVoucherModel;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//public class RecyclerViewAdvancedAdapter_my_voucher extends RecyclerView.Adapter<RecyclerViewAdvancedAdapter_my_voucher.ViewHolder> {
//
//    private MyVoucherActivity myVoucherActivity;
//    List<MyVoucherModel> myVoucherModels = new ArrayList<>();
//    //ArrayList<MyVoucherModel> listTemp;
//    private SessionManager sessionManager;
//    private RadioButton lastCheckedRB = null;
//    private static int lastCheckedPos = 0;
//    private RadioButton selectedRadioButton;
//    private boolean isVoucherUse = false;
//    private int indexVoucherSeleted = 0;
//    private String voucherPackageName = "";
//    private String voucherCode = "";
//    private String voucherTrainerSpecialityID = "";
//    private String voucherTrainerID = "";
//    private String voucherID = "";
//    private boolean fromBookingActivity2;
//    private boolean clickOnRadioButton = false;
//
//    private int selectedItemIndex = RecyclerView.NO_POSITION;
//
//    public MyVoucherModel getSelectedItem(){
//        if(selectedItemIndex != RecyclerView.NO_POSITION){
//            return myVoucherModels.get(selectedItemIndex);
//        } else {
//            return null;
//        }
//    }
//
//
//    public RecyclerViewAdvancedAdapter_my_voucher(MyVoucherActivity myVoucherActivity, SessionManager sessionManager){
//        this.myVoucherActivity = myVoucherActivity;
//        this.sessionManager = sessionManager;
//        fromBookingActivity2 = false;
//    }
//
//    public RecyclerViewAdvancedAdapter_my_voucher(MyVoucherActivity myVoucherActivity, SessionManager sessionManager, String voucherID){
//        this.myVoucherActivity = myVoucherActivity;
//        this.sessionManager = sessionManager;
//        this.voucherID = voucherID;
//        fromBookingActivity2 = true;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerViewAdvancedAdapter_my_voucher.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_voucher_owned,
//                parent, false);
//        //context = parent.getContext();
//        //initTypefaces(context);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerViewAdvancedAdapter_my_voucher.ViewHolder holder, final int position) {
//        //Log.d("FRISTY LOG", "POSISI KE " + position);
//        Context context = holder.itemView.getContext();
//        holder.voucherDetailTitle.setText(myVoucherModels.get(position).getSpeciality_name()
//                +" "+"by"+" "+myVoucherModels.get(position).getTrainer_name());
//        holder.voucherDetailName.setText(myVoucherModels.get(position).getPackage_name());
//
//        String currentQuantity = String.valueOf(myVoucherModels.get(position).getCurrent_quantity());
//        String quantity = String.valueOf(myVoucherModels.get(position).getQuantity());
//
//        if (currentQuantity == null && quantity == null){
//            holder.voucherDetailUseFor.setText(context.getResources().getString(R.string.single_voucher));
//        }else {
//            holder.voucherDetailUseFor.setText(context.getResources().getString(R.string.available)+" "+currentQuantity +" / " +quantity);
//        }
//
//        long date = myVoucherModels.get(position).getEnd_date();
//        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
//        String dateFormatted = dateFormat.format(date);
//        holder.voucherDetailValidDate.setText(context.getResources().getString(R.string.until)+ " " + dateFormatted);
//        //TODO: - fristy only can click one radio button
//        RadioButton radioButton = holder.radioButtonVoucherSelect;
//
//        if(fromBookingActivity2)
//        {
//            for(int i =0; i<myVoucherModels.size();i++)
//            {
//                if(myVoucherModels.get(i).getId().equals(voucherID))
//                    myVoucherModels.get(i).setChecked(true);
//            }
//            fromBookingActivity2 = false;
//        }
//        radioButton.setChecked(myVoucherModels.get(position).isChecked());
//
//        radioButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                selectedItemIndex = holder.getAdapterPosition();
//
//
//                clickOnRadioButton = true;
//
//                // Set unchecked all other elements in the list, so to display only one selected radio button at a time
//                for(MyVoucherModel model: myVoucherModels)
//                    model.setChecked(false);
//
//                // Set "checked" the model associated to the clicked radio button
//                myVoucherModels.get(position).setChecked(true);
//
//                // If current view (RadioButton) differs from previous selected radio button, then uncheck selectedRadioButton
//                if(null != selectedRadioButton )
//                {
//                    if(!v.equals(selectedRadioButton)) {
//                        //Log.d("FRISTY LOG", "MASUK RADIO BUTTON TIDAK SAMA");
//                        selectedRadioButton.setChecked(false);
//                        isVoucherUse = false;
//                        notifyDataSetChanged();
//                    }
//                    else if(v.equals(selectedRadioButton))
//                    {
//                        //Log.d("FRISTY LOG", "MASUK RADIO BUTTON SAMA");
//                        selectedRadioButton.setChecked(false);
//                        myVoucherModels.get(position).setChecked(false);
//                        isVoucherUse = false;
//                        selectedRadioButton = null;
//                        notifyDataSetChanged();
//                        return;
//                    }
//                }
//                //Log.d("FRISTY LOG", "LINE AKHIR");
//                // Replace the previous selected radio button with the current (clicked) one, and "check" it
//                selectedRadioButton = (RadioButton) v;
//                selectedRadioButton.setChecked(true);
//                isVoucherUse = true;
//                indexVoucherSeleted = position;
//                voucherPackageName = myVoucherModels.get(position).getPackage_name();
//                voucherCode = myVoucherModels.get(position).getCode();
//                voucherTrainerSpecialityID = myVoucherModels.get(position).getTrainer_speciality_id();
//                voucherTrainerID = myVoucherModels.get(position).getTrainer_id();
//                voucherID = myVoucherModels.get(position).getId();
//                notifyDataSetChanged();
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        //Log.d("FRISTY LOG", "jumlah list nya " + myVoucherModels.size());
//        return myVoucherModels.size();
//    }
//
//    public void refresh(List<MyVoucherModel> voucherModels) {
//        myVoucherModels = new ArrayList<>();
//        myVoucherModels.addAll(voucherModels);
//        notifyDataSetChanged();
//    }
//
//    public void clearList() {
//        //this.myVoucherAdvancedModel = new ArrayList<>();
//        notifyDataSetChanged();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        TextView voucherDetailName, voucherDetailTitle, voucherDetailUseFor, voucherDetailValidDate;
//        RadioButton radioButtonVoucherSelect;
//        ViewGroup layoutVoucherDetailList;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            layoutVoucherDetailList = itemView.findViewById(R.id.layout_voucher_detail);
//            voucherDetailName = itemView.findViewById(R.id.text_voucher_detail_name);
//            voucherDetailTitle = itemView.findViewById(R.id.text_voucher_detail_title);
//            voucherDetailUseFor = itemView.findViewById(R.id.text_voucher_detail_use_for);
//            voucherDetailValidDate = itemView.findViewById(R.id.text_voucher_detail_valid_date);
//            radioButtonVoucherSelect = itemView.findViewById(R.id.radioButton_voucher_select);
//        }
//    }
//
//    public boolean isVoucherUsed()
//    {
//        //Log.d("FRISTY LOG", "ISI dari isVoucherUsed " + isVoucherUse);
//        return this.isVoucherUse;
//    }
//
//    public int indexVoucherSelected()
//    {
//        //Log.d("FRISTY LOG", "ISI dari indexVoucherSelected " + indexVoucherSeleted);
//        return this.indexVoucherSeleted;
//    }
//
//    public String voucherPackageName()
//    {
//        return this.voucherPackageName;
//    }
//
//    public String voucherCode()
//    {
//        return this.voucherCode;
//    }
//
//    public String voucherTrainerSpecialityID()
//    {
//        return this.voucherTrainerSpecialityID;
//    }
//
//    public String voucherTrainerD()
//    {
//        return this.voucherTrainerID;
//    }
//
//    public String voucherID()
//    {
//        return this.voucherID;
//    }
//
//    public boolean clickOnRadioButton()
//    {
//        return this.clickOnRadioButton;
//    }
//
//    public RadioButton selectedRadioButton()
//    {
//        return this.selectedRadioButton;
//    }
//
//
//
//
//}
