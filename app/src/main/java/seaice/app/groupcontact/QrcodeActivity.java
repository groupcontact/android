package seaice.app.groupcontact;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;

import butterknife.InjectView;
import seaice.app.groupcontact.utils.BitmapUtils;
import seaice.app.groupcontact.utils.CipherUtils;

public class QrcodeActivity extends BaseActivity {

    @InjectView(R.id.resultQRCode)
    ImageView mQrcodeView;

    private static final String QRCODE_FILE_PATH = Let.APP_DIR + "qrcode.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String content = "friend:" + Var.userAO.getName() + ":" + Var.userAO.getPhone() + ":" +
                System.currentTimeMillis();

        Bitmap bitMap = generateQRCode(CipherUtils.encrypt(content, Let.DEFAULT_KEY));
        mQrcodeView.setImageBitmap(bitMap);

        BitmapUtils.saveBitmapToFile(bitMap, QRCODE_FILE_PATH);

        mNavBarView.setRightItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(QRCODE_FILE_PATH)));
                shareIntent.setType("image/jpeg");
                startActivity(shareIntent);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected boolean needDagger() {
        return false;
    }

    private Bitmap bitMatrix2Bitmap(BitMatrix matrix) {
        int w = matrix.getWidth();
        int h = matrix.getHeight();
        int[] rawData = new int[w * h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int color = Color.WHITE;
                if (matrix.get(i, j)) {
                    color = Color.BLACK;
                }
                rawData[i + (j * w)] = color;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        bitmap.setPixels(rawData, 0, w, 0, 0, w, h);
        return bitmap;
    }

    private Bitmap generateQRCode(String content) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, 500, 500);
            return bitMatrix2Bitmap(matrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

}
