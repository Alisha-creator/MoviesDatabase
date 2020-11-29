package alisha.digipodium.roomdatabasedemo1;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

public class MainAdaptor extends RecyclerView.Adapter<MainAdaptor.ViewHolder> {

    //Intialize Variables
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    //Create Constructor
    public MainAdaptor(Activity context,List<MainData> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

     @NonNull
    @Override
    public MainAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize View
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdaptor.ViewHolder holder, int position) {
        //Initialize main data
        final MainData data = dataList.get(position);
        //Intialize database
        database = RoomDB.getInstance(context);
        //Set text on text view
        holder.textView.setText(data.getText());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intialize main data
                MainData d = dataList.get(holder.getAdapterPosition());
                //Get id
                final int sID = d.getID();
                //Get text
                String sText = d.getText();

                //Create dialog
                final Dialog dialog = new Dialog(context);
                //set content view
                dialog.setContentView(R.layout.dialog_update);
                //Initialize Width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //Initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //Set layout
                dialog.getWindow().setLayout(width,height);
                //show dialog
                dialog.show();

                //Initialize and assign variable
                final EditText editText = dialog.findViewById(R.id.edit_text);
                Button btUpdate = dialog.findViewById(R.id.bt_update);

                //Set text on edit text
                editText.setText(sText);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Dismiss dialog
                        dialog.dismiss();
                        //Get updated text from edit text
                        String uText = editText.getText().toString().trim();
                        //update the databse
                        database.mainDao().update(sID,uText);
                        //notify when data is updated
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();

                    }
                });
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initailize main data
                MainData d = dataList.get(holder.getAdapterPosition());
                //Delete text from databse
                database.mainDao().delete(d);
                //Notify when data is deleted
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Intialize Variables
        TextView textView;
        ImageView btnEdit,btnDelete;

        public ViewHolder(@NonNull View itemView){
             super(itemView);

             //Assign variable
            textView = itemView.findViewById(R.id.text_view);
            btnEdit = itemView.findViewById(R.id.bt_edit);
            btnDelete = itemView.findViewById(R.id.bt_delete);
         }
    }
}
