package com.example.albatross.final_salon_app;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UserHistoryAdapter extends RecyclerView.Adapter<UserHistoryAdapter.ViewHolder> {
    private Context context;
    private List<History> services;
    ProgressDialog pDialog;
    ConnectivityManager conMgr;

    final String url_delete = Server.URL + "delete_service.php";
    public UserHistoryAdapter(Context context, List<History> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.carduserhistory, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.dreg.setText( services.get(position).getdateadded());
        holder.name.setText(services.get(position).getname());
        if(services.get(position).getmember().equals("1")){
            holder.member.setText("Member" );
        }else{
            holder.member.setText("Not Member" );
        }
        holder.service.setText("Service: " +services.get(position).getservice());
        holder.price.setText("Price: " +services.get(position).getprice());

    }


    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView cv;
        public TextView dreg;
        public TextView name;
        public TextView member;
        public TextView service;
        public TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            dreg = (TextView) itemView.findViewById(R.id.dreg);
            name = (TextView) itemView.findViewById(R.id.name);
            member = (TextView) itemView.findViewById(R.id.member);
            service = (TextView) itemView.findViewById(R.id.service);
            price = (TextView) itemView.findViewById(R.id.price);
            cv = itemView.findViewById(R.id.card_view);
            cv.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
           // showPopupMenu(v, position);
          //  new MenuClickListener(position);
        }
    }

    private void showPopupMenu(View view, int poaition) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_myservices, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuClickListener(poaition));
        popup.show();
    }


    class MenuClickListener implements PopupMenu.OnMenuItemClickListener {
        Integer pos;

        public MenuClickListener(int pos) {

        // Toast.makeText(context, services.get(pos).getimage(),Toast.LENGTH_SHORT).show();
            BookingHandler.id =services.get(pos).getid();
            BookingHandler.dateadded =services.get(pos).getdateadded();
            BookingHandler.name =services.get(pos).getname();
            BookingHandler.status =services.get(pos).getmember();
            BookingHandler.service =services.get(pos).getservice();
            BookingHandler.price =services.get(pos).getprice();
            BookingHandler.duration =services.get(pos).getduration();
            BookingHandler.date =services.get(pos).getdatetime();
            BookingHandler.code =services.get(pos).getcode();
            BookingHandler.image =services.get(pos).getimage();

            ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new fragment_history_record()).commit();
            this.pos = pos;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.action_update:    /*
                    UpdateServiceHandler.salon_service_id = services.get(pos).getsalon_service_id();
                    UpdateServiceHandler.service_name = services.get(pos).getservice_name();
                    UpdateServiceHandler.service_type = services.get(pos).getservice_type();
                    UpdateServiceHandler.service_price = services.get(pos).getservice_price();
                    UpdateServiceHandler.service_duration = services.get(pos).getservice_duration();
                    ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new fragment_update_service()).commit();



                            sh = new ServicesHandler();
                             sh.csid =services.get(pos).getId();
                            sh.cid=services.get(pos).getCid();
                            sh.csName=services.get(pos).getcsName();
                            sh.csPrice=services.get(pos).getcsPrice();
                            sh.csDescription=services.get(pos).getcsDescription();
                            sh.csStatus=services.get(pos).getStatus();
                    ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new UpdateClinicServicesFragment()).commit();
                            */
                    return true;

                default:
            }
            return false;

        }

    }

    public FragmentManager f_manager;

    //in your constructor add FragmentManager
    public UserHistoryAdapter(Context context, FragmentManager f_manager) {
        this.context = context;
        this.f_manager = f_manager;
    }



    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();

    }

    public void showToast(final String Text) {
        ((MainActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showDialogs() {
        ((MainActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(context)
                        .setTitle("Success")
                        .setMessage("Service successfully deleted...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }



}