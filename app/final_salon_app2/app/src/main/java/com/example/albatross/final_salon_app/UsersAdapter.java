package com.example.albatross.final_salon_app;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private Context context;
    private List<Users> services;
    ProgressDialog pDialog;
    ConnectivityManager conMgr;
    String statuss = "";
    public static String uid;
    final String url_delete = Server.URL + "deactivate_user.php";
    public UsersAdapter(Context context, List<Users> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardusers, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText( services.get(position).getu_firstname()+" "+ services.get(position).getu_lastname());
        holder.role.setText( services.get(position).getu_role());
        if(services.get(position).getu_status().equals("1")) {
            holder.status.setText("Activated");
        }else {
            holder.status.setText("Deactivate");
        }
    }
    @Override
    public int getItemCount() {
        return services.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cv;
        public TextView name;
        public TextView role;
        public TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            role = (TextView) itemView.findViewById(R.id.role);
            status = (TextView) itemView.findViewById(R.id.status);
            cv = itemView.findViewById(R.id.card_view);
            cv.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            showPopupMenu(v, position);
            //new MenuClickListener(position);
        }
    }

    private void showPopupMenu(View view, int poaition) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        if(services.get(poaition).getu_status().equals("1")){
            inflater.inflate(R.menu.menu_users, popup.getMenu());
        }else{
            inflater.inflate(R.menu.menu_users1, popup.getMenu());
        }

        popup.setOnMenuItemClickListener(new MenuClickListener(poaition));
        popup.show();
    }


    class MenuClickListener implements PopupMenu.OnMenuItemClickListener {
        Integer pos;

        public MenuClickListener(int pos) {
            this.pos = pos;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.action_update:
                    addUsersHandler.ID = services.get(pos).getu_information_id_PK();
                    addUsersHandler.firstname = services.get(pos).getu_firstname();
                    addUsersHandler.lastname = services.get(pos).getu_lastname();
                    addUsersHandler.contact = services.get(pos).getu_phone_number();
                    addUsersHandler.facebook = services.get(pos).getu_fb_name();
                    addUsersHandler.address = services.get(pos).getu_address();
                    addUsersHandler.gender = services.get(pos).getu_gender();
                    addUsersHandler.email = services.get(pos).getu_gmail();
                    addUsersHandler.role = services.get(pos).getu_role();
                    addUsersHandler.ImageName = services.get(pos).getu_picture();
                    ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new UpdateUserProfile1()).commit();

                    /*
                    UpdateServiceHandler.salon_service_id = services.get(pos).getsalon_service_id();
                    UpdateServiceHandler.service_name = services.get(pos).getservice_name();
                    UpdateServiceHandler.service_type = services.get(pos).getservice_type();
                    UpdateServiceHandler.service_price = services.get(pos).getservice_price();
                    UpdateServiceHandler.service_duration = services.get(pos).getservice_duration();
                    ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new fragment_update_service()).commit();
*/
                    return true;

                case R.id.action_delete:
                    String str= "";
                    if(services.get(pos).getu_status().equals("1")){
                        str = "Deactivate";
                        statuss = "0";
                    }else{
                        str = "Activate";
                        statuss = "1";
                    }
                    new AlertDialog.Builder(context)
                            .setTitle("Status")
                            .setMessage("Are you sure you want to "+str+ services.get(pos).getu_firstname()+" "+ services.get(pos).getu_lastname()+"?")
                            // .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                                    if (conMgr.getActiveNetworkInfo() != null
                                            && conMgr.getActiveNetworkInfo().isAvailable()
                                            && conMgr.getActiveNetworkInfo().isConnected()) {
                                        String ID = String.valueOf(services.get(pos).getu_information_id_PK());

                                        pDialog = new ProgressDialog(context);
                                        pDialog.setCancelable(false);
                                        pDialog.setMessage("Changing Status...");
                                        showDialog();
                                        new DeactivateUser().execute(ID,statuss);
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

                case R.id.action_history:

                    uid = String.valueOf(services.get(pos).getu_information_id_PK());
                    ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new fragment_user_history()).commit();
                    return true;
                default:
            }
            return false;

        }

    }

    public FragmentManager f_manager;

    //in your constructor add FragmentManager
    public UsersAdapter(Context context, FragmentManager f_manager) {
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

    public class DeactivateUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String pid = strings[0];
            String status = strings[1];
            String finalURL = url_delete +
                        "?id=" + pid+
                     "&status=" + status ;
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
                            ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new fragment_all_users()).commit();
                        }else{
                            showDialogs();
                            //showToast("Error has occured, try again");
                        }
                    }
                } catch (Exception e) {
                    // showToast("Check your internet connection");
                    showDialogs();
                    e.printStackTrace();
                }
            } catch (Exception e) {
                //showToast("Check your internet connection");
                showDialogs();
                e.printStackTrace();
            }

            hideDialog();

            return null;
        }

    }

}

