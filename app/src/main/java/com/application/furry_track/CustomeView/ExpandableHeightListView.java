package com.application.furry_track.CustomeView;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;


public class ExpandableHeightListView extends RecyclerView {

boolean expanded = false;

public ExpandableHeightListView(Context context)
{
    super(context);
}

public ExpandableHeightListView(Context context, AttributeSet attrs)
{
    super(context, attrs);
}

public ExpandableHeightListView(Context context, AttributeSet attrs,
                                int defStyle)
{
    super(context, attrs, defStyle);
}

public boolean isExpanded()
{
    return expanded;
}

@Override
public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
{
    // HACK! TAKE THAT ANDROID!
    if (isExpanded())
    {
        // Calculate entire height by providing a very large height hint.
        // View.MEASURED_SIZE_MASK represents the largest height possible.
        int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
    else
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

public void setExpanded(boolean expanded)
{
    this.expanded = expanded;
} }