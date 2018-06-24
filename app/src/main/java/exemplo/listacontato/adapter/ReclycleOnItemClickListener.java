package exemplo.listacontato.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class ReclycleOnItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener mListerner;

    private GestureDetector mGestureDetector;
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public ReclycleOnItemClickListener(Context context, OnItemClickListener listener){
        mListerner = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if(childView != null && mListerner != null && mGestureDetector.onTouchEvent(e)){
            mListerner.onItemClick(childView, rv.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
