package android.support.v7.widget.helper;

/**
 * Created by Administrator on 2016/9/10.
 */
public class YolandaItemTouchHelper extends ItemTouchHelper {
    public YolandaItemTouchHelper(Callback callback) {
        super(callback);
    }

    public Callback getCallback(){
        return mCallback;
    }

}
