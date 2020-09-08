package com.example.ex1;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A collection of static classes - Holder, Adapter and Diff Callback
 */
public class RecyclerUtils {


    /**
     * one RecyclerHolder holds one view template
     */
    static class RecyclerHolder extends RecyclerView.ViewHolder{
        TextView textView;

        RecyclerHolder(@NonNull final View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.text_to_show);
        }
    }

    /**
     * providing calculations for insertions / deletions of items
     * DiffUtil is a utility class that can calculate the difference
     * between two lists and output a list of update operations that
     * converts the first list into the second one
     */
    static class RecyclerCallback extends DiffUtil.ItemCallback<Message_item>{
        @Override
        public boolean areItemsTheSame(@NonNull Message_item r1, @NonNull Message_item r2){
            return r1.text.equals(r2.text);
        }
        @Override
        public boolean areContentsTheSame(@NonNull Message_item r1, @NonNull Message_item r2){
            return  r1.equals(r2);
        }
    }


    /**
     * Customize each view template, can see the actual data
     */
    static class RecyclerViewAdapter extends ListAdapter<Message_item,RecyclerHolder>{

        RecyclerViewAdapter( ){
            super(new RecyclerCallback());
        }

        //Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int itemType){

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one_recycle, parent,false);
            RecyclerHolder holder = new RecyclerHolder(itemView);
            return holder;
        }

        //Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(@NonNull final RecyclerHolder recyclerHolder, final int position){
            MutableLiveData<Message_item> messagesLiveData = MainActivity.app.getMessagesLiveData() ;
            ManageMessagesDB manageMessagesDB = new ManageMessagesDB();
            final Message_item message_item = getItem(position);
            recyclerHolder.textView.setText(message_item.text);

            recyclerHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    messagesLiveData.postValue(message_item);

//                    Intent intent   = new Intent(this, MessageDetails.class);
//
//                    intent.putExtra("MESSAGE_ITEM", message_item);
//                    startActivity(intent);



//                    // Build an AlertDialog
//                    AlertDialog.Builder builder = new AlertDialog.Builder(recyclerHolder.itemView.getContext());
//                    builder.setCancelable(true);
//                    // Set a title for alert dialog
//                    builder.setTitle("Delete message");
//                    builder.setMessage("Are you sure to delete?? there is no going back");
//                    // Set the alert dialog yes button click listener
//                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // Do something when user clicked the Yes button
//                            int place = recyclerHolder.getAdapterPosition();
//                            MainActivity.mToPresent.remove(recycler_item);
//                            notifyItemRemoved(place);
//                            submitList(MainActivity.mToPresent);
//                            manageMessagesDB.deleteMessage(recycler_item);
//                        }
//                    });
//                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            //dont do anything
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                    return true;
                }
            });

        }


    }





}
