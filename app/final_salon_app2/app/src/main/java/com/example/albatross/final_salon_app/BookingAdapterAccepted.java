package com.example.albatross.final_salon_app;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BookingAdapterAccepted extends RecyclerView.Adapter<BookingAdapterAccepted.ViewHolder> {
    private Context context;
    private List<BookingAccepted> services;
    ProgressDialog pDialog;
    ConnectivityManager conMgr;
String url_data = Server.URL+"update_booking_accepted.php";
    final String url_delete = Server.URL + "delete_service.php";
    public BookingAdapterAccepted(Context context, List<BookingAccepted> services) {
        this.context = context;
        this.services = services;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardbookingaccepted, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.dreg.setText( services.get(position).getdatetime());
        holder.name.setText(services.get(position).getname());
        String str = services.get(position).getmember();
        if(str.equals("1")){
            holder.member.setText("Member");
        }else{
            holder.member.setText("Not Member");
        }
        holder.staff.setText("Staff: "+services.get(position).getuname());
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
        public TextView staff;

        public ViewHolder(View itemView) {
            super(itemView);
            dreg = (TextView) itemView.findViewById(R.id.dreg);
            name = (TextView) itemView.findViewById(R.id.name);
            member = (TextView) itemView.findViewById(R.id.member);
            staff = (TextView) itemView.findViewById(R.id.service);

            cv = itemView.findViewById(R.id.card_view);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            SharedPreferences pref = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
            String role = pref.getString("u_role", null);
            //Toast.makeText(context,role,Toast.LENGTH_SHORT).show();
            if(role.equals("Admin")){
                new MenuClickListener(position);
            }else{
                showPopupMenu(v, position);
            }
        }
    }

    private void showPopupMenu(View view, int poaition) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_booking_accepted, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuClickListener(poaition));
        popup.show();
    }


    class MenuClickListener implements PopupMenu.OnMenuItemClickListener {
        Integer pos;
        public MenuClickListener(int pos) {
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

            SharedPreferences pref = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
            String role = pref.getString("u_role", null);
            if(role.equals("Admin")){
                ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_history_record()).commit();
            }

            this.pos = pos;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_update:
                    new AlertDialog.Builder(context)
                            .setTitle("Select")
                            .setMessage("Are you sure? ")
                            // .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                                    if (conMgr.getActiveNetworkInfo() != null
                                            && conMgr.getActiveNetworkInfo().isAvailable()
                                            && conMgr.getActiveNetworkInfo().isConnected()) {
                                        String clinicID = String.valueOf(services.get(pos).getid());
                                        pDialog = new ProgressDialog(context);
                                        pDialog.setCancelable(false);
                                        pDialog.setMessage("Loading...");
                                        showDialog();
                                        new SelectBooking().execute(clinicID);
                                    }else{
                                        new AlertDialog.Builder(context)
                                                .setTitle("Error")
                                                .setMessage("No Internet Connection")
                                                .setPositiveButton(android.R.string.ok, null).show();
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                    return true;

                default:
            }
            return false;

        }

    }

    public FragmentManager f_manager;

    //in your constructor add FragmentManager
    public BookingAdapterAccepted(Context context, FragmentManager f_manager) {
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

    public class SelectBooking extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String cid = strings[0];
            String finalURL = url_data +
                    "?bid=" + cid ;
            Log.e("", finalURL);
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(finalURL)
                        .get()
                        .build();
                Response response = null;
                try {
                    response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String result = response.body().string().trim();
                        if (result.equalsIgnoreCase("success")) {
                            showSuccess();
                            ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new fragment_bookings_accepted()).commit();
                        }else{
                            showError();
                        }
                    }
                } catch (Exception e) {
                    showError();
                    e.printStackTrace();
                }
            } catch (Exception e) {
                showError();
                e.printStackTrace();
            }
            hideDialog();
            return null;
        }

    }



    public void showSuccess() {
        ((MainActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(context)
                        .setTitle("Success")
                        .setMessage("Successful...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

    public void showError() {
        ((MainActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("Check your internet connection...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }




}