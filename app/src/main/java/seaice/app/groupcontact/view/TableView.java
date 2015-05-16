package seaice.app.groupcontact.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import seaice.app.groupcontact.R;

/**
 * 仿iOS中TableView式样的View
 *
 * @author 周海兵
 */
public class TableView extends ListView {

    /* 数据模型 */
    TableAdapter mAdapter;

    public TableView(Context context) {
        super(context);
        init(null);
    }

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TableView);

        /* 获取属性配置 */

        a.recycle();
    }

    public void setAdapter(TableAdapter adapter) {
        mAdapter = adapter;

        setAdapter(new ListAdapter());
    }

    private class ListAdapter extends BaseAdapter {

        /* 在ListView中的行数量 */
        private int mCount = 0;

        /* 前闭后开 */
        class Range {
            int start;
            int end;
            int header;
            int footer;
        }

        /* 每个Section的position范围 */
        private Map<Integer, Range> mRangeMap;

        /* 初始化相关数据 */
        public ListAdapter() {
            mRangeMap = new HashMap<>();
            /* TableView的header和footer */
            int mHeader = mAdapter.getHeader() == null ? 0 : 1;
            int mFooter = mAdapter.getFooter() == null ? 0 : 1;
            mCount = mHeader + mFooter;
            for (int i = 0; i < mAdapter.getSectionCount(); ++i) {
                /* 每个Section中的header和footer */
                Range range = new Range();
                range.start = mCount + 1;
                range.header = mAdapter.getSectionHeader(i) == null ? 0 : 1;
                range.footer = mAdapter.getSectionFooter(i) == null ? 0 : 1;
                mCount += range.header;
                mCount += range.footer;
                /* Section中行的数量 */
                mCount += mAdapter.getRowCount(i);
                range.end = mCount + 1;
                mRangeMap.put(i, range);
            }
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public Object getItem(int position) {
            int rowCount = 0;
            for (int i = 0; i < mAdapter.getSectionCount(); ++i) {
                Range range = mRangeMap.get(i);
                int count = range.start - range.end;
                if (position >= range.start && position < range.end) {
                    /* 锁定范围 */
                    return mAdapter.getDataset().get(rowCount + (position - range.start));
                }
                rowCount += count;
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            for (int section : mRangeMap.keySet()) {
                Range range = mRangeMap.get(section);
                /* 如果比第0个Section的位置还小, 那只能是Table的Header */
                if (section == 0 && position < range.start) {
                    return mAdapter.getHeader();
                }
                /* 如果比最后一个Section的位置还大, 那只能是Table的Footer */
                if (section == mAdapter.getSectionCount() && position >= range.end) {
                    return mAdapter.getFooter();
                }
                /* 如果不在当前Section内, 继续进行 */
                if (position < range.start || position >= range.end) {
                    continue;
                }
                /* 如果是当前Section的Header */
                if (position == range.start && mAdapter.getSectionHeader(section) != null) {
                    return mAdapter.getSectionHeader(section);
                }
                /* 如果是当前Section的Footer */
                if (position == (range.end - 1) && mAdapter.getSectionFooter(section) != null) {
                    return mAdapter.getSectionFooter(section);
                }
                /* 在当前Section行中 */
                return mAdapter.getRow(section, position - range.start - range.header);
            }
            return null;
        }
    }
}
