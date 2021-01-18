package com.example.albatross.final_salon_app;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

public class FootAdapter extends RecyclerView.Adapter<FootAdapter.ViewHolder> {
    private Context context;
    private List<Foot> services;
    ProgressDialog pDialog;
    ConnectivityManager conMgr;

    final String url_delete = Server.URL + "delete_service.php";
    public FootAdapter(Context context, List<Foot> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardservices, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.servicename.setText( services.get(position).getservice_name());
        holder.price.setText("PHP: " + services.get(position).getservice_price());
        holder.hours.setText(services.get(position).getservice_duration()+" Hour/s" );
    }


    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView cv;
        public TextView servicename;
        public TextView price;
        public TextView hours;

        public ViewHolder(View itemView) {
            super(itemView);
            servicename = (TextView) itemView.findViewById(R.id.dreg);
            price = (TextView) itemView.findViewById(R.id.name);
            hours = (TextView) itemView.findViewById(R.id.hours);
            cv = itemView.findViewById(R.id.card_view);
            cv.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

           // showPopupMenu(v, position);
            new MenuClickListener(position);
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
            SharedPreferences pref = context.getSharedPreferences("USER_INFO", MODE_PRIVATE);
            String guest_id_PK = pref.getString("guest_id_PK", null);
            String g_status = pref.getString("g_status", null);
            String selectedServiceID = services.get(pos).getsalon_service_id();
            Bookhandler.customerID = guest_id_PK;
            Bookhandler.membership = g_status;
            Bookhandler.serviceId = selectedServiceID;
            Bookhandler.price = Integer.parseInt(services.get(pos).getservice_price());
            try {
                if (g_status.equals("1")) {

                    ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new book_member()).commit();
                } else {

                    ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new book_not_member()).commit();
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            this.pos = pos;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.action_update:
                    UpdateServiceHandler.salon_service_id = services.get(pos).getsalon_service_id();
                    UpdateServiceHandler.service_name = services.get(pos).getservice_name();
                    UpdateServiceHandler.service_type = services.get(pos).getservice_type();
                    UpdateServiceHandler.service_price = services.get(pos).getservice_price();
                    UpdateServiceHandler.service_duration = services.get(pos).getservice_duration();
                    ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new fragment_update_service()).commit();


                    /*
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
    public FootAdapter(Context context, FragmentManager f_manager) {
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