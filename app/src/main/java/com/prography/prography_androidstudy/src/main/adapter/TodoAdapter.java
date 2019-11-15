package com.prography.prography_androidstudy.src.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prography.prography_androidstudy.R;
import com.prography.prography_androidstudy.Room.Todo;
import com.prography.prography_androidstudy.src.add_edit.AddEditActivity;
import com.prography.prography_androidstudy.src.common.utils.ItemTouchHelperListener;
import com.prography.prography_androidstudy.src.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> implements ItemTouchHelperListener {

    private ArrayList<Todo> todos;
    private LayoutInflater layoutInflater;
    private boolean deleteMode = false;

    private SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy년 M월 d일 a HH시 mm분", Locale.getDefault());

    public TodoAdapter(ArrayList<Todo> todos, Context context) {
        this.todos = todos;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = todos.get(position);
        if (todo != null) {
            holder.tvNoteTitle.setText(todo.getTitle());
            holder.tvNoteDescription.setText(todo.getDescription());
            holder.tvNoteDateTime.setText("Deadline: " + sdfDateTime.format(todo.getDateTime()));
        }
    }

    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    @Override
    public boolean onItemMoved(int fromPosition, int toPosition) {
        if (fromPosition < 0 || fromPosition >= todos.size() || toPosition < 0 || toPosition >= todos.size())
            return false;

        Todo todo = todos.get(fromPosition);
        todos.remove(fromPosition);
        todos.add(toPosition, todo);

        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemRemoved(int position) {
        todos.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoteTitle;
        TextView tvNoteDescription;
        TextView tvNoteDateTime;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            /* findViewByID */
            tvNoteTitle = itemView.findViewById(R.id.note_title);
            tvNoteDescription = itemView.findViewById(R.id.note_description);
            tvNoteDateTime = itemView.findViewById(R.id.note_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent newIntent = new Intent(itemView.getContext(), AddEditActivity.class);
                        newIntent.putExtra("noteId", todos.get(pos).getId());
                        itemView.getContext().startActivity(newIntent);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!deleteMode) {
                        setDeleteMode(true);
                        notifyDataSetChanged();
                        ((MainActivity) v.getContext()).deletemode = true;
                        // 메뉴 편집 버튼 타이틀 - 삭제로 변경 기능 추가 예정
                    }
                    return true;
                }
            });
        }
    }
}
