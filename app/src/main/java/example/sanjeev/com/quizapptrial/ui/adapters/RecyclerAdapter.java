package example.sanjeev.com.quizapptrial.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import example.sanjeev.com.quizapptrial.R;
import example.sanjeev.com.quizapptrial.model.Message;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<Message> messageList;

    public RecyclerAdapter(ArrayList<Message> messageList){
        this.messageList = messageList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        String messageText = message.getMessage();
        String time = message.getTimeStamp();

        holder.messageTv.setText(messageText);
        holder.dateTv.setText(time);

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTv ;
        public TextView dateTv;
        public MyViewHolder(View itemView) {
            super(itemView);

            messageTv = itemView.findViewById(R.id.message_tv);
            dateTv = itemView.findViewById(R.id.time_tv);
        }
    }
}
