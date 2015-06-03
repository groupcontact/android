package seaice.app.groupcontact.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.larvalabs.svgandroid.SVGParser;

import seaice.app.appbase.utils.ColorUtils;
import seaice.app.appbase.view.TabBarAdapter;
import seaice.app.groupcontact.fragment.FriendListFragment;
import seaice.app.groupcontact.fragment.GroupListFragment;
import seaice.app.groupcontact.fragment.ProfileFragment;

public class MainPagerAdapter extends FragmentPagerAdapter implements TabBarAdapter {

    private static String strokeColorStr = "#33BBEE";

    private static String initStrokeColorStr = "#454545";

    private static String fillColorStr = "#33BBEE";

    private static String initFillColorStr = "#FFFFFF";

    private static String titleColorStr = "#33BBEE";

    private static String initTitleColorStr = "#6E6E6E";

    private static String[] iconSVGContents = new String[]{
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<!-- Generator: Adobe Illustrator 17.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->\n" +
                    "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1 Basic//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11-basic.dtd\">\n" +
                    "<svg version=\"1.1\" baseProfile=\"basic\" id=\"图层_1\"\n" +
                    "\t xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"32px\" height=\"32px\"\n" +
                    "\t viewBox=\"0 0 32 32\" xml:space=\"preserve\">\n" +
                    "<path fill=\"#FFFFFF\" stroke=\"#454545\" stroke-miterlimit=\"10\" d=\"M27.932,26.712c-3.322-2.689-10.624-3.703-10.624-3.703\n" +
                    "\tl0.018-2.098c1.92-1.554,4.733-4.49,5.917-7.284c1.483-3.502-0.58-3.596-0.58-3.596c1.751-12.398-12.1-9.127-12.1-9.127\n" +
                    "\tC9.116,0.591,8.374,1.536,8.374,1.536C9.25,2.22,8.926,3.533,8.926,3.533C6.965,5.311,7.784,9.988,7.784,9.988\n" +
                    "\tc-1.6,1.056-0.676,3.398-0.676,3.398c0.714,2.823,5.225,7.496,5.225,7.496l-0.006,2.098c-3.635,0.362-10.82,2.743-11.811,5.708\n" +
                    "\tv2.829l22.616-0.025C23.221,29.074,24.818,26.727,27.932,26.712z\"/>\n" +
                    "<path fill=\"#FFFFFF\" stroke=\"#454545\" stroke-miterlimit=\"10\" d=\"M23.422,31.375c9.373-3.456,9.945-13.113,5.417-14.746\n" +
                    "\tc-3.412-1.231-5.149,1.065-5.537,1.787c-0.388-0.722-2.125-3.017-5.538-1.787c-4.527,1.632-3.956,11.289,5.415,14.746l0.121,0.04\n" +
                    "\tL23.422,31.375z\"/>\n" +
                    "</svg>\n",
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<!-- Generator: Adobe Illustrator 17.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->\n" +
                    "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1 Basic//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11-basic.dtd\">\n" +
                    "<svg version=\"1.1\" baseProfile=\"basic\" id=\"图层_1\"\n" +
                    "\t xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"32px\" height=\"32px\"\n" +
                    "\t viewBox=\"0 0 32 32\" xml:space=\"preserve\">\n" +
                    "<path fill=\"#FFFFFF\" stroke=\"#454545\" stroke-miterlimit=\"10\" d=\"M14.725,1.794c0,0,0.59-0.845,1.738-0.564\n" +
                    "\tc0,0,10.999-2.929,9.609,8.168c0,0,1.639,0.084,0.46,3.219c-0.94,2.501-3.175,5.128-4.699,6.519l-0.014,1.879\n" +
                    "\tc0,0,7.778,1.337,9.697,5.203V28.6L8.486,28.627v-2.529c0-1.991,3.906-4.725,6.793-5.047l0.005-1.878c0,0-0.996-4.244-1.563-6.771\n" +
                    "\tc0,0-0.733-2.096,0.537-3.042c0,0-0.651-4.185,0.907-5.776C15.164,3.583,15.422,2.408,14.725,1.794z\"/>\n" +
                    "<path fill=\"#FFFFFF\" stroke=\"#454545\" stroke-miterlimit=\"10\" d=\"M7.44,1.422c0,0,0.664-0.95,1.955-0.634\n" +
                    "\tc0,0,12.372-3.295,10.808,9.188c0,0,1.844,0.094,0.517,3.621c-1.058,2.813-3.571,5.768-5.285,7.333l-0.016,2.114\n" +
                    "\tc0,0,9.949,1.507,10.908,5.852v2.678L0.422,31.605V28.76c0.886-2.987,7.303-5.385,10.55-5.748l0.006-2.113\n" +
                    "\tc0,0-4.03-4.703-4.668-7.546c0,0-0.825-2.357,0.604-3.421c0,0-0.732-4.708,1.02-6.497C7.934,3.435,8.224,2.112,7.44,1.422z\"/>\n" +
                    "</svg>\n",
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<!-- Generator: Adobe Illustrator 17.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->\n" +
                    "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1 Basic//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11-basic.dtd\">\n" +
                    "<svg version=\"1.1\" baseProfile=\"basic\" id=\"图层_1\"\n" +
                    "\t xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"32px\" height=\"32px\"\n" +
                    "\t viewBox=\"0 0 32 32\" xml:space=\"preserve\">\n" +
                    "<path fill=\"#FFFFFF\" stroke=\"#454545\" stroke-miterlimit=\"10\" d=\"M8.863,1.422c0,0,0.799-0.95,2.351-0.634\n" +
                    "\tc0,0,14.879-3.295,12.999,9.188c0,0,2.217,0.094,0.622,3.621c-1.272,2.813-4.295,5.768-6.357,7.333l-0.019,2.114\n" +
                    "\tc0,0,11.965,1.507,13.119,5.852v2.678L0.422,31.605V28.76c1.065-2.987,8.783-5.385,12.688-5.748l0.007-2.113\n" +
                    "\tc0,0-4.847-4.703-5.614-7.546c0,0-0.992-2.357,0.727-3.421c0,0-0.88-4.708,1.227-6.497C9.457,3.435,9.805,2.112,8.863,1.422z\"/>\n" +
                    "</svg>\n"
    };

    private static int strokeColor = Color.parseColor(strokeColorStr);

    private static int initStrokeColor = Color.parseColor(initStrokeColorStr);

    private static int fillColor = Color.parseColor(fillColorStr);

    private static int initFillColor = Color.parseColor(initFillColorStr);

    private static int titleColor = Color.parseColor(titleColorStr);

    private static int initTitleColor = Color.parseColor(initTitleColorStr);

    private String[] mTitles;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        if (position == 0) {
            fragment = new FriendListFragment();
        } else if (position == 1) {
            fragment = new GroupListFragment();
        } else {
            fragment = new ProfileFragment();
        }
        Bundle args = new Bundle();
        args.putString("title", mTitles[position]);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Drawable getIcon(int position, float offset) {
        String newFillColor = String.format("#%06X", 0xFFFFFF &
                ColorUtils.getInterColor(fillColor, initFillColor, offset));
        String newStrokeColor = String.format("#%06X", 0xFFFFFF &
                ColorUtils.getInterColor(strokeColor, initStrokeColor, offset));
        String content = iconSVGContents[position].replace(initFillColorStr, newFillColor)
                .replace(initStrokeColorStr, newStrokeColor);
        return SVGParser.getSVGFromString(content).createPictureDrawable();
    }

    @Override
    public String getTitle(int position) {
        return getPageTitle(position).toString();
    }

    @Override
    public int getTitleColor(float offset) {
        return ColorUtils.getInterColor(titleColor, initTitleColor, offset);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    public void setTitles(String[] titles) {
        mTitles = titles;
    }
}
