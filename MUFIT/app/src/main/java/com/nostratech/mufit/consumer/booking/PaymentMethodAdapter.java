package com.nostratech.mufit.consumer.booking;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.booking.PaymentMethodModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by AnonymousX on 2018-10-21.
 */

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.ViewHolderPaymentMethod> {
    private Context context;
    private List<PaymentMethodModel> paymentMethodModels = new ArrayList<>();
    private int mSelectedItem = -1;

    private OnItemSelectedListener listener;

    public PaymentMethodAdapter(OnItemSelectedListener listener) {
        this.listener = listener;
    }


//    private Typeface tfRegular;

    @NonNull
    @Override
    public ViewHolderPaymentMethod onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        context = parent.getContext();
//        initTypefaces(context);

        PaymentMethodModel paymentMethodModel = paymentMethodModels.get(viewType);

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_payment_method, parent, false);
        return new ViewHolderPaymentMethod(view, this);

    }

    public int getSelectedItem(){
        if(mSelectedItem>=0) {
            return paymentMethodModels.get(mSelectedItem).getId();
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentMethodAdapter.ViewHolderPaymentMethod holder, final int position) {
        final PaymentMethodModel paymentMethodModel = paymentMethodModels.get(position);

        ViewHolderPaymentMethod viewHolderPaymentMethod = (ViewHolderPaymentMethod) holder;
        viewHolderPaymentMethod.setIsRecyclable(false);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getInstance(localeID);
        viewHolderPaymentMethod.label_service_fee.setText(context.getString(R.string.service_fee)+" Rp. "+formatRupiah.format(Double.parseDouble(paymentMethodModel.getServiceFee())));
        viewHolderPaymentMethod.paymentMethodLabel.setText(paymentMethodModel.getName());
        viewHolderPaymentMethod.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.updateServiceFeesText(Double.parseDouble((paymentMethodModel.getServiceFee())));
                mSelectedItem = position;
                notifyDataSetChanged();
            }
        });

        if (position == mSelectedItem) {
            holder.rootLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner_payment_method_selected));
        } else {
            holder.rootLayout.setBackground(null);
        }
//        try {
//            viewHolderPaymentMethod.setDateToView(paymentMethodModel, position);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public int getItemCount() {
        return paymentMethodModels.size();
    }

    public class ViewHolderPaymentMethod extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView paymentMethodLabel,label_service_fee;
        ViewGroup rootLayout;
//        public LinearLayout layout_payment_method;
        private PaymentMethodAdapter mAdapter;

        public ViewHolderPaymentMethod(View itemView, final PaymentMethodAdapter mAdapter) {
            super(itemView);
            this.mAdapter = mAdapter;
            label_service_fee = itemView.findViewById(R.id.label_service_fee);
            rootLayout = itemView.findViewById(R.id.root_layout);
//            layout_payment_method = itemView.findViewById(R.id.layout_payment_method);
            paymentMethodLabel = itemView.findViewById(R.id.label_payment_method);
//            layout_payment_method.setOnClickListener(this);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

//            mSelectedItem = getAdapterPosition();
//            notifyDataSetChanged();
//            mAdapter.onItemHolderClick(ViewHolderPaymentMethod.this);
//
//            Intent intent = new Intent("serviceFeeReceiver");
//            intent.putExtra("serviceFee",paymentMethodModels.get(mSelectedItem).getServiceFee());
//            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
//        public void setDateToView(PaymentMethodModel item, int i) {
//            //paymentMethodRadio.setChecked(i == mSelectedItem);
//            item.setChecked(i == mSelectedItem);
//            if(i==mSelectedItem){
//                rootLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_corner_payment_method_selected));
//            }else{
//                rootLayout.setBackground(null);
//            }
//        }
    }

    public interface OnItemSelectedListener {
        void updateServiceFeesText(double price);
    }


    public void updateList(List<PaymentMethodModel> listModels) {
        this.paymentMethodModels.addAll(listModels);
        notifyDataSetChanged();
    }

    public void clearList() {
        this.paymentMethodModels = new ArrayList<>();
        notifyDataSetChanged();
    }

}
