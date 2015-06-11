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

    private static String initStrokeColorStr = "#6E6E6E";

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
                    "<path fill=\"#FFFFFF\" stroke=\"#6E6E6E\" stroke-width=\"2\" stroke-miterlimit=\"10\" d=\"M23.422,31.375\n" +
                    "\tc9.373-3.456,9.945-13.113,5.417-14.746c-3.412-1.231-5.149,1.065-5.537,1.787c-0.388-0.722-2.125-3.017-5.538-1.787\n" +
                    "\tc-4.527,1.632-3.956,11.289,5.415,14.746l0.121,0.04L23.422,31.375z\"/>\n" +
                    "<path fill=\"#FFFFFF\" stroke=\"#6E6E6E\" stroke-width=\"2\" stroke-miterlimit=\"10\" d=\"M17.764,16.629\n" +
                    "\tc1.553-0.56,2.755-0.387,3.645,0.032c0.746-0.986,1.4-2.027,1.834-3.049c1.483-3.502-0.58-3.596-0.58-3.596\n" +
                    "\tc1.751-12.397-12.1-9.126-12.1-9.126C9.116,0.576,8.374,1.521,8.374,1.521C9.25,2.205,8.926,3.518,8.926,3.518\n" +
                    "\tC6.965,5.296,7.784,9.973,7.784,9.973c-1.6,1.056-0.676,3.397-0.676,3.397c0.714,2.823,5.225,7.496,5.225,7.496l-0.006,2.098\n" +
                    "\tc-3.635,0.362-10.82,2.743-11.811,5.708V31.5l22.616-0.025c0.001-0.039,0.011-0.076,0.013-0.115\n" +
                    "\tC13.808,27.893,13.243,18.26,17.764,16.629z\"/>\n" +
                    "</svg>\n",
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<!-- Generator: Adobe Illustrator 17.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->\n" +
                    "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1 Basic//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11-basic.dtd\">\n" +
                    "<svg version=\"1.1\" baseProfile=\"basic\" id=\"图层_1\"\n" +
                    "\t xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"32px\" height=\"32px\"\n" +
                    "\t viewBox=\"0 0 32 32\" xml:space=\"preserve\">\n" +
                    "<path fill=\"#FFFFFF\" stroke=\"#6E6E6E\" stroke-width=\"2\" stroke-miterlimit=\"10\" d=\"M7.44,1.422c0,0,0.664-0.95,1.955-0.634\n" +
                    "\tc0,0,12.372-3.295,10.808,9.188c0,0,1.844,0.094,0.517,3.621c-1.058,2.813-3.571,5.768-5.285,7.333l-0.016,2.114\n" +
                    "\tc0,0,9.949,1.507,10.908,5.852v2.678L0.422,31.605V28.76c0.886-2.987,7.303-5.385,10.55-5.748l0.006-2.113\n" +
                    "\tc0,0-4.03-4.703-4.668-7.546c0,0-0.825-2.357,0.604-3.421c0,0-0.732-4.708,1.02-6.497C7.934,3.435,8.224,2.112,7.44,1.422z\"/>\n" +
                    "<g>\n" +
                    "\t<path fill=\"#FFFFFF\" stroke=\"#6E6E6E\" stroke-width=\"2\" stroke-miterlimit=\"10\" d=\"M21.819,21.015l0.014-1.879\n" +
                    "\t\tc1.524-1.391,3.759-4.018,4.699-6.519c1.179-3.135-0.46-3.219-0.46-3.219c1.228-9.805-7.211-8.66-9.194-8.261\n" +
                    "\t\tc2.365,1.062,4.238,3.511,3.571,8.839c0,0,1.844,0.094,0.517,3.621c-1.058,2.813-3.571,5.768-5.285,7.333l-0.016,2.114\n" +
                    "\t\tc0,0,9.497,1.441,10.824,5.562l5.027-0.006v-2.382C29.597,22.352,21.819,21.015,21.819,21.015z\"/>\n" +
                    "</g>\n" +
                    "</svg>\n",
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<!-- Generator: Adobe Illustrator 17.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->\n" +
                    "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1 Basic//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11-basic.dtd\">\n" +
                    "<svg version=\"1.1\" baseProfile=\"basic\" id=\"图层_1\"\n" +
                    "\t xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"32px\" height=\"32px\"\n" +
                    "\t viewBox=\"0 0 32 32\" xml:space=\"preserve\">\n" +
                    "<path fill=\"#FFFFFF\" stroke=\"#6E6E6E\" stroke-width=\"2\" stroke-miterlimit=\"10\" d=\"M9.029,1.896c0,0,0.775-0.922,2.283-0.615\n" +
                    "\tc0,0,14.446-3.197,12.621,8.914c0,0,2.153,0.092,0.604,3.513c-1.235,2.729-4.17,5.596-6.172,7.114l-0.018,2.051\n" +
                    "\tc0,0,11.617,1.462,12.737,5.678v2.599l-30.249,0.03V28.42c1.034-2.898,8.528-5.225,12.319-5.577l0.006-2.05\n" +
                    "\tc0,0-4.706-4.564-5.45-7.322c0,0-0.963-2.287,0.705-3.32c0,0-0.854-4.568,1.191-6.304C9.605,3.849,9.944,2.566,9.029,1.896z\"/>\n" +
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
